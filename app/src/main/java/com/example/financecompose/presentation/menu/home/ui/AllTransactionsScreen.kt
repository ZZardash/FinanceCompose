package com.example.financecompose.presentation.menu.home.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.financecompose.R
import com.example.financecompose.data.enums.Currency
import com.example.financecompose.presentation.menu.home.viewmodel.HomeScreenViewModel

@Composable
fun AllTransactionsScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val allTransactions by viewModel.allTransactions.collectAsState()
    val userCurrency = viewModel.userCurrency.collectAsState().value
    val currencySymbol = Currency.entries.firstOrNull { it.code == userCurrency }?.symbol ?: userCurrency


    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                // Title
                Text(
                    text = stringResource(R.string.all_transactions),
                    fontSize = 25.sp,
                    modifier = Modifier.padding(16.dp)
                )

                // List of all transactions
                allTransactions.forEach { transaction ->
                    transaction.transactionAmount?.toInt()?.let { amount ->
                        if (transaction.transactionTitle != null &&
                            transaction.transactionDate != null &&
                            transaction.transactionCategory != null
                        ) {
                            TransactionItem(
                                transactionType = transaction.transactionType ?: "",
                                transactionTitle = transaction.transactionTitle,
                                transactionAmount = amount.toString(),
                                transactionCategory = transaction.transactionCategory,
                                transactionDate = transaction.transactionDate,
                                transactionCurrency = currencySymbol
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}
