package com.example.spotq.ui.main

import com.example.core_ui.base.*

class MainContract {

    data class State(
        val isLoading: Boolean = true,
        val isUserAuthenticated: Boolean = false,
        val shouldShowOnboarding: Boolean = false,
        val currentDestination: Destination = Destination.SPLASH,
        val userId: Int? = null,
    ) : UiState

    sealed class Event : UiEvent {
        object SplashFinished : Event()
        object CheckInitialState : Event()
        object OnboardingCompleted : Event()
        data class AuthenticationCompleted(val userId: Int) : Event()
        object NavigateToAuth : Event()
        object NavigateToMain : Event()
    }

    sealed class Effect : UiEffect {
    }

    enum class Destination {
        SPLASH,
        LOADING,
        ONBOARDING,
        AUTH,
        MAIN
    }
}
