package com.example.data.di

import com.example.data.repositories.ProfileRepository
import com.example.domain.repositories.IProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class ProfileDataModule {

    @Binds
    @Singleton
    abstract fun bindsProfileRepository(
        impl: ProfileRepository
    ): IProfileRepository
}