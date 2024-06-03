package com.example.financecompose.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.financecompose.R

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val SeaBlue = Color(0xFFBDDBEB)
val Seapl = Color(0xFFB2AECD)
val Pinkple = Color(0xFFA680AE)

val TextColor = Color(0xFF49454F)

val gradientBrush: Brush = Brush.linearGradient(
    colors = listOf(
        SeaBlue,
        Pinkple
    ),
    start = Offset(Float.POSITIVE_INFINITY, 0f),
    end = Offset(0f, Float.POSITIVE_INFINITY)
)
