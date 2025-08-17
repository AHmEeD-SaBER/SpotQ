package com.example.domain.usecases

import com.example.domain.models.UserModel
import com.example.domain.repositories.IProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(private val repository: IProfileRepository) :
    IGetUserProfileUseCase {
    override fun invoke(userInt: Int): Flow<Result<UserModel?>> {
        return repository.getUserProfile(userInt)
    }
}