package com.example.voteapp.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable

@Composable
fun RowScope.ThemeToggleSwitch(
    isDarkMode: Boolean,
    onThemeChanged: (Boolean) -> Unit
) {
    IconButton(onClick = { onThemeChanged(!isDarkMode) }) {
        Icon(
            imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
            contentDescription = if (isDarkMode) "Переключить на светлую тему" else "Переключить на темную тему"
        )
    }
}
