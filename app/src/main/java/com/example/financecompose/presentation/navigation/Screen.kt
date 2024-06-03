package com.example.financecompose.presentation.navigation

sealed class Screen(val route: String){
    data object IntroScreen: Screen(route = "intro_screen")
    data object LoginScreen: Screen(route = "login_screen")
    data object RegisterScreen: Screen(route = "register_screen")
    data object ForgotPasswordScreen: Screen(route = "forgot_password_screen")
    data object ChildLoginScreen: Screen(route = "child_login_screen")
    data object GoogleSignInScreen: Screen(route = "google_sign_in_screen")
    data object ProfileScreen: Screen(route = "profile_screen")
}