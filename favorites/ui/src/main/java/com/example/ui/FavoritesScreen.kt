package com.example.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.components.CustomAppBar
import com.example.core_ui.theme.AppTypography
import com.example.ui.components.HorizontalPlaceCard
import com.example.core_ui.R as coreUiR

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    state: FavoritesContract.State,
    onEvent: (FavoritesContract.Event) -> Unit,
    userId: Int
) {
    LaunchedEffect(Unit) {
        onEvent(FavoritesContract.Event.LoadFavorites(userId))
    }


    Column(modifier.fillMaxSize()) {
        CustomAppBar(
            showNavigation = false,
            showSearchBar = false,
            navigationIcon = null,
            title = {
                Text(
                    text = stringResource(coreUiR.string.label_favorites),
                    modifier = modifier,
                    style = AppTypography.h7
                )
            }
        )
        Spacer(modifier = Modifier.padding(vertical = dimensionResource(coreUiR.dimen.padding_sm)))
        if (state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error != null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(state.error),
                    style = AppTypography.sh5
                )
                if (state.subtitleError != null) {
                    Text(
                        text = stringResource(state.subtitleError),
                        style = AppTypography.sh8,
                        modifier = Modifier.padding(dimensionResource(coreUiR.dimen.padding_md))
                    )
                }
            }
        } else {
            LazyColumn {
                items(state.favorites) { item ->
                    HorizontalPlaceCard(
                        place = item,
                        onClick = {
                            onEvent(
                                FavoritesContract.Event.NavigateToDetails(
                                    item,
                                    userId
                                )
                            )
                        },
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(coreUiR.dimen.padding_md),
                            vertical = dimensionResource(coreUiR.dimen.padding_sm)
                        )
                    )

                }
                item {
                    Spacer(modifier = Modifier.padding(vertical = dimensionResource(coreUiR.dimen.padding_xxl)))
                }
            }
        }
    }

}

@Preview
@Composable
fun FavoritesScreenPreview() {
    FavoritesScreen(
        state = FavoritesContract.State(),
        onEvent = {},
        userId = 1,
    )
}