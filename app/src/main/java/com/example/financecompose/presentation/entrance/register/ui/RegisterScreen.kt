package com.example.financecompose.presentation.entrance.register.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.financecompose.R
import com.example.financecompose.presentation.components.EntranceTextField
import com.example.financecompose.presentation.components.PasswordValidator
import com.example.financecompose.presentation.entrance.register.viewmodel.RegisterScreenEvent
import com.example.financecompose.presentation.entrance.register.viewmodel.RegisterViewModel
import com.example.financecompose.presentation.navigation.Screen
import com.example.financecompose.ui.theme.Seapl
import com.example.financecompose.ui.theme.TextColor
import com.example.financecompose.ui.theme.gradientBrush

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel()
) {

    val nameState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    val state = viewModel.uiState.value

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
                // Back button
                IconButton(onClick = {
                    navController.navigate(Screen.LoginScreen.route)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_keyboard_backspace_24),
                        contentDescription = stringResource(R.string.back),
                        tint = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                // Welcome back text
                Text(
                    text = stringResource(R.string.create_your_account),
                    fontSize = 45.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.kanit)),
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(50.dp))

                //Sign Up form
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
                            text = stringResource(R.string.sign_up),
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.kanit)),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(40.dp))

                        //Name input
                        EntranceTextField(
                            stringState = nameState,
                            text = stringResource(R.string.name),
                            fontFamily = FontFamily(Font(R.font.kanit)),
                            textType = KeyboardType.Text,
                            textSize = 15,
                            leadingIcon = Icons.Default.Person,
                            leadIconDesc = stringResource(R.string.person_icon)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Email input
                        EntranceTextField(
                            stringState = emailState,
                            text = stringResource(R.string.email),
                            fontFamily = FontFamily(Font(R.font.kanit)),
                            textType = KeyboardType.Email,
                            textSize = 15,
                            leadingIcon = Icons.Default.Email,
                            leadIconDesc = stringResource(R.string.email_icon)
                        )

                        // Show email error if exists
                        if (state.emailError != null) {
                            Text(
                                text = state.emailError,
                                color = Color.Red,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        //Password input
                        EntranceTextField(
                            stringState = passwordState,
                            text = stringResource(R.string.password),
                            fontFamily = FontFamily(Font(R.font.kanit)),
                            textType = KeyboardType.Password,
                            textSize = 15,
                            leadingIcon = Icons.Default.Lock,
                            leadIconDesc = stringResource(R.string.lock_icon)
                        )
                        Spacer(modifier = Modifier.height(5.dp))

                        PasswordValidator(passwordState)

                        Spacer(modifier = Modifier.height(35.dp))

                        // Sign up button
                        Button(
                            onClick = {
                                viewModel.onEvent(RegisterScreenEvent.RegisterUser(nameState.value, emailState.value, passwordState.value))
                            },
                            colors = ButtonDefaults.buttonColors(Seapl),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = stringResource(R.string.sign_up))
                        }
                        Spacer(modifier = Modifier.height(5.dp))

                        OutlinedButton(
                            onClick = {
                                navController.navigate(Screen.LoginScreen.route)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = stringResource(R.string.login),
                                color = TextColor)
                        }

                        Spacer(modifier = Modifier.height(5.dp))

                    }
                }
            }
        }
    }
}
