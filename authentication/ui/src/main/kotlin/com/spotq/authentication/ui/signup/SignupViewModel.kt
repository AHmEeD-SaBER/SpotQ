package com.spotq.authentication.ui.signup

import androidx.lifecycle.viewModelScope
import com.example.core_ui.base.BaseViewModel
import com.spotq.authentication.domain.model.AuthResult
import com.spotq.authentication.domain.model.SignUpRequest
import com.spotq.authentication.domain.usecases.signup.ISignUpUseCase
import com.spotq.authentication.ui.utils.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.core_ui.R

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
        val nameValidation = ValidationUtils.validateName(name)
        setState {
            copy(
                name = name,
                nameError = if (nameValidation.isValid) null else nameValidation.errorMessage,
                isFormValid = ValidationUtils.isSignUpFormValid(name, email, password)
            )
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
                isFormValid = ValidationUtils.isSignUpFormValid(name, email, password)
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
                isFormValid = ValidationUtils.isSignUpFormValid(name, email, password)
            )
        }
    }

    private fun handleTogglePasswordVisibility() {
        val currentState = uiState.value
        setState {
            copy(isPasswordVisible = !currentState.isPasswordVisible)
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
                            setEffect { SignupContract.Effect.ShowSuccess(R.string.signup_success) }
                            val userId = result.data.user.id
                            setEffect { SignupContract.Effect.NavigateToMain(userId) }
                        }

                        is AuthResult.Error -> {
                            setState { copy(isLoading = false) }
                            setEffect {
                                SignupContract.Effect.ShowError(R.string.signup_failed)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                setState { copy(isLoading = false) }
                setEffect {
                    SignupContract.Effect.ShowError(R.string.unexpected_error)
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

    private fun validateAndSetErrors(): Boolean {
        val currentState = uiState.value
        val emailValidation = ValidationUtils.validateEmail(currentState.email)
        val passwordValidation = ValidationUtils.validatePassword(currentState.password)
        val nameValidation = ValidationUtils.validateName(currentState.name)

        setState {
            copy(
                nameError = if (!nameValidation.isValid) nameValidation.errorMessage else null,
                emailError = if (!emailValidation.isValid) emailValidation.errorMessage else null,
                passwordError = if (!passwordValidation.isValid) passwordValidation.errorMessage else null,
                isFormValid = emailValidation.isValid && passwordValidation.isValid
            )
        }

        return currentState.isFormValid &&
                nameValidation.isValid &&
                emailValidation.isValid &&
                passwordValidation.isValid
    }

}