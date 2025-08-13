package com.example.ui.components

import android.graphics.Paint.Align
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.R
import com.example.core_ui.components.CustomTag
import com.example.core_ui.components.DistanceContainer
import com.example.core_ui.components.PlaceImage
import com.example.core_ui.components.PlaceTitle
import com.example.core_ui.components.RatingContainer
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.theme.SpotQTheme
import com.example.core_ui.theme.Yellow
import com.example.core_ui.utils.Constants
import com.example.domain.dto.PlaceDto
import com.example.ui.R as UiR

@Composable
fun HorizontalPlaceCard(
    modifier: Modifier = Modifier,
    place: PlaceDto,
    onClick: () -> Unit = {}
) {
    Box(
        modifier
            .clip(RoundedCornerShape(dimensionResource(R.dimen.card_horizontal_corner_radius)))
            .background(
                MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(dimensionResource(R.dimen.card_horizontal_corner_radius))
            )
            .height(dimensionResource(R.dimen.horizontal_card_height))
            .width(
                dimensionResource(R.dimen.horizontal_card_width)
            )
            .border(
                width = dimensionResource(R.dimen.border_width_thin),
                color = MaterialTheme.colorScheme.outline.copy(alpha = Constants.ALPHA_MEDIUM),
                shape = RoundedCornerShape(dimensionResource(R.dimen.card_horizontal_corner_radius))
            )
            .clickable {
                onClick()
            }
    ) {
        Box(
            modifier = Modifier
                .height(dimensionResource(R.dimen.horizontal_card_image_height))
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimensionResource(R.dimen.card_horizontal_corner_radius)))
                .padding(dimensionResource(R.dimen.padding_xxs))
                .background(
                    Color.Transparent,
                    shape = RoundedCornerShape(dimensionResource(R.dimen.card_horizontal_corner_radius))
                )


        ) {
            place.imageUrl?.let {
                Log.d("HorizontalPlaceCard", "Image URL: $it")
                PlaceImage(
                    image = it, contentScale = ContentScale.Crop
                )
            }
        }
        Box(
            modifier = Modifier.padding(
                vertical = dimensionResource(R.dimen.padding_sm),
                horizontal = dimensionResource(R.dimen.padding_md)
            )
        ) {
            CustomTag(
                text = place.kinds
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(
                    vertical = dimensionResource(R.dimen.padding_lg),
                    horizontal = dimensionResource(R.dimen.padding_md)
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                PlaceTitle(
                    title = place.name,
                    style = AppTypography.bt5
                )
                DistanceContainer(
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_xs)),
                    distance = place.distance.toString(),
                    icon = {
                        Icon(
                            painter = painterResource(UiR.drawable.location),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    style = AppTypography.bt9,
                )

            }
            RatingContainer(
                rate = place.rate,
                icon = {
                    Icon(
                        painter = painterResource(UiR.drawable.star_icon),
                        contentDescription = null,
                        tint = Yellow
                    )
                },
                style = AppTypography.bt8,
            )
        }

    }

}

@Preview
@Composable
fun HorizontalPlaceCardPreview() {
    SpotQTheme {
        HorizontalPlaceCard(
            place = PlaceDto(
                xid = "1",
                name = "Sample Place",
                imageUrl = "https://www.pexels.com/search/places/",
                kinds = "Restaurant",
                latitude = 1.2345,
                longitude = 1.2345,
                rate = 5,
            )
        )
    }
}