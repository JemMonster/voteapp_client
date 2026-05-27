package com.example.voteapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.voteapp.presentation.createvoting.CreateVotingScreen
import com.example.voteapp.presentation.feed.FeedScreen
import com.example.voteapp.presentation.history.VotingHistoryScreen
import com.example.voteapp.presentation.profile.ProfileScreen
import com.example.voteapp.presentation.signin.SignInScreen
import com.example.voteapp.presentation.signin.SignUpScreen
import com.example.voteapp.presentation.votingdetail.VotingDetailScreen

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "signin") {
        composable("signin") { SignInScreen(navController) }
        composable("signup") { SignUpScreen(navController) }

        composable("feed") { FeedScreen(navController) }
        composable("createVoting") { CreateVotingScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("history") { VotingHistoryScreen(navController) }

        composable("votingDetail/{votingId}") { backStackEntry ->
            val votingId = backStackEntry.arguments?.getString("votingId")
                ?: return@composable
            VotingDetailScreen(navController = navController, votingId = votingId)
        }
    }
}

