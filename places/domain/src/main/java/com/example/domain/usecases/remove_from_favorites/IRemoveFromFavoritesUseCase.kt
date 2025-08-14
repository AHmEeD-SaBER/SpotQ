package com.example.domain.usecases.remove_from_favorites

import com.example.domain.models.AddToFavoritesResponse

interface IRemoveFromFavoritesUseCase {
    suspend operator fun invoke(placeId: String, userId: Int): AddToFavoritesResponse
}