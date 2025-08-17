package com.example.location_provider

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class LocationProvider @Inject constructor(@ApplicationContext private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    data class LocationData(
        val latitude: Double,
        val longitude: Double,
    )

    sealed class LocationResult {
        data class Success(val location: LocationData) : LocationResult()
        data class Error(val errorMsgId : Int) : LocationResult()
        object PermissionDenied : LocationResult()
        object LocationDisabled : LocationResult()
    }

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


    private fun Location.toLocationData(): LocationData {
        return LocationData(
            latitude = latitude,
            longitude = longitude,
        )
    }



    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(
        priority: Int = Priority.PRIORITY_HIGH_ACCURACY
    ): LocationResult = suspendCancellableCoroutine { continuation ->
        if (!hasLocationPermission()) {
            continuation.resume(LocationResult.PermissionDenied)
            return@suspendCancellableCoroutine
        }

        val cancellationTokenSource = CancellationTokenSource()

        try {
            fusedLocationClient.getCurrentLocation(priority, cancellationTokenSource.token)
                .addOnSuccessListener { location ->
                    if (location != null) {
                        continuation.resume(LocationResult.Success(location.toLocationData()))
                    } else {
                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { lastLocation ->
                                if (lastLocation != null) {
                                    continuation.resume(LocationResult.Success(lastLocation.toLocationData()))
                                } else {
                                    continuation.resume(LocationResult.LocationDisabled)
                                }
                            }
                            .addOnFailureListener {
                                continuation.resume(LocationResult.Error(R.string.location_error))
                            }
                    }
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
}