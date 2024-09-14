package com.example.financecompose.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financecompose.presentation.entrance.intro.ui.IntroScreen
import com.example.financecompose.presentation.entrance.login.ui.LoginScreen
import com.example.financecompose.presentation.entrance.child_login.ui.ChildLoginScreen
import com.example.financecompose.presentation.entrance.forgot_password.ui.ForgotPasswordScreen
import com.example.financecompose.presentation.entrance.preferences.ui.PreferencesScreen
import com.example.financecompose.presentation.entrance.register.ui.RegisterScreen
import com.example.financecompose.presentation.menu.home.ui.AllTransactionsScreen
import com.example.financecompose.presentation.menu.navbar.MainScreenWithNavBar

@Composable
fun ScreensNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.IntroScreen.route
    ) {
        composable(Screen.IntroScreen.route) { IntroScreen(navController) }
        addComposable(Screen.LoginScreen.route, navController) { LoginScreen(navController) }
        addComposable(Screen.ChildLoginScreen.route, navController) { ChildLoginScreen(navController) }
        addComposable(Screen.ForgotPasswordScreen.route, navController) { ForgotPasswordScreen(navController) }
        addComposable(Screen.RegisterScreen.route, navController) { RegisterScreen(navController) }
        addComposable(Screen.PreferencesScreen.route, navController) { PreferencesScreen(navController) }
        composable(Screen.AllTransactionsScreen.route) { AllTransactionsScreen(navController) }
        addComposable(Screen.HomeScreen.route, navController) { MainScreenWithNavBar(navController) }
    }
}

fun NavGraphBuilder.addComposable(route: String, navController: NavController, content: @Composable () -> Unit) {
    composable(
        route = route,
        enterTransition = {
            fadeIn(animationSpec = tween(500)) + scaleIn(initialScale = 0.8f, animationSpec = tween(500))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(500)) + scaleOut(targetScale = 0.8f, animationSpec = tween(500))
        }
    ) {
        content()
    }
}


