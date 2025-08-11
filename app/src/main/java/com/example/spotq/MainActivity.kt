package com.example.spotq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.core_ui.theme.SpotQTheme
import com.example.spotq.navigation.AppNavigation
import com.example.spotq.navigation.Screen
import com.example.spotq.ui.main.MainContract
import com.example.spotq.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpotQTheme {
                val navController = rememberNavController()
                val mainViewModel: MainViewModel = hiltViewModel()
                val mainState by mainViewModel.uiState.collectAsState()

                // Handle main app navigation based on MainViewModel state
                LaunchedEffect(mainState.currentDestination) {
                    when (mainState.currentDestination) {
                        MainContract.Destination.ONBOARDING -> {
                            navController.navigate(Screen.Onboarding) {
                                popUpTo<Screen.Splash> { inclusive = true }
                            }
                        }
                        MainContract.Destination.AUTH -> {
                            navController.navigate(Screen.Login) {
                                popUpTo<Screen.Splash> { inclusive = true }
                            }
                        }
                        MainContract.Destination.MAIN -> {
                            navController.navigate(Screen.Main) {
                                popUpTo<Screen.Splash> { inclusive = true }
                            }
                        }
                        else -> {
                            // Stay on splash or loading
                        }
                    }
                }

                // Handle MainViewModel effects
                LaunchedEffect(mainViewModel) {
                    mainViewModel.effect.collect { effect ->
                        when (effect) {
                            is MainContract.Effect.NavigateToOnboarding -> {
                                navController.navigate(Screen.Onboarding) {
                                    popUpTo<Screen.Splash> { inclusive = true }
                                }
                            }
                            is MainContract.Effect.NavigateToAuth -> {
                                navController.navigate(Screen.Login) {
                                    popUpTo<Screen.Splash> { inclusive = true }
                                }
                            }
                            is MainContract.Effect.NavigateToMainScreen -> {
                                navController.navigate(Screen.Main) {
                                    popUpTo<Screen.Splash> { inclusive = true }
                                }
                            }
                        }
                    }
                }

                // Initialize the app state
                LaunchedEffect(Unit) {
                    mainViewModel.setEvent(MainContract.Event.CheckInitialState)
                }

                AppNavigation(
                    navController = navController,
                    onAuthenticationCompleted = {
                        mainViewModel.setEvent(MainContract.Event.AuthenticationCompleted)
                    }
                )
            }
        }
    }
}
