package com.example.data.repositories

import android.util.Log
import com.example.core_data.database.FavoritesDao
import com.example.core_data.database.PlacesDao
import com.example.core_data.database.UserDao
import com.example.core_data.database.UserFavoriteCrossRef
import com.example.data.utils.PlaceMapper
import com.example.domain.dto.PlaceDto
import com.example.domain.models.AddToFavoritesResponse
import com.example.domain.repositories.IAddToFavoritesRepositories
import javax.inject.Inject

class AddToFavoritesRepositories @Inject constructor(
    private val favoritesDao: FavoritesDao,
    private val placesDao: PlacesDao,
    private val userDao: UserDao
) : IAddToFavoritesRepositories {
    override suspend fun addToFavorites(place: PlaceDto, userId: Int): AddToFavoritesResponse {
        val placeEntity = PlaceMapper.dtoToEntity(place)
        placesDao.upsert(placeEntity)
        Log.d(
            "AddToFavoritesRepositories",
            "Place upserted: ${placeEntity.xid}, ${placeEntity.name}, $userId"
        )
        val user = userDao.getUserById(userId)
            ?: return AddToFavoritesResponse.Error(error = com.example.core_data.R.string.user_not_found)
        val result = favoritesDao.addFavorite(UserFavoriteCrossRef(userId, place.xid))
        return if (result >= 0) {
            AddToFavoritesResponse.Success(message = com.example.core_data.R.string.added_to_favorites)
        } else {
            AddToFavoritesResponse.Error(error = com.example.core_data.R.string.error_adding_to_favorites)
        }
    }

    override suspend fun removeFromFavorites(placeId: String, userId: Int): AddToFavoritesResponse {
        val result = favoritesDao.removeFavorite(userId, placeId)
        return if (result >= 0) {
            AddToFavoritesResponse.Success(message = com.example.core_data.R.string.removed_from_favorites)
        } else {
            AddToFavoritesResponse.Error(error = com.example.core_data.R.string.error_adding_to_favorites)
        }
    }

    override suspend fun isPlaceFavorite(placeId: String, userId: Int): Boolean {
        return favoritesDao.isFavorite(userId, placeId)
    }


}