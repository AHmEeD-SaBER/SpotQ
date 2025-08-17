package com.example.ui

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.core_ui.base.BaseViewModel
import com.example.core_ui.utils.Constants.KEY_USER_AUTHENTICATED
import com.example.core_ui.utils.Constants.KEY_USER_EMAIL
import com.example.core_ui.utils.Constants.KEY_USER_ID
import com.example.core_ui.utils.Constants.KEY_USER_NAME
import com.example.core_ui.utils.Routes
import com.example.domain.usecases.IGetUserProfileUseCase
import com.example.errors.CustomError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase: IGetUserProfileUseCase,
    private val sharedPreferences: SharedPreferences,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ProfileContract.Event, ProfileContract.State, ProfileContract.Effect>() {

    override fun setInitialState(): ProfileContract.State {
        return ProfileContract.State()
    }

    val data = savedStateHandle.toRoute<Routes.Profile>()
    val userId = data.userId

    init {
        handleEvent(ProfileContract.Event.LoadProfile)
    }

    override fun handleEvent(event: ProfileContract.Event) {
        when (event) {
            ProfileContract.Event.LoadProfile -> handleLoadProfile()
            ProfileContract.Event.Logout -> handleLogout()
        }
    }

    private fun handleLogout() {
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    sharedPreferences.edit {
                        remove(KEY_USER_NAME)
                        remove(KEY_USER_EMAIL)
                        remove(KEY_USER_ID)
                        putBoolean(KEY_USER_AUTHENTICATED, false)
                    }
                }

                setState {
                    copy(
                        userName = "",
                        userEmail = "",
                        isLoading = false,
                        error = null
                    )
                }
                setEffect { ProfileContract.Effect.NavigateToLogin }
            } catch (e: Exception) {
                setState {
                    copy(
                        isLoading = false,
                        error = CustomError.Unknown().titleRes
                    )
                }
                setEffect {
                    ProfileContract.Effect.ShowError(
                        e.message?.hashCode() ?: 0
                    )
                }
            }
        }
    }

    private fun handleLoadProfile() {
        setState { copy(isLoading = true, error = null) }
        viewModelScope.launch {
            useCase(userId).collect { result ->
                result.onSuccess { user ->
                    setState {
                        copy(
                            userName = user?.name ?: "",
                            userEmail = user?.email ?: "",
                            isLoading = false
                        )
                    }
                }.onFailure { error ->
                    val e = error as CustomError
                    setState {
                        copy(
                            isLoading = false,
                            error = e.titleRes
                        )
                    }
                    setEffect { ProfileContract.Effect.ShowError(error.message?.toInt() ?: 0) }
                }
            }
        }
    }
}