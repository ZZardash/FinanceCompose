package com.example.financecompose.presentation.menu.transaction.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.financecompose.domain.model.Transaction
import com.example.financecompose.util.TransactionWorker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {
    private val _state = MutableStateFlow(TransactionScreenState())
    val state: StateFlow<TransactionScreenState> get() = _state

    fun addTransaction(transaction: Transaction, context: Context) {
        _state.value = _state.value.copy(isLoading = true)

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userDocRef = firestore.collection("users").document(currentUser.uid)
            val transactionsCollectionRef = userDocRef.collection("transactions")

            if (transaction.transactionCycle == "one_time") {
                transactionsCollectionRef.add(transaction)
                    .addOnSuccessListener {
                        updateBalance(transaction)
                    }
                    .addOnFailureListener { exception ->
                        _state.value = _state.value.copy(
                            errorMessage = "Failed to add transaction: ${exception.message}",
                            isLoading = false
                        )
                    }
            } else {
                transactionsCollectionRef.add(transaction)
                    .addOnSuccessListener {
                        updateBalance(transaction)
                    }
                    .addOnFailureListener { exception ->
                        _state.value = _state.value.copy(
                            errorMessage = "Failed to add transaction: ${exception.message}",
                            isLoading = false
                        )
                    }
                scheduleRecurrentTransaction(transaction, context)
            }
        } else {
            _state.value = _state.value.copy(
                errorMessage = "User not authenticated",
                isLoading = false
            )
        }
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
                            .addOnSuccessListener {
                                _state.value = _state.value.copy(
                                    successMessage = "Transaction added successfully",
                                    isLoading = false,
                                    showSuccessAnimation = true
                                )
                            }
                            .addOnFailureListener { e ->
                                _state.value = _state.value.copy(
                                    errorMessage = "Failed to update balance: ${e.message}",
                                    isLoading = false
                                )
                            }
                    }
                }
                .addOnFailureListener {
                    _state.value = _state.value.copy(errorMessage = "Couldn't fetch user balance")
                }
        }
    }

    private fun scheduleRecurrentTransaction(transaction: Transaction, context: Context) {
        val workManager = WorkManager.getInstance(context)

        // Transaction bilgilerini JSON formatında işlemek
        val transactionJson = Gson().toJson(transaction)
        val inputData = workDataOf("transaction_data" to transactionJson)

        // Periyot süresini belirle
        val repeatInterval = when (transaction.transactionCycle) {
            "daily" -> 1L to TimeUnit.DAYS
            "weekly" -> 7L to TimeUnit.DAYS
            "monthly" -> 30L to TimeUnit.DAYS
            else -> 15L to TimeUnit.MINUTES  // Varsayılan olarak 15 dakika aralık kullan
        }

        // Belirlenen periyotla periyodik bir iş oluştur
        val periodicWorkRequest = PeriodicWorkRequestBuilder<TransactionWorker>(repeatInterval.first, repeatInterval.second)
            .setInputData(inputData)
            .build()

        // Periodik iş için benzersiz bir ad kullanarak işin tekrar eklenmesini engelle
        workManager.enqueueUniquePeriodicWork(
            "recurrent_transaction_${transaction.transactionCycle}",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }





}

