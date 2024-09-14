package com.example.financecompose.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.financecompose.domain.model.Transaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await

class TransactionWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override suspend fun doWork(): Result {
        val transactionData = inputData.getString("transaction_data")

        if (transactionData != null) {
            val transaction = Gson().fromJson(transactionData, Transaction::class.java)

            updateBalance(transaction)
        } else {
            return Result.failure()
        }

        return Result.success()
    }

    private fun updateBalance(transaction: Transaction) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            firestore.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val currentBalance =
                            document.getString("userBalance")?.toDoubleOrNull() ?: 0.0
                        println(transaction.transactionType)
                        val newBalance = if (transaction.transactionType == "income") {
                            currentBalance + (transaction.transactionAmount?.toDoubleOrNull()
                                ?: 0.0)
                        } else {
                            currentBalance - (transaction.transactionAmount?.toDoubleOrNull()
                                ?: 0.0)
                        }

                        firestore.collection("users")
                            .document(userId)
                            .update("userBalance", newBalance.toString())

                    }
                }

        }
    }

}
