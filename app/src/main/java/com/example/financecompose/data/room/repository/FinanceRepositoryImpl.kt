package com.example.financecompose.data.room.repository

import com.example.financecompose.data.room.dao.FinanceDao
import com.example.financecompose.data.room.mapper.toUser
import com.example.financecompose.data.room.mapper.toUserEntity
import com.example.financecompose.domain.model.User
import com.example.financecompose.domain.repository.FinanceRepository
import com.example.financecompose.domain.repository.Users
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FinanceRepositoryImpl (private val dao: FinanceDao): FinanceRepository{
    override fun getAllUsers(): Flow<Users> {
        return dao.getAllUsers().map { userEntities ->
            userEntities.map { it.toUser() }
        }
    }

    override suspend fun getUserByEmail(email: String): User? {
        return dao.getUserByEmail(email)?.toUser()
    }

    override suspend fun insertUser(user: User): Long {
        return dao.insertUser(user.toUserEntity())
    }

    override suspend fun updateUser(user: User) {
        dao.updateUser(user.toUserEntity())
    }

    override suspend fun deleteUser(userId: Long) {
        dao.deleteUser(userId)
    }

    override suspend fun isEmailExists(email: String): Boolean {
        return dao.getUserByEmail(email) != null
    }

}