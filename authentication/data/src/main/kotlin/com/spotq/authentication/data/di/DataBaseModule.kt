package com.spotq.authentication.data.di

import android.content.Context
import androidx.room.Room
import com.spotq.authentication.data.local.database.AuthDatabase
import com.spotq.authentication.data.local.dao.UserDao
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
    fun provideDatabase(@ApplicationContext appContext: Context): AuthDatabase =
        Room.databaseBuilder(
            appContext,
            AuthDatabase::class.java,
            AuthDatabase.DATABASE_NAME
        ).build()

    @Provides
    fun provideUserDao(database: AuthDatabase): UserDao = database.userDao()
}