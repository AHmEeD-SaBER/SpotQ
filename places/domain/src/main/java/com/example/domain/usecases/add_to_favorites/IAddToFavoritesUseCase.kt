package com.example.domain.usecases.add_to_favorites

import com.example.domain.dto.PlaceDto
import com.example.domain.models.AddToFavoritesResponse

interface IAddToFavoritesUseCase {
    suspend operator fun invoke(placeDto: PlaceDto, userId: Int): AddToFavoritesResponse

}