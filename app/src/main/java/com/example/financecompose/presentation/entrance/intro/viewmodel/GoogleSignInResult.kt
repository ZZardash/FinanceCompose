package com.example.financecompose.presentation.entrance.intro.viewmodel

import com.example.financecompose.domain.model.User

data class GoogleSignInResult(
    val data: User?,
    val errorMessage: String?,
    val isNewUser: Boolean = false,
    val isSuccess: Boolean = false
)

