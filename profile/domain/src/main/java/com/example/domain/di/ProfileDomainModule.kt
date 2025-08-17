package com.example.domain.di

import com.example.domain.usecases.GetUserProfileUseCase
import com.example.domain.usecases.IGetUserProfileUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class ProfileDomainModule {

    @Binds
    @ViewModelScoped
    abstract fun bindsGetUserProfileUseCase(
        impl: GetUserProfileUseCase
    ): IGetUserProfileUseCase
}