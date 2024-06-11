package com.example.financecompose.domain.repository

interface FinanceRepository {
    suspend fun isEmailExists(email: String): Boolean
}