package com.example.core_ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.theme.SpotQTheme
import com.example.core_ui.utils.Constants
import com.example.core_ui.R as CoreUiR

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    label: String? = "",
    value: String = "",
    placeholder: String = stringResource(CoreUiR.string.placeholder_email),
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null,
    isPasswordField: Boolean = false,
    isPasswordVisible: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    height: Dp = dimensionResource(CoreUiR.dimen.text_field_height),
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier) {
        // Static label above the text field
        if (label != null) {
            Text(
                text = label,
                style = AppTypography.bt7,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = dimensionResource(CoreUiR.dimen.padding_xs))
            )
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = AppTypography.bt4.copy(color = MaterialTheme.colorScheme.onSurface),
            interactionSource = interactionSource,
            visualTransformation = if (isPasswordField && !isPasswordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardOptions = if (isPasswordField) {
                KeyboardOptions(keyboardType = KeyboardType.Password)
            } else {
                KeyboardOptions.Default
            },
            decorationBox = { innerTextField ->
                OutlinedTextFieldDefaults.DecorationBox(
                    value = value,
                    innerTextField = innerTextField,
                    singleLine = true,
                    enabled = true,
                    isError = isError,
                    visualTransformation = VisualTransformation.None,
                    placeholder = {
                        Text(
                            text = placeholder,
                            style = AppTypography.bt7,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = Constants.ALPHA_MEDIUM)
                        )
                    },
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    // REMOVE supportingText from here to prevent height jump
                    supportingText = null,
                    interactionSource = interactionSource,
                    label = null,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.tertiary.copy(alpha = Constants.ALPHA_MEDIUM),
                        errorBorderColor = MaterialTheme.colorScheme.error,
                        disabledBorderColor = MaterialTheme.colorScheme.tertiary.copy(alpha = Constants.ALPHA_MEDIUM),
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    contentPadding = contentPadding,
                    container = {
                        OutlinedTextFieldDefaults.Container(
                            enabled = true,
                            isError = isError,
                            interactionSource = interactionSource,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.tertiary.copy(alpha = Constants.ALPHA_MEDIUM),
                                errorBorderColor = MaterialTheme.colorScheme.error
                            ),
                            shape = RoundedCornerShape(dimensionResource(CoreUiR.dimen.text_field_corner_radius))
                        )
                    }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
        )

        // Error text outside so it doesn't change the text field height
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = AppTypography.bt7,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview() {
    SpotQTheme {
        CustomTextField(
            label = "Email",
            height = 56.dp,
            value = "",
            onValueChange = {},
            isError = true,
            errorMessage = "Invalid email"
        )
    }
}
