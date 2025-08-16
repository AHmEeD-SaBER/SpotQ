package com.spotq.authentication.data.datasources

import com.example.core_data.database.UserDao
import com.example.core_data.database.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthLocalDataSource @Inject constructor(
    private val userDao: UserDao
) : IAuthLocalDataSource {

    override suspend fun getUserByEmailAndPassword(
        email: String,
        password: String
    ): Flow<UserEntity?> {
        return flow {
            val user = userDao.getUserByEmailAndPassword(email, password)
            emit(user)
        }
    }

    override suspend fun getUserByEmail(email: String): Flow<UserEntity?> {
        return flow {
            val user = userDao.getUserByEmail(email)
            emit(user)
        }
    }

    override suspend fun insertUser(user: UserEntity): Flow<Long> {
        return flow {
            val id = userDao.insertUser(user)
            emit(id)
        }
    }


    override suspend fun isEmailExists(email: String): Flow<Boolean> {
        return flow {
            val count = userDao.isEmailExists(email)
            emit(count > 0)
        }
    }

    override suspend fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }

}
