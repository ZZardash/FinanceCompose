package com.example.financecompose.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.financecompose.data.room.dao.FinanceDao
import com.example.financecompose.data.room.database.FinanceDatabase
import com.example.financecompose.data.room.repository.FinanceRepositoryImpl
import com.example.financecompose.domain.repository.FinanceRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRecipeDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            FinanceDatabase::class.java,
            "finance_db"
        ).build()

    @Provides
    @Singleton
    fun provideRecipeRepository(financeDao: FinanceDao): FinanceRepository =
        FinanceRepositoryImpl(dao = financeDao)

    @Provides
    @Singleton
    fun provideRecipeDao(recipeDatabase: FinanceDatabase) = recipeDatabase.dao

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

}