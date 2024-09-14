package com.example.financecompose.presentation.menu.navbar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.financecompose.R

sealed class NavItem(val route: String, @StringRes val title: Int, val icon: ImageVector) {
    data object Home : NavItem("home", R.string.home, Icons.Default.Home)
    data object Child : NavItem("child", R.string.children, Icons.Default.Face)
    data object Transaction : NavItem("transaction", R.string.transaction, Icons.Default.Add)
    data object Reports : NavItem("reports", R.string.reports, Icons.Default.Info)
    data object Settings : NavItem("settings", R.string.settings, Icons.Default.Settings)
}
