package com.yourpackage.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import com.example.location_provider.R

class LocationProvider(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    /**
     * Data class to hold location information
     */
    data class LocationData(
        val latitude: Double,
        val longitude: Double,
    )

    /**
     * Sealed class for location results
     */
    sealed class LocationResult {
        data class Success(val location: LocationData) : LocationResult()
        data class Error(val errorMsgId : Int) : LocationResult()
        object PermissionDenied : LocationResult()
        object LocationDisabled : LocationResult()
    }

    /**
     * Check if location permissions are granted
     */
    fun hasLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Extension function to convert Location to LocationData
     */
    private fun Location.toLocationData(): LocationData {
        return LocationData(
            latitude = latitude,
            longitude = longitude,
        )
    }

    /**
     * Get the last known location (fast but might be outdated)
     */
    @SuppressLint("MissingPermission")
    suspend fun getLastLocation(): LocationResult = suspendCancellableCoroutine { continuation ->
        if (!hasLocationPermission()) {
            continuation.resume(LocationResult.PermissionDenied)
            return@suspendCancellableCoroutine
        }

        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    location?.let {
                        continuation.resume(
                            LocationResult.Success(location.toLocationData())
                        )
                    }
                        ?: continuation.resume(LocationResult.Error(R.string.location_disabled))
                }
                .addOnFailureListener {
                    continuation.resume(LocationResult.Error(R.string.location_error))
                }
        } catch (e: SecurityException) {
            continuation.resume(LocationResult.Error(R.string.location_permission_denied))
        }
    }

    /**
     * Get current location (fresh location)
     */
    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(
        priority: Int = Priority.PRIORITY_HIGH_ACCURACY
    ): LocationResult = suspendCancellableCoroutine { continuation ->
        if (!hasLocationPermission()) {
            continuation.resume(LocationResult.PermissionDenied)
            return@suspendCancellableCoroutine
        }

        try {
            val cancellationTokenSource = CancellationTokenSource()

            fusedLocationClient.getCurrentLocation(priority, cancellationTokenSource.token)
                .addOnSuccessListener { location ->
                    location?.let {
                        continuation.resume(
                            LocationResult.Success(location.toLocationData())
                        )
                    } ?: continuation.resume(LocationResult.LocationDisabled)
                }
                .addOnFailureListener {
                    continuation.resume(LocationResult.Error(R.string.location_error))
                }

            continuation.invokeOnCancellation {
                cancellationTokenSource.cancel()
            }
        } catch (e: SecurityException) {
            continuation.resume(LocationResult.Error(R.string.location_permission_denied))
        }
    }

    /**
     * Get continuous location updates
     */
    @SuppressLint("MissingPermission")
    fun getLocationUpdates(
        interval: Long = 10000L,
        fastestInterval: Long = 5000L,
        priority: Int = Priority.PRIORITY_HIGH_ACCURACY
    ): Flow<LocationResult> = callbackFlow {
        if (!hasLocationPermission()) {
            trySend(LocationResult.PermissionDenied)
            close()
            return@callbackFlow
        }

        val locationRequest = LocationRequest.Builder(priority, interval)
            .setMinUpdateIntervalMillis(fastestInterval)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                locationResult.locations.forEach { location ->
                    trySend(LocationResult.Success(location.toLocationData()))
                }
            }

            override fun onLocationAvailability(availability: LocationAvailability) {
                if (!availability.isLocationAvailable) {
                    trySend(LocationResult.LocationDisabled)
                }
            }
        }

        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            trySend(LocationResult.Error(R.string.location_permission_denied))
            close()
        }

        awaitClose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }
}