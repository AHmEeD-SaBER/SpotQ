package com.example.spotq.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.core_ui.utils.Routes
import com.example.core_ui.utils.fadeEnter
import com.example.core_ui.utils.fadeExit
import com.example.core_ui.utils.slideLeftEnter
import com.example.spotq.ui.main.MainContract
import com.example.spotq.navigation.components.ForgotPasswordRoute
import com.example.spotq.navigation.components.LoginRoute
import com.example.spotq.navigation.components.MainRoute
import com.example.spotq.navigation.components.OnboardingRoute
import com.example.spotq.navigation.components.PlaceDetailsRoute
import com.example.spotq.navigation.components.SignupRoute
import com.example.spotq.navigation.components.SplashRoute

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
            SplashRoute(navController, mainState, onSplashFinished)
        }
        composable<Routes.Onboarding> {
            OnboardingRoute(navController, onOnboardingComplete)
        }
        composable<Routes.Login>(
            enterTransition = { fadeEnter() },
            exitTransition = { fadeExit() }
        ) {
            LoginRoute(navController, onAuthComplete)
        }
        composable<Routes.ForgotPassword> {
            ForgotPasswordRoute(navController)
        }
        composable<Routes.Signup>(
            enterTransition = { fadeEnter() },
            exitTransition = { fadeExit() }
        )
        {
            SignupRoute(navController, onAuthComplete)
        }
        composable<Routes.PlaceDetails>(
            enterTransition = { fadeEnter() },
            exitTransition = { fadeExit() }
        ) {
            PlaceDetailsRoute(navController, mainState)
        }
        composable<Routes.Main>(
            enterTransition = { fadeEnter() },
            exitTransition = { fadeExit() }
        ) {
            MainRoute(navController, mainState)
        }
    }
}
