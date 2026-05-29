package com.example.voteapp.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.voteapp.presentation.signin.SignInViewModel
import com.example.voteapp.presentation.theme.ThemeViewModel
import com.example.voteapp.ui.theme.LocalThemeManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val themeViewModel: ThemeViewModel = hiltViewModel()
    val context = LocalContext.current
    val themeManager = LocalThemeManager.current
    val scope = rememberCoroutineScope()

    var isDarkTheme by remember { 
        mutableStateOf(themeViewModel.isDarkTheme.value) 
    }
    
    LaunchedEffect(themeViewModel.isDarkTheme) {
        themeViewModel.isDarkTheme.collect { isDarkTheme = it }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Профиль") },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when (val s = state) {
                is ProfileState.Ready -> {
                    Text(
                        text = "Email: ${s.email ?: "—"}",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }

                is ProfileState.SigningOut -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                is ProfileState.Error -> {
                    Text(
                        text = s.message,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Theme Toggle Card
            Card(
                onClick = { themeViewModel.toggleTheme(themeManager) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isDarkTheme) "Темная тема" else "Светлая тема",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Icon(
                        imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = "Переключить тему"
                    )
                }
            }

            Button(
                onClick = { viewModel.sendPasswordResetEmail { /* MVP: просто покажем toast позже */ } },
                enabled = state !is ProfileState.SigningOut,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Сменить пароль")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.signOut {
                        navController.navigate("signin") {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                },
                enabled = state !is ProfileState.SigningOut,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Выйти")
            }
        }
    }
}

