package com.example.spotq

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.core_ui.theme.SpotQTheme
import com.example.core_ui.utils.LanguageManager.getSavedLanguage
import com.example.core_ui.utils.LocaleHelper
import com.example.spotq.navigation.AppNavigation
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

                val currentRoute = navController.currentBackStackEntry?.destination?.route

                Box(modifier = Modifier.fillMaxSize()) {

                    AppNavigation(
                        navController = navController,
                        mainState = state,
                        onSplashFinished = {
                            mainViewModel.handleEvent(MainContract.Event.CheckInitialState)
                        },
                        onAuthComplete = { userid ->
                            mainViewModel.handleEvent(
                                MainContract.Event.AuthenticationCompleted(
                                    userId = userid
                                )
                            )
                        },
                        onOnboardingComplete = {
                            mainViewModel.handleEvent(MainContract.Event.OnboardingCompleted)
                        }
                    )


                }

            }
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val lang = getSavedLanguage(newBase) // e.g., from SharedPreferences
        super.attachBaseContext(LocaleHelper.setLocale(newBase, lang))
    }

}