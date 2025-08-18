package com.example.ui.places


import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.core_ui.base.BaseViewModel
import com.example.core_domain.dto.PlaceDto
import com.example.domain.usecases.get_places.IGetPlacesUseCase
import com.example.errors.CustomError
import com.example.location_provider.LocationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import com.example.location_provider.IGeocoderProvider
import com.example.location_provider.R.string as locationStrings

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val useCase: IGetPlacesUseCase,
    private val locationProvider: LocationProvider,
    private val geocoderProvider: IGeocoderProvider
) : BaseViewModel<PlacesContract.Events, PlacesContract.State, PlacesContract.Effects>() {

    override fun setInitialState(): PlacesContract.State {
        return PlacesContract.State()
    }

    init {
        handleEvent(PlacesContract.Events.CheckPermissions)
    }

    override fun handleEvent(event: PlacesContract.Events) {
        when (event) {
            PlacesContract.Events.LoadPlaces -> handleLoadPlaces()
            is PlacesContract.Events.PlaceClicked -> handlePlaceClicked(event.place)
            PlacesContract.Events.Retry -> handleRetry()
            PlacesContract.Events.RequestLocationPermission -> handleRequestLocationPermission()
            PlacesContract.Events.CheckPermissions -> handleCheckPermissions()
            PlacesContract.Events.NavigateToSearch -> handleNavigateToSearch()
        }
    }

    private fun handleNavigateToSearch() {
        Log.d("PlacesViewModel", "NavigateToSearch event triggered")
        setEffect { PlacesContract.Effects.NavigateToSearch }
        Log.d("PlacesViewModel", "NavigateToSearch effect emitted")
    }

    private fun handleCheckPermissions() {
        val hasPermission = locationProvider.hasLocationPermission()
        setState { copy(hasPermission = hasPermission) }

        if (hasPermission) {
            handleLoadPlaces()
        } else {
            setEffect { PlacesContract.Effects.RequestLocationPermission }
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

                    // Use the injected geocoder provider
                    val locationName = geocoderProvider.getLocationName(
                        location.latitude,
                        location.longitude
                    )

                    setState {
                        copy(
                            isLoadingLocation = false,
                            isLoading = true,
                            userLocation = Pair(location.latitude, location.longitude),
                            locationName = locationName,
                            hasPermission = true
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
                            hasPermission = false
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
            uiState.value.userLocation?.let { (_, _) ->
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