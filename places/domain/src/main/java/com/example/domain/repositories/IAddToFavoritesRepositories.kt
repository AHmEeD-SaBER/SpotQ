package com.example.domain.repositories

import com.example.domain.dto.PlaceDto
import com.example.domain.models.AddToFavoritesResponse

interface IAddToFavoritesRepositories {
    suspend fun addToFavorites(place: PlaceDto, userId: Int) : AddToFavoritesResponse
    suspend fun removeFromFavorites(placeId: String, userId: Int) : AddToFavoritesResponse
    suspend fun isPlaceFavorite(placeId: String, userId: Int): Boolean
}