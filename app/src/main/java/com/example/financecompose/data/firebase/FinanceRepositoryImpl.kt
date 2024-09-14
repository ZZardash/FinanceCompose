package com.example.financecompose.data.firebase

import com.example.financecompose.domain.model.User
import com.example.financecompose.domain.repository.FinanceRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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

    override suspend fun fetchUserCurrency(userId: String): String? {
        val document = firestore.collection("users").document(userId).get().await()
        return if (document.exists()) {
            document.getString("userCurrency") // Fetch the 'userCurrency' field
        } else {
            null
        }
    }

    override suspend fun getUserCurrencySnapshotListener(userId: String): Flow<String> = callbackFlow {
        val listener = firestore.collection("users")
            .document(userId)
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    val currency = it.getString("userCurrency") ?: "USD"
                    trySend(currency)
                }
            }
        awaitClose { listener.remove() }
    }


    override suspend fun updateUserCurrency(userId: String, newCurrency: String) {
        firestore.collection("users").document(userId)
            .update("userCurrency", newCurrency) // Update the 'userCurrency' field
            .await()
    }

}
