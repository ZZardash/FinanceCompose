package com.example.financecompose.presentation.menu.navbar

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.financecompose.R
import com.example.financecompose.ui.theme.Purple90


@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        NavItem.Home,
        NavItem.Child,
        NavItem.Transaction,
        NavItem.Reports,
        NavItem.Settings
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            if (item.route == NavItem.Transaction.route) {
                Box(
                    modifier = Modifier
                        .offset(y = (-10).dp)
                        .background(Purple90, shape = CircleShape)
                        .size(50.dp)
                        .padding(8.dp)
                        .shadow(elevation = 12.dp, shape = CircleShape)
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate(item.route) {
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route) {
                                        saveState = true
                                    }
                                }
                                // Avoid multiple copies of the same destination
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        modifier = Modifier
                            .size(56.dp) // Size of the button
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            modifier = Modifier.size(24.dp), // Adjust this size if needed
                            contentDescription = stringResource(id = R.string.transaction),
                            tint = Color.White
                        )
                    }
                }
            } else {
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = stringResource(id = item.title)
                        )
                    },
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
}







