package com.example.domain.usecases.is_favorite

import com.example.domain.repositories.IAddToFavoritesRepositories
import javax.inject.Inject

class IsFavoriteUseCaseImpl @Inject constructor(private val repo: IAddToFavoritesRepositories) : IsFavoriteUseCase {
    override suspend fun invoke(placeId: String, userId: Int): Boolean {
        return repo.isPlaceFavorite(placeId, userId)
    }
}