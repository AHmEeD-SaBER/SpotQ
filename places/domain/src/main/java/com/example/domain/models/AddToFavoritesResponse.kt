package com.example.domain.models

sealed class AddToFavoritesResponse{
    data class Success(val message: Int) : AddToFavoritesResponse()
    data class Error(val error: Int) : AddToFavoritesResponse()
}
