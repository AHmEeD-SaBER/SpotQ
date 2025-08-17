package com.example.ui

import com.example.core_ui.base.UiEffect
import com.example.core_ui.base.UiEvent
import com.example.core_ui.base.UiState

class ProfileContract {
    data class State(
        val userName: String = "",
        val userEmail: String = "",
        val isLoading: Boolean = false,
        val error: Int? = null
    ) : UiState

    sealed class Event : UiEvent {
        object LoadProfile : Event()
        object Logout : Event()
    }

    sealed class Effect : UiEffect {
        data class ShowError(val message: Int) : Effect()
        object NavigateToLogin : Effect()
    }
}