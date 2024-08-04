package com.example.financecompose.presentation.navigation

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
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
import com.example.financecompose.presentation.menu.home.ui.ProfileScreen
import com.example.financecompose.presentation.entrance.intro.viewmodel.GoogleAuthUiClient
import com.example.financecompose.presentation.menu.navbar.MainScreenWithNavBar
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

@Composable
fun ScreensNavigation() {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

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
        addComposable(Screen.HomeScreen.route, navController) { MainScreenWithNavBar() }


        composable(Screen.ProfileScreen.route) {
            val googleAuthUiClient = GoogleAuthUiClient(
                context = context,
                oneTapClient = Identity.getSignInClient(context)
            )
            ProfileScreen(
                userData = googleAuthUiClient.getSignedInUser(),
                onSignOut = {
                    coroutineScope.launch {
                        googleAuthUiClient.signOut()
                        Toast.makeText(
                            context,
                            "Signed out",
                            Toast.LENGTH_LONG
                        ).show()
                        navController.popBackStack()
                    }
                }
            )
        }
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
