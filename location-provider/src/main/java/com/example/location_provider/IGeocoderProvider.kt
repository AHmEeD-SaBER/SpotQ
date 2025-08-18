package com.example.location_provider

interface IGeocoderProvider {
    suspend fun getLocationName(latitude: Double, longitude: Double): String?
}