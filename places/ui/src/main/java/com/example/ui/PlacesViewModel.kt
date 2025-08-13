package com.example.ui

import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.core_ui.base.BaseViewModel
import com.example.domain.dto.PlaceDto
import com.example.domain.usecases.IGetPlacesUseCase
import com.example.errors.CustomError
import com.example.location_provider.LocationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume
import android.content.Context
import com.example.location_provider.R.string as locationStrings

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val useCase: IGetPlacesUseCase,
    private val locationProvider: LocationProvider,
    @ApplicationContext private val context: Context
) : BaseViewModel<PlacesContract.Events, PlacesContract.State, PlacesContract.Effects>() {

    private val geocoder = if (Geocoder.isPresent()) Geocoder(context, Locale.getDefault()) else null

    override fun setInitialState(): PlacesContract.State {
        return PlacesContract.State()
    }

    override fun handleEvent(event: PlacesContract.Events) {
        when (event) {
            PlacesContract.Events.LoadPlaces -> handleLoadPlaces()
            is PlacesContract.Events.LoadPlacesWithCategory -> handleLoadPlacesWithCategory(event.kinds)
            is PlacesContract.Events.LoadPlacesWithParams -> handleLoadPlacesWithParams(
                event.kinds, event.radius, event.limit
            )
            is PlacesContract.Events.PlaceClicked -> handlePlaceClicked(event.place)
            PlacesContract.Events.Retry -> handleRetry()
            PlacesContract.Events.RequestLocationPermission -> handleRequestLocationPermission()
            PlacesContract.Events.CheckPermissions -> handleCheckPermissions() // Handle the new event
        }
    }

    /**
     * Check and update permission state
     */
    private fun handleCheckPermissions() {
        val hasPermission = locationProvider.hasLocationPermission()
        setState { copy(hasPermission = hasPermission) }

        if (hasPermission) {
            // If permission is granted, load places
            handleLoadPlaces()
        } else {
            // If no permission, trigger permission request
            setEffect { PlacesContract.Effects.RequestLocationPermission }
        }
    }

    /**
     * Get location name from coordinates using reverse geocoding
     */
    private suspend fun getLocationName(latitude: Double, longitude: Double): String? {
        return try {
            if (geocoder == null) return null

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // Use the new async API for Android 13+
                suspendCancellableCoroutine { continuation ->
                    geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
                        val locationName = addresses.firstOrNull()?.let { address ->
                            formatLocationName(address)
                        }
                        continuation.resume(locationName)
                    }
                }
            } else {
                // Use the deprecated synchronous API for older versions
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                addresses?.firstOrNull()?.let { address ->
                    formatLocationName(address)
                }
            }
        } catch (e: Exception) {
            Log.e("PlacesViewModel", "Error getting location name", e)
            null
        }
    }

    /**
     * Format the address into a readable location name
     */
    private fun formatLocationName(address: Address): String {
        return buildString {
            // Add city/locality
            address.locality?.let { append(it) }

            // Add admin area (state/province) if different from locality
            address.adminArea?.let { adminArea ->
                if (address.locality != adminArea) {
                    if (isNotEmpty()) append(", ")
                    append(adminArea)
                }
            }

            // Add country
            address.countryName?.let { country ->
                if (isNotEmpty()) append(", ")
                append(country)
            }

            // Fallback to sub-admin area if nothing else is available
            if (isEmpty()) {
                address.subAdminArea?.let { append(it) }
            }
        }
    }

    private fun handleLoadPlaces(
        kinds: String? = null,
        radius: Int? = null,
        limit: Int? = null,
    ) {
        viewModelScope.launch {
            setState { copy(isLoadingLocation = true, error = null) }
            when (val locationResult = locationProvider.getCurrentLocation()) {
                is LocationProvider.LocationResult.Success -> {
                    val location = locationResult.location

                    // Get location name
                    val locationName = getLocationName(location.latitude, location.longitude)

                    setState {
                        copy(
                            isLoadingLocation = false,
                            isLoading = true,
                            userLocation = Pair(location.latitude, location.longitude),
                            locationName = locationName,
                            hasPermission = true // Update permission state
                        )
                    }

                    loadPlacesData(
                        location.latitude,
                        location.longitude,
                        kinds,
                        radius,
                        limit,
                    )
                    Log.d(
                        "PlacesViewModel",
                        "Location: ${location.latitude}, ${location.longitude}, LocationName: $locationName, Places: ${uiState.value.places}, Radius: $radius, Limit: $limit"
                    )
                }

                is LocationProvider.LocationResult.PermissionDenied -> {
                    setState {
                        copy(
                            isLoadingLocation = false,
                            error = locationStrings.location_permission_denied,
                            hasPermission = false // Update permission state
                        )
                    }
                    setEffect { PlacesContract.Effects.RequestLocationPermission }
                }

                is LocationProvider.LocationResult.LocationDisabled -> {
                    setState {
                        copy(
                            isLoadingLocation = false,
                            error = locationStrings.location_disabled
                        )
                    }
                }

                is LocationProvider.LocationResult.Error -> {
                    setState {
                        copy(
                            isLoadingLocation = false,
                            error = locationStrings.location_error
                        )
                    }
                }
            }
        }
    }

    private fun handleLoadPlacesWithCategory(kinds: String?) {
        handleLoadPlaces(kinds = kinds)
    }

    private fun handleLoadPlacesWithParams(kinds: String?, radius: Int?, limit: Int?) {
        handleLoadPlaces(kinds = kinds, radius = radius, limit = limit)
    }

    private suspend fun loadPlacesData(
        latitude: Double,
        longitude: Double,
        kinds: String?,
        radius: Int?,
        limit: Int?,
    ) {
        try {
            useCase.invoke(latitude, longitude, kinds, radius, limit).first().fold(
                onSuccess = { places ->
                    setState { copy(isLoading = false, places = places) }
                },
                onFailure = { error ->
                    when (error) {
                        is CustomError.NoNetwork -> {
                            setState { copy(isLoading = false, error = error.title) }
                            setEffect {
                                PlacesContract.Effects.ShowError(
                                    title = error.title,
                                    subtitle = error.subtitle
                                )
                            }
                        }

                        is CustomError.NoData -> {
                            setState { copy(isLoading = false, error = error.title) }
                            setEffect {
                                PlacesContract.Effects.ShowError(
                                    title = error.title,
                                    subtitle = error.subtitle
                                )
                            }
                        }

                        is CustomError.Unknown -> {
                            setState { copy(isLoading = false, error = error.title) }
                            setEffect {
                                PlacesContract.Effects.ShowError(
                                    title = error.title,
                                    subtitle = error.subtitle
                                )
                            }
                        }
                    }
                }
            )
        } catch (e: CustomError) {
            setState { copy(isLoading = false, error = e.titleRes) }
        }
    }

    private fun handlePlaceClicked(place: PlaceDto) {
        setEffect { PlacesContract.Effects.NavigateToPlaceDetails(place) }
    }

    private fun handleRetry() {
        viewModelScope.launch {
            uiState.value.userLocation?.let { (lat, lon) ->
                // If we have coordinates but maybe missing location name, reload
                handleLoadPlaces()
            } ?: run {
                handleLoadPlaces()
            }
        }
    }

    private fun handleRequestLocationPermission() {
        setEffect { PlacesContract.Effects.RequestLocationPermission }
    }
}