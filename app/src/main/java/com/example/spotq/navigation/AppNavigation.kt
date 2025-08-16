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
import com.example.core_domain.dto.PlaceDto
import com.example.core_ui.utils.Routes
import com.example.main_navigation.BottomNavViewModel
import com.example.main_navigation.MainNavigation
import com.example.splash.SplashScreen
import com.example.spotq.ui.main.MainContract
import com.example.ui.place_details.PlaceDetailsContract
import com.example.ui.place_details.PlaceDetailsScreen
import com.example.ui.place_details.PlaceDetailsViewModel
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
        startDestination = Routes.Splash
    ) {

        composable<Routes.Splash> {
            SplashScreen(
                onSplashFinished = onSplashFinished
            )
            LaunchedEffect(mainState.currentDestination) {
                when (mainState.currentDestination) {
                    MainContract.Destination.ONBOARDING -> {
                        navController.navigate(Routes.Onboarding) {
                            popUpTo<Routes.Splash> { inclusive = true }
                        }
                    }

                    MainContract.Destination.AUTH -> {
                        navController.navigate(Routes.Login) {
                            popUpTo<Routes.Splash> { inclusive = true }
                        }
                    }

                    MainContract.Destination.MAIN -> {
                        // Navigate to Main, not directly to Places
                        navController.navigate(Routes.Main(userId = mainState.userId ?: -1)) {
                            popUpTo<Routes.Splash> { inclusive = true }
                        }
                    }

                    else -> {
                    }
                }
            }
        }

        composable<Routes.Onboarding> {
            OnboardingScreen(
                onOnboardingComplete = {
                    onOnboardingComplete()
                    navController.navigate(Routes.Login) {
                        popUpTo<Routes.Onboarding> { inclusive = true }
                    }
                }
            )
        }

        composable<Routes.Login> {
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
                            // Navigate to Main, not directly to Places
                            navController.navigate(Routes.Main(userId = effect.userId)) {
                                popUpTo<Routes.Login> { inclusive = true }
                            }
                        }

                        is LoginContract.Effect.NavigateToSignup -> {
                            navController.navigate(Routes.Signup) {
                                popUpTo<Routes.Login> { inclusive = false }
                            }
                        }

                        is LoginContract.Effect.NavigateToForgotPassword -> {
                            navController.navigate(Routes.ForgotPassword) {
                                popUpTo<Routes.Login> { inclusive = false }
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

        composable<Routes.ForgotPassword> {
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
                            navController.navigate(Routes.Login)
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

        composable<Routes.Signup> {
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
                            // Navigate to Main, not directly to Places
                            navController.navigate(Routes.Main(userId = effect.userId)) {
                                popUpTo<Routes.Signup> { inclusive = true }
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

        // PlaceDetails at the main navigation level
        composable<Routes.PlaceDetails> {
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

                            is PlaceDetailsContract.Effect.ShowError -> {
                                Toast.makeText(
                                    context,
                                    context.getString(effect.messageRes),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            is PlaceDetailsContract.Effect.ShowSuccess -> {
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
        }

        composable<Routes.Main> {
            val mainViewModel : BottomNavViewModel = hiltViewModel()
            val state by mainViewModel.uiState.collectAsState()
            mainState.userId?.let {
                MainNavigation(
                    navController = navController,
                    state = state,
                    onEvent = mainViewModel::handleEvent,
                )
            }
        }
    }
}