package com.example.financecompose.presentation.menu.add.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.financecompose.R
import com.example.financecompose.presentation.components.CurrencyDropdownMenu
import com.example.financecompose.presentation.components.CustomIconTextField
import com.example.financecompose.presentation.menu.add.viewmodel.CategoryChipGroup
import com.example.financecompose.presentation.menu.add.viewmodel.SelectableOptionsRow
import com.example.financecompose.ui.theme.Purple90
import com.example.financecompose.ui.theme.Gray90

@Composable
fun AddScreen(
    navController: NavHostController
) {
    var isIncomeButtonSelected by remember { mutableStateOf(true) }
    var isExpenseButtonSelected by remember { mutableStateOf(false) }

    val IncomeButtonColor by animateColorAsState(targetValue = if (isIncomeButtonSelected) Purple90 else Gray90)
    val IncomeButtonTextColor by animateColorAsState(targetValue = if (isIncomeButtonSelected) Gray90 else Purple90)
    val ExpenseButtonColor by animateColorAsState(targetValue = if (isExpenseButtonSelected) Purple90 else Gray90)
    val ExpenseButtonTextColor by animateColorAsState(targetValue = if (isExpenseButtonSelected) Gray90 else Purple90)

    val titleState = remember { mutableStateOf("") }
    val noteState = remember { mutableStateOf("") }
    val amountState = remember { mutableStateOf("") }
    var selectedCurrency by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 20.dp)
            ) {
                Button(
                    onClick = { /* Handle click */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
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
        Spacer(modifier = Modifier.height(15.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
                .padding(horizontal = 20.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /*navController.popBackStack()*/ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                        )
                    }

                    Text(
                        text = stringResource(R.string.add),
                        Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.eina)),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.width(48.dp))
                }

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            if (!isIncomeButtonSelected) {
                                isIncomeButtonSelected = true
                                isExpenseButtonSelected = false
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .padding(end = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = IncomeButtonColor
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.income),
                            contentDescription = "Income",
                            tint = IncomeButtonTextColor
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Income",
                            color = IncomeButtonTextColor
                        )
                    }

                    Button(
                        onClick = {
                            if (!isExpenseButtonSelected) {
                                isExpenseButtonSelected = true
                                isIncomeButtonSelected = false
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .padding(start = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ExpenseButtonColor
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.expense),
                            contentDescription = "Expense",
                            tint = ExpenseButtonTextColor
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Expense",
                            color = ExpenseButtonTextColor
                        )
                    }
                }


                Spacer(modifier = Modifier.height(25.dp))

                SelectableOptionsRow()

                Spacer(modifier = Modifier.height(45.dp))

                Row {
                    Text(
                        text = stringResource(R.string.choose_category),
                        fontFamily = FontFamily(Font(R.font.eina)),
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(25.dp))

                CategoryChipGroup()

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
            }
        }
    }
}



