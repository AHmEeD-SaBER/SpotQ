package com.example.data.repositories

import android.content.SharedPreferences
import com.example.core_data.database.UserDao
import com.example.domain.models.UserModel
import com.example.domain.repositories.IProfileRepository
import com.example.errors.CustomError
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val dao: UserDao,
    private val sharedPreferences: SharedPreferences
) : IProfileRepository {
    override fun getUserProfile(userId: Int) = flow {
        val user = dao.getUserById(userId)
        if (user != null) {
            emit(Result.success(UserModel(name = user.name, email = user.email)))
        } else {
            emit(Result.failure(CustomError.Unknown()))
        }
    }


}