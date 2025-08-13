package com.example.ui

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.domain.dto.PlaceDto

@OptIn(ExperimentalMaterial3Api::class)
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
        if (!state.hasPermission && state.error == com.example.location_provider.R.string.location_permission_denied) {
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
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Location",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = locationName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                )
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
                            text = if (state.isLoadingLocation) "Getting your location..." else "Loading places...",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                state.error != null && !state.hasPermission -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Location permission required")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { onEvent(PlacesContract.Events.CheckPermissions) }) {
                            Text("Grant Permission")
                        }
                    }
                }
                state.error != null -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Error: ${state.error}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { onEvent(PlacesContract.Events.Retry) }) {
                            Text("Retry")
                        }
                    }
                }
                else -> {
                    PlacesList(
                        places = state.places,
                        onPlaceClick = { place ->
                            onEvent(PlacesContract.Events.PlaceClicked(place))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PlacesList(
    places: List<PlaceDto>,
    onPlaceClick: (PlaceDto) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(places) { place ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                onClick = { onPlaceClick(place) }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = place.name ?: "Unknown Place",
                        style = MaterialTheme.typography.titleMedium
                    )
                    place.kinds?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}