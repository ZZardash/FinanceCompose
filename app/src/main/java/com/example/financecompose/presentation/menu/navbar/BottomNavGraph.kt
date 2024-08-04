package com.example.financecompose.presentation.menu.navbar

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.financecompose.presentation.menu.child.ui.ChildScreen
import com.example.financecompose.presentation.menu.add.ui.AddScreen
import com.example.financecompose.presentation.menu.home.ui.HomeScreen
import com.example.financecompose.presentation.menu.reports.ui.ReportsScreen
import com.example.financecompose.presentation.menu.settings.ui.SettingsScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavItem.Home.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(NavItem.Home.route) { HomeScreen(navController) }
        composable(NavItem.Add.route) { AddScreen(navController) }
        composable(NavItem.Child.route) { ChildScreen(navController) }
        composable(NavItem.Reports.route) { ReportsScreen(navController) }
        composable(NavItem.Settings.route) { SettingsScreen(navController) }
    }
}