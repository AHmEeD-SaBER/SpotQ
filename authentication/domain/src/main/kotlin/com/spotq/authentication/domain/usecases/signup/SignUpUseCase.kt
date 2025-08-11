package com.spotq.authentication.domain.usecases.signup

import com.spotq.authentication.domain.model.AuthResult
import com.spotq.authentication.domain.model.AuthResponse
import com.spotq.authentication.domain.model.SignUpRequest
import com.spotq.authentication.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: IAuthRepository
) : ISignUpUseCase {
    override suspend operator fun invoke(signUpRequest: SignUpRequest): Flow<AuthResult<AuthResponse>> {
        return authRepository.signUp(signUpRequest)
    }
}
