package com.example.data.di

import com.example.data.datasources.IPlacesRemoteDataSource
import com.example.data.datasources.PlacesRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class PlacesDataModule {
    @Binds
    @Singleton
    abstract fun bindPlacesRemoteDataSource(
        placesRemoteDataSource: PlacesRemoteDataSource
    ): IPlacesRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindPlacesRepository(
        placesRepository: com.example.data.repositories.PlacesRepository
    ): com.example.domain.repositories.IPlacesRepository
}