package com.example.ui

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.components.CustomAppBar
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.theme.SpotQTheme
import com.example.domain.dto.PlaceDto
import com.example.ui.components.PlacesScreenContent
import com.example.core_ui.R.string as coreUiString

@Composable
fun PlacesScreen(
    state: PlacesContract.State,
    onEvent: (PlacesContract.Events) -> Unit
) {
    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (fineGranted || coarseGranted) {
            onEvent(PlacesContract.Events.CheckPermissions)
        } else {
            onEvent(
                PlacesContract.Events.RequestLocationPermission
            )
        }
    }

    LaunchedEffect(Unit) {
        onEvent(PlacesContract.Events.CheckPermissions)
    }

    LaunchedEffect(state.hasPermission) {
        if (!state.hasPermission && !state.isLoadingLocation) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    Scaffold(
        topBar = {
            state.locationName?.let { locationName ->
                CustomAppBar(
                    title = {
                        Column {
                            Text(
                                text = "Your Location",
                                style = AppTypography.bt8
                            )
                            Text(
                                text = locationName,
                                style = AppTypography.bt3
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { }) {
                            Icon(
                                painter = painterResource(R.drawable.settings_icon),
                                contentDescription = stringResource(coreUiString.cd_settings)
                            )
                        }
                    },
                    showSearchBar = true
                ) {
                    com.example.ui.components.SearchBar(
                        modifier = Modifier,
                        value = "",
                        onSearch = {
                        }
                    )
                }
            }

        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoadingLocation || state.isLoading -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = if (state.isLoadingLocation) stringResource(coreUiString.cd_loading_location) else stringResource(
                                coreUiString.cd_loading_places
                            ),
                            style = AppTypography.bt4
                        )
                    }
                }

                state.error != null && !state.hasPermission -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = stringResource(state.error))
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { onEvent(PlacesContract.Events.CheckPermissions) }) {
                            Text(
                                stringResource(coreUiString.cd_grant_permission),
                                style = AppTypography.bt4
                            )
                        }
                    }
                }

                state.error != null -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = stringResource(state.error))
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { onEvent(PlacesContract.Events.Retry) }) {
                            Text("Retry")
                        }
                    }
                }

                else -> {
                    PlacesScreenContent(
                        modifier = Modifier.fillMaxSize(),
                        state = state,
                        onEvent = onEvent
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PlacesScreenPreview() {
    val samplePlaces = listOf(
        PlaceDto(
            xid = "1", name = "Central Park", kinds = "Park",
            latitude = 1.0,
            longitude = 1.0,
            rate = 3,
        ),
        PlaceDto(
            xid = "2", name = "Statue of Liberty", kinds = "Monument",
            latitude = 2.0,
            longitude = 2.0,
            rate = 4
        ),
        PlaceDto(
            xid = "3", name = "Empire State Building", kinds = "Building",
            latitude = 3.0,
            longitude = 3.0,
            rate = 5
        )
    )

    SpotQTheme {
        PlacesScreen(
            state = PlacesContract.State(
                isLoading = false,
                isLoadingLocation = false,
                hasPermission = true,
                error = null,
                locationName = "New York",
                places = samplePlaces
            ),
            onEvent = {}
        )
    }
}