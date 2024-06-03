package com.example.financecompose.presentation.entrance.login.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financecompose.domain.repository.FinanceRepository
import com.example.financecompose.presentation.entrance.register.viewmodel.RegisterScreenEvent
import com.example.financecompose.presentation.entrance.register.viewmodel.RegisterScreenState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: FinanceRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _uiState = mutableStateOf(LoginScreenState())
    val uiState: State<LoginScreenState> = _uiState

    private fun loginUser(email: String, password: String) =
        viewModelScope.launch {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        _uiState.value = _uiState.value.copy(successMessage = "Login successful")
                        println("LoginViewModel: User logged in successfully")
                    }
                    .addOnFailureListener { exception ->
                        _uiState.value = _uiState.value.copy(errorMessage = exception.localizedMessage)
                        println("LoginViewModel: Login failed - ${exception.localizedMessage}")
                    }
            }

    fun onEvent(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.LoginUser -> {
                viewModelScope.launch {
                    loginUser(
                        event.userMail,
                        event.userPassword
                    )
                }
            }
        }
    }


}