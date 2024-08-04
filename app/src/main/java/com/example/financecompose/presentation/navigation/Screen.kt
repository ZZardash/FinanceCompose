package com.example.financecompose.presentation.navigation

sealed class Screen(val route: String){
    data object IntroScreen: Screen(route = "intro_screen")
    data object LoginScreen: Screen(route = "login_screen")
    data object RegisterScreen: Screen(route = "register_screen")
    data object ForgotPasswordScreen: Screen(route = "forgot_password_screen")
    data object ChildLoginScreen: Screen(route = "child_login_screen")
    data object ProfileScreen: Screen(route = "profile_screen")
    data object PreferencesScreen: Screen(route = "preferences_screen")
    data object HomeScreen: Screen(route = "home_screen")
}