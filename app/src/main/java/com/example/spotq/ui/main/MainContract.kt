package com.example.spotq.ui.main

import com.example.core_ui.base.*

class MainContract {

    // UI State
    data class State(
        val isLoading: Boolean = true,
        val isUserAuthenticated: Boolean = false,
        val shouldShowOnboarding: Boolean = false,
        val currentDestination: Destination = Destination.SPLASH
    ) : UiState

    // UI Events
    sealed class Event : UiEvent {
        object SplashFinished : Event()
        object CheckInitialState : Event()
        object OnboardingCompleted : Event()
        object AuthenticationCompleted : Event()
        object NavigateToAuth : Event()
        object NavigateToMain : Event()
        object Logout : Event()
    }

    // UI Effects
    sealed class Effect : UiEffect {
        object NavigateToOnboarding : Effect()
        object NavigateToAuth : Effect()
        object NavigateToMainScreen : Effect()
    }

    // Destinations
    enum class Destination {
        SPLASH,
        LOADING,
        ONBOARDING,
        AUTH,
        MAIN
    }
}
