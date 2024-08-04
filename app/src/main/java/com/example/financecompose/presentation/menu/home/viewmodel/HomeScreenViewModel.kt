package com.example.financecompose.presentation.menu.home.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.financecompose.presentation.entrance.login.viewmodel.LoginScreenState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeScreenViewModel : ViewModel() {

    private val _userBalance = MutableStateFlow("")
    val userBalance: StateFlow<String> = _userBalance

    private val _userCurrency = MutableStateFlow("")
    val userCurrency: StateFlow<String> = _userCurrency

    private val _userProfilePhoto = MutableStateFlow("")
    val userProfilePhoto: StateFlow<String> = _userProfilePhoto.asStateFlow()

    private val _uiState = mutableStateOf(LoginScreenState())

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    init {
        fetchUserBalance()
    }

    private fun fetchUserBalance() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            firestore.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        _userBalance.value = document.getString("userBalance") ?: "0.0"
                        _userCurrency.value = document.getString("userCurrency") ?: ""
                        _userProfilePhoto.value = document.getString("userProfilePhoto") ?: ""
                    }
                }
                .addOnFailureListener {
                    _uiState.value = _uiState.value.copy(errorMessage = "Couldn't fetch data")
                }
        }
    }
}
