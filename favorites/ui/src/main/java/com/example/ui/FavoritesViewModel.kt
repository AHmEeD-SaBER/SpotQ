package com.example.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.core_domain.dto.PlaceDto
import com.example.core_ui.base.BaseViewModel
import com.example.core_ui.utils.Routes
import com.example.domain.usecases.IGetFavoritesUseCase
import com.example.errors.CustomError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val useCase: IGetFavoritesUseCase,
    savedStateHandle: SavedStateHandle
) :
    BaseViewModel<FavoritesContract.Event, FavoritesContract.State, FavoritesContract.Effect>() {
    override fun setInitialState(): FavoritesContract.State {
        return FavoritesContract.State()
    }

    val data = savedStateHandle.toRoute<Routes.Favorites>()
    val userId = data.userId


    override fun handleEvent(event: FavoritesContract.Event) {
        when (event) {
            is FavoritesContract.Event.LoadFavorites -> handleLoadFavorites(event.userId)
            is FavoritesContract.Event.NavigateToDetails -> handleNavigateToDetails(
                event.place,
                event.userId
            )
        }
    }

    private fun handleNavigateToDetails(place: PlaceDto, userId: Int) {
        setEffect {
            FavoritesContract.Effect.NavigateToDetails(place, userId)
        }
    }

    private fun handleLoadFavorites(userId: Int) {
        setState { copy(isLoading = true, error = null) }
        viewModelScope.launch {
            useCase(userId).collect {
                it.onSuccess { favorites ->
                    setState {
                        copy(
                            isLoading = false,
                            error = null,
                            favorites = favorites,
                            subtitleError = null
                        )
                    }
                }.onFailure { error ->
                    val e = error as CustomError
                    setState {
                        copy(
                            isLoading = false,
                            error = e.titleRes,
                            subtitleError = e.subtitleRes
                        )
                    }
                    if(e is CustomError.Unknown)
                        setEffect {
                            FavoritesContract.Effect.ShowError(e.titleRes)
                        }
                }
            }
        }
    }
}