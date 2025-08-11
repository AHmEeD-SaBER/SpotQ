package com.spotq.authentication.ui.utils

import android.util.Patterns

object ValidationUtils {

    data class ValidationResult(
        val isValid: Boolean,
        val errorMessage: String? = null
    )

    fun validateEmail(email: String): ValidationResult {
        return when {
            email.isBlank() -> ValidationResult(
                isValid = false,
                errorMessage = "Email is required"
            )

            !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> ValidationResult(
                isValid = false,
                errorMessage = "Please enter a valid email address"
            )

            else -> ValidationResult(isValid = true)
        }
    }

    fun validateName(name: String): ValidationResult {
        return when {
            name.isBlank() -> ValidationResult(
                isValid = false,
                errorMessage = "Name is required"
            )

            name.length < 2 -> ValidationResult(
                isValid = false,
                errorMessage = "Name must be at least 3 characters"
            )

            else -> ValidationResult(isValid = true)
        }
    }

    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isBlank() -> ValidationResult(
                isValid = false,
                errorMessage = "Password is required"
            )

            password.length < 6 -> ValidationResult(
                isValid = false,
                errorMessage = "Password must be at least 6 characters"
            )

            else -> ValidationResult(isValid = true)
        }
    }

    fun isLoginFormValid(email: String, password: String): Boolean {
        val emailValidation = validateEmail(email)
        val passwordValidation = validatePassword(password)
        return emailValidation.isValid && passwordValidation.isValid
    }

    fun isSignUpFormValid(name: String, email: String, password: String): Boolean {
        val nameValidation = validateName(name)
        val emailValidation = validateEmail(email)
        val passwordValidation = validatePassword(password)
        return nameValidation.isValid && emailValidation.isValid && passwordValidation.isValid
    }
}