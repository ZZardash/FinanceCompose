package com.example.financecompose.presentation.entrance.child_login.ui

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.example.financecompose.R
import com.example.financecompose.presentation.navigation.Screen
import com.example.financecompose.ui.theme.Seapl
import com.example.financecompose.ui.theme.gradientBrush

@Composable
fun ChildLoginScreen(
    navController: NavController
) {

    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }


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
                        contentDescription = stringResource(id = R.string.back),
                        tint = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                // Welcome back text
                Text(
                    text = stringResource(R.string.hello_there),
                    fontSize = 45.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.kanit)),
                    color = Color.Black
                )


                // Login to continue text
                Text(
                    text = stringResource(R.string.login_to_continue),
                    fontSize = 17.sp,
                    fontFamily = FontFamily(Font(R.font.lora)),
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(100.dp))

                // Login form

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
                            text = stringResource(R.string.child_login),
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.kanit)),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(40.dp))


                        // Email input
                        OutlinedTextField(
                            value = emailState.value,
                            onValueChange = { emailState.value = it },
                            label = {
                                Text(
                                    stringResource(R.string.parent_email),
                                    fontFamily = FontFamily(Font(R.font.kanit))
                                )
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.kanit))
                            ),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = stringResource(id = R.string.email_icon)
                                )
                            }
                        )

                        Spacer(modifier = Modifier.height(10.dp))


                        // Password input
                        OutlinedTextField(
                            value = passwordState.value,
                            onValueChange = { passwordState.value = it },
                            label = {
                                Text(
                                    stringResource(id = R.string.password),
                                    fontFamily = FontFamily(Font(R.font.kanit))
                                )
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.kanit))
                            ),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = stringResource(R.string.password_icon)
                                )
                            }
                        )


                        Spacer(modifier = Modifier.height(40.dp))

                        // Login button
                        Button(
                            onClick = {
                                //Login to child home
                            },
                            colors = ButtonDefaults.buttonColors(Seapl),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Sign in")
                        }
                        Spacer(modifier = Modifier.height(15.dp))

                    }
                }
            }
        }
    }
}
