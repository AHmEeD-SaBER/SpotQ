package com.example.core_ui.components

import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.core_ui.theme.AppTypography
import com.example.core_ui.utils.LanguageManager

@Composable
fun LanguageSwitcher(
    activityContext: Context? = null
) {
    val localContext = LocalContext.current
    val context = activityContext ?: localContext
    val activity = context as? ComponentActivity

    Button(
        onClick = {
            val currentLang = LanguageManager.getSavedLanguage(context)
            val newLang = if (currentLang == "en") "ar" else "en"
            LanguageManager.setLanguage(context, newLang)
            activity?.recreate()
            Log.d("LanguageSwitcher", "Current Language: $currentLang, Display Text: $newLang")
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
        ),
    ) {
        val currentLang = LanguageManager.getSavedLanguage(context)
        val displayText = if (currentLang == "ar") "🌐 English" else "🌐 العربية"



        Text(displayText, style = AppTypography.bt7, color = Color.Black)
    }
}