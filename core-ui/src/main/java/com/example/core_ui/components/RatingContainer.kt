package com.example.core_ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.R

@Composable
fun RatingContainer(
    modifier: Modifier = Modifier,
    rate: Int,
    icon: @Composable () -> Unit,
    style: TextStyle
) {
    Row(modifier) {
        icon()
        Text(
            modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_xs)),
            text = rate.toString(),
            style = style
        )
    }


}

@Preview
@Composable
fun RatingContainerPreview() {
    RatingContainer(
        rate = 4,
        icon = { /* Replace with your icon composable */ },
        style = TextStyle.Default
    )
}