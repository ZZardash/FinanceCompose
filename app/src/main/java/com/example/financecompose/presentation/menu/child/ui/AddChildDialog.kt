package com.example.financecompose.presentation.menu.child.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financecompose.R
import com.example.financecompose.presentation.components.CustomIconTextField

@Composable
fun AddChildDialog(
    onAdd: (String, String) -> Unit,
    onCancel: () -> Unit
) {
    val childName = remember { mutableStateOf("") }
    val childBalance = remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = { onCancel() },
        title = { Text(text = "Add New Child") },
        text = {
            Column {
                CustomIconTextField(
                    stringState = childName,
                    text = "Name",
                    fontFamily = FontFamily(Font(R.font.eina)),
                    textType = KeyboardType.Text,
                    textSize = 15,
                    leadingIcon = Icons.Default.Face,
                    leadIconDesc = "Child Name icon",

                )
                if (nameError != null) {
                    Text(
                        text = nameError!!,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                CustomIconTextField(
                    stringState = childBalance,
                    text = "Starting Balance",
                    fontFamily = FontFamily(Font(R.font.eina)),
                    textType = KeyboardType.Number,
                    textSize = 15,
                    leadingIcon = Icons.Default.Create,
                    leadIconDesc = "Child Balance icon"
                )
            }

        },
        confirmButton = {
            Button(onClick = {
                if (childName.value.isNotEmpty()) {
                    onAdd(childName.value, childBalance.value)
                } else {
                    nameError = "Name cannot be empty"
                }
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = { onCancel() }) {
                Text("Cancel")
            }
        }
    )
}
