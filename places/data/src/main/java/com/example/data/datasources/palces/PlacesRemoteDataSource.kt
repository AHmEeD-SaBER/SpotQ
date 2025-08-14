package com.example.data.datasources.palces

import com.example.core_data.network.PlacesApi
import com.example.core_data.network.models.place_details.PlaceDetailsResponse
import com.example.core_data.network.models.places.PlacesResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlacesRemoteDataSource @Inject constructor(
    private val placesApi: PlacesApi,
) :
    IPlacesRemoteDataSource {
    override suspend fun getPlaces(
        latitude: Double,
        longitude: Double,
        kinds: String?,
        radius: Int?,
        limit: Int?,
    ): Flow<PlacesResponse> {
        return kotlinx.coroutines.flow.flow {
            val response = placesApi.getPlaces(
                latitude = latitude,
                longitude = longitude,
                kinds = kinds,
                limit = limit,
                radius = radius,
            )
            emit(response)
        }
    }

    override suspend fun getPlaceDetails(xid: String): Flow<PlaceDetailsResponse> {
        return kotlinx.coroutines.flow.flow {
            val response = placesApi.getPlaceDetails(xid = xid)
            emit(response)
        }
    }


}