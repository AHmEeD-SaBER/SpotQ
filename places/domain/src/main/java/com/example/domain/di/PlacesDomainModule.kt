package com.example.domain.di

import com.example.domain.usecases.GetPlacesUseCase
import com.example.domain.usecases.IGetPlacesUseCase
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


}