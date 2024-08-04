package com.example.financecompose.presentation.menu.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.financecompose.R
import com.example.financecompose.presentation.menu.home.viewmodel.HomeScreenViewModel
import com.example.financecompose.ui.theme.homeGradientBrush

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val userBalance = viewModel.userBalance.collectAsState().value
    val userCurrency = viewModel.userCurrency.collectAsState().value
    val userProfilePhoto = viewModel.userProfilePhoto.collectAsState().value

    Surface(modifier = Modifier.fillMaxSize())
    {
        Scaffold { paddingValue ->
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .background(homeGradientBrush)
                    .padding(paddingValue)
                    .padding(20.dp)
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

                // Balance text can be added below if needed
                Text(
                    text = "$userBalance $userCurrency",
                    fontSize = 25.sp,
                    fontFamily = FontFamily(Font(R.font.eina)),
                    color = Color.Black,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}
