package com.example.financecompose.domain.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class User(
    val userId: Long? = null,
    val userFirstName: String?,
    val userLastName: String? = null,
    val userEmail: String?,
    val userBalance: String? = null,
    val userCurrency: String? = null,
    val userCreationDate: String?

): Parcelable
