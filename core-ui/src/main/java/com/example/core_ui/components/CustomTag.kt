package com.example.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.R
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.theme.SpotQTheme

@Composable
fun CustomTag(modifier: Modifier = Modifier, text: String) {

    Box(
        modifier
            .background(
                MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(
                    dimensionResource(R.dimen.tag_corner_radius)
                )
            )
            .padding(
                vertical = dimensionResource(R.dimen.padding_xs),
                horizontal = dimensionResource(R.dimen.padding_xs)
            )
    )
    {
        Text(
            text = text,
            style = AppTypography.bt9,


            )
    }

}

@Preview(showBackground = true)
@Composable
fun CustomTagPreview() {
    SpotQTheme {
        CustomTag(text = "Tag")
    }
}