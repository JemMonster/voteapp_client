package com.example.voteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.voteapp.presentation.theme.ThemeViewModel
import com.example.voteapp.ui.theme.LocalThemeManager
import com.example.voteapp.ui.theme.ThemeManager
import com.example.voteapp.ui.theme.VoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val themeManager = remember { ThemeManager(context) }
            
            VoteAppThemeWrapper(themeManager = themeManager) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun VoteAppThemeWrapper(
    themeManager: ThemeManager,
    content: @Composable () -> Unit
) {
    val viewModel: ThemeViewModel = viewModel()

    LaunchedEffect(themeManager) {
        viewModel.loadThemeSettings(themeManager)
    }
    
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    
    LocalThemeManager provides themeManager
    
    VoteAppTheme(darkTheme = isDarkTheme) {
        content()
    }
}

