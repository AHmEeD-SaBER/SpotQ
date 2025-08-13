package com.example.core_ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core_ui.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage

@Composable
fun PlaceImage(
    modifier: Modifier = Modifier,
    image: String,
    contentScale: ContentScale = ContentScale.Crop
) {
    AsyncImage(
        model = image,
        contentDescription = null,
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(dimensionResource(R.dimen.card_vertical_corner_radius))),
        contentScale = contentScale,
        placeholder = painterResource(R.drawable.img_placeholder),
        error = painterResource(R.drawable.img_placeholder)
    )

}

@Preview
@Composable
fun PlaceImagePreview() {
    PlaceImage(
        image = "https://example.com/image.jpg",
        modifier = Modifier.fillMaxSize()
    )
}