package com.example.spotq.navigation.components

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.spotq.authentication.ui.signup.*
import com.example.core_ui.utils.Routes

@Composable
fun SignupRoute(
    navController: NavHostController,
    onAuthComplete: (userId: Int) -> Unit
) {
    val signupViewModel: SignupViewModel = hiltViewModel()
    val state by signupViewModel.uiState.collectAsState()
    val context = LocalContext.current

    SignupScreen(
        state = state,
        onEvent = signupViewModel::handleEvent
    )

    LaunchedEffect(signupViewModel) {
        signupViewModel.effect.collect { effect ->
            when (effect) {
                is SignupContract.Effect.NavigateToMain -> {
                    onAuthComplete(effect.userId)
                    navController.navigate(Routes.Main(userId = effect.userId)) {
                        popUpTo<Routes.Signup> { inclusive = true }
                    }
                }
                is SignupContract.Effect.NavigateToLogin -> {
                    navController.popBackStack()
                }
                is SignupContract.Effect.ShowError -> {
                    Toast.makeText(context, context.getString(effect.messageRes), Toast.LENGTH_SHORT).show()
                }
                is SignupContract.Effect.ShowSuccess -> {
                    Toast.makeText(context, context.getString(effect.messageRes), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

