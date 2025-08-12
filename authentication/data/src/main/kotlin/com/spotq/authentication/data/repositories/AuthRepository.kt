package com.spotq.authentication.data.repositories

import com.spotq.authentication.data.datasources.IAuthLocalDataSource
import com.spotq.authentication.data.models.UserEntity
import com.spotq.authentication.domain.model.AuthResponse
import com.spotq.authentication.domain.model.AuthResult
import com.spotq.authentication.domain.model.LoginRequest
import com.spotq.authentication.domain.model.SignUpRequest
import com.spotq.authentication.domain.model.User
import com.spotq.authentication.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.singleOrNull
import javax.inject.Inject

class AuthRepository @Inject constructor(private val localDataSource: IAuthLocalDataSource) :
    IAuthRepository {
    override suspend fun login(loginRequest: LoginRequest): Flow<AuthResult<AuthResponse>> {
        return kotlinx.coroutines.flow.flow {
            emit(AuthResult.Loading)
            try {
                val userEntity = localDataSource.getUserByEmailAndPassword(
                    loginRequest.email,
                    loginRequest.password
                ).singleOrNull()

                if (userEntity != null) {
                    val user = User(
                        name = userEntity.name,
                        email = userEntity.email,
                    )
                    val authResponse = AuthResponse(
                        user = user
                    )
                    emit(AuthResult.Success(authResponse))
                } else {
                    emit(AuthResult.Error(Exception("Invalid email or password")))
                }
            } catch (e: Exception) {
                emit(AuthResult.Error(e))
            }
        }
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): Flow<AuthResult<AuthResponse>> {
        return kotlinx.coroutines.flow.flow {
            emit(AuthResult.Loading)
            try {
                val userEntity = localDataSource.getUserByEmail(signUpRequest.email).singleOrNull()

                if (userEntity == null) {
                    val newUserEntity = UserEntity(
                        name = signUpRequest.name,
                        email = signUpRequest.email,
                        password = signUpRequest.password
                    )
                    localDataSource.insertUser(newUserEntity)

                    val user = User(
                        name = newUserEntity.name,
                        email = newUserEntity.email,
                    )
                    val authResponse = AuthResponse(
                        user = user
                    )
                    emit(AuthResult.Success(authResponse))
                } else {
                    emit(AuthResult.Error(Exception("Email already exists")))
                }
            } catch (e: Exception) {
                emit(AuthResult.Error(e))
            }
        }
    }

    override suspend fun isEmailExists(email: String): Flow<Boolean> {
        return kotlinx.coroutines.flow.flow {
            try {
                val userEntity = localDataSource.getUserByEmail(email).singleOrNull()
                emit(userEntity != null)
            } catch (e: Exception) {
                emit(false)
            }
        }
    }


}