package com.example.data.utils

import kotlin.math.*

object DistanceCalculator {

    fun calculateDistance(
        userLat: Double,
        userLon: Double,
        placeLat: Double,
        placeLon: Double
    ): Double {
        val earthRadius = 6371.0 // Earth radius in KILOMETERS (changed from 6371000.0)

        val dLat = Math.toRadians(placeLat - userLat)
        val dLon = Math.toRadians(placeLon - userLon)

        val a = sin(dLat / 2).pow(2) +
                cos(Math.toRadians(userLat)) * cos(Math.toRadians(placeLat)) *
                sin(dLon / 2).pow(2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return earthRadius * c // Distance in KILOMETERS
    }

    fun formatDistance(distanceInKm: Double): String {
        return when {
            distanceInKm < 1.0 -> "${(distanceInKm * 1000).toInt()} m" // Show meters if less than 1km
            else -> "${"%.1f".format(distanceInKm)} km"
        }
    }
}