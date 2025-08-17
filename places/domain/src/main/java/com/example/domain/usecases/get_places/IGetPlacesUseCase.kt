package com.example.domain.usecases.get_places

import com.example.core_domain.dto.PlaceDto
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