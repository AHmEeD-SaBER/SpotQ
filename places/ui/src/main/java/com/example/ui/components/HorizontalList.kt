package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.core_ui.R
import com.example.domain.dto.PlaceDto
import com.example.ui.PlacesContract

@Composable
fun HorizontalList(modifier: Modifier = Modifier, items: List<PlaceDto>, onEvent: (PlacesContract.Events) -> Unit = {}) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_md))) {
        items(items) { place ->
            HorizontalPlaceCard(
                modifier = modifier, place = place,
                onClick = {
                    onEvent(PlacesContract.Events.PlaceClicked(place))
                }
            )

        }
    }

}







