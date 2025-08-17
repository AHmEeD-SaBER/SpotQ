package com.example.data.data_sources

import com.example.core_data.database.PlaceEntity
import kotlinx.coroutines.flow.Flow

interface IFavoritesDataSource {
    fun getFavorites(userId: Int): Flow<List<PlaceEntity>>
}