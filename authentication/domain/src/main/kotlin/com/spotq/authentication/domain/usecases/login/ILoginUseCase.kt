package com.spotq.authentication.domain.usecases.login

import com.spotq.authentication.domain.model.AuthResult
import com.spotq.authentication.domain.model.AuthResponse
import com.spotq.authentication.domain.model.LoginRequest
import kotlinx.coroutines.flow.Flow

interface ILoginUseCase {
    suspend operator fun invoke(loginRequest: LoginRequest): Flow<AuthResult<AuthResponse>>
}
