package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.domain.dto.PlaceDto
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
import toOneDecimalString
import java.util.Locale
import com.example.ui.R as RUi

@Composable
fun HorizontalPlaceCard(modifier: Modifier = Modifier, place: PlaceDto, onClick: () -> Unit = {}) {
    Row(
        modifier
            .clip(RoundedCornerShape(dimensionResource(R.dimen.card_vertical_corner_radius)))
            .clickable { onClick() }
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.horizontal_card_height))
            .background(
                MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(dimensionResource(R.dimen.card_vertical_corner_radius))
            )
            .border(
                width = dimensionResource(R.dimen.border_width_thin),
                color = MaterialTheme.colorScheme.outline.copy(alpha = Constants.ALPHA_MEDIUM),
                shape = RoundedCornerShape(dimensionResource(R.dimen.card_vertical_corner_radius))
            )
            .padding(dimensionResource(R.dimen.padding_xxs))


    ) {
        Box(
            modifier = Modifier
                .height(dimensionResource(R.dimen.horizontal_card_image_height))
                .width(
                    dimensionResource(R.dimen.horizontal_card_image_width)
                )
        ) {
            PlaceImage(
                image = place.imageUrl, contentScale = ContentScale.Crop
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = dimensionResource(R.dimen.padding_sm))
                .weight(1f)
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                PlaceTitle(
                    title = place.name,
                    style = AppTypography.bt3
                )
                CustomTag(
                    text = place.kinds
                )
                DistanceContainer(
                    distance = place.distance.toOneDecimalString(Locale.ENGLISH),
                    icon = {
                        Icon(
                            painter = painterResource(RUi.drawable.location),
                            contentDescription = null,
                        )
                    },
                    style = AppTypography.bt9
                )

            }
            RatingContainer(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        vertical = dimensionResource(R.dimen.padding_sm),
                        horizontal = dimensionResource(R.dimen.padding_md)
                    ),
                rate = place.rate,
                style = AppTypography.bt8,
                icon = {
                    Icon(
                        painter = painterResource(com.example.ui.R.drawable.star_icon),
                        tint = Yellow,
                        contentDescription = null,
                    )
                }

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
                name = "Beautiful Parkhfjkldshfjdshfklsaflsflkjsahf",
                kinds = "Nature",
                imageUrl = "https://example.com/image.jpg",
                distance = 2.5,
                rate = 4,
                latitude = 1.1,
                longitude = 2.2,
            )
        )
    }
}