package com.example.spotq.navigation.components

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.core_domain.dto.PlaceDto
import com.example.ui.place_details.*
import com.example.spotq.ui.main.MainContract

@Composable
fun PlaceDetailsRoute(
    navController: NavHostController,
    mainState: MainContract.State
) {
    val place = navController.previousBackStackEntry?.savedStateHandle?.get<PlaceDto>("place")
    val context = LocalContext.current

    place?.let {
        val placeDetailsViewModel: PlaceDetailsViewModel = hiltViewModel()
        val state by placeDetailsViewModel.uiState.collectAsState()

        mainState.userId?.let { userId ->
            PlaceDetailsScreen(
                place = place,
                state = state,
                onEvent = placeDetailsViewModel::handleEvent,
                modifier = Modifier,
                userId = userId
            )
        }

        LaunchedEffect(placeDetailsViewModel) {
            placeDetailsViewModel.effect.collect { effect ->
                when (effect) {
                    is PlaceDetailsContract.Effect.NavigateUp -> navController.popBackStack()
                    is PlaceDetailsContract.Effect.ShowError -> {
                        Toast.makeText(context, context.getString(effect.messageRes), Toast.LENGTH_SHORT).show()
                    }
                    is PlaceDetailsContract.Effect.ShowSuccess -> {
                        Toast.makeText(context, context.getString(effect.messageRes), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

