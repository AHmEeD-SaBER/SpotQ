package com.example.domain.usecases.is_favorite

interface IsFavoriteUseCase {
    suspend operator fun invoke(placeId: String, userId: Int): Boolean
}