package com.example.data.repositories

import android.util.Log
import com.example.errors.CustomError
import com.example.data.datasources.palces.IPlacesRemoteDataSource
import com.example.domain.dto.PlaceDto
import com.example.domain.repositories.IPlacesRepository
import com.example.core_data.utils.INetworkMonitor
import com.example.data.utils.PlaceMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlacesRepository @Inject constructor(
    private val dataSource: IPlacesRemoteDataSource,
    private val networkMonitor: INetworkMonitor
) : IPlacesRepository {

    override fun getPlaces(
        latitude: Double,
        longitude: Double,
        kinds: String?,
        radius: Int?,
        limit: Int?,
    ): Flow<Result<List<PlaceDto>>> = flow {
        if (!networkMonitor.isNetworkAvailable()) {
            emit(Result.failure(CustomError.NoNetwork()))
            return@flow
        }

        val placesWithDetails = mutableListOf<PlaceDto>()

        // Step 1: Get places list
        dataSource.getPlaces(
            latitude = latitude,
            longitude = longitude,
            kinds = kinds,
            radius = radius,
            limit = limit,
        ).collect { placeList ->
            for (place in placeList) {
                if (!place.xid.isNullOrEmpty()) {
                    try {
                        dataSource.getPlaceDetails(place.xid!!).collect { placeDetails ->
                            val placeDto = PlaceMapper.mapToPlaceDto(
                                place = place,
                                placeDetails = placeDetails,
                                userLatitude = latitude,
                                userLongitude = longitude
                            )
                            placesWithDetails.add(placeDto)
                        }
                    } catch (_: Exception) {

                    }
                }
            }
        }

        val sortedPlaces = placesWithDetails.sortedByDescending { it.distance }

        Log.d("PlacesRepository", "Sorted places: $sortedPlaces")
        if (sortedPlaces.isEmpty()) {
            emit(Result.failure(CustomError.NoData()))
        } else {
            emit(Result.success(sortedPlaces))
        }
    }.catch { e ->
        emit(Result.failure(e))
    }

}
