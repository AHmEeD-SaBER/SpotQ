package com.example.domain.repositories

import com.example.domain.dto.PlaceDto

interface IFavoritesRepository {
    fun addToFavorites(place: PlaceDto)
    fun removeFromFavorites(place: PlaceDto)
    fun isFavorite(place: PlaceDto): Boolean
}