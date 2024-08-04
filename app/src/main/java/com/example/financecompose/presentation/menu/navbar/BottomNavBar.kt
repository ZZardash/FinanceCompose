package com.example.financecompose.presentation.menu.navbar

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        NavItem.Home,
        NavItem.Child,
        NavItem.Add,
        NavItem.Reports,
        NavItem.Settings
    )

    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = stringResource(id = item.title)) },
                label = { Text(stringResource(id = item.title)) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination to avoid building up a large stack of destinations
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}







