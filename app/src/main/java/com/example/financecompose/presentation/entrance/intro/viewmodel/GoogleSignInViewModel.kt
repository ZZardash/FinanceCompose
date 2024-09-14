package com.example.financecompose.presentation.entrance.intro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financecompose.domain.model.Category
import com.example.financecompose.domain.model.User
import com.example.financecompose.domain.repository.FinanceRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoogleSignInViewModel @Inject constructor(
    private val userRepository: FinanceRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(GoogleSignInState())
    val state: StateFlow<GoogleSignInState> = _state.asStateFlow()

    private val firestore = FirebaseFirestore.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser

    fun onSignInResult(signInResult: GoogleSignInResult) {
        viewModelScope.launch {
            if (signInResult.isSuccess) {
                val userId = signInResult.data?.userId
                if (userId != null) {
                    val user = userRepository.getUser(userId)
                    if (user != null) {
                        _state.value = GoogleSignInState(isSignInSuccessful = true, user = user)
                    } else {
                        val newUser = userRepository.createUser(signInResult.data)
                        _state.value = GoogleSignInState(isSignInSuccessful = true, user = newUser, isNewUser = true)
                        if (currentUser != null) createDefaultCategoriesForUser(newUser.userId.toString())

                    }
                } else {
                    _state.value = GoogleSignInState(signInError = "User ID is null")
                }
            } else {
                _state.value = GoogleSignInState(signInError = signInResult.errorMessage)
            }
        }
    }

    private fun createDefaultCategoriesForUser(userUid: String) {
            val categoriesCollectionRef = firestore.collection("users")
                .document(userUid)
                .collection("categories")

            // List of default categories
            val defaultCategories = listOf(
                Category(
                    categoryName = "Food",
                    categoryColor = "#1E88E5" // Blue
                ),
                Category(
                    categoryName = "Transport",
                    categoryColor = "#EC407A" // Pink
                ),
                Category(
                    categoryName = "Shopping",
                    categoryColor = "#66BB6A" // Green
                ),
                Category(
                    categoryName = "Kitchen",
                    categoryColor = "#FFCA28" // Yellow
                ),
                Category(
                    categoryName = "Others",
                    categoryColor = "#AB47BC" // Purple
                )
            )

            // Add default categories to Firestore
            defaultCategories.forEach { category ->
                val categoryId = category.id
                categoriesCollectionRef.document(categoryId).set(category, SetOptions.merge())
                    .addOnSuccessListener {
                        // Category added successfully
                        println("Category $categoryId added successfully")
                    }
                    .addOnFailureListener { e ->
                        // Handle any errors
                        println("Failed to add category $categoryId: ${e.message}")
                        e.printStackTrace()
                    }
            }
    }


    fun resetState() {
        _state.value = GoogleSignInState()
    }
}
