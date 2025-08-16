package com.example.domain.usecases

import com.example.core_domain.dto.PlaceDto
import kotlinx.coroutines.flow.Flow

interface IGetFavoritesUseCase {
    operator fun invoke(userId: Int): Flow<Result<List<PlaceDto>>>
}