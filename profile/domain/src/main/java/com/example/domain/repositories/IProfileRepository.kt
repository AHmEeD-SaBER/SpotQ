package com.example.domain.repositories

import com.example.domain.models.UserModel
import kotlinx.coroutines.flow.Flow

interface IProfileRepository {
    fun getUserProfile(userId: Int): Flow<Result<UserModel?>>
}