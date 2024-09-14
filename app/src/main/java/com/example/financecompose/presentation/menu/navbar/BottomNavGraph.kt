package com.example.financecompose.presentation.menu.navbar

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.financecompose.presentation.menu.child.ui.ChildScreen
import com.example.financecompose.presentation.menu.transaction.ui.AddScreen
import com.example.financecompose.presentation.menu.home.ui.HomeScreen
import com.example.financecompose.presentation.menu.reports.ui.ReportsScreen
import com.example.financecompose.presentation.menu.settings.ui.SettingsScreen

@Composable
fun BottomNavGraph(bottomNavController: NavHostController, parentNavController: NavHostController) {
    NavHost(
        navController = bottomNavController,
        startDestination = NavItem.Home.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(NavItem.Home.route) { HomeScreen(parentNavController) }
        composable(NavItem.Transaction.route) { AddScreen(bottomNavController) }
        composable(NavItem.Child.route) { ChildScreen(bottomNavController) }
        composable(NavItem.Reports.route) { ReportsScreen(bottomNavController) }
        composable(NavItem.Settings.route) { SettingsScreen(bottomNavController, parentNavController) }
    }
}
