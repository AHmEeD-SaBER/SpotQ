package com.spotq.authentication.ui.login

import com.example.core_ui.base.*

class LoginContract {

    // UI State
    data class State(
        val isLoading: Boolean = false,
        val email: String = "",
        val password: String = "",
        val emailError: String? = null,
        val passwordError: String? = null,
        val isFormValid: Boolean = false,
        val isPasswordVisible: Boolean = false
    ) : UiState

    // UI Events
    sealed class Event : UiEvent {
        data class EmailChanged(val email: String) : Event()
        data class PasswordChanged(val password: String) : Event()
        object TogglePasswordVisibility : Event()
        object LoginClicked : Event()
        object NavigateToSignup : Event()
        object ForgotPasswordClicked : Event()
        object ClearErrors : Event()
    }

    // UI Effects
    sealed class Effect : UiEffect {
        object NavigateToMain : Effect()
        object NavigateToSignup : Effect()
        object NavigateToForgotPassword : Effect()
        data class ShowError(val message: String) : Effect()
        data class ShowSuccess(val message: String) : Effect()
    }
}