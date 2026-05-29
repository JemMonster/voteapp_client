package com.example.voteapp.ui.theme

import android.content.Context
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "settings")

class ThemeManager(private val context: Context) {
    companion object {
        private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")
    }

    suspend fun isDarkThemeEnabled(): Boolean {
        return context.dataStore.data.first()[DARK_THEME_KEY] ?: false
    }

    suspend fun setDarkThemeEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_THEME_KEY] = enabled
        }
    }
}

val LocalThemeManager = staticCompositionLocalOf<ThemeManager> {
    error("No ThemeManager provided")
}
