package com.example.data.repositories

import com.example.core_domain.dto.PlaceDto
import com.example.data.data_sources.IFavoritesDataSource
import com.example.data.utils.PlaceDtoMapper
import com.example.domain.repositories.IFavoritesRepository
import com.example.errors.CustomError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepository @Inject constructor(
    private val dataSource: IFavoritesDataSource
) : IFavoritesRepository {
    override fun getFavorites(userId: Int): Flow<Result<List<PlaceDto>>> = flow {
        dataSource.getFavorites(userId).map { placeEntities ->
            val places = placeEntities.map { PlaceDtoMapper.entityToDto(it) }
            if (places.isEmpty()) {
                return@map Result.failure(CustomError.NoData(subtitle = com.example.errors.R.string.error_no_fav_data_subtitle))
            }
            Result.success(places)
        }.collect { result ->
            emit(result)
        }
    }

}