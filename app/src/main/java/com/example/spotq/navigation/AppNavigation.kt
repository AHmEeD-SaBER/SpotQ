package com.example.spotq.navigation

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.splash.SplashScreen
import com.example.spotq.ui.main.MainContract
import com.example.spotq.ui.main.MainViewModel
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
    mainState: MainContract.State,
    onSplashFinished: () -> Unit = {},
    onAuthComplete: () -> Unit = {},
    onOnboardingComplete: () -> Unit = {},
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash
    ) {


        composable<Screen.Splash> {
            SplashScreen(
                onSplashFinished = onSplashFinished

            )
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
                        // Stay on splash while loading/determining destination
                    }
                }
            }
        }

        composable<Screen.Onboarding> {
            OnboardingScreen(
                onOnboardingComplete = {
                    onOnboardingComplete()
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
                onEvent = loginViewModel::handleEvent,
                modifier = Modifier,
            )

            // Handle login effects
            val context = LocalContext.current
            LaunchedEffect(loginViewModel) {
                loginViewModel.effect.collect { effect ->
                    when (effect) {
                        is LoginContract.Effect.NavigateToMain -> {
                            onAuthComplete()
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
                            Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                        }

                        is LoginContract.Effect.ShowSuccess -> {
                            // Handle success message
                        }

                        LoginContract.Effect.None -> TODO()
                    }
                }
            }
        }

        composable<Screen.Signup> {
            val signupViewModel: SignupViewModel = hiltViewModel()
            val state by signupViewModel.uiState.collectAsState()

            SignupScreen(
                state = state,
                onEvent = signupViewModel::handleEvent
            )

            // Handle signup effects
            val context = LocalContext.current
            LaunchedEffect(signupViewModel) {
                signupViewModel.effect.collect { effect ->
                    when (effect) {
                        is SignupContract.Effect.NavigateToMain -> {
                            onAuthComplete()
                            navController.navigate(Screen.Main) {
                                popUpTo<Screen.Signup> { inclusive = true }
                            }
                        }

                        is SignupContract.Effect.NavigateToLogin -> {
                            navController.navigate(Screen.Login)
                        }

                        is SignupContract.Effect.ShowError -> {
                            Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
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
