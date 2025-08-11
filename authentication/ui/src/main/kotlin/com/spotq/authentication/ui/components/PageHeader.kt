package com.spotq.authentication.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.R as CoreUiR

@Composable
fun PageHeader(modifier: Modifier = Modifier, title: String, subtitle: String) {
    Column(modifier) {
        Text(
            modifier = Modifier.padding(bottom = dimensionResource(CoreUiR.dimen.padding_sm)),
            text = title,
            style = AppTypography.h7
        )

        Text(
            text = subtitle,
            style = AppTypography.bt6
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PageHeaderPreview() {
    PageHeader(
        title = "Welcome to SpotQ",
        subtitle = "Your journey starts here"
    )
}