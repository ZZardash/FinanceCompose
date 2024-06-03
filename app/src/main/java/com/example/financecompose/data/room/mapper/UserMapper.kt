package com.example.financecompose.data.room.mapper

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.financecompose.data.room.entity.UserEntity
import com.example.financecompose.domain.model.User

fun UserEntity.toUser(): User {
    return User(
        userId = userId ?: 0L,
        userFirstName = userFirstName,
        userLastName = userLastName,
        userEmail = userEmail,
        userBalance = userBalance,
        userCurrency = userCurrency,
        userCreationDate = userCreationDate
    )
}

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        userId = userId,
        userFirstName = userFirstName,
        userLastName = userLastName,
        userEmail = userEmail,
        userBalance = userBalance,
        userCurrency = userCurrency,
        userCreationDate = userCreationDate
    )
}