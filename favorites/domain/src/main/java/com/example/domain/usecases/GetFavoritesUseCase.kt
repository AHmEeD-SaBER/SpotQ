package com.example.domain.usecases

import com.example.core_domain.dto.PlaceDto
import com.example.domain.repositories.IFavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val repository: IFavoritesRepository) :
    IGetFavoritesUseCase {
    override operator fun invoke(userId: Int): Flow<Result<List<PlaceDto>>> {
        return repository.getFavorites(userId)
    }
}