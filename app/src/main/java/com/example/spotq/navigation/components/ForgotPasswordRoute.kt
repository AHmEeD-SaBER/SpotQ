package com.example.spotq.navigation.components

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.spotq.authentication.ui.forgotpassword.*
import com.example.core_ui.utils.Routes

@Composable
fun ForgotPasswordRoute(navController: NavHostController) {
    val forgotPasswordViewModel: ForgotPasswordViewModel = hiltViewModel()
    val state by forgotPasswordViewModel.uiState.collectAsState()
    val context = LocalContext.current

    ForgotPasswordScreen(
        state = state,
        onEvent = forgotPasswordViewModel::handleEvent
    )

    LaunchedEffect(forgotPasswordViewModel) {
        forgotPasswordViewModel.effect.collect { effect ->
            when (effect) {
                ForgotPasswordContract.Effect.NavigateToLogin -> {
                    navController.navigate(Routes.Login)
                }
                is ForgotPasswordContract.Effect.ShowError -> {
                    Toast.makeText(context, context.getString(effect.messageRes), Toast.LENGTH_SHORT).show()
                }
                is ForgotPasswordContract.Effect.ShowSuccess -> {
                    Toast.makeText(context, context.getString(effect.messageRes), Toast.LENGTH_SHORT).show()
                }
                ForgotPasswordContract.Effect.None -> Unit
            }
        }
    }
}

