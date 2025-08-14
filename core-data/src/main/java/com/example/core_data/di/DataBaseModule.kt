package com.example.core_data.di

import android.content.Context
import androidx.room.Room
import com.example.core_data.database.AppDataBase
import com.example.core_data.database.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDataBase =
        Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            AppDataBase.DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideUserDao(database: AppDataBase): UserDao = database.userDao()

    @Provides
    @Singleton
    fun providePlacesDao(database: AppDataBase) = database.placesDao()

    @Provides
    @Singleton
    fun provideFavoritesDao(database: AppDataBase) = database.favoritesDao()
}