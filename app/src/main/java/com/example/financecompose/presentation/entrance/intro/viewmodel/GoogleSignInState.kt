package com.example.financecompose.presentation.entrance.intro.viewmodel

import com.example.financecompose.domain.model.User

data class GoogleSignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
    val user: User? = null,
    val isNewUser: Boolean = false,
    val navigateTo: String? = null
)