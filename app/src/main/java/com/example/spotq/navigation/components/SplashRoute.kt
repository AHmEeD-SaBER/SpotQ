package com.example.spotq.navigation.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.spotq.ui.main.MainContract
import com.example.splash.SplashScreen
import com.example.core_ui.utils.Routes
import androidx.compose.runtime.LaunchedEffect

@Composable
fun SplashRoute(
    navController: NavHostController,
    mainState: MainContract.State,
    onSplashFinished: () -> Unit
) {
    SplashScreen(onSplashFinished = onSplashFinished)

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
                navController.navigate(Routes.Main(userId = mainState.userId ?: -1)) {
                    popUpTo<Routes.Splash> { inclusive = true }
                }
            }
            else -> Unit
        }
    }
}

