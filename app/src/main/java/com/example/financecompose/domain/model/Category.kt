package com.example.financecompose.domain.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
@Immutable
data class Category(
    val id: String = UUID.randomUUID().toString(),
    val categoryName: String? = null,
    val categoryColor: String? = null,
    val categoryTotal: String? = null,
): Parcelable
