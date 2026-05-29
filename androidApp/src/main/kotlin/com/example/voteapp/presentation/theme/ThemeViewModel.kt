package com.example.voteapp.presentation.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voteapp.ui.theme.ThemeManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ThemeViewModel : ViewModel() {
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    fun loadThemeSettings(themeManager: ThemeManager) {
        viewModelScope.launch {
            _isDarkTheme.value = themeManager.isDarkThemeEnabled()
        }
    }

    fun toggleTheme(themeManager: ThemeManager) {
        viewModelScope.launch {
            _isDarkTheme.value = !_isDarkTheme.value
            themeManager.setDarkThemeEnabled(_isDarkTheme.value)
        }
    }
}
