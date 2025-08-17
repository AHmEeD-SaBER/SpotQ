package com.example.spotq.navigation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.main_navigation.BottomNavViewModel
import com.example.main_navigation.MainNavigation
import com.example.spotq.ui.main.MainContract

@Composable
fun MainRoute(
    navController: NavHostController,
    mainState: MainContract.State
) {
    val mainViewModel: BottomNavViewModel = hiltViewModel()
    val state by mainViewModel.uiState.collectAsState()

    mainState.userId?.let {
        MainNavigation(
            navController = navController,
            state = state,
            onEvent = mainViewModel::handleEvent,
        )
    }
}

