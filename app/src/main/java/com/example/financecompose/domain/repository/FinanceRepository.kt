package com.example.financecompose.domain.repository

import com.example.financecompose.domain.model.User

interface FinanceRepository {
    suspend fun isEmailExists(email: String): Boolean
    suspend fun getUser(userId: String): User?
    suspend fun createUser(userData: User): User
}