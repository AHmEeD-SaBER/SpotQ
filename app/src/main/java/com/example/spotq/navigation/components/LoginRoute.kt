package com.example.spotq.navigation.components

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.spotq.authentication.ui.login.*
import com.example.core_ui.utils.Routes

@Composable
fun LoginRoute(
    navController: NavHostController,
    onAuthComplete: (userId: Int) -> Unit
) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val state by loginViewModel.uiState.collectAsState()
    val context = LocalContext.current

    LoginScreen(
        state = state,
        onEvent = loginViewModel::handleEvent,
        modifier = Modifier,
    )

    LaunchedEffect(loginViewModel) {
        loginViewModel.effect.collect { effect ->
            when (effect) {
                is LoginContract.Effect.NavigateToMain -> {
                    onAuthComplete(effect.userId)
                    navController.navigate(Routes.Main(userId = effect.userId)) {
                        popUpTo<Routes.Login> { inclusive = true }
                    }
                }
                is LoginContract.Effect.NavigateToSignup -> {
                    navController.navigate(Routes.Signup)
                }
                is LoginContract.Effect.NavigateToForgotPassword -> {
                    navController.navigate(Routes.ForgotPassword)
                }
                is LoginContract.Effect.ShowError -> {
                    Toast.makeText(context, context.getString(effect.messageRes), Toast.LENGTH_SHORT).show()
                }
                is LoginContract.Effect.ShowSuccess -> {
                    Toast.makeText(context, context.getString(effect.messageRes), Toast.LENGTH_SHORT).show()
                }
                LoginContract.Effect.None -> Unit
            }
        }
    }
}

