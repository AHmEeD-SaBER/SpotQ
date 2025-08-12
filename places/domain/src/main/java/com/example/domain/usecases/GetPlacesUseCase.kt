package com.example.domain.usecases

import android.util.Log
import com.example.domain.dto.PlaceDto
import com.example.domain.repositories.IPlacesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlacesUseCase @Inject constructor(private val placesRepository: IPlacesRepository) :
    IGetPlacesUseCase {
    override fun invoke(
        latitude: Double,
        longitude: Double,
        kinds: String?,
        radius: Int?,
        limit: Int?,
    ): Flow<Result<List<PlaceDto>>> {
        Log.d(
            "GetPlacesUseCase",
            "invoke called with latitude: $latitude, longitude: $longitude, kinds: $kinds, radius: $radius, limit: $limit"
        )
        return placesRepository.getPlaces(
            latitude = latitude,
            longitude = longitude,
            kinds = kinds,
            radius = radius,
            limit = limit,
        )
    }

}