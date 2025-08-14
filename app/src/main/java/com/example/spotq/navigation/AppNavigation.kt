package com.example.spotq.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.domain.dto.PlaceDto
import com.example.ui.places.PlacesScreen
import com.example.splash.SplashScreen
import com.example.spotq.ui.main.MainContract
import com.example.ui.place_details.PlaceDetailsContract
import com.example.ui.place_details.PlaceDetailsScreen
import com.example.ui.place_details.PlaceDetailsViewModel
import com.example.ui.places.PlacesContract
import com.example.ui.places.PlacesViewModel
import com.spotq.authentication.ui.forgotpassword.ForgotPasswordContract
import com.spotq.authentication.ui.forgotpassword.ForgotPasswordScreen
import com.spotq.authentication.ui.forgotpassword.ForgotPasswordViewModel
import com.spotq.authentication.ui.login.LoginContract
import com.spotq.authentication.ui.login.LoginScreen
import com.spotq.authentication.ui.login.LoginViewModel
import com.spotq.authentication.ui.signup.SignupContract
import com.spotq.authentication.ui.signup.SignupScreen
import com.spotq.authentication.ui.signup.SignupViewModel
import com.spotq.onboarding.OnboardingScreen
import kotlinx.serialization.Serializable

// Sealed class for type-safe navigation (except PlaceDetails)
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
    data object Places : Screen()

    @Serializable
    data object ForgotPassword : Screen()
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    mainState: MainContract.State,
    onSplashFinished: () -> Unit = {},
    onAuthComplete: (userId: Int) -> Unit = {},
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
                        navController.navigate(Screen.Places) {
                            popUpTo<Screen.Splash> { inclusive = true }
                        }
                    }

                    else -> {
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

            val context = LocalContext.current
            LaunchedEffect(loginViewModel) {
                loginViewModel.effect.collect { effect ->
                    when (effect) {
                        is LoginContract.Effect.NavigateToMain -> {
                            onAuthComplete(effect.userId)
                            navController.navigate(Screen.Places) {
                                popUpTo<Screen.Login> { inclusive = true }
                            }
                        }

                        is LoginContract.Effect.NavigateToSignup -> {
                            navController.navigate(Screen.Signup) {
                                popUpTo<Screen.Login> { inclusive = false }
                            }
                        }

                        is LoginContract.Effect.NavigateToForgotPassword -> {
                            navController.navigate(Screen.ForgotPassword) {
                                popUpTo<Screen.Login> { inclusive = false }
                            }
                        }

                        is LoginContract.Effect.ShowError -> {
                            Toast.makeText(
                                context,
                                context.getString(effect.messageRes),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is LoginContract.Effect.ShowSuccess -> {
                            Toast.makeText(
                                context,
                                context.getString(effect.messageRes),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        LoginContract.Effect.None -> TODO()
                    }
                }
            }
        }

        composable<Screen.ForgotPassword> {
            val forgotPasswordViewModel: ForgotPasswordViewModel = hiltViewModel()
            val state by forgotPasswordViewModel.uiState.collectAsState()
            ForgotPasswordScreen(
                state = state,
                onEvent = forgotPasswordViewModel::handleEvent
            )
            val context = LocalContext.current
            LaunchedEffect(forgotPasswordViewModel) {
                forgotPasswordViewModel.effect.collect { effect ->
                    when (effect) {
                        ForgotPasswordContract.Effect.NavigateToLogin -> {
                            navController.navigate(Screen.Login)
                        }

                        ForgotPasswordContract.Effect.None -> TODO()
                        is ForgotPasswordContract.Effect.ShowError -> {
                            Toast.makeText(
                                context,
                                context.getString(effect.messageRes),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is ForgotPasswordContract.Effect.ShowSuccess -> {
                            Toast.makeText(
                                context,
                                context.getString(effect.messageRes),
                                Toast.LENGTH_SHORT
                            ).show()
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
                onEvent = signupViewModel::handleEvent
            )

            val context = LocalContext.current
            LaunchedEffect(signupViewModel) {
                signupViewModel.effect.collect { effect ->
                    when (effect) {
                        is SignupContract.Effect.NavigateToMain -> {
                            onAuthComplete(effect.userId)
                            navController.navigate(Screen.Places) {
                                popUpTo<Screen.Signup> { inclusive = true }
                            }
                        }

                        is SignupContract.Effect.NavigateToLogin -> {
                            navController.popBackStack()
                        }

                        is SignupContract.Effect.ShowError -> {
                            Toast.makeText(
                                context,
                                context.getString(effect.messageRes),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is SignupContract.Effect.ShowSuccess -> {
                            Toast.makeText(
                                context,
                                context.getString(effect.messageRes),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        composable<Screen.Places> {
            val placesViewModel: PlacesViewModel = hiltViewModel()
            val state by placesViewModel.uiState.collectAsState()

            PlacesScreen(
                state = state,
                onEvent = placesViewModel::handleEvent
            )
            val context = LocalContext.current

            LaunchedEffect(placesViewModel) {
                placesViewModel.effect.collect { effect ->
                    when (effect) {
                        is PlacesContract.Effects.NavigateToPlaceDetails -> {
                            // Store the place in savedStateHandle and navigate
                            navController.currentBackStackEntry?.savedStateHandle?.set("place", effect.place)
                            navController.navigate("place_details")
                        }

                        is PlacesContract.Effects.RequestLocationPermission -> { // handled at the screen
                        }

                        is PlacesContract.Effects.ShowError -> {
                            Toast.makeText(
                                context,
                                context.getString(effect.title) + " " + context.getString(effect.subtitle),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }

        // Simple string-based navigation for PlaceDetails using savedStateHandle
        composable("place_details") {
            val place = navController.previousBackStackEntry?.savedStateHandle?.get<PlaceDto>("place")
            place?.let {
                val placeDetailsViewModel: PlaceDetailsViewModel = hiltViewModel()
                val state by placeDetailsViewModel.uiState.collectAsState()
                mainState.userId?.let { userId ->
                    PlaceDetailsScreen(
                        place = place,
                        state = state,
                        onEvent = placeDetailsViewModel::handleEvent,
                        modifier = Modifier,
                        userId = userId
                    )
                }

                val context = LocalContext.current
                LaunchedEffect(placeDetailsViewModel) {
                    placeDetailsViewModel.effect.collect { effect ->
                        when (effect) {
                            is PlaceDetailsContract.Effect.NavigateUp -> {
                                navController.popBackStack()
                            }
                        }
                    }
                }
            }
        }
    }
}