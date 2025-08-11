package com.example.core_ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.theme.SpotQTheme
import com.example.core_ui.R

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary,
            disabledContainerColor = MaterialTheme.colorScheme.tertiary,
            disabledContentColor = MaterialTheme.colorScheme.onTertiary
        )
    ) {
        if (!isLoading)
            Text(
                modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_sm)),
                text = text,
                color = MaterialTheme.colorScheme.surface,
                style = AppTypography.bt4.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp),
            )
        else
            CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun CustomButtonPreview() {
    SpotQTheme {
        CustomButton(
            text = "Next",
            isLoading = true,
            enabled = false,
            onClick = { }
        )
    }
}
