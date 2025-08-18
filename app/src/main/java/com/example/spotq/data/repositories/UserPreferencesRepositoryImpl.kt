package com.example.spotq.data.repositories

import android.content.SharedPreferences
import javax.inject.Inject
import androidx.core.content.edit
import com.example.core_ui.utils.Constants.KEY_FIRST_TIME_LAUNCH
import com.example.core_ui.utils.Constants.KEY_USER_AUTHENTICATED
import com.example.core_ui.utils.Constants.KEY_USER_EMAIL
import com.example.core_ui.utils.Constants.KEY_USER_ID
import com.example.core_ui.utils.Constants.KEY_USER_NAME
import com.example.spotq.domain.repositories.UserPreferencesRepository

class UserPreferencesRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : UserPreferencesRepository {

    override suspend fun isFirstTimeLaunch(): Boolean {
        return sharedPreferences.getBoolean(KEY_FIRST_TIME_LAUNCH, true)
    }

    override suspend fun setFirstTimeLaunch(isFirstTime: Boolean) {
        sharedPreferences.edit { putBoolean(KEY_FIRST_TIME_LAUNCH, isFirstTime) }
    }

    override suspend fun isUserAuthenticated(): Boolean {
        return sharedPreferences.getBoolean(KEY_USER_AUTHENTICATED, false)
    }

    override suspend fun setUserAuthenticated(isAuthenticated: Boolean) {
        sharedPreferences.edit { putBoolean(KEY_USER_AUTHENTICATED, isAuthenticated) }
    }

    override suspend fun resetAllPreferences() {
        sharedPreferences.edit { clear() }
    }

    override suspend fun saveUserData(name: String, email: String) {
        sharedPreferences.edit {
            putString(KEY_USER_NAME, name)
            putString(KEY_USER_EMAIL, email)
            putBoolean(KEY_USER_AUTHENTICATED, true)
        }
    }

    override suspend fun getUserName(): String? {
        return sharedPreferences.getString(KEY_USER_NAME, null)
    }

    override suspend fun getUserEmail(): String? {
        return sharedPreferences.getString(KEY_USER_EMAIL, null)
    }

    override suspend fun setUserId(userId: Int) {
        sharedPreferences.edit { putInt(KEY_USER_ID, userId) }
    }

    override suspend fun getUserId(): Int? {
        return if (sharedPreferences.contains(KEY_USER_ID)) {
            sharedPreferences.getInt(KEY_USER_ID, -1).takeIf { it != -1 }
        } else null
    }
}