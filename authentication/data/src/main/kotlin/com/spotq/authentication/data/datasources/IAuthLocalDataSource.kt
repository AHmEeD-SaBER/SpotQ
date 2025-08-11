package com.spotq.authentication.data.datasources

import com.spotq.authentication.data.models.UserEntity
import kotlinx.coroutines.flow.Flow

interface IAuthLocalDataSource {
    suspend fun getUserByEmailAndPassword(email: String, password: String): Flow<UserEntity?>
    suspend fun getUserByEmail(email: String): Flow<UserEntity?>
    suspend fun insertUser(user: UserEntity)
    suspend fun isEmailExists(email: String): Flow<Boolean>
    suspend fun deleteAllUsers()
}