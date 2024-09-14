package com.example.financecompose.presentation.menu.transaction.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.financecompose.R
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun IconSelectionDialog(onIconSelected: (String) -> Unit, onDismissRequest: () -> Unit) {
    val incomeIcons = listOf(
        R.drawable.alimony, R.drawable.bonuses, R.drawable.budgeting, R.drawable.business_income, R.drawable.capital_gains, R.drawable.cash_gifts, R.drawable.child_support, R.drawable.commission, R.drawable.crypto, R.drawable.disablity_income, R.drawable.dividends, R.drawable.freelancer, R.drawable.inheritance, R.drawable.interest_income, R.drawable.online_income, R.drawable.online_sales, R.drawable.overtime_pay, R.drawable.rental_income, R.drawable.scholarship, R.drawable.tax_refund // Add your actual drawable icons here
    )
    val expenseIcons = listOf(
        R.drawable.car_maintanance, R.drawable.child_care, R.drawable.clothing, R.drawable.dining, R.drawable.enterntainment, R.drawable.gift, R.drawable.grocery, R.drawable.gym, R.drawable.healthcare, R.drawable.house_repair, R.drawable.household, R.drawable.insurance, R.drawable.internet, R.drawable.laundry, R.drawable.personal_care, R.drawable.rent, R.drawable.streaming_service, R.drawable.subscription, R.drawable.transportation, R.drawable.utlilites // Add your actual drawable icons here
    )

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Select Icon") },
        text = {
            Column {
                // Income Icons
                Text(text = "Income Icons", modifier = Modifier.padding(vertical = 8.dp))
                FlowRow(
                    mainAxisSpacing = 8.dp,
                    crossAxisSpacing = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    incomeIcons.forEach { iconRes ->
                        Icon(
                            painter = painterResource(id = iconRes),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clickable {
                                    onIconSelected(iconRes.toString())
                                }
                        )
                    }
                }

                // Expense Icons
                Text(text = "Expense Icons", modifier = Modifier.padding(vertical = 8.dp))
                FlowRow(
                    mainAxisSpacing = 8.dp,
                    crossAxisSpacing = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    expenseIcons.forEach { iconRes ->
                        Icon(
                            painter = painterResource(id = iconRes),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clickable {
                                    onIconSelected(iconRes.toString())
                                }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Close")
            }
        }
    )
}
