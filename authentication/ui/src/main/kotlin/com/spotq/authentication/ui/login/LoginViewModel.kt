package com.spotq.authentication.ui.login

import androidx.lifecycle.viewModelScope
import com.example.core_ui.base.BaseViewModel
import com.spotq.authentication.domain.model.AuthResult
import com.spotq.authentication.domain.model.LoginRequest
import com.spotq.authentication.domain.usecases.login.ILoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: ILoginUseCase
) : BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>() {

    override fun setInitialState(): LoginContract.State {
        return LoginContract.State()
    }

    override fun handleEvent(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.EmailChanged -> {
                handleEmailChanged(event.email)
            }
            is LoginContract.Event.PasswordChanged -> {
                handlePasswordChanged(event.password)
            }
            is LoginContract.Event.TogglePasswordVisibility -> {
                handleTogglePasswordVisibility()
            }
            is LoginContract.Event.LoginClicked -> {
                handleLoginClicked()
            }
            is LoginContract.Event.NavigateToSignup -> {
                handleNavigateToSignup()
            }
            is LoginContract.Event.ForgotPasswordClicked -> {
                handleForgotPasswordClicked()
            }
            is LoginContract.Event.ClearErrors -> {
                handleClearErrors()
            }
        }
    }

    private fun handleEmailChanged(email: String) {
        setState {
            copy(
                email = email,
                emailError = null,
                isFormValid = validateForm(email, password)
            )
        }
    }

    private fun handlePasswordChanged(password: String) {
        setState {
            copy(
                password = password,
                passwordError = null,
                isFormValid = validateForm(email, password)
            )
        }
    }

    private fun handleTogglePasswordVisibility() {
        setState {
            copy(isPasswordVisible = !isPasswordVisible)
        }
    }

    private fun handleLoginClicked() {
        if (!validateAndSetErrors()) return

        setState { copy(isLoading = true) }

        viewModelScope.launch {
            try {
                loginUseCase(
                    LoginRequest(
                        email = uiState.value.email.trim(),
                        password = uiState.value.password
                    )
                ).collect { result ->
                    when (result) {
                        is AuthResult.Loading -> {
                            setState { copy(isLoading = true) }
                        }
                        is AuthResult.Success -> {
                            setState { copy(isLoading = false) }

                            setEffect { LoginContract.Effect.ShowSuccess("Login successful!") }
                            setEffect { LoginContract.Effect.NavigateToMain }
                        }
                        is AuthResult.Error -> {
                            setState { copy(isLoading = false) }
                            setEffect {
                                LoginContract.Effect.ShowError(
                                    result.exception.message ?: "Login failed. Please try again."
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                setState { copy(isLoading = false) }
                setEffect {
                    LoginContract.Effect.ShowError(
                        e.message ?: "An unexpected error occurred"
                    )
                }
            }
        }
    }

    private fun handleNavigateToSignup() {
        setEffect { LoginContract.Effect.NavigateToSignup }
    }

    private fun handleForgotPasswordClicked() {
        setEffect { LoginContract.Effect.NavigateToForgotPassword }
    }

    private fun handleClearErrors() {
        setState {
            copy(
                emailError = null,
                passwordError = null
            )
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        return email.isNotBlank() &&
               android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
               password.isNotBlank() &&
               password.length >= 6
    }

    private fun validateAndSetErrors(): Boolean {
        val currentState = uiState.value
        var isValid = true

        val emailError = when {
            currentState.email.isBlank() -> {
                isValid = false
                "Email is required"
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(currentState.email).matches() -> {
                isValid = false
                "Please enter a valid email address"
            }
            else -> null
        }

        val passwordError = when {
            currentState.password.isBlank() -> {
                isValid = false
                "Password is required"
            }
            currentState.password.length < 6 -> {
                isValid = false
                "Password must be at least 6 characters"
            }
            else -> null
        }

        setState {
            copy(
                emailError = emailError,
                passwordError = passwordError
            )
        }

        return isValid
    }
}