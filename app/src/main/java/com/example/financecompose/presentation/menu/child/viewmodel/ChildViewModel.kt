package com.example.financecompose.presentation.menu.child.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financecompose.domain.model.Child
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ChildViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): ViewModel() {
    private val _children = MutableStateFlow<List<Child>>(emptyList())
    val children: StateFlow<List<Child>> get() = _children

    init {
        fetchChildren()
    }

    private fun fetchChildren() {
        viewModelScope.launch {
            val currentUserUid = auth.currentUser?.uid ?: return@launch

            firestore.collection("users")
                .document(currentUserUid)
                .collection("children")
                .addSnapshotListener { result, _ ->
                    val fetchedChildren = result?.map { document ->
                        document.toObject(Child::class.java)
                    } ?: emptyList()
                    _children.value = fetchedChildren
                }
        }
    }

    fun addChild(childName: String, childBalance: String) {
        viewModelScope.launch {
            val newChild = Child(
                childName = childName,
                childBalance = childBalance,
                childCreationDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
            )

            val currentUserUid = auth.currentUser?.uid ?: return@launch

            firestore.collection("users")
                .document(currentUserUid)
                .collection("children")
                .document(newChild.id)
                .set(newChild)
                .addOnSuccessListener {
                    // Success handling if needed
                }
                .addOnFailureListener {
                    // Failure handling if needed
                }
        }
    }

    fun deleteChild(childId: String) {
        viewModelScope.launch {
            val currentUserUid = auth.currentUser?.uid ?: return@launch

            firestore.collection("users")
                .document(currentUserUid)
                .collection("children")
                .document(childId)
                .delete()
                .addOnSuccessListener {
                    // Success handling if needed
                }
                .addOnFailureListener {
                    // Failure handling if needed
                }
        }
    }
}
