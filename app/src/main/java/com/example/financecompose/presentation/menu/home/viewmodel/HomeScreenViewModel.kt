package com.example.financecompose.presentation.menu.home.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financecompose.domain.model.Transaction
import com.example.financecompose.domain.repository.FinanceRepository
import com.example.financecompose.presentation.entrance.login.viewmodel.LoginScreenState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val userRepository: FinanceRepository
) : ViewModel() {

    private val _userBalance = MutableStateFlow("")
    val userBalance: StateFlow<String> = _userBalance

    private val _userCurrency = MutableStateFlow("")
    val userCurrency: StateFlow<String> = _userCurrency

    private val _userProfilePhoto = MutableStateFlow("")
    val userProfilePhoto: StateFlow<String> = _userProfilePhoto.asStateFlow()

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions

    private val _incomeTransactions = MutableStateFlow<List<Transaction>>(emptyList())
    val incomeTransactions: StateFlow<List<Transaction>> = _incomeTransactions

    private val _expenseTransactions = MutableStateFlow<List<Transaction>>(emptyList())
    val expenseTransactions: StateFlow<List<Transaction>> = _expenseTransactions

    // Combine both income and expense transactions
    private val _allTransactions = MutableStateFlow<List<Transaction>>(emptyList())
    val allTransactions: StateFlow<List<Transaction>> = _allTransactions

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    init {
        viewModelScope.launch {
            fetchUserBalance()
            listenToUserCurrency()
            fetchTransactions()
        }

        viewModelScope.launch {
            combine(incomeTransactions, expenseTransactions) { income, expense ->
                income + expense // Combine both lists
            }.collect { combinedTransactions ->
                _allTransactions.value = combinedTransactions
            }
        }
    }

    // Fetch user's currency from Firebase
    fun fetchUserCurrency() {
        viewModelScope.launch {
            val uid = auth.currentUser?.uid
            if (uid != null) {
                val currency = userRepository.fetchUserCurrency(uid)
                _userCurrency.value = currency ?: "USD"
            } else {
                _userCurrency.value = "USD" // Fallback to USD if no user is signed in
            }
        }
    }

    private fun listenToUserCurrency() {
        viewModelScope.launch {
            val uid = auth.currentUser?.uid
            if (uid != null) {
                userRepository.getUserCurrencySnapshotListener(uid).collect { currency ->
                    _userCurrency.value = currency
                    println(_userCurrency.value)
                }
            } else {
                _userCurrency.value = "USD" // Fallback to USD if no user is signed in
            }
        }
    }


    private fun fetchUserBalance() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            firestore.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        _userBalance.value = document.getString("userBalance") ?: "0.0"
                        _userProfilePhoto.value = document.getString("userProfilePhoto") ?: ""
                    }
                }
                .addOnFailureListener {
                    _uiState.value = _uiState.value.copy(errorMessage = "Couldn't fetch data")
                }
        }
    }

    private fun fetchTransactions() {
        val currentUserUid = auth.currentUser?.uid

        if (currentUserUid != null) {
            firestore.collection("users")
                .document(currentUserUid)
                .collection("transactions")
                .get()
                .addOnSuccessListener { result ->
                    val incomeList = mutableListOf<Transaction>()
                    val expenseList = mutableListOf<Transaction>()

                    for (document in result) {
                        val transactionType = document.getString("transactionType")
                        val transactionCategory = document.getString("transactionCategory")
                        val transactionAmount =
                            document.getString("transactionAmount")?.toIntOrNull()
                        val transactionTitle = document.getString("transactionTitle")
                        val transactionDate = document.getString("transactionDate")

                        if (transactionCategory != null && transactionAmount != null) {
                            val transactionDetails = Transaction(
                                transactionType = transactionType ?: "",
                                transactionAmount = transactionAmount.toString(),
                                transactionCategory = transactionCategory,
                                transactionTitle = transactionTitle ?: "No Title",
                                transactionDate = transactionDate ?: "No Date"
                            )

                            if (transactionType == "income") {
                                incomeList.add(transactionDetails)
                            } else if (transactionType == "expense") {
                                expenseList.add(transactionDetails)
                            }
                        }
                    }

                    _incomeTransactions.value = incomeList
                    _expenseTransactions.value = expenseList
                }
                .addOnFailureListener { exception ->
                    // Handle error
                    _uiState.value =
                        _uiState.value.copy(errorMessage = "Couldn't fetch transactions")
                }
        } else {
            // Handle the case where the user is not logged in
            _uiState.value = _uiState.value.copy(errorMessage = "User not logged in")
        }
    }

}