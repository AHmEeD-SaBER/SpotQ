package com.example.data.data_sources

import com.example.core_data.database.FavoritesDao
import com.example.core_data.database.PlaceEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoritesDataSource @Inject constructor(private val dao: FavoritesDao) :
    IFavoritesDataSource {
    override fun getFavorites(userId: Int): Flow<List<PlaceEntity>> {
        return flow {
            val favorites = dao.getFavoritesForUser(userId)
            emit(favorites)
        }
    }

}