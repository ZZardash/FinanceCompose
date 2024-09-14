package com.example.financecompose.presentation.menu.child.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financecompose.R

@Composable
@Preview
fun EmptyChildListScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.kid),
            contentDescription = null,
            modifier = Modifier.size(150.dp),
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Children will be here",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.eina)),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            ),
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "So far, it's empty. Wanna add your child?",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.cabrion)),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            ),
            color = Color.Gray
        )
    }
}