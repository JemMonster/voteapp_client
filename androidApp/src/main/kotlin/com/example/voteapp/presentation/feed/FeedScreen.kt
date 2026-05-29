package com.example.voteapp.presentation.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.voteapp.presentation.components.VotingCard
import com.example.voteapp.presentation.theme.ThemeViewModel
import com.example.voteapp.ui.theme.LocalThemeManager
import com.example.voteapp.ui.theme.ThemeManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    navController: NavController,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val themeViewModel: ThemeViewModel = hiltViewModel()
    val context = LocalContext.current
    val themeManager = LocalThemeManager.current

    var isDarkTheme by remember { 
        mutableStateOf(themeViewModel.isDarkTheme.value) 
    }
    
    LaunchedEffect(themeViewModel.isDarkTheme) {
        themeViewModel.isDarkTheme.collect { isDarkTheme = it }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Лента") },
                actions = {
                    IconButton(onClick = { 
                        navController.navigate("search") 
                    }) {
                        Icon(Icons.Default.Search, contentDescription = "Поиск")
                    }
                    IconButton(onClick = { 
                        themeViewModel.toggleTheme(themeManager)
                    }) {
                        Icon(
                            if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Переключить тему"
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Лента") }
                )
            }
        }
    ) { paddingValues ->

        when (val s = state) {
            FeedState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is FeedState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Ошибка: ${s.message}")
                }
            }

            is FeedState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(s.votings) { voting ->
                        VotingCard(
                            voting = voting,
                            onClick = { navController.navigate("votingDetail/${voting.id}") }
                        )
                    }
                }
            }
        }
    }
}

