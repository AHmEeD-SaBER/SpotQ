package com.example.ui.search


import androidx.lifecycle.viewModelScope
import com.example.core_ui.base.BaseViewModel
import com.example.core_domain.dto.PlaceDto
import com.example.domain.usecases.get_places.IGetPlacesUseCase
import com.example.errors.CustomError
import com.example.location_provider.IGeocoderProvider
import com.example.location_provider.LocationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: IGetPlacesUseCase,
    private val locationProvider: LocationProvider,
    private val geocoderProvider: IGeocoderProvider
) : BaseViewModel<SearchContract.Events, SearchContract.State, SearchContract.Effects>() {

    override fun setInitialState(): SearchContract.State {
        return SearchContract.State()
    }

    init {
        handleEvent(SearchContract.Events.LoadPlaces)
    }

    override fun handleEvent(event: SearchContract.Events) {
        when (event) {
            is SearchContract.Events.SearchQueryChanged -> handleSearchQueryChanged(event.query)
            is SearchContract.Events.PlaceClicked -> handlePlaceClicked(event.place)
            SearchContract.Events.ClearSearch -> handleClearSearch()
            SearchContract.Events.LoadPlaces -> handleLoadPlaces()
            SearchContract.Events.Retry -> handleRetry()
            SearchContract.Events.NavigateBack -> handleNavigateBack()
        }
    }

    private fun handleSearchQueryChanged(query: String) {
        val allPlaces = uiState.value.allPlaces
        val filteredPlaces = if (query.isEmpty()) {
            allPlaces
        } else {
            filterPlaces(allPlaces, query)
        }

        setState {
            copy(
                searchQuery = query,
                filteredPlaces = filteredPlaces
            )
        }
    }

    private fun handleClearSearch() {
        setState {
            copy(
                searchQuery = "",
                filteredPlaces = allPlaces
            )
        }
    }

    private fun handleLoadPlaces() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }

            when (val locationResult = locationProvider.getCurrentLocation()) {
                is LocationProvider.LocationResult.Success -> {
                    val location = locationResult.location
                    val locationName = geocoderProvider.getLocationName(
                        location.latitude,
                        location.longitude
                    )

                    setState {
                        copy(locationName = locationName)
                    }

                    loadPlacesData(location.latitude, location.longitude)
                }

                is LocationProvider.LocationResult.PermissionDenied,
                is LocationProvider.LocationResult.LocationDisabled,
                is LocationProvider.LocationResult.Error -> {
                    setState {
                        copy(
                            isLoading = false,
                            error = com.example.location_provider.R.string.location_error
                        )
                    }
                }
            }
        }
    }

    private suspend fun loadPlacesData(latitude: Double, longitude: Double) {
        try {
            useCase.invoke(
                latitude = latitude,
                longitude = longitude,
                kinds = null,
                radius = null,
                limit = null
            ).first().fold(
                onSuccess = { places ->
                    setState {
                        copy(
                            isLoading = false,
                            allPlaces = places,
                            filteredPlaces = places
                        )
                    }
                },
                onFailure = { error ->
                    handleError(error)
                }
            )
        } catch (e: CustomError) {
            setState { copy(isLoading = false, error = e.titleRes) }
        }
    }

    private fun filterPlaces(places: List<PlaceDto>, query: String): List<PlaceDto> {
        val queryLower = query.lowercase()

        return places.filter { place ->
            place.name.lowercase().contains(queryLower) ||
                    place.kinds.lowercase().contains(queryLower) || place.mainCategory.lowercase()
                .contains(queryLower) || place.categories.any {
                it.lowercase().contains(queryLower)
            } || place.description?.lowercase()?.contains(queryLower) == true
        }.sortedWith(
            compareByDescending<PlaceDto> { it.rate }.thenBy {
                when {
                    it.name.lowercase().startsWith(queryLower) -> 0
                    it.name.lowercase().contains(queryLower) -> 1
                    else -> 2
                }
            }
        )
    }

    private fun handleError(error: Throwable) {
        when (error) {
            is CustomError.NoNetwork -> {
                setState { copy(isLoading = false, error = error.title) }
                setEffect {
                    SearchContract.Effects.ShowError(
                        title = error.title,
                        subtitle = error.subtitle
                    )
                }
            }
            is CustomError.NoData -> {
                setState { copy(isLoading = false, error = error.title) }
                setEffect {
                    SearchContract.Effects.ShowError(
                        title = error.title,
                        subtitle = error.subtitle
                    )
                }
            }
            is CustomError.Unknown -> {
                setState { copy(isLoading = false, error = error.title) }
                setEffect {
                    SearchContract.Effects.ShowError(
                        title = error.title,
                        subtitle = error.subtitle
                    )
                }
            }
        }
    }

    private fun handlePlaceClicked(place: PlaceDto) {
        setEffect { SearchContract.Effects.NavigateToPlaceDetails(place) }
    }

    private fun handleRetry() {
        handleEvent(SearchContract.Events.LoadPlaces)
    }

    private fun handleNavigateBack() {
        setEffect { SearchContract.Effects.NavigateBack }
    }
}