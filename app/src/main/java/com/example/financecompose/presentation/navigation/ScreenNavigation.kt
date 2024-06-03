package com.example.financecompose.presentation.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financecompose.presentation.ui.ChildLoginScreen
import com.example.financecompose.presentation.entrance.forgot_password.ui.ForgotPasswordScreen
import com.example.financecompose.presentation.entrance.intro.ui.GoogleSignInScreen
import com.example.financecompose.presentation.entrance.login.ui.LoginScreen
import com.example.financecompose.presentation.entrance.intro.ui.IntroScreen
import com.example.financecompose.presentation.entrance.intro.ui.ProfileScreen
import com.example.financecompose.presentation.entrance.intro.viewmodel.GoogleAuthUiClient
import com.example.financecompose.presentation.entrance.register.ui.RegisterScreen
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

@Composable
fun ScreensNavigation() {

    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    // Determine the start destination based on whether it's the first run
    val startDestination = if (isFirstRun(context)) {
        Screen.IntroScreen.route
    } else {
        Screen.LoginScreen.route // or any other screen you want as the default for subsequent runs
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.IntroScreen.route) {
            IntroScreen(navController)
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(Screen.ChildLoginScreen.route) {
            ChildLoginScreen(navController)
        }
        composable(Screen.ForgotPasswordScreen.route) {
            ForgotPasswordScreen(navController)
        }
        composable(Screen.RegisterScreen.route) {
            RegisterScreen(navController)
        }
        composable(Screen.GoogleSignInScreen.route) {
            GoogleSignInScreen(navController)
        }
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

fun isFirstRun(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
    val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)
    if (isFirstRun) {
        with(sharedPreferences.edit()) {
            putBoolean("isFirstRun", false)
            apply()
        }
    }
    return isFirstRun
}
