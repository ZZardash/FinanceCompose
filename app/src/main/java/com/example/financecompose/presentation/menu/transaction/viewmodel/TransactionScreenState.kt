package com.example.financecompose.presentation.menu.transaction.viewmodel

import androidx.compose.runtime.Immutable

@Immutable
data class TransactionScreenState(
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val isLoading: Boolean = false,  // To handle loading state
    val showSuccessAnimation: Boolean = false  // To show animation
)
