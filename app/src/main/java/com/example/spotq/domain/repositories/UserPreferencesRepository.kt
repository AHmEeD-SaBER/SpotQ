package com.example.spotq.domain.repositories

interface UserPreferencesRepository {
    suspend fun isFirstTimeLaunch(): Boolean
    suspend fun setFirstTimeLaunch(isFirstTime: Boolean)
    suspend fun isUserAuthenticated(): Boolean
    suspend fun setUserAuthenticated(isAuthenticated: Boolean)
    suspend fun resetAllPreferences()

    // User data methods
    suspend fun saveUserData(name: String, email: String)
    suspend fun getUserName(): String?
    suspend fun getUserEmail(): String?
    suspend fun clearUserData()
}
