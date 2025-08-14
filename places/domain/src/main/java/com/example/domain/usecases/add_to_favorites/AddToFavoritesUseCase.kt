package com.example.domain.usecases.add_to_favorites

import com.example.domain.dto.PlaceDto
import com.example.domain.models.AddToFavoritesResponse
import com.example.domain.repositories.IAddToFavoritesRepositories
import javax.inject.Inject

class AddToFavoritesUseCase @Inject constructor(private val repo: IAddToFavoritesRepositories) :
    IAddToFavoritesUseCase {
    override suspend fun invoke(placeDto: PlaceDto, userId: Int): AddToFavoritesResponse {
        return repo.addToFavorites(placeDto, userId)
    }

}