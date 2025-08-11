package com.spotq.authentication.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.theme.SpotQTheme
import com.example.core_ui.utils.Constants
import com.example.core_ui.R as CoreUiR

@Composable
fun DontOrHaveAccount(
    modifier: Modifier = Modifier,
    leading: String,
    trailing: String,
    onClick: () -> Unit
) {
    Row(
        modifier.clickable { onClick.invoke() }, horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(CoreUiR.dimen.padding_xs)
        )
    ) {
        Text(
            text = leading,
            style = AppTypography.bt6,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = Constants.ALPHA_MEDIUM)
        )

        Text(
            text = trailing,
            style = AppTypography.bt5,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )

    }
}

@Preview(showBackground = true)
@Composable
fun DontOrHaveAccountPreview() {
    SpotQTheme {
        DontOrHaveAccount(
            leading = "Don't have an account?",
            trailing = "Sign Up",
            onClick = {}
        )
    }
}