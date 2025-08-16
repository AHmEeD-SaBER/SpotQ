package com.example.domain.repositories

import com.example.core_domain.dto.PlaceDto
import kotlinx.coroutines.flow.Flow

interface IFavoritesRepository {
    fun getFavorites(userId: Int): Flow<Result<List<PlaceDto>>>
}