package com.example.financecompose.presentation.menu.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.financecompose.R
import com.example.financecompose.data.enums.Currency
import com.example.financecompose.presentation.components.TransactionSwitchButtons
import com.example.financecompose.presentation.menu.home.viewmodel.HomeScreenViewModel
import com.example.financecompose.presentation.menu.home.ui.TransactionItem
import com.example.financecompose.presentation.menu.settings.viewmodel.SettingsViewModel
import com.example.financecompose.presentation.navigation.Screen

@Composable
fun HomeScreen(
    rootNavController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {

    // Collecting the userCurrency state from SettingsViewModel
    val userBalance = viewModel.userBalance.collectAsState().value
    val userCurrency by viewModel.userCurrency.collectAsState()
    val userProfilePhoto = viewModel.userProfilePhoto.collectAsState().value

    val incomeTransactionsChart = viewModel.incomeTransactions.collectAsState().value
    val expenseTransactionsChart = viewModel.expenseTransactions.collectAsState().value

    val allTransactions = incomeTransactionsChart + expenseTransactionsChart

    var selectedTransactionType by remember { mutableStateOf("income") }

    val transactionsToShowChart = if (selectedTransactionType == "income") {
        incomeTransactionsChart
    } else {
        expenseTransactionsChart
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold { paddingValue ->
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .background(Color.White)
                    .padding(paddingValue)
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.available_balance),
                        fontSize = 25.sp,
                        fontFamily = FontFamily(Font(R.font.eina)),
                        color = Color.Black
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(45.dp)
                                .clickable {
                                    // Handle click action here
                                }
                        ) {
                            Image(
                                painter = painterResource(R.drawable.menu),
                                contentDescription = "Menu Icon"
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(45.dp)
                                .clip(CircleShape)
                                .background(Color.Gray),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = userProfilePhoto,
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .size(45.dp)
                                    .clip(CircleShape)
                            )
                        }
                    }
                }

                // Get the currency symbol dynamically based on userCurrency state
                val currencySymbol = Currency.entries.firstOrNull { it.code == userCurrency }?.symbol ?: userCurrency

                // Displaying the user's balance with the current currency
                Text(
                    text = "$userBalance $currencySymbol",
                    fontSize = 25.sp,
                    fontFamily = FontFamily(Font(R.font.eina)),
                    color = Color.Black,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Spacer(modifier = Modifier.height(50.dp))

                // Transaction switch buttons
                TransactionSwitchButtons(
                    isIncomeButtonSelected = selectedTransactionType == "income",
                    isExpenseButtonSelected = selectedTransactionType == "expense",
                    onIncomeSelected = {
                        selectedTransactionType = "income"
                    },
                    onExpenseSelected = {
                        selectedTransactionType = "expense"
                    }
                )
                Spacer(modifier = Modifier.height(50.dp))

                // Pie chart to show income or expense transactions
                PieChart(
                    data = transactionsToShowChart,
                    radiusOuter = 140.dp,
                    chartBarWidth = 35.dp,
                    animDuration = 1000,
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Recent Transactions section with "See all >"
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Recent Transactions",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.eina)),
                        color = Color.Black
                    )

                    Text(
                        text = "See all>",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.eina)),
                        modifier = Modifier
                            .clickable {
                                rootNavController.navigate(Screen.AllTransactionsScreen.route)
                            }
                            .alpha(0.5f)
                    )
                }

                // Displaying each transaction with updated currency
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
                                transactionCurrency = currencySymbol  // Updated with latest currency
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

