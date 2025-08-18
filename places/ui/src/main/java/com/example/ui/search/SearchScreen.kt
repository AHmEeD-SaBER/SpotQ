package com.example.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.components.CustomAppBar
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.theme.SpotQTheme
import com.example.core_domain.dto.PlaceDto
import com.example.core_ui.utils.Constants
import com.example.ui.components.HorizontalPlaceCard
import com.example.core_ui.R as CoreUiR

@Composable
fun SearchScreen(
    state: SearchContract.State,
    onEvent: (SearchContract.Events) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            CustomAppBar(
                title = {
                    Column {
                        Text(
                            text = stringResource(CoreUiR.string.label_search),
                            style = AppTypography.bt8
                        )
                        state.locationName?.let { locationName ->
                            Text(
                                text = locationName,
                                style = AppTypography.bt5
                            )
                        }
                    }
                },
                navigationIcon = null,
                showSearchBar = true,
                showNavigation = false,
                searchBar = {
                    SearchBarContent(
                        modifier = Modifier.focusRequester(focusRequester),
                        query = state.searchQuery,
                        onQueryChange = { onEvent(SearchContract.Events.SearchQueryChanged(it)) },
                        onClear = { onEvent(SearchContract.Events.ClearSearch) }
                    )
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.isLoading -> {
                    LoadingContent()
                }

                state.error != null -> {
                    ErrorContent(
                        error = state.error,
                        onRetry = { onEvent(SearchContract.Events.Retry) }
                    )
                }

                state.filteredPlaces.isNotEmpty() -> {
                    PlacesListContent(
                        places = state.filteredPlaces,
                        searchQuery = state.searchQuery,
                        onPlaceClick = { onEvent(SearchContract.Events.PlaceClicked(it)) }
                    )
                }

                state.allPlaces.isNotEmpty() && state.filteredPlaces.isEmpty() && state.searchQuery.isNotEmpty() -> {
                    NoResultsContent(query = state.searchQuery)
                }

                else -> {
                    EmptyContent()
                }
            }
        }
    }
}

@Composable
private fun SearchBarContent(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                text = stringResource(CoreUiR.string.label_search_placeholder),
                style = AppTypography.bt5
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription =  stringResource(CoreUiR.string.label_search)
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = onClear) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription =  stringResource(CoreUiR.string.cd_clear)
                    )
                }
            }
        },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = Constants.ALPHA_MEDIUM)
        )
    )
}

@Composable
private fun LoadingContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(dimensionResource(CoreUiR.dimen.padding_md)))
        Text(
            text = stringResource(CoreUiR.string.cd_loading_places),
            style = AppTypography.bt4
        )
    }
}

@Composable
private fun ErrorContent(
    error: Int,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(error))
        Spacer(modifier = Modifier.height(dimensionResource(CoreUiR.dimen.padding_sm)))
        Button(onClick = onRetry) {
            Text(text = stringResource(CoreUiR.string.cd_retry))
        }
    }
}

@Composable
private fun EmptyContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(CoreUiR.dimen.padding_md)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier.size(dimensionResource(CoreUiR.dimen.icon_size_xl)),
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = Constants.ALPHA_MEDIUM)
        )
        Spacer(modifier = Modifier.height(dimensionResource(CoreUiR.dimen.padding_md)))
        Text(
            text = "No places found in your area",
            style = AppTypography.bt4,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = Constants.ALPHA_MD)
        )
    }
}

@Composable
private fun NoResultsContent(query: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(CoreUiR.dimen.padding_md)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${stringResource(CoreUiR.string.labelr_no_result_for)}\"$query\"",
            style = AppTypography.bt3,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(dimensionResource(CoreUiR.dimen.padding_sm)))
        Text(
            text = stringResource(CoreUiR.string.label_try_different_query),
            style = AppTypography.bt5,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = Constants.ALPHA_MD)
        )
    }
}

@Composable
private fun PlacesListContent(
    places: List<PlaceDto>,
    searchQuery: String,
    onPlaceClick: (PlaceDto) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(dimensionResource(CoreUiR.dimen.padding_md)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(CoreUiR.dimen.padding_sm))
    ) {
        item {
            Text(
                text = if (searchQuery.isEmpty()) {
                    "${places.size}" + stringResource(CoreUiR.string.label_places_found)
                } else {
                    "${places.size} ${stringResource(CoreUiR.string.label_result_for)}\"$searchQuery\""
                },
                style = AppTypography.bt4,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = dimensionResource(CoreUiR.dimen.padding_sm))
            )
        }

        items(places) { place ->
            HorizontalPlaceCard(
                place = place,
                onClick = { onPlaceClick(place) }
            )
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    val samplePlaces = listOf(
        PlaceDto(
            xid = "1",
            name = "Central Park",
            kinds = "Park",
            latitude = 1.0,
            longitude = 1.0,
            rate = 3
        ),
        PlaceDto(
            xid = "2",
            name = "Museum of Modern Art",
            kinds = "Museum",
            latitude = 2.0,
            longitude = 2.0,
            rate = 4
        )
    )

    SpotQTheme {
        SearchScreen(
            state = SearchContract.State(
                isLoading = false,
                error = null,
                searchQuery = "park",
                allPlaces = samplePlaces,
                filteredPlaces = listOf(samplePlaces[0]),
                locationName = "New York"
            ),
            onEvent = {}
        )
    }
}