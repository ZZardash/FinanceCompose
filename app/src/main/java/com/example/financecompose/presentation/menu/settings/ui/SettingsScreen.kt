package com.example.financecompose.presentation.menu.settings.ui

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.financecompose.R
import com.example.financecompose.presentation.menu.settings.viewmodel.SettingsViewModel
import com.example.financecompose.presentation.navigation.Screen
import com.example.financecompose.ui.theme.FinanceComposeTheme
import com.example.financecompose.ui.theme.Gray30
import com.example.financecompose.ui.theme.Purple90

@Composable
fun SettingsScreen(
    navController: NavHostController,
    rootNavController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val userCurrency by viewModel.userCurrency.collectAsState()
    val showSignOutDialog = remember { mutableStateOf(false) }
    var isDarkModeEnabled by remember { mutableStateOf(false) }

    // Fetch the user's currency when this screen is opened
    LaunchedEffect(Unit) {
        viewModel.fetchUserCurrency()
    }

    FinanceComposeTheme(darkTheme = isDarkModeEnabled) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
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
                            text = stringResource(R.string.settings),
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
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = stringResource(R.string.three_dot),
                                modifier = Modifier.padding(bottom = 4.dp).size(27.dp)
                            )
                        }
                    }
                },
                bottomBar = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {
                                showSignOutDialog.value = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .shadow(elevation = 8.dp, shape = RoundedCornerShape(24.dp)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Purple90,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Sign Out",
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }
                },
                containerColor = Gray30
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    // Show a loading state while fetching currency
                    if (userCurrency == null) {
                        CircularProgressIndicator() // Display a loading indicator
                    } else {
                        // Display the settings when currency is loaded
                        SettingsSection(
                            title = "Profile",
                            items = listOf(
                                SettingsItem(
                                    label = "Currency",
                                    showCurrencyDropdown = true,
                                    selectedCurrency = userCurrency, // Use current currency
                                    onCurrencySelected = { selectedCurrency ->
                                        viewModel.updateUserCurrency(selectedCurrency)
                                    }
                                ),
                                SettingsItem(
                                    label = "Language",
                                    showLanguageDropdown = true,
                                    selectedLanguage = "English", // Modify as needed
                                    onLanguageSelected = { selectedLanguage ->
                                        // Handle language selection
                                    }
                                ),
                                SettingsItem(
                                    "Personal Information",
                                    Icons.Default.KeyboardArrowRight
                                ),
                                SettingsItem("Reset Password", Icons.Default.KeyboardArrowRight)
                            )
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        SettingsSection(
                            title = "Other",
                            items = listOf(
                                SettingsItem(
                                    "Dark Mode",
                                    switch = true,
                                    isChecked = isDarkModeEnabled
                                ) { isChecked ->
                                    isDarkModeEnabled = isChecked
                                },
                                SettingsItem("Privacy", Icons.Default.KeyboardArrowRight),
                                SettingsItem("FAQ", Icons.Default.KeyboardArrowRight),
                                SettingsItem("About", Icons.Default.KeyboardArrowRight),
                            )
                        )
                    }
                }
            }

            if (showSignOutDialog.value) {
                AlertDialog(
                    onDismissRequest = { showSignOutDialog.value = false },
                    title = { Text("Are you sure you want to sign out?") },
                    confirmButton = {
                        Button(onClick = {
                            viewModel.signOut {
                                rootNavController.navigate(Screen.IntroScreen.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                }
                            }
                            showSignOutDialog.value = false
                        }) {
                            Text("Yes")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showSignOutDialog.value = false }) {
                            Text("No")
                        }
                    }
                )
            }
        }
    }
}






