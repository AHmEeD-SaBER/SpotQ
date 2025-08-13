package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.R
import com.example.domain.dto.PlaceDto
import com.example.ui.PlacesContract

@Composable
fun VerticalList(
    modifier: Modifier = Modifier,
    items: List<PlaceDto>,
    onEvent: (PlacesContract.Events) -> Unit = {}
) {
    Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_md))) {
        items.forEach { place ->
            HorizontalPlaceCard(
                modifier = modifier, place = place,
                onClick = {
                    onEvent(PlacesContract.Events.PlaceClicked(place))
                }
            )

        }
    }


}

@Preview
@Composable
fun VerticalListPreview() {
    VerticalList(
        items = listOf(
            PlaceDto(
                xid = "1",
                name = "Place 1",
                description = "Description 1",
                imageUrl = "",
                latitude = 1.1,
                longitude = 2.2,
                rate = 4,
                kinds = "Nature"
            ),
            PlaceDto(
                xid = "2",
                name = "Place 2",
                description = "Description 2",
                imageUrl = "",
                latitude = 1.1,
                longitude = 2.2,
                rate = 4,
                kinds = "Nature"
            ),
            PlaceDto(
                xid = "3",
                name = "Place 3",
                description = "Description 3",
                imageUrl = "",
                latitude = 1.1,
                longitude = 2.2,
                rate = 4,
                kinds = "Nature"
            ),
        )
    )
}