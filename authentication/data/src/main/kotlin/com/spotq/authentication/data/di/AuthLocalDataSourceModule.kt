package com.spotq.authentication.data.di

import com.spotq.authentication.data.datasources.AuthLocalDataSource
import com.spotq.authentication.data.datasources.IAuthLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AuthLocalDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindAuthLocalDataSource(
        impl: AuthLocalDataSource
    ): IAuthLocalDataSource
}