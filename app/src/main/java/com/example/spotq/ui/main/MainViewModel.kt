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
//    init {
//        viewModelScope.launch {
//            userPreferencesRepository.resetAllPreferences()
//        }
//    }

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
                handleAuthenticationCompleted(event.userId)
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
            val isUserAuthenticated = userPreferencesRepository.isUserAuthenticated()
            val savedUserId = userPreferencesRepository.getUserId()

            when {
                isFirstTimeLaunch -> {
                    setState {
                        copy(
                            isLoading = false,
                            shouldShowOnboarding = true,
                            currentDestination = MainContract.Destination.ONBOARDING
                        )
                    }
                }

                isUserAuthenticated -> {
                    setState {
                        copy(
                            isLoading = false,
                            isUserAuthenticated = true,
                            userId = savedUserId,
                            currentDestination = MainContract.Destination.MAIN
                        )
                    }
                }

                else -> {
                    setState {
                        copy(
                            isLoading = false,
                            isUserAuthenticated = false,
                            currentDestination = MainContract.Destination.AUTH
                        )
                    }
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
        }
    }

    private fun handleAuthenticationCompleted(userId: Int) {
        viewModelScope.launch {
            userPreferencesRepository.setUserAuthenticated(true)
            userPreferencesRepository.setUserId(userId)
            setState {
                copy(
                    userId = userId,
                    isUserAuthenticated = true,
                    currentDestination = MainContract.Destination.MAIN
                )
            }
        }
    }

    private fun handleLogout() {
        viewModelScope.launch {
            setState {
                copy(
                    isUserAuthenticated = false,
                    currentDestination = MainContract.Destination.AUTH
                )
            }
        }
    }

    private fun navigateToAuth() {
        setState {
            copy(currentDestination = MainContract.Destination.AUTH)
        }
    }

    private fun navigateToMain() {
        setState {
            copy(currentDestination = MainContract.Destination.MAIN)
        }
    }
}
