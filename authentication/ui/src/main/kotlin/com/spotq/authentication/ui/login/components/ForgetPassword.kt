package com.spotq.authentication.ui.login.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.R as CoreUiR

@Composable
fun ForgetPassword(modifier: Modifier = Modifier, onClick : () -> Unit = {}) {
    Text(
        modifier = modifier.clickable { onClick.invoke() },
        text = stringResource(CoreUiR.string.forget_password),
        style = AppTypography.bt7,
        fontWeight = FontWeight.Bold
    )
}