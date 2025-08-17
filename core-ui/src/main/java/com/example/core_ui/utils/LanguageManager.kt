package com.example.core_ui.utils
import android.content.Context
import androidx.core.content.edit

object LanguageManager {

    private const val PREFS_NAME = "settings"
    private const val KEY_LANG = "lang"
    private const val DEFAULT_LANG = "en"

    fun setLanguage(context: Context, lang: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit { putString(KEY_LANG, lang) }
    }

    fun getSavedLanguage(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_LANG, DEFAULT_LANG) ?: DEFAULT_LANG
    }
}
