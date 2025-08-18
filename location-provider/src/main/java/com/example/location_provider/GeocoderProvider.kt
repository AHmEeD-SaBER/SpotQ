package com.example.location_provider

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume

class GeocoderProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : IGeocoderProvider {

    private val geocoder = if (Geocoder.isPresent()) {
        Geocoder(context, Locale.getDefault())
    } else null

    override suspend fun getLocationName(latitude: Double, longitude: Double): String? {
        return try {
            if (geocoder == null) return null

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                suspendCancellableCoroutine { continuation ->
                    geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
                        val locationName = addresses.firstOrNull()?.let { address ->
                            formatLocationName(address)
                        }
                        continuation.resume(locationName)
                    }
                }
            } else {
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                addresses?.firstOrNull()?.let { address ->
                    formatLocationName(address)
                }
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun formatLocationName(address: Address): String {
        return buildString {
            address.locality?.let { append(it) }
            address.countryName?.let { country ->
                if (isNotEmpty()) append(", ")
                append(country)
            }
            if (isEmpty()) {
                address.subAdminArea?.let { append(it) }
            }
        }
    }
}
