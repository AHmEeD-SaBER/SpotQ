package com.spotq.authentication.ui.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.core_ui.base.BaseViewModel
import com.spotq.authentication.domain.model.AuthResult
import com.spotq.authentication.domain.model.AuthResponse
import com.spotq.authentication.domain.model.LoginRequest
import com.spotq.authentication.domain.usecases.login.ILoginUseCase
import com.spotq.authentication.ui.utils.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.core_ui.R

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
        val emailValidation = ValidationUtils.validateEmail(email)

        setState {
            copy(
                email = email,
                emailError = if (email.isNotBlank() && !emailValidation.isValid) {
                    emailValidation.errorMessage
                } else null,
                isFormValid = ValidationUtils.isLoginFormValid(email, password)
            )
        }
    }

    private fun handlePasswordChanged(password: String) {
        val passwordValidation = ValidationUtils.validatePassword(password)

        setState {
            copy(
                password = password,
                passwordError = if (password.isNotBlank() && !passwordValidation.isValid) {
                    passwordValidation.errorMessage
                } else null,
                isFormValid = ValidationUtils.isLoginFormValid(email, password)
            )
        }
    }

    private fun handleTogglePasswordVisibility() {
        Log.d("LoginViewModel", "Before toggle: ${uiState.value.isPasswordVisible}")
        setState {
            copy(isPasswordVisible = !this.isPasswordVisible)
        }
        Log.d("LoginViewModel", "After toggle: ${uiState.value.isPasswordVisible}")
    }

    private fun handleLoginClicked() {
        if (!validateFormAndShowErrors()) {
            return
        }

        setState { copy(isLoading = true) }

        viewModelScope.launch {
            try {
                loginUseCase(
                    LoginRequest(
                        email = uiState.value.email.trim(),
                        password = uiState.value.password
                    )
                ).collect { result ->
                    handleAuthResult(result)
                }
            } catch (e: Exception) {
                setState { copy(isLoading = false) }
                setEffect {
                    LoginContract.Effect.ShowError(R.string.unexpected_error)
                }
            }
        }
    }

    private fun handleAuthResult(result: AuthResult<AuthResponse>) {
        when (result) {
            is AuthResult.Loading -> {
                setState { copy(isLoading = true) }
                Log.d("LoginViewModel", "Loading state")
            }
            is AuthResult.Success -> {
                setState { copy(isLoading = false) }
                setEffect { LoginContract.Effect.ShowSuccess(R.string.login_success) }
                val userId = result.data.user.id
                Log.d("LoginViewModel", "User ID: $userId")
                setEffect { LoginContract.Effect.NavigateToMain(userId) }
            }
            is AuthResult.Error -> {
                setState { copy(isLoading = false) }
                setEffect {
                    LoginContract.Effect.ShowError(R.string.login_failed)
                }
                Log.e("LoginViewModel", "Login failed: ")
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


    private fun validateFormAndShowErrors(): Boolean {
        val currentState = uiState.value

        val emailValidation = ValidationUtils.validateEmail(currentState.email)
        val passwordValidation = ValidationUtils.validatePassword(currentState.password)

        setState {
            copy(
                emailError = if (!emailValidation.isValid) emailValidation.errorMessage else null,
                passwordError = if (!passwordValidation.isValid) passwordValidation.errorMessage else null,
                isFormValid = emailValidation.isValid && passwordValidation.isValid
            )
        }

        return emailValidation.isValid && passwordValidation.isValid
    }
}