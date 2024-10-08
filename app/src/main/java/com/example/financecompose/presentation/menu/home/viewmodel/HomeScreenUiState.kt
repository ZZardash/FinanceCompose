package com.example.financecompose.presentation.menu.home.viewmodel

import androidx.compose.runtime.Immutable

@Immutable
data class HomeScreenUiState(
    val email: String = "",
    val errorMessage: String? = null,
    val successMessage: String? = null
)
