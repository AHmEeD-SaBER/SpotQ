package com.spotq.authentication.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.utils.Constants
import com.example.core_ui.R as CoreUiR
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.core_ui.theme.SpotQTheme

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    label: String = "",
    value: String = "",
    placeholder: String = stringResource(CoreUiR.string.placeholder_email),
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null,
    isPasswordField: Boolean = false,
    isPasswordVisible: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    height: Dp? = dimensionResource(CoreUiR.dimen.text_field_height)
) {
    Column(modifier) {
        Text(
            text = label,
            style = AppTypography.bt7,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = dimensionResource(CoreUiR.dimen.padding_xs))
        )
        OutlinedTextField(
            value = value,
            textStyle = AppTypography.bt4,
            onValueChange = onValueChange,
            isError = isError,
            shape = RoundedCornerShape(dimensionResource(CoreUiR.dimen.text_field_corner_radius)),
            placeholder = {
                Text(
                    text = placeholder,
                    style = AppTypography.bt7,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = Constants.ALPHA_MEDIUM)
                )
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.tertiary.copy(alpha = Constants.ALPHA_MEDIUM),
                errorBorderColor = MaterialTheme.colorScheme.error,
                disabledBorderColor = MaterialTheme.colorScheme.tertiary.copy(alpha = Constants.ALPHA_MEDIUM),
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            ),
            supportingText = if (isError && errorMessage != null) {
                { Text(text = errorMessage) }
            } else null,
            singleLine = true,
            visualTransformation = if (isPasswordField && !isPasswordVisible) {
                androidx.compose.ui.text.input.PasswordVisualTransformation()
            } else {
                androidx.compose.ui.text.input.VisualTransformation.None
            },
            keyboardOptions = if (isPasswordField) {
                androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Password)
            } else {
                androidx.compose.foundation.text.KeyboardOptions.Default
            },
            trailingIcon = trailingIcon
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview() {
    SpotQTheme {
        CustomTextField(
            label = "Email",
            height = 45.dp,
            onValueChange = {},
            isError = false,
            errorMessage = null,
        )
    }
}
