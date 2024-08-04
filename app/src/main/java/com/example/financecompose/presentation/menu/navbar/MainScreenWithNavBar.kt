package com.example.financecompose.presentation.menu.navbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreenWithNavBar() {
    // Initialize the NavHostController
    val navController = rememberNavController()

    // Set up Scaffold with BottomNavBar and BottomNavGraph
    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            BottomNavGraph(navController = navController)
        }
    }
}