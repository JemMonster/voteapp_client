package com.example.voteapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHostController

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "signin") {
        composable("signin") { SignInScreen(navController) }
        composable("feed") { FeedScreen(navController) }
        // Add more screens...
    }

}

