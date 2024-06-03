package com.example.financecompose.domain.repository

import com.example.financecompose.domain.model.User
import kotlinx.coroutines.flow.Flow

typealias Users = List<User>
interface FinanceRepository {

    fun getAllUsers(): Flow<Users>

    suspend fun getUserByEmail(email: String): User?

    suspend fun insertUser(user: User): Long

    suspend fun updateUser(user: User)

    suspend fun deleteUser(userId: Long)

    suspend fun isEmailExists(email: String): Boolean

}