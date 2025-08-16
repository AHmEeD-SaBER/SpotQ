package com.example.ui.place_details

import androidx.lifecycle.viewModelScope
import com.example.core_ui.base.BaseViewModel
import com.example.domain.dto.PlaceDto
import com.example.domain.models.AddToFavoritesResponse
import com.example.domain.usecases.add_to_favorites.IAddToFavoritesUseCase
import com.example.domain.usecases.is_favorite.IsFavoriteUseCase
import com.example.domain.usecases.remove_from_favorites.RemoveFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PlaceDetailsViewModel @Inject constructor(
    private val addToFavorites: IAddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val isFavoriteUseCaseImpl: IsFavoriteUseCase,
) : BaseViewModel<PlaceDetailsContract.Events, PlaceDetailsContract.State, PlaceDetailsContract.Effect>() {
    override fun setInitialState(): PlaceDetailsContract.State {
        return PlaceDetailsContract.State()
    }


    override fun handleEvent(event: PlaceDetailsContract.Events) {
        when (event) {
            is PlaceDetailsContract.Events.AddToFavorites -> handleAddToFavorites(
                event.place,
                event.userId
            )

            is PlaceDetailsContract.Events.IsFavorite -> handleIsFavorite(
                event.placeId,
                event.userId
            )

            PlaceDetailsContract.Events.NavigateUp -> handleNavigateUp()
            is PlaceDetailsContract.Events.RemoveFromFavorites -> handleRemoveFromFavorites(
                event.placeId,
                event.userId
            )
        }
    }

    private fun handleRemoveFromFavorites(placeId: String, userId: Int) {
        viewModelScope.launch {
            val result = removeFromFavoritesUseCase(placeId, userId)
            if (result is AddToFavoritesResponse.Success) {
                setState {
                    copy(isFavorite = false)
                }
                setEffect {
                    PlaceDetailsContract.Effect.ShowSuccess(result.message)
                }
            } else if (result is AddToFavoritesResponse.Error) {
                setEffect {
                    PlaceDetailsContract.Effect.ShowError(result.error)
                }
            }
        }
    }

    private fun handleNavigateUp() {
        setEffect {
            PlaceDetailsContract.Effect.NavigateUp
        }

    }

    private fun handleIsFavorite(placeId: String, userId: Int) {
        viewModelScope.launch {
            val isFavorite = isFavoriteUseCaseImpl(placeId, userId)
            setState {
                copy(isFavorite = isFavorite)
            }
        }
    }

    private fun handleAddToFavorites(place: PlaceDto, userId: Int) {
        viewModelScope.launch {
            val result = addToFavorites(place, userId)
            if (result is AddToFavoritesResponse.Success) {
                setState {
                    copy(isFavorite = true)
                }
                setEffect {
                    PlaceDetailsContract.Effect.ShowSuccess(result.message)
                }
            } else if (result is AddToFavoritesResponse.Error) {
                setEffect {
                    PlaceDetailsContract.Effect.ShowError(result.error)
                }
            }
        }
    }

}