package com.example.financecompose.presentation.entrance.intro.ui

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.financecompose.R
import com.example.financecompose.presentation.navigation.Screen
import com.example.financecompose.ui.theme.Seapl
import com.example.financecompose.ui.theme.TextColor
import com.example.financecompose.ui.theme.gradientBrush
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financecompose.presentation.entrance.intro.viewmodel.GoogleAuthUiClient
import com.example.financecompose.presentation.entrance.intro.viewmodel.SignInViewModel
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch


@Composable
fun IntroScreen(
    navController: NavController
) {
    val context = LocalContext.current

    val viewModel = viewModel<SignInViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if(result.resultCode == RESULT_OK) {
                viewModel.viewModelScope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    viewModel.onSignInResult(signInResult)
                }
            }
        }
    )

    LaunchedEffect(key1 = state.isSignInSuccessful) {
        if(state.isSignInSuccessful) {
            Toast.makeText(
                context,
                "Sign in successful",
                Toast.LENGTH_LONG
            ).show()

            navController.navigate(Screen.GoogleSignInScreen.route)
            viewModel.resetState()
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold {
            Column (
                horizontalAlignment = CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .background(gradientBrush)
                    .padding(it)
                    .padding(20.dp)
                    .fillMaxSize()
            ){
                IconButton(onClick = {
                    //Language Menu appears
                }, modifier = Modifier.align(End)) {
                    Icon(painter = painterResource(
                        id = R.drawable.baseline_language_24
                    ),
                        contentDescription = stringResource(R.string.language)
                    )
                }


                Image(
                    painter = painterResource(id = R.drawable.intro_image),
                    contentDescription = stringResource(R.string.intro_image),
                    alignment = Alignment.TopCenter
                )

                Spacer(modifier = Modifier.height(50.dp))

                Card(
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)

                ){
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth()

                    ) {
                        Column (
                            modifier = Modifier
                                .align(CenterHorizontally)

                        ){
                            Text(
                                text = stringResource(R.string.financely),
                                fontSize = 50.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.kanit)),
                                modifier = Modifier.align(CenterHorizontally)
                            )
                            Text(
                                text = stringResource(R.string.easiest_way_to_manage_your_money),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Light,
                                fontFamily = FontFamily(Font(R.font.lora)),
                                modifier = Modifier.align(CenterHorizontally)
                            )
                        }

                        Spacer(modifier = Modifier.height(40.dp))

                        Column (
                            modifier = Modifier
                                .align(CenterHorizontally)
                                .padding(10.dp)
                        ){
                            OutlinedButton(
                                onClick = {
                                    viewModel.viewModelScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(CenterHorizontally)
                            )
                             {
                                Image(
                                    painter = painterResource(id = R.drawable.google),
                                    contentDescription = stringResource(R.string.google_icon),
                                    modifier = Modifier.width(30.dp)
                                )
                                 Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = stringResource(R.string.continue_with_google),
                                    fontFamily = FontFamily(Font(R.font.kanit)),
                                    color = TextColor
                                )
                            }



                            Spacer(modifier = Modifier.height(10.dp))

                            Button(
                                onClick = {
                                    navController.navigate(Screen.LoginScreen.route)
                                },
                                colors = ButtonDefaults.buttonColors(Seapl),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(CenterHorizontally)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = stringResource(R.string.email_icon)
                                )
                                Spacer(modifier = Modifier.width(8.dp)) // Add some spacing (optional)

                                Text(text = stringResource(R.string.continue_with_email),
                                    fontFamily = FontFamily(Font(R.font.kanit)))
                            }
                        }
                    }
                }
            }
        }
    }
}