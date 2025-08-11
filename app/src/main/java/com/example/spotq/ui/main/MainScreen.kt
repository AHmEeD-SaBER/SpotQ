package com.example.spotq.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.splash.SplashScreen
import com.spotq.onboarding.OnboardingScreen

@Composable
fun MainScreen(
    state: MainContract.State,
    onEvent: (MainContract.Event) -> Unit
) {
    when (state.currentDestination) {
        MainContract.Destination.SPLASH -> {
            SplashScreen(
                onSplashFinished = {
                    onEvent(MainContract.Event.SplashFinished)
                }
            )
        }
        MainContract.Destination.LOADING -> {
            LoadingScreen()
        }
        MainContract.Destination.ONBOARDING -> {
            OnboardingScreen(
                onOnboardingComplete = {
                    onEvent(MainContract.Event.OnboardingCompleted)
                }
            )
        }
        MainContract.Destination.AUTH -> {
            AuthScreen(
                onAuthenticationComplete = {
                    onEvent(MainContract.Event.AuthenticationCompleted)
                }
            )
        }
        MainContract.Destination.MAIN -> {
            MainAppScreen()
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun AuthScreen(
    onAuthenticationComplete: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Button(
                onClick = onAuthenticationComplete
            ) {
                Text("Mock Sign In")
            }
        }
    }
}

@Composable
private fun MainAppScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Welcome to SpotQ!\nYou are now authenticated.",
                textAlign = TextAlign.Center
            )
        }
    }
}
