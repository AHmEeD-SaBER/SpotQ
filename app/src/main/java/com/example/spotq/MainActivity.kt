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
                val state by mainViewModel.uiState.collectAsState()
                AppNavigation(
                    navController = navController,
                    mainState = state,
                    onSplashFinished = {
                        mainViewModel.setEvent(MainContract.Event.CheckInitialState)
                    },
                    onAuthComplete = {
                        MainContract.Event.AuthenticationCompleted
                    },
                )

            }
        }
    }
}