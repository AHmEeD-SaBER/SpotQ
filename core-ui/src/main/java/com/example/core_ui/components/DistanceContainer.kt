package com.example.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.R

@Composable
fun DistanceContainer(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    distance: String,
    style: TextStyle
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        icon()
        Text(
            text = distance,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_xs)),
            style = style
        )
    }

}

@Preview(showBackground = true)
@Composable
fun DistanceContainerPreview() {
    DistanceContainer(
        icon = {
            Box(
                modifier = Modifier
                    .height(12.dp)
                    .width(12.dp)
                    .background(Color.Black)
            )
        },
        distance = "2.5 km",
        style = TextStyle.Default
    )
}