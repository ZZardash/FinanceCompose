package com.example.financecompose.domain.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import kotlinx.parcelize.Parcelize
import java.util.UUID


@Parcelize
@Immutable
data class Transaction(
    val id: String = UUID.randomUUID().toString(),
    val transactionType: String? = null,
    val transactionCycle: String? = null,
    val transactionCategory: String? = null,
    val transactionCategoryColor: String? = null,
    val transactionTitle: String? = null,
    val transactionAmount: String? = null,
    val transactionNote: String? = null,
    val transactionDate: String? = null
): Parcelable