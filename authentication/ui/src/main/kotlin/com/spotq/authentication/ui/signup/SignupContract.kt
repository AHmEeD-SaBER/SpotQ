package com.spotq.authentication.ui.signup

import com.example.core_ui.base.*

class SignupContract {

    data class State(
        val isLoading: Boolean = false,
        val name: String = "",
        val email: String = "",
        val password: String = "",
        val nameError: String? = null,
        val emailError: String? = null,
        val passwordError: String? = null,
        val isNameValid: Boolean = true,
        val isFormValid: Boolean = false,
        val isPasswordVisible: Boolean = false
    ) : UiState

    sealed class Event : UiEvent {
        data class NameChanged(val name: String) : Event()
        data class EmailChanged(val email: String) : Event()
        data class PasswordChanged(val password: String) : Event()
        object TogglePasswordVisibility : Event()
        object SignupClicked : Event()
        object NavigateToLogin : Event()
        object ClearErrors : Event()
    }

    sealed class Effect : UiEffect {
        object NavigateToMain : Effect()
        object NavigateToLogin : Effect()
        data class ShowError(val messageRes: Int) : Effect()
        data class ShowSuccess(val messageRes: Int) : Effect()
    }
}