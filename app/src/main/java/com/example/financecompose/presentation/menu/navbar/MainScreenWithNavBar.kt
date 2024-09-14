package com.example.financecompose.presentation.menu.navbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreenWithNavBar(parentNavController: NavHostController) {
    val bottomNavController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavBar(bottomNavController) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            BottomNavGraph(bottomNavController, parentNavController)
        }
    }
}