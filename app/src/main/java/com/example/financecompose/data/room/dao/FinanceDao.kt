package com.example.financecompose.data.room.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.financecompose.data.room.entity.UserEntity
import com.example.financecompose.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface FinanceDao {

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity): Long

    @Query("DELETE FROM users WHERE userId = :userId")
    suspend fun deleteUser(userId: Long)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(userEntity: UserEntity)

    @Query("SELECT * FROM users WHERE userEmail = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?
}