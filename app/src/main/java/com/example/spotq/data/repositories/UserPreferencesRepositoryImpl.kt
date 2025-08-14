package com.example.spotq.data.repositories

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit
import com.example.core_ui.utils.Constants.KEY_FIRST_TIME_LAUNCH
import com.example.core_ui.utils.Constants.KEY_USER_AUTHENTICATED
import com.example.core_ui.utils.Constants.KEY_USER_EMAIL
import com.example.core_ui.utils.Constants.KEY_USER_ID
import com.example.core_ui.utils.Constants.KEY_USER_NAME
import com.example.spotq.domain.repositories.UserPreferencesRepository

@Singleton
class UserPreferencesRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : UserPreferencesRepository {

    override suspend fun isFirstTimeLaunch(): Boolean = withContext(Dispatchers.IO) {
        sharedPreferences.getBoolean(KEY_FIRST_TIME_LAUNCH, true)
    }

    override suspend fun setFirstTimeLaunch(isFirstTime: Boolean) = withContext(Dispatchers.IO) {
        sharedPreferences.edit { putBoolean(KEY_FIRST_TIME_LAUNCH, isFirstTime) }
    }

    override suspend fun isUserAuthenticated(): Boolean = withContext(Dispatchers.IO) {
        sharedPreferences.getBoolean(KEY_USER_AUTHENTICATED, false)
    }

    override suspend fun setUserAuthenticated(isAuthenticated: Boolean) =
        withContext(Dispatchers.IO) {
            sharedPreferences.edit { putBoolean(KEY_USER_AUTHENTICATED, isAuthenticated) }
        }

    override suspend fun resetAllPreferences() = withContext(Dispatchers.IO) {
        sharedPreferences.edit { clear() }
    }

    override suspend fun saveUserData(name: String, email: String) = withContext(Dispatchers.IO) {
        sharedPreferences.edit {
            putString(KEY_USER_NAME, name)
            putString(KEY_USER_EMAIL, email)
            putBoolean(KEY_USER_AUTHENTICATED, true)
        }
    }

    override suspend fun getUserName(): String? = withContext(Dispatchers.IO) {
        sharedPreferences.getString(KEY_USER_NAME, null)
    }

    override suspend fun getUserEmail(): String? = withContext(Dispatchers.IO) {
        sharedPreferences.getString(KEY_USER_EMAIL, null)
    }

    override suspend fun clearUserData() = withContext(Dispatchers.IO) {
        sharedPreferences.edit {
            remove(KEY_USER_NAME)
            remove(KEY_USER_EMAIL)
            putBoolean(KEY_USER_AUTHENTICATED, false)
        }
    }

    override suspend fun setUserId(userId: Int) = withContext(Dispatchers.IO) {
        sharedPreferences.edit { putInt(KEY_USER_ID, userId) }
    }

    override suspend fun getUserId(): Int? = withContext(Dispatchers.IO) {
        if (sharedPreferences.contains(KEY_USER_ID)) sharedPreferences.getInt(
            KEY_USER_ID,
            -1
        ) else null
    }
}
