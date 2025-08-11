package com.example.spotq.di

import android.content.Context
import android.content.SharedPreferences
import com.example.spotq.domain.repositories.UserPreferencesRepository
import com.example.spotq.data.repositories.UserPreferencesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences("spotq_preferences", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(
        sharedPreferences: SharedPreferences
    ): UserPreferencesRepository {
        return UserPreferencesRepositoryImpl(sharedPreferences)
    }
}
