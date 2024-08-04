package com.example.financecompose.presentation.entrance.login.ui

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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.financecompose.R
import com.example.financecompose.presentation.components.ButtonContent
import com.example.financecompose.presentation.components.CustomButton
import com.example.financecompose.presentation.components.CustomIconTextField
import com.example.financecompose.presentation.entrance.login.viewmodel.LoginScreenEvent
import com.example.financecompose.presentation.entrance.login.viewmodel.LoginViewModel
import com.example.financecompose.presentation.navigation.Screen
import com.example.financecompose.ui.theme.Purple40
import com.example.financecompose.ui.theme.Seapl
import com.example.financecompose.ui.theme.SymbolGray
import com.example.financecompose.ui.theme.gradientBrush

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
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
                    navController.navigate(Screen.IntroScreen.route)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_keyboard_backspace_24),
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                // Welcome back text
                Text(
                    text = stringResource(R.string.welcome_back),
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

                Spacer(modifier = Modifier.height(70.dp))

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
                            text = "Sign In",
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.kanit)),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(40.dp))

                        CustomIconTextField(
                            stringState = emailState,
                            text = "Email",
                            fontFamily = FontFamily(Font(R.font.kanit)),
                            textType = KeyboardType.Email,
                            textSize = 15,
                            leadingIcon = Icons.Default.Email,
                            leadIconDesc = "Email icon"
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        CustomIconTextField(
                            stringState = passwordState,
                            text = "Password",
                            fontFamily = FontFamily(Font(R.font.kanit)),
                            textType = KeyboardType.Password,
                            textSize = 15,
                            leadingIcon = Icons.Default.Lock,
                            leadIconDesc = "Password icon"
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        // Show email error if exists
                        if (state.matchError != null) {
                            Text(
                                text = state.matchError,
                                color = Color.Red,
                                fontFamily = FontFamily(Font(R.font.kanit)),
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))


                        Spacer(modifier = Modifier.height(10.dp))

                        ClickableText(
                            text = buildAnnotatedString {
                                // Append text with custom font style
                                withStyle(
                                    style = SpanStyle(
                                        fontFamily = FontFamily(Font(R.font.kanit)),
                                        fontSize = 15.sp,
                                        color = Purple40
                                    )
                                ) {
                                    append("Forgot password?")
                                }
                            },
                            onClick = {
                                navController.navigate(Screen.ForgotPasswordScreen.route)
                            },
                            modifier = Modifier
                        )

                        Spacer(modifier = Modifier.height(40.dp))

                        // Login button

                        CustomButton(
                            onClick = {
                                viewModel.onEvent(LoginScreenEvent.LoginUser(emailState.value, passwordState.value))
                            },
                            buttonColor = ButtonDefaults.buttonColors(Seapl),
                            modifier = Modifier.fillMaxWidth(),
                            isOutlined = false,
                            buttonContent = ButtonContent.IconContent(
                                imageVector = Icons.Default.Home,
                                contentDescription = stringResource(R.string.sign_in_icon)
                            ),
                            text = stringResource(R.string.sign_in),
                            fontFamily = FontFamily(Font(R.font.kanit)),
                            tint = Color.White
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        //Sign Up button

                        CustomButton(
                            onClick = {
                                navController.navigate(Screen.RegisterScreen.route)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            isOutlined = true,
                            buttonContent = ButtonContent.IconContent(
                                imageVector = Icons.Default.Person,
                                contentDescription = stringResource(R.string.sign_up_icon),

                            ),
                            text = stringResource(R.string.sign_up),
                            fontFamily = FontFamily(Font(R.font.kanit)),
                            tint = SymbolGray
                        )


                        Spacer(modifier = Modifier.height(5.dp))
                        //Child login button

                        CustomButton(
                            onClick = {
                                navController.navigate(Screen.ChildLoginScreen.route)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            isOutlined = true,
                            buttonContent = ButtonContent.IconContent(
                                imageVector = Icons.Default.Face,
                                contentDescription = stringResource(R.string.sign_in_icon)
                            ),
                            text = stringResource(R.string.child_login),
                            fontFamily = FontFamily(Font(R.font.kanit)),
                            tint = SymbolGray
                        )

                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
        }
    }
}
