package com.example.ui.place_details


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_ui.components.CustomAppBar
import com.example.core_ui.components.CustomTag
import com.example.core_ui.components.DistanceContainer
import com.example.core_ui.components.PlaceImage
import com.example.core_ui.components.PlaceTitle
import com.example.core_ui.components.RatingContainer
import com.example.core_ui.components.SectionHeader
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.theme.SpotQTheme
import com.example.core_ui.theme.Yellow
import com.example.core_ui.utils.Constants
import com.example.domain.dto.PlaceDto
import com.example.core_ui.R as coreUiR
import com.example.ui.R
import kotlinx.coroutines.channels.ticker
import toOneDecimalString
import java.util.Locale

@Composable
fun PlaceDetailsScreen(
    modifier: Modifier = Modifier,
    place: PlaceDto,
    state: PlaceDetailsContract.State,
    onEvent: (PlaceDetailsContract.Events) -> Unit,
    userId: Int
) {

    LaunchedEffect(Unit) {
        Log.d("PlaceDetailsScreen", "Checking if place is favorite: ${place.xid}, userId: $userId")
        onEvent(PlaceDetailsContract.Events.IsFavorite(place.xid, userId))
    }

    Scaffold() { padding ->
        val scrollState = rememberScrollState()
        Column(
            modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(dimensionResource(coreUiR.dimen.padding_sm))

        ) {
            Box(
                modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .height(dimensionResource(coreUiR.dimen.place_image_height))
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = dimensionResource(coreUiR.dimen.padding_xxl))

                ) {
                    PlaceImage(
                        image = place.imageUrl
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = Constants.ALPHA_MEDIUM)
                                ),
                            ),
                            shape = RoundedCornerShape(dimensionResource(coreUiR.dimen.card_vertical_corner_radius))

                        )
                        .clip(RoundedCornerShape(dimensionResource(coreUiR.dimen.card_vertical_corner_radius)))
                )
                CustomAppBar(
                    modifier = Modifier.padding(dimensionResource(coreUiR.dimen.padding_sm)),
                    actions = {
                        IconButton(
                            onClick = {
                                if (state.isFavorite) onEvent(
                                    PlaceDetailsContract.Events.RemoveFromFavorites(
                                        place.xid, userId
                                    )
                                )
                                else
                                    onEvent(
                                        PlaceDetailsContract.Events.AddToFavorites(
                                            place,
                                            userId
                                        )
                                    )
                            }, modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.surface,
                                    shape = CircleShape
                                )
                                .size(dimensionResource(coreUiR.dimen.icon_size_md))
                        ) {
                            Icon(
                                painter = if (state.isFavorite) painterResource(coreUiR.drawable.love_icon_field) else
                                    painterResource(coreUiR.drawable.love_icon_outlined),
                                contentDescription = stringResource(
                                    if (state.isFavorite) coreUiR.string.cd_add_to_favorites else
                                        coreUiR.string.cd_remove_from_favorites
                                ),
                                tint = MaterialTheme.colorScheme.primary
                            )

                        }
                    },
                    showSearchBar = false,

                    navigationIcon = {
                        IconButton(
                            onClick = { onEvent(PlaceDetailsContract.Events.NavigateUp) },
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.surface,
                                    shape = CircleShape
                                )
                                .size(dimensionResource(coreUiR.dimen.icon_size_md))
                        ) {
                            Icon(
                                painter = painterResource(coreUiR.drawable.arrow_back),
                                contentDescription = stringResource(coreUiR.string.cd_back),
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(end = dimensionResource(coreUiR.dimen.padding_sm))
                            )
                        }
                    }
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(
                            vertical = dimensionResource(coreUiR.dimen.spacing_lg),
                            horizontal = dimensionResource(coreUiR.dimen.padding_md)
                        )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            PlaceTitle(
                                title = place.name,
                                style = AppTypography.sh6.copy(color = Color.White)
                            )
                            Spacer(modifier = Modifier.padding(top = dimensionResource(coreUiR.dimen.padding_xs)))
                            DistanceContainer(
                                distance = place.distance.toOneDecimalString(Locale.getDefault()),
                                icon = {
                                    Icon(
                                        painter = painterResource(R.drawable.location),
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                },
                                style = AppTypography.bt6.copy(color = Color.White)
                            )
                        }
                        RatingContainer(
//                        modifier = Modifier.align(Alignment.CenterHorizontally),
                            rate = place.rate,
                            style = AppTypography.bt3.copy(color = Color.White, fontSize = 20.sp),
                            icon = {
                                Icon(
                                    painter = painterResource(R.drawable.star_icon),
                                    contentDescription = null,
                                    tint = Yellow,
                                    modifier = Modifier.size(
                                        dimensionResource(coreUiR.dimen.icon_size_sm)
                                    )
                                )
                            },
                        )

                    }
                }

            }
            Column(
                modifier = Modifier.padding(dimensionResource(coreUiR.dimen.spacing_md))
            ) {

                Spacer(modifier = Modifier.height(dimensionResource(coreUiR.dimen.spacing_sm)))
                SectionHeader(
                    title = stringResource(coreUiR.string.label_category),
                    titleStyle = AppTypography.bt2,
                    showTrailing = false
                )
                Spacer(modifier = Modifier.height(dimensionResource(coreUiR.dimen.spacing_sm)))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(coreUiR.dimen.padding_sm)),
                    verticalArrangement = Arrangement.spacedBy(
                        dimensionResource(coreUiR.dimen.spacing_md)
                    )
                ) {
                    place.categories.forEach { category ->
                        CustomTag(
                            modifier = Modifier.padding(dimensionResource(coreUiR.dimen.padding_sm)),
                            text = category,
                            style = AppTypography.bt6.copy(color = MaterialTheme.colorScheme.onSurface)
                        )
                    }

                }
                Spacer(modifier = Modifier.height(dimensionResource(coreUiR.dimen.spacing_md)))
                SectionHeader(
                    title = stringResource(coreUiR.string.label_description),
                    titleStyle = AppTypography.bt2,
                    showTrailing = false
                )
                Spacer(modifier = Modifier.height(dimensionResource(coreUiR.dimen.spacing_sm)))
                Text(
                    text = place.description ?: "",
                    style = AppTypography.bt9

                )
                Spacer(modifier = Modifier.height(dimensionResource(coreUiR.dimen.spacing_md)))
                SectionHeader(
                    title = stringResource(coreUiR.string.label_full_address),
                    titleStyle = AppTypography.bt3,
                    showTrailing = false
                )
                Spacer(modifier = Modifier.height(dimensionResource(coreUiR.dimen.spacing_sm)))
                Text(
                    text = place.fullAddress ?: "",
                    style = AppTypography.bt9

                )
            }

        }

    }


}

@Preview
@Composable
fun PlaceDetailsScreenPreview() {
    SpotQTheme {
        PlaceDetailsScreen(
            place = PlaceDto(
                xid = "123",
                name = "Test Place",
                latitude = 0.0,
                longitude = 0.0,
                kinds = "test",
                rate = 4,
                categories = listOf(
                    "Category1",
                    "Category2",
                    "Category3",
                    "Category4",
                    "Category5",
                    "Category6",
                    "Category7",
                    "Category8"
                ),
                description = "This is a test description for the place. It can be quite long and should wrap properly in the UI.",
            ),
            state = PlaceDetailsContract.State(isFavorite = false),
            onEvent = {},
            userId = 1
        )
    }
}


