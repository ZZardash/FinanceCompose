package com.example.financecompose.presentation.menu.home.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financecompose.R

@Composable
fun TransactionItem(
    transactionType: String,
    transactionTitle: String,
    transactionAmount: String,
    transactionCategory: String,
    transactionDate: String,
    transactionCurrency: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                BorderStroke(1.dp, Color.Gray),
                shape = RoundedCornerShape(8.dp)
            )
            .background(Color.White)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val icon = if (transactionType == "income") R.drawable.income_icon else R.drawable.expense_icon
        val amountColor = if (transactionType == "income") Color.Green else Color.Red
        val amountSign = if (transactionType == "income") "+" else "-"

        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.size(16.dp))
        // Column for title and date
        Column {
            Text(
                text = transactionTitle,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.eina)),
                color = Color.Black
            )
            Text(
                text = transactionDate, // Display transactionDate below title
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.eina)),
                color = Color.Gray // Use gray color for date
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Column for amount and category
        Column(
            horizontalAlignment = Alignment.End // Align content to the end of the column
        ) {
            Text(
                text = "$amountSign$transactionAmount$transactionCurrency",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.eina)),
                color = amountColor
            )
            Text(
                text = transactionCategory,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.eina))
            )
        }
    }
}

