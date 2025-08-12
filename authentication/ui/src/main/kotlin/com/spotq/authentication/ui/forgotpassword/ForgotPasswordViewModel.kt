package com.spotq.authentication.ui.forgotpassword

import androidx.lifecycle.viewModelScope
import com.example.core_ui.base.BaseViewModel
import com.spotq.authentication.domain.repository.IAuthRepository
import com.example.core_ui.R
import com.spotq.authentication.ui.utils.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val authRepository: IAuthRepository) :
    BaseViewModel<ForgotPasswordContract.Event, ForgotPasswordContract.State, ForgotPasswordContract.Effect>() {

    override fun setInitialState(): ForgotPasswordContract.State {
        return ForgotPasswordContract.State()
    }

    override fun handleEvent(event: ForgotPasswordContract.Event) {
        when (event) {
            is ForgotPasswordContract.Event.EmailChanged -> {
                handleEmailChanged(event.email)
            }

            is ForgotPasswordContract.Event.SubmitClicked -> {
                handleSubmitClicked()
            }

            is ForgotPasswordContract.Event.ClearErrors -> {
                handleClearErrors()
            }


        }
    }

    private fun handleNavigateToLogin() {
        setEffect { ForgotPasswordContract.Effect.NavigateToLogin }
    }

    private fun handleEmailChanged(email: String) {
        val emailValidation = ValidationUtils.validateEmail(email)

        setState {
            copy(
                email = email,
                emailError = if (email.isNotBlank() && !emailValidation.isValid) {
                    emailValidation.errorMessage
                } else null,
                isFormValid = emailValidation.isValid
            )
        }
    }

    private fun handleSubmitClicked() {
        val emailValidation = ValidationUtils.validateEmail(uiState.value.email).isValid
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            if (emailValidation) {
                authRepository.isEmailExists(uiState.value.email).collect { emailExists ->
                    if (emailExists) {
                        delay(500)
                        setEffect { ForgotPasswordContract.Effect.ShowSuccess(R.string.forgot_password_success) }
                        handleNavigateToLogin()
                    } else {
                        setEffect { ForgotPasswordContract.Effect.ShowError(R.string.forgot_password_email_not_exist) }
                    }
                }
            } else {
                setEffect { ForgotPasswordContract.Effect.ShowError(R.string.forgot_password_invalid_form) }
            }
        }
    }

    private fun handleClearErrors() {
        setState { copy(emailError = null) }
    }


}