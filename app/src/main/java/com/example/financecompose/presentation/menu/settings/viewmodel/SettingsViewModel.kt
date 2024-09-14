package com.example.financecompose.presentation.menu.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financecompose.domain.repository.FinanceRepository
import com.example.financecompose.presentation.menu.home.ui.HomeScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val userRepository: FinanceRepository
) : ViewModel() {

    private val _userCurrency = MutableStateFlow<String?>(null)
    val userCurrency: StateFlow<String?> = _userCurrency.asStateFlow()

    init {
        viewModelScope.launch {
            fetchUserCurrency()
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

    fun updateUserCurrency(newCurrency: String) {
        viewModelScope.launch {
            auth.currentUser?.uid?.let {
                userRepository.updateUserCurrency(it, newCurrency)
                _userCurrency.value = newCurrency

            }
        }
    }

    fun signOut(onSignOutComplete: () -> Unit) {
        viewModelScope.launch {
            auth.signOut()
            onSignOutComplete()
        }
    }
}

