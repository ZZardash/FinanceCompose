package com.example.financecompose.presentation.entrance.register.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.financecompose.R
import com.example.financecompose.data.enums.Currency
import com.example.financecompose.presentation.components.EntranceTextField
import com.example.financecompose.presentation.entrance.login.viewmodel.LoginViewModel
import com.example.financecompose.ui.theme.gradientBrush

@Composable
fun PreferencesScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val balanceState = remember { mutableStateOf("") }
    val emptyState = remember { mutableStateOf("") }

    val state = viewModel.uiState.value
    var selectedOption by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold { paddingValue ->
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .background(
                        gradientBrush
                    )
                    .padding(paddingValue)
                    .padding(20.dp)
                    .fillMaxSize()
            ) {
                // Welcome back text
                Text(
                    text = "Select your preferences",
                    fontSize = 45.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.kanit)),
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Column(
                        modifier = Modifier.padding(15.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Login title
                        Text(
                            text = "Sign In",
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.kanit)),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Dropdown Menu
                        /*
                        OutlinedTextField(
                            value = selectedOption.displayName,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Select Option") },
                            trailingIcon = {
                                IconButton(onClick = { expanded = !expanded }) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowDropDown,
                                        contentDescription = null
                                    )
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { expanded = !expanded }
                        )*/

                        //Currency Text Field
                        EntranceTextField(
                            stringState = emptyState,
                            text = "Starting Balance",
                            readOnlyOption = true,
                            fontFamily = FontFamily(Font(R.font.kanit)),
                            textType = KeyboardType.Number,
                            textSize = 15,
                            leadingIcon = Icons.Default.Add,
                            trailingIcon = Icons.Default.ArrowDropDown,
                            trailerOnClick = { expanded = !expanded },
                            onClick = { expanded = !expanded },
                            leadIconDesc = " icon"
                        )

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            Currency.entries.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option.displayName) },
                                    onClick = {
                                        selectedOption = option.displayName
                                        expanded = false
                                    }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Balance Input Field
                        EntranceTextField(
                            stringState = balanceState,
                            text = "Balance",
                            fontFamily = FontFamily(Font(R.font.kanit)),
                            textType = KeyboardType.Number,
                            textSize = 15,
                            leadingIcon = Icons.Default.Add,
                            leadIconDesc = "Balance icon"
                        )

                        Spacer(modifier = Modifier.height(30.dp))

                        // Continue Button
                        Button(
                            onClick = {
                                // Handle continue action
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Continue",
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.kanit)),
                                color = Color.Black
                            )
                        }

                        Spacer(modifier = Modifier.height(5.dp))

                        // Login Text
                        Text(
                            text = "Already have an account? Log In",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.lora)),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}