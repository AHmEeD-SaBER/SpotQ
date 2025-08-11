package com.example.spotq.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.splash.SplashScreen
import com.spotq.authentication.ui.login.LoginContract
import com.spotq.authentication.ui.login.LoginScreen
import com.spotq.authentication.ui.login.LoginViewModel
import com.spotq.authentication.ui.signup.SignupContract
import com.spotq.authentication.ui.signup.SignupScreen
import com.spotq.authentication.ui.signup.SignupViewModel
import com.spotq.onboarding.OnboardingScreen
import kotlinx.serialization.Serializable

// Sealed class for type-safe navigation
@Serializable
sealed class Screen {
    @Serializable
    data object Splash : Screen()

    @Serializable
    data object Onboarding : Screen()

    @Serializable
    data object Login : Screen()

    @Serializable
    data object Signup : Screen()

    @Serializable
    data object Main : Screen()
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    onAuthenticationCompleted: () -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash
    ) {
        composable<Screen.Splash> {
            SplashScreen(
                onSplashFinished = {
                    // This will be handled by MainActivity through MainViewModel
                }
            )
        }

        composable<Screen.Onboarding> {
            OnboardingScreen(
                onOnboardingComplete = {
                    navController.navigate(Screen.Login) {
                        popUpTo<Screen.Onboarding> { inclusive = true }
                    }
                }
            )
        }

        composable<Screen.Login> {
            val loginViewModel: LoginViewModel = hiltViewModel()
            val state by loginViewModel.uiState.collectAsState()

            LoginScreen(
                state = state,
                onEvent = loginViewModel::setEvent
            )

            // Handle login effects
            LaunchedEffect(loginViewModel) {
                loginViewModel.effect.collect { effect ->
                    when (effect) {
                        is LoginContract.Effect.NavigateToMain -> {
                            onAuthenticationCompleted() // Notify MainActivity
                            navController.navigate(Screen.Main) {
                                popUpTo<Screen.Login> { inclusive = true }
                            }
                        }
                        is LoginContract.Effect.NavigateToSignup -> {
                            navController.navigate(Screen.Signup)
                        }
                        is LoginContract.Effect.NavigateToForgotPassword -> {
                            // Handle forgot password navigation
                        }
                        is LoginContract.Effect.ShowError -> {
                            // Handle error display (e.g., show snackbar)
                        }
                        is LoginContract.Effect.ShowSuccess -> {
                            // Handle success message
                        }
                    }
                }
            }
        }

        composable<Screen.Signup> {
            val signupViewModel: SignupViewModel = hiltViewModel()
            val state by signupViewModel.uiState.collectAsState()

            SignupScreen(
                state = state,
                onEvent = signupViewModel::setEvent
            )

            // Handle signup effects
            LaunchedEffect(signupViewModel) {
                signupViewModel.effect.collect { effect ->
                    when (effect) {
                        is SignupContract.Effect.NavigateToMain -> {
                            onAuthenticationCompleted() // Notify MainActivity
                            navController.navigate(Screen.Main) {
                                popUpTo<Screen.Signup> { inclusive = true }
                            }
                        }
                        is SignupContract.Effect.NavigateToLogin -> {
                            navController.popBackStack()
                        }
                        is SignupContract.Effect.ShowError -> {
                            // Handle error display
                        }
                        is SignupContract.Effect.ShowSuccess -> {
                            // Handle success message
                        }
                    }
                }
            }
        }

        composable<Screen.Main> {
            // Simple main screen placeholder - replace with your actual main screen
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Welcome to SpotQ!",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Main Screen - Authentication Successful",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
