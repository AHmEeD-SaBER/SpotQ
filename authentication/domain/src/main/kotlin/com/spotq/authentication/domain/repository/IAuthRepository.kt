package com.spotq.authentication.domain.repository

import com.spotq.authentication.domain.model.AuthResult
import com.spotq.authentication.domain.model.AuthResponse
import com.spotq.authentication.domain.model.LoginRequest
import com.spotq.authentication.domain.model.SignUpRequest
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    suspend fun login(loginRequest: LoginRequest): Flow<AuthResult<AuthResponse>>
    suspend fun signUp(signUpRequest: SignUpRequest): Flow<AuthResult<AuthResponse>>
}
