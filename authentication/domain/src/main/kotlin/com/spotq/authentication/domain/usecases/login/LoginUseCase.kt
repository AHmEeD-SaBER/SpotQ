package com.spotq.authentication.domain.usecases.login

import com.spotq.authentication.domain.model.AuthResult
import com.spotq.authentication.domain.model.AuthResponse
import com.spotq.authentication.domain.model.LoginRequest
import com.spotq.authentication.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: IAuthRepository
) : ILoginUseCase {
    override suspend operator fun invoke(loginRequest: LoginRequest): Flow<AuthResult<AuthResponse>> {
        return authRepository.login(loginRequest)
    }
}
