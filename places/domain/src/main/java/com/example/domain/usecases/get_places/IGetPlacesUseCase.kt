package com.example.domain.usecases

import com.example.domain.dto.PlaceDto
import kotlinx.coroutines.flow.Flow

interface IGetPlacesUseCase {
    operator fun invoke(
        latitude: Double,
        longitude: Double,
        kinds: String?,
        radius: Int?,
        limit: Int?,
    ): Flow<Result<List<PlaceDto>>>
}