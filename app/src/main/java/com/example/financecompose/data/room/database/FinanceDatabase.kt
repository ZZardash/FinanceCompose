package com.example.financecompose.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.financecompose.data.room.dao.FinanceDao
import com.example.financecompose.data.room.entity.UserEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Database(
    entities = [UserEntity::class],
    version = 1
)

abstract class FinanceDatabase: RoomDatabase() {
    abstract val dao: FinanceDao
}