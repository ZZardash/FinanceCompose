package com.example.financecompose.domain.repository

import com.example.financecompose.domain.model.User
import kotlinx.coroutines.flow.Flow

interface FinanceRepository {
    suspend fun isEmailExists(email: String): Boolean
    suspend fun getUser(userId: String): User?
    suspend fun createUser(userData: User): User
    suspend fun fetchUserCurrency(userId: String): String?
    suspend fun updateUserCurrency(userId: String, newCurrency: String)
    suspend fun getUserCurrencySnapshotListener(userId: String): Flow<String>
}