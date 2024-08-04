package com.example.financecompose.data.firebase

import com.example.financecompose.domain.model.User
import com.example.financecompose.domain.repository.FinanceRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FinanceRepositoryImpl(
    private val firestore: FirebaseFirestore
) : FinanceRepository {

    override suspend fun isEmailExists(email: String): Boolean {
        val querySnapshot = firestore.collection("users")
            .whereEqualTo("userEmail", email)
            .get()
            .await()
        return !querySnapshot.isEmpty
    }
    override suspend fun getUser(userId: String): User? {
        val document = firestore.collection("users").document(userId).get().await()
        return if (document.exists()) {
            document.toObject(User::class.java)
        } else {
            null
        }
    }

    override suspend fun createUser(userData: User): User {
        val userDocument = userData.userId?.let { firestore.collection("users").document(it.toString()) }
        userDocument?.set(userData)?.await()
        return userData
    }

}
