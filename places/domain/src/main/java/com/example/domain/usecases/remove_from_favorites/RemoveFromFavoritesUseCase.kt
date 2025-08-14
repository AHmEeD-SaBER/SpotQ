package com.example.domain.usecases.remove_from_favorites

import com.example.domain.models.AddToFavoritesResponse
import com.example.domain.repositories.IAddToFavoritesRepositories
import javax.inject.Inject

class RemoveFromFavoritesUseCase @Inject constructor(private val repo: IAddToFavoritesRepositories) :
    IRemoveFromFavoritesUseCase {
    override suspend fun invoke(placeId: String, userId: Int): AddToFavoritesResponse {
        return repo.removeFromFavorites(placeId, userId)
    }
}