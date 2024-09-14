package com.example.financecompose.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.financecompose.R
import com.example.financecompose.ui.theme.Gray90

@Composable
fun TransactionSwitchButtons(
    isIncomeButtonSelected: Boolean,
    isExpenseButtonSelected: Boolean,
    onIncomeSelected: () -> Unit,
    onExpenseSelected: () -> Unit
) {
    val IncomeButtonColor by animateColorAsState(
        targetValue = if (isIncomeButtonSelected) Color(0xFF3D7940) else Gray90
    ) // Green
    val IncomeButtonTextColor by animateColorAsState(
        targetValue = if (isIncomeButtonSelected) Gray90 else Color(0xFF3D7940)
    )

    val ExpenseButtonColor by animateColorAsState(
        targetValue = if (isExpenseButtonSelected) Color(0xFFB12F25) else Gray90
    ) // Red
    val ExpenseButtonTextColor by animateColorAsState(
        targetValue = if (isExpenseButtonSelected) Gray90 else Color(0xFFB12F25)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = {
                if (!isIncomeButtonSelected) {
                    onIncomeSelected()
                }
            },
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .padding(end = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = IncomeButtonColor
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.income),
                contentDescription = "Income",
                tint = IncomeButtonTextColor
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Income",
                color = IncomeButtonTextColor
            )
        }

        Button(
            onClick = {
                if (!isExpenseButtonSelected) {
                    onExpenseSelected()
                }
            },
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .padding(start = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ExpenseButtonColor
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.expense),
                contentDescription = "Expense",
                tint = ExpenseButtonTextColor
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Expense",
                color = ExpenseButtonTextColor
            )
        }
    }
}



