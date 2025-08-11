package com.spotq.authentication.ui.signup

import androidx.lifecycle.viewModelScope
import com.example.core_ui.base.BaseViewModel
import com.spotq.authentication.domain.model.AuthResult
import com.spotq.authentication.domain.model.SignUpRequest
import com.spotq.authentication.domain.usecases.signup.ISignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signUpUseCase: ISignUpUseCase
) : BaseViewModel<SignupContract.Event, SignupContract.State, SignupContract.Effect>() {

    override fun setInitialState(): SignupContract.State {
        return SignupContract.State()
    }

    override fun handleEvent(event: SignupContract.Event) {
        when (event) {
            is SignupContract.Event.NameChanged -> {
                handleNameChanged(event.name)
            }
            is SignupContract.Event.EmailChanged -> {
                handleEmailChanged(event.email)
            }
            is SignupContract.Event.PasswordChanged -> {
                handlePasswordChanged(event.password)
            }
            is SignupContract.Event.TogglePasswordVisibility -> {
                handleTogglePasswordVisibility()
            }
            is SignupContract.Event.SignupClicked -> {
                handleSignupClicked()
            }
            is SignupContract.Event.NavigateToLogin -> {
                handleNavigateToLogin()
            }
            is SignupContract.Event.ClearErrors -> {
                handleClearErrors()
            }
        }
    }

    private fun handleNameChanged(name: String) {
        setState {
            copy(
                name = name,
                nameError = null,
                isFormValid = validateForm(name, email, password)
            )
        }
    }

    private fun handleEmailChanged(email: String) {
        setState {
            copy(
                email = email,
                emailError = null,
                isFormValid = validateForm(name, email, password)
            )
        }
    }

    private fun handlePasswordChanged(password: String) {
        setState {
            copy(
                password = password,
                passwordError = null,
                isFormValid = validateForm(name, email, password)
            )
        }
    }

    private fun handleTogglePasswordVisibility() {
        setState {
            copy(isPasswordVisible = !isPasswordVisible)
        }
    }

    private fun handleSignupClicked() {
        if (!validateAndSetErrors()) return

        setState { copy(isLoading = true) }

        viewModelScope.launch {
            try {
                signUpUseCase(
                    SignUpRequest(
                        name = uiState.value.name.trim(),
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

                            setEffect { SignupContract.Effect.ShowSuccess("Account created successfully!") }
                            setEffect { SignupContract.Effect.NavigateToMain }
                        }
                        is AuthResult.Error -> {
                            setState { copy(isLoading = false) }
                            setEffect {
                                SignupContract.Effect.ShowError(
                                    result.exception.message ?: "Signup failed. Please try again."
                                )
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                setState { copy(isLoading = false) }
                setEffect {
                    SignupContract.Effect.ShowError(
                        e.message ?: "An unexpected error occurred"
                    )
                }
            }
        }
    }

    private fun handleNavigateToLogin() {
        setEffect { SignupContract.Effect.NavigateToLogin }
    }

    private fun handleClearErrors() {
        setState {
            copy(
                nameError = null,
                emailError = null,
                passwordError = null
            )
        }
    }

    private fun validateForm(
        name: String,
        email: String,
        password: String
    ): Boolean {
        return name.isNotBlank() &&
               email.isNotBlank() &&
               android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
               password.isNotBlank() &&
               password.length >= 6
    }

    private fun validateAndSetErrors(): Boolean {
        val currentState = uiState.value
        var isValid = true

        val nameError = when {
            currentState.name.isBlank() -> {
                isValid = false
                "Name is required"
            }
            currentState.name.length < 2 -> {
                isValid = false
                "Name must be at least 2 characters"
            }
            else -> null
        }

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
                nameError = nameError,
                emailError = emailError,
                passwordError = passwordError
            )
        }

        return isValid
    }
}