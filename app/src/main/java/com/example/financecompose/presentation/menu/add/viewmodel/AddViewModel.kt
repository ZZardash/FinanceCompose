package com.example.financecompose.presentation.menu.add.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AddViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

}
