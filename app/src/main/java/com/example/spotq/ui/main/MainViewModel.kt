package com.example.spotq.ui.main

import androidx.lifecycle.viewModelScope
import com.example.spotq.domain.repositories.UserPreferencesRepository
import com.example.core_ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : BaseViewModel<MainContract.Event, MainContract.State, MainContract.Effect>() {

    override fun setInitialState(): MainContract.State {
        return MainContract.State()
    }

    override fun handleEvent(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.SplashFinished -> {
                handleSplashFinished()
            }

            is MainContract.Event.CheckInitialState -> {
                checkInitialState()
            }

            is MainContract.Event.OnboardingCompleted -> {
                handleOnboardingCompleted()
            }

            is MainContract.Event.AuthenticationCompleted -> {
                handleAuthenticationCompleted()
            }

            is MainContract.Event.NavigateToAuth -> {
                navigateToAuth()
            }

            is MainContract.Event.NavigateToMain -> {
                navigateToMain()
            }

            is MainContract.Event.Logout -> {
                handleLogout()
            }
        }
    }

    private fun handleSplashFinished() {
        setState {
            copy(currentDestination = MainContract.Destination.LOADING)
        }
        checkInitialState()
    }

    private fun checkInitialState() {
        viewModelScope.launch {
            val isFirstTimeLaunch = userPreferencesRepository.isFirstTimeLaunch()
            // TODO: Add auth check through use case if needed
            val isUserAuthenticated = false // Simplified for now

            when {
                isFirstTimeLaunch -> {
                    setState {
                        copy(
                            isLoading = false,
                            shouldShowOnboarding = true,
                            currentDestination = MainContract.Destination.ONBOARDING
                        )
                    }
                    setEffect { MainContract.Effect.NavigateToOnboarding }
                }

                !isUserAuthenticated -> {
                    setState {
                        copy(
                            isLoading = false,
                            isUserAuthenticated = false,
                            currentDestination = MainContract.Destination.AUTH
                        )
                    }
                    setEffect { MainContract.Effect.NavigateToAuth }
                }

                else -> {
                    setState {
                        copy(
                            isLoading = false,
                            isUserAuthenticated = true,
                            currentDestination = MainContract.Destination.MAIN
                        )
                    }
                    setEffect { MainContract.Effect.NavigateToMainScreen }
                }
            }
        }
    }

    private fun handleOnboardingCompleted() {
        viewModelScope.launch {
            userPreferencesRepository.setFirstTimeLaunch(false)
            setState {
                copy(
                    shouldShowOnboarding = false,
                    currentDestination = MainContract.Destination.AUTH
                )
            }
            setEffect { MainContract.Effect.NavigateToAuth }
        }
    }

    private fun handleAuthenticationCompleted() {
        viewModelScope.launch {
            // No need to manually set auth status - it's already handled by auth module
            setState {
                copy(
                    isUserAuthenticated = true,
                    currentDestination = MainContract.Destination.MAIN
                )
            }
            setEffect { MainContract.Effect.NavigateToMainScreen }
        }
    }

    private fun handleLogout() {
        viewModelScope.launch {
            // TODO: Add logout through use case if needed
            setState {
                copy(
                    isUserAuthenticated = false,
                    currentDestination = MainContract.Destination.AUTH
                )
            }
            setEffect { MainContract.Effect.NavigateToAuth }
        }
    }

    private fun navigateToAuth() {
        setState {
            copy(currentDestination = MainContract.Destination.AUTH)
        }
        setEffect { MainContract.Effect.NavigateToAuth }
    }

    private fun navigateToMain() {
        setState {
            copy(currentDestination = MainContract.Destination.MAIN)
        }
        setEffect { MainContract.Effect.NavigateToMainScreen }
    }
}
