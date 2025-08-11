package com.spotq.authentication.domain.usecases.signup

import com.spotq.authentication.domain.model.AuthResult
import com.spotq.authentication.domain.model.AuthResponse
import com.spotq.authentication.domain.model.SignUpRequest
import kotlinx.coroutines.flow.Flow

interface ISignUpUseCase {
    suspend operator fun invoke(signUpRequest: SignUpRequest): Flow<AuthResult<AuthResponse>>
}
