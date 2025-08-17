package com.example.spotq.navigation.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.spotq.onboarding.OnboardingScreen
import com.example.core_ui.utils.Routes

@Composable
fun OnboardingRoute(
    navController: NavHostController,
    onOnboardingComplete: () -> Unit
) {
    OnboardingScreen(
        onOnboardingComplete = {
            onOnboardingComplete()
            navController.navigate(Routes.Login) {
                popUpTo<Routes.Onboarding> { inclusive = true }
            }
        }
    )
}

