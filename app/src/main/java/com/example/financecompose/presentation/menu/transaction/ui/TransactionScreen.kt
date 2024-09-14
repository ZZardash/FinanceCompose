package com.example.financecompose.presentation.menu.transaction.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.financecompose.R
import com.example.financecompose.domain.model.Transaction
import com.example.financecompose.presentation.components.CurrencyDropdownMenu
import com.example.financecompose.presentation.components.CustomIconTextField
import com.example.financecompose.presentation.components.TransactionSwitchButtons
import com.example.financecompose.presentation.menu.transaction.viewmodel.TransactionViewModel
import com.example.financecompose.presentation.menu.home.ui.colorToHex
import com.example.financecompose.ui.theme.Purple90
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddScreen(
    navController: NavController,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    var isIncomeButtonSelected by remember { mutableStateOf(true) }
    var isExpenseButtonSelected by remember { mutableStateOf(false) }
    var selectedCategoryColor by remember { mutableStateOf<Color?>(null) }
    var selectedCycle by remember { mutableStateOf("one_time") }
    var selectedCategory by remember { mutableStateOf("") }
    val titleState = remember { mutableStateOf("") }
    val noteState = remember { mutableStateOf("") }
    val amountState = remember { mutableStateOf("") }
    var selectedCurrency by remember { mutableStateOf("") }

    val categoryError = remember { mutableStateOf("") }
    val titleError = remember { mutableStateOf("") }
    val amountError = remember { mutableStateOf("") }

    LaunchedEffect(state.showSuccessAnimation) {
        if (state.showSuccessAnimation) {
            delay(2000)
            navController.navigate("home") {
                popUpTo("add_screen") { inclusive = true }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),

        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // Yatayda düzgün hizalama
            ) {

                IconButton(
                    onClick = { /* Menü butonuna tıklanma işlemi */ }
                ) {
                    Icon(
                        modifier = Modifier.padding(bottom = 3.dp),
                        imageVector = Icons.Default.Menu,
                        contentDescription = stringResource(R.string.menu)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = stringResource(R.string.transaction),
                    Modifier.weight(1f),
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.eina)),
                        fontSize = 20.sp
                    )
                )

                IconButton(
                    onClick = { /* + butonuna tıklanma işlemi */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.add_child),
                        modifier = Modifier.padding(bottom = 4.dp).size(27.dp)
                    )
                }
            }
        },

        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {

                        // Reset error messages
                        categoryError.value = ""
                        titleError.value = ""
                        amountError.value = ""

                        // Validation
                        if (selectedCategory.isEmpty()) {
                            categoryError.value = "Please select a category"
                        }
                        if (titleState.value.isEmpty()) {
                            titleError.value = "Please enter a title"
                        }
                        if (amountState.value.isEmpty()) {
                            amountError.value = "Please enter an amount"
                        }

                        if (categoryError.value.isEmpty() && titleError.value.isEmpty() && amountError.value.isEmpty()) {
                            val transactionDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                                Date()
                            )
                            val transaction = Transaction(
                                transactionType = if (isIncomeButtonSelected) "income" else "expense",
                                transactionCycle = selectedCycle,
                                transactionCategory = selectedCategory,
                                transactionCategoryColor = selectedCategoryColor?.let {
                                    colorToHex(
                                        it
                                    )
                                },
                                transactionTitle = titleState.value,
                                transactionAmount = amountState.value,
                                transactionNote = noteState.value,
                                transactionDate = transactionDate
                            )

                            viewModel.addTransaction(transaction, context)
                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(24.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Purple90,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "Add Transaction",
                        fontSize = 16.sp
                    )
                }
            }
        }
    ) { paddingValue ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
                .padding(top = 5.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(15.dp))


                TransactionSwitchButtons(
                    isIncomeButtonSelected = isIncomeButtonSelected,
                    isExpenseButtonSelected = isExpenseButtonSelected,
                    onIncomeSelected = {
                        isIncomeButtonSelected = true
                        isExpenseButtonSelected = false
                    },
                    onExpenseSelected = {
                        isIncomeButtonSelected = false
                        isExpenseButtonSelected = true
                    }
                )

                Spacer(modifier = Modifier.height(25.dp))

                SelectableOptionsRow(selectedCycle = selectedCycle) { cycle ->
                    selectedCycle = cycle
                }

                Spacer(modifier = Modifier.height(65.dp))

                Row {
                    Text(
                        text = stringResource(R.string.choose_category),
                        fontFamily = FontFamily(Font(R.font.eina)),
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(25.dp))


                CategoryChipGroup(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { category, color ->
                        selectedCategory = category
                        selectedCategoryColor = color
                    }
                )


                if (categoryError.value.isNotEmpty()) {
                    Text(
                        text = categoryError.value,
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.eina)),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }


                Spacer(modifier = Modifier.height(25.dp))

                CustomIconTextField(
                    stringState = titleState,
                    text = "Title",
                    fontFamily = FontFamily(Font(R.font.eina)),
                    textType = KeyboardType.Text,
                    textSize = 15,
                    leadingIcon = Icons.Default.Create,
                    leadIconDesc = "Title icon"
                )

                if (titleError.value.isNotEmpty()) {
                    Text(
                        text = titleError.value,
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.eina)),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomIconTextField(
                        stringState = amountState,
                        text = "Amount",
                        fontFamily = FontFamily(Font(R.font.eina)),
                        textType = KeyboardType.Number,
                        textSize = 15,
                        leadingIcon = Icons.Default.ShoppingCart,
                        leadIconDesc = "Amount icon",
                        modifier = Modifier.weight(0.65f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    CurrencyDropdownMenu(
                        selectedCurrency = selectedCurrency,
                        onCurrencySelected = { selectedCurrency = it },
                        showDisplayName = false,
                        modifier = Modifier
                            .weight(0.35f)
                            .padding(top = 8.dp)
                    )
                }


                if (amountError.value.isNotEmpty()) {
                    Text(
                        text = amountError.value,
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.eina)),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                CustomIconTextField(
                    stringState = noteState,
                    text = "Note",
                    fontFamily = FontFamily(Font(R.font.eina)),
                    textType = KeyboardType.Text,
                    textSize = 15,
                    height = 150,
                    singleLine = false,
                    leadingIcon = Icons.Default.Info,
                    leadIconDesc = "Note icon",
                    leadIconAlign = Alignment.TopCenter,
                    characterLimit = 200,
                    showCharacterCounter = true
                )

                if (state.errorMessage != null) {
                    Text(
                        text = state.errorMessage!!,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
        // Display animation
        if (state.showSuccessAnimation) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.transaction_saved))
                LottieAnimation(
                    composition,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    iterations = 1,
                )
                Text(
                    text = "Transaction Saved!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}




