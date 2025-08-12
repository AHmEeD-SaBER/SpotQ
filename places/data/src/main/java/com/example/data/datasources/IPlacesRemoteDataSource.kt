package com.example.data.datasources

import kotlinx.coroutines.flow.Flow

interface IPlacesRemoteDataSource {
    suspend fun getPlaces(
        latitude: Double,
        longitude: Double,
        kinds: String?,
        radius: Int? = null,
        limit: Int? = null,
    ): Flow<com.example.core_data.network.models.places.PlacesResponse>

    suspend fun getPlaceDetails(
        xid: String
    ): Flow<com.example.core_data.network.models.place_details.PlaceDetailsResponse>
}