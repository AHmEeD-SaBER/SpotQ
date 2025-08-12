package com.example.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.core_ui.base.BaseViewModel
import com.example.domain.dto.PlaceDto
import com.example.domain.usecases.IGetPlacesUseCase
import com.example.errors.CustomError
import com.example.location_provider.LocationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import com.example.location_provider.R.string as locationStrings

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val useCase: IGetPlacesUseCase,
    private val locationProvider: LocationProvider
) : BaseViewModel<PlacesContract.Events, PlacesContract.State, PlacesContract.Effects>() {

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
                    setState {
                        copy(
                            isLoadingLocation = false,
                            isLoading = true,
                            userLocation = Pair(location.latitude, location.longitude)
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
                        "PlacesView)Model",
                        "Location: ${location.latitude}, ${location.longitude} , Places: ${uiState.value.places}, Radius: $radius, Limit: $limit"
                    )
                }

                is LocationProvider.LocationResult.PermissionDenied -> {
                    setState {
                        copy(
                            isLoadingLocation = false,
                            error = locationStrings.location_permission_denied
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
            } ?: run {
                handleLoadPlaces()
            }
        }
    }

    private fun handleRequestLocationPermission() {
        setEffect { PlacesContract.Effects.RequestLocationPermission }
    }

}