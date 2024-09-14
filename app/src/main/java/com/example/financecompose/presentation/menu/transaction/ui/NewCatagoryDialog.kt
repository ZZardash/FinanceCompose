package com.example.financecompose.presentation.menu.transaction.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financecompose.R
import com.example.financecompose.presentation.components.ColorPicker

@Composable
fun NewCategoryDialog(
    onDismissRequest: () -> Unit,
    onSave: (String, Color, String?) -> Unit
) {

    var newCategoryTitle by remember { mutableStateOf("") }
    var newCategoryColor by remember { mutableStateOf(Color.Gray) }
    var selectedIcon by remember { mutableStateOf<String?>(null) }
    var isTitleError by remember { mutableStateOf(false) }
    var showIconDialog by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(R.string.new_category)) },
        text = {
            Column {
                OutlinedTextField(
                    value = newCategoryTitle,
                    onValueChange = {
                        newCategoryTitle = it
                        isTitleError = it.isBlank()
                    },
                    label = { Text("Title") },
                    maxLines = 1,
                    isError = isTitleError
                )
                if (isTitleError) {
                    Text(
                        text = "Title cannot be empty",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                ColorPicker(
                    onColorSelected = { selectedColor ->
                        newCategoryColor = selectedColor
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))


            }
        },
        confirmButton = {
            Button(onClick = {
                if (newCategoryTitle.isBlank()) {
                    isTitleError = true
                } else {
                    onSave(newCategoryTitle, newCategoryColor, selectedIcon)
                    onDismissRequest()
                }
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )
}


