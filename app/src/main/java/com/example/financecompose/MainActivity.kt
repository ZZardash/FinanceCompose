package com.example.financecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.financecompose.presentation.navigation.ScreensNavigation
import com.example.financecompose.ui.theme.FinanceComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinanceComposeTheme{
                ScreensNavigation()
            }
        }
    }
}

