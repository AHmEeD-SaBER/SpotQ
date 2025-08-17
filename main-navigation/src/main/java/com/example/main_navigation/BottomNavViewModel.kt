package com.example.main_navigation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.core_ui.base.BaseViewModel
import com.example.core_ui.utils.Routes
import com.example.main_navigation.models.BottomNavItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.core_ui.R as CoreUiR


@HiltViewModel
class BottomNavViewModel @Inject constructor(savedStateHandle: SavedStateHandle) :
    BaseViewModel<BottomNavigationContract.Event, BottomNavigationContract.State, BottomNavigationContract.Effect>() {
    override fun setInitialState(): BottomNavigationContract.State {
        return BottomNavigationContract.State()
    }

    val data = savedStateHandle.toRoute<Routes.Main>()
    val userId = data.userId

    init {
        Log.d("BottomNavViewModel", "User ID: $userId")
        setState {
            copy(
                items = listOf(
                    BottomNavItem(
                        route = Routes.Places,
                        label = CoreUiR.string.label_home,
                        selectedIcon = CoreUiR.drawable.home_icon_filled,
                        unselectedIcon = CoreUiR.drawable.home_icon_outlined,
                        isSelected = true // default
                    ),
                    BottomNavItem(
                        route = Routes.Favorites(userId), // you can inject userId later
                        label = CoreUiR.string.label_favorites,
                        selectedIcon = CoreUiR.drawable.love_icon_field,
                        unselectedIcon = CoreUiR.drawable.love_icon_outlined
                    ),
                    BottomNavItem(
                        route = Routes.Search,
                        label = CoreUiR.string.label_search,
                        selectedIcon = CoreUiR.drawable.search_filled,
                        unselectedIcon = CoreUiR.drawable.search_outlined
                    ),
                    BottomNavItem(
                        route = Routes.Profile(userId),
                        label = CoreUiR.string.label_profile,
                        selectedIcon = CoreUiR.drawable.user_filled,
                        unselectedIcon = CoreUiR.drawable.user_outlined
                    )
                )
            )
        }
    }

    override fun handleEvent(event: BottomNavigationContract.Event) {
        when (event) {
            is BottomNavigationContract.Event.OnTabSelected -> handleOnTapSelected(event.route)
        }
    }

    private fun handleOnTapSelected(route: Any) {
        setState {
            copy(
                items = uiState.value.items.map {
                    it.copy(
                        isSelected = it.route == route
                    )
                }
            )
        }
    }
}