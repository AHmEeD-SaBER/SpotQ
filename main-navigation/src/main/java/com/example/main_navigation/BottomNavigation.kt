package com.example.main_navigation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.core_ui.utils.Routes
import com.example.ui.FavoritesContract
import com.example.ui.FavoritesScreen
import com.example.ui.FavoritesViewModel
import com.example.ui.places.PlacesContract
import com.example.ui.places.PlacesScreen
import com.example.ui.places.PlacesViewModel
import com.example.core_ui.R as CoreUiR

@Composable
fun MainNavigation(
    navController: NavHostController, // This is the main app navController
    state: BottomNavigationContract.State,
    onEvent: (BottomNavigationContract.Event) -> Unit
) {
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            CustomBottomBar(
                items = state.items,
                onItemClick = { item ->
                    onEvent(BottomNavigationContract.Event.OnTabSelected(item.route))
                    bottomNavController.navigate(item.route) {
                        popUpTo(bottomNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = Routes.Places,
        ) {
            composable<Routes.Places> {
                val placesViewModel: PlacesViewModel = hiltViewModel()
                val state by placesViewModel.uiState.collectAsState()

                PlacesScreen(
                    state = state,
                    onEvent = placesViewModel::handleEvent
                )
                val context = LocalContext.current

                LaunchedEffect(placesViewModel) {
                    placesViewModel.effect.collect { effect ->
                        when (effect) {
                            is PlacesContract.Effects.NavigateToPlaceDetails -> {
                                // Use the main app navController to navigate to PlaceDetails
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "place",
                                    effect.place
                                )
                                navController.navigate(Routes.PlaceDetails)
                            }

                            is PlacesContract.Effects.RequestLocationPermission -> {
                                // handled at the screen
                            }

                            is PlacesContract.Effects.ShowError -> {
                                Toast.makeText(
                                    context,
                                    context.getString(effect.title) + " " + context.getString(effect.subtitle),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }

            composable<Routes.Favorites> { backStackEntry ->
                val args = backStackEntry.toRoute<Routes.Favorites>()
                val favoritesViewModel: FavoritesViewModel = hiltViewModel()
                val state by favoritesViewModel.uiState.collectAsState()
                FavoritesScreen(
                    userId = args.userId,
                    state = state,
                    onEvent = favoritesViewModel::handleEvent
                )

                val context = LocalContext.current
                LaunchedEffect(favoritesViewModel) {
                    favoritesViewModel.effect.collect { effect ->
                        when (effect) {
                            is FavoritesContract.Effect.NavigateToDetails -> {
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    "place",
                                    effect.place
                                )
                                navController.navigate(Routes.PlaceDetails)
                            }

                            is FavoritesContract.Effect.ShowError -> {
                                Toast.makeText(
                                    context,
                                    context.getString(effect.message),
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            is FavoritesContract.Effect.ShowMessage -> {
                                Toast.makeText(
                                    context,
                                    context.getString(effect.message),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }

            composable<Routes.Search> {
                // SearchScreen()

                // Placeholder for now
                Text(
                    text = "Search Screen",
                    modifier = Modifier.padding(16.dp)
                )
            }

            composable<Routes.Profile> {
                // ProfileScreen()

                // Placeholder for now
                Text(
                    text = "Profile Screen",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}


