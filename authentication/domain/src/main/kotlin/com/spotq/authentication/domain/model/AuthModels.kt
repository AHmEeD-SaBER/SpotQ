package com.spotq.authentication.domain.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String
)

data class AuthResponse(
    val user: User
)

data class User(
    val name: String,
    val email: String,
)

sealed class AuthResult<out T> {
    data class Success<T>(val data: T) : AuthResult<T>()
    data class Error(val exception: Throwable) : AuthResult<Nothing>()
    object Loading : AuthResult<Nothing>()
}
