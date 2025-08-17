package com.example.core_ui.components

import androidx.activity.ComponentActivity
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.core_ui.utils.LanguageManager

@Composable
fun LanguageSwitcher() {
    val context = LocalContext.current
    val activity = context as? ComponentActivity

    Button(onClick = {
        val currentLang = LanguageManager.getSavedLanguage(context)
        val newLang = if (currentLang == "en") "ar" else "en"
        LanguageManager.setLanguage(context, newLang)
        activity?.recreate()
    }) {
        Text("🌐 Switch Language")
    }
}

