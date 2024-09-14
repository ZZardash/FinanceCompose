package com.example.financecompose.domain.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
@Immutable
data class Child (
    val id: String = UUID.randomUUID().toString(),
    val childName: String? = null,
    val childBalance: String? = null,
    val childCreationDate: String? = null

): Parcelable