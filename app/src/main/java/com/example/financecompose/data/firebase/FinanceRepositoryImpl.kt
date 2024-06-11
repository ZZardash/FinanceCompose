package com.example.financecompose.data.firebase

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
}
