package com.example.domain.usecases

import com.example.domain.models.UserModel
import kotlinx.coroutines.flow.Flow

interface IGetUserProfileUseCase {
    operator fun invoke(userInt: Int): Flow<Result<UserModel?>>
}