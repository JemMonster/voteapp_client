package com.example.voteapp.presentation.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.voteapp.domain.model.Voting
import com.example.voteapp.presentation.components.VotingCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(navController: NavController) {
    val votings = remember {
        listOf(
            Voting(
                id = "1",
                title = "Лучший язык программирования 2026",
                description = "Голосуйте за ваш любимый язык программирования в этом году",
                type = VotingType.SINGLE,
                status = VotingStatus.ACTIVE,
                endsAt = System.currentTimeMillis() + 1000000,
                options = listOf(
                    VotingOption("1", "TypeScript", 45),
                    VotingOption("2", "Python", 38)
                )
            )
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Лента") })
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(votings) { voting ->
                VotingCard(voting = voting)
            }
        }
    }
}

