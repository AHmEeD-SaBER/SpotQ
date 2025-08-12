package com.example.domain.repositories

import com.example.domain.dto.PlaceDto
import kotlinx.coroutines.flow.Flow

interface IPlacesRepository {
    fun getPlaces(
        latitude: Double,
        longitude: Double,
        kinds: String? = null,
        radius: Int? = null,
        limit: Int? = null,
    ): Flow<Result<List<PlaceDto>>>

}