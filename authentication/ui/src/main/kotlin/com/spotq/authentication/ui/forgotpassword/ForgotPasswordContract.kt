package com.spotq.authentication.ui.forgotpassword

import com.example.core_ui.base.UiEffect
import com.example.core_ui.base.UiEvent
import com.example.core_ui.base.UiState

class ForgotPasswordContract{
    data class State(
        val isLoading: Boolean = false,
        val email: String = "",
        val emailError: String? = null,
        val isFormValid: Boolean = false
    ) : UiState

    sealed class Event : UiEvent {
        data class EmailChanged(val email: String) : Event()
        data object SubmitClicked : Event()
        data object ClearErrors : Event()
    }

    sealed class Effect : UiEffect {
        data class ShowError(val messageRes: Int) : Effect()
        data object NavigateToLogin : Effect()
        data class ShowSuccess(val messageRes: Int) : Effect()
        data object None : Effect()
    }
}