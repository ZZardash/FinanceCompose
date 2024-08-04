package com.example.financecompose.presentation.entrance.intro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financecompose.domain.repository.FinanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoogleSignInViewModel @Inject constructor(
    private val userRepository: FinanceRepository
) : ViewModel() {

    private val _state = MutableStateFlow(GoogleSignInState())
    val state: StateFlow<GoogleSignInState> = _state.asStateFlow()

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
                    }
                } else {
                    _state.value = GoogleSignInState(signInError = "User ID is null")
                }
            } else {
                _state.value = GoogleSignInState(signInError = signInResult.errorMessage)
            }
        }
    }

    fun resetState() {
        _state.value = GoogleSignInState()
    }
}
