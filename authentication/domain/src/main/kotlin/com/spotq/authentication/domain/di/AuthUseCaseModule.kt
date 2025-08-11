package com.spotq.authentication.domain.di

import com.spotq.authentication.domain.usecases.login.ILoginUseCase
import com.spotq.authentication.domain.usecases.login.LoginUseCase
import com.spotq.authentication.domain.usecases.signup.ISignUpUseCase
import com.spotq.authentication.domain.usecases.signup.SignUpUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthUseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun bindLoginUseCase(
        loginUseCase: LoginUseCase
    ): ILoginUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindSignUpUseCase(
        signUpUseCase: SignUpUseCase
    ): ISignUpUseCase
}
