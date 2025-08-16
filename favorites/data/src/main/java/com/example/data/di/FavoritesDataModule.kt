package com.example.data.di

import com.example.data.data_sources.FavoritesDataSource
import com.example.data.data_sources.IFavoritesDataSource
import com.example.data.repositories.FavoritesRepository
import com.example.domain.repositories.IFavoritesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class FavoritesDataModule {

    @Binds
    @Singleton
    abstract fun bindFavoritesDataSource(
        impl: FavoritesDataSource
    ): IFavoritesDataSource

    @Binds
    @Singleton
    abstract fun bindFavoritesRepository(
        impl: FavoritesRepository
    ): IFavoritesRepository


}