package com.example.core_ui.components

import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.core_ui.R

@Composable
fun PlaceTitle(
    modifier: Modifier = Modifier,
    title: String,
    style: TextStyle,
    maxWidth: Dp = dimensionResource(R.dimen.title_max_width)
) {
    Text(
        text = title,
        modifier = modifier.widthIn(
            max = maxWidth
        ),
        style = style,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )

}

@Preview(showBackground = true)
@Composable
fun PlaceTitlePreview() {
    PlaceTitle(
        title = "This is a very long title that should be truncated",
        style = TextStyle.Default,
        maxWidth = dimensionResource(R.dimen.title_max_width)
    )
}