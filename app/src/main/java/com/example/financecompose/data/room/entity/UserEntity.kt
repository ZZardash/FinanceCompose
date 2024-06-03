package com.example.financecompose.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val userId: Long? = null,  // Make userId nullable
    val userFirstName: String?,
    val userLastName: String?,
    val userEmail: String?,
    val userBalance: String?,
    val userCurrency: String?,
    val userCreationDate: String?
)
