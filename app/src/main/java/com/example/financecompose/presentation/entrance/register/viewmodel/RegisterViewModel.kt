package com.example.financecompose.presentation.entrance.register.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.example.financecompose.domain.model.User
import com.example.financecompose.domain.repository.FinanceRepository
import com.example.financecompose.util.CurrentDate
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: FinanceRepository,
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ViewModel() {

    private val _uiState = mutableStateOf(RegisterScreenState())
    val uiState: State<RegisterScreenState> = _uiState

    private fun registerUser(name: String, email: String, password: String) =
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    val firebaseUserId = authResult.user?.uid
                    if (firebaseUserId == null) {
                        println("RegisterViewModel: Firebase User ID is null")
                        _uiState.value = _uiState.value.copy(errorMessage = "Firebase User ID is null")
                        return@addOnSuccessListener
                    }

                    println("RegisterViewModel: Firebase User ID is $firebaseUserId")

                    val userId = UUID.randomUUID().toString()
                    val user = User(
                        userId,
                        userFirstName = name,
                        userLastName = null,
                        userEmail = email,
                        userCreationDate = CurrentDate().getFormattedDate()
                    )
                    saveUserToFirestore(user, userId)
                }
                .addOnFailureListener { exception ->
                    println("RegisterViewModel: Failed to create user - ${exception.localizedMessage}")
                    _uiState.value = _uiState.value.copy(errorMessage = exception.localizedMessage)
                }
        }


    private fun saveUserToFirestore(user: User, firebaseUserId: String) {
        val docRef = db.collection("users").document(firebaseUserId)
        println("RegisterViewModel: Firestore User ID: ${user.userId}")
        docRef.set(user)
            .addOnSuccessListener {
                _uiState.value = _uiState.value.copy(successMessage = "User registered successfully")
                println("RegisterViewModel: User saved to Firestore successfully")
            }
            .addOnFailureListener { exception ->
                _uiState.value = _uiState.value.copy(errorMessage = exception.localizedMessage)
                println("RegisterViewModel: Error saving user to Firestore - ${exception.localizedMessage}")
            }
    }

    init {
        // Observe changes in Firestore for potential data updates while offline
        db.collection("users").addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                // Handle Firestore listener error (optional)
                println("RegisterViewModel"+ "Firestore listener error")
                return@addSnapshotListener
            }
            if (snapshot != null) {
                // Update UI state with any changes in user data (optional)
            }
        }
    }

    fun onEvent(event: RegisterScreenEvent) {
        when (event) {
            is RegisterScreenEvent.RegisterUser -> {
                viewModelScope.launch {
                    if (repository.isEmailExists(event.userMail)){
                        _uiState.value = _uiState.value.copy(emailError = "*This email is already in use")
                    }
                    else{
                        _uiState.value = _uiState.value.copy(emailError = null)
                        registerUser(
                            event.userName,
                            event.userMail,
                            event.userPassword
                        )
                    }
                }

            }
        }
    }
}
