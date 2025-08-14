package com.example.domain.di

import com.example.domain.usecases.get_places.GetPlacesUseCase
import com.example.domain.usecases.IGetPlacesUseCase
import com.example.domain.usecases.add_to_favorites.AddToFavoritesUseCase
import com.example.domain.usecases.add_to_favorites.IAddToFavoritesUseCase
import com.example.domain.usecases.is_favorite.IsFavoriteUseCase
import com.example.domain.usecases.is_favorite.IsFavoriteUseCaseImpl
import com.example.domain.usecases.remove_from_favorites.IRemoveFromFavoritesUseCase
import com.example.domain.usecases.remove_from_favorites.RemoveFromFavoritesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class PlacesDomainModule {

    @Binds
    @ViewModelScoped
    abstract fun bindGetPlacesUseCase(
        impl: GetPlacesUseCase
    ): IGetPlacesUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindAddToFavoritesUseCase(
        impl: AddToFavoritesUseCase
    ): IAddToFavoritesUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindRemoveFromFavoritesUseCase(
        impl: RemoveFromFavoritesUseCase
    ): IRemoveFromFavoritesUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindIsFavoriteUseCase(
        impl: IsFavoriteUseCaseImpl
    ): IsFavoriteUseCase


}