package com.example.domain.di

import com.example.domain.usecases.GetFavoritesUseCase
import com.example.domain.usecases.IGetFavoritesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class FavoritesDomainModule {

    @Binds
    @ViewModelScoped
    abstract fun bindGetFavoritesUseCase(
        impl: GetFavoritesUseCase
    ): IGetFavoritesUseCase


}