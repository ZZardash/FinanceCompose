package com.example.financecompose.presentation.menu.add.viewmodel

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financecompose.R
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryChipGroup() {
    var selectedChip by remember { mutableStateOf(-1) } // -1 to indicate no selection initially
    var chipTexts by remember { mutableStateOf(listOf("Food", "Transport", "Shopping", "Kitchen", "Others")) }
    var colors by remember { mutableStateOf(listOf(
        Color(0xFF1E88E5), // Blue
        Color(0xFFEC407A), // Pink
        Color(0xFF66BB6A), // Green
        Color(0xFFFFCA28), // Yellow
        Color(0xFFAB47BC)  // Purple
    )) }
    var showDialog by remember { mutableStateOf(false) }
    var newCategoryTitle by remember { mutableStateOf("") }
    var newCategoryColor by remember { mutableStateOf(Color.Gray) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var chipToDelete by remember { mutableStateOf(-1) }
    var isTitleError by remember { mutableStateOf(false) }

    // Reset the new category fields when the dialog is dismissed
    LaunchedEffect(showDialog) {
        if (!showDialog) {
            newCategoryTitle = ""
            newCategoryColor = Color.Gray
            isTitleError = false
        }
    }

    if (showDialog) {
        AlertDialog(
            modifier = Modifier.size(600.dp),
            onDismissRequest = { showDialog = false },
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
                            newCategoryColor = selectedColor // Update the selected color
                        }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (newCategoryTitle.isBlank()) {
                        isTitleError = true
                    } else {
                        chipTexts = chipTexts + newCategoryTitle
                        colors = colors + newCategoryColor
                        showDialog = false
                    }
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    FlowRow(
        mainAxisSpacing = 8.dp,
        crossAxisSpacing = 8.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        chipTexts.forEachIndexed { index, text ->
            val backgroundColor by animateColorAsState(
                targetValue = if (selectedChip == index) colors[index] else colors[index].copy(alpha = 0.2f)
            )
            val textColor by animateColorAsState(
                targetValue = if (selectedChip == index) Color.White else colors[index]
            )
            val iconTint by animateColorAsState(
                targetValue = if (selectedChip == index) Color.White else Color.Transparent
            )

            Box(
                modifier = Modifier
                    .background(backgroundColor, shape = RoundedCornerShape(16.dp))
                    .clickable(
                        indication = null, // Disable ripple effect
                        interactionSource = remember { MutableInteractionSource() }
                    ) { selectedChip = index }
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .combinedClickable(
                        onClick = { selectedChip = index },
                        onLongClick = {
                            chipToDelete = index
                            showDeleteDialog = true
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = text,
                        style = TextStyle(
                            color = textColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    if (selectedChip == index) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Selected",
                            tint = iconTint,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        // "+" chip for adding a new category
        Box(
            modifier = Modifier
                .background(Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(16.dp))
                .clickable { showDialog = true }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+",
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }

    // Delete Confirmation Dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(
                text = "Are you sure to delete this category?",
                fontFamily = FontFamily(Font(R.font.eina))
            ) },
            confirmButton = {
                TextButton(
                    onClick = {
                        chipTexts = chipTexts.toMutableList().apply { removeAt(chipToDelete) }
                        colors = colors.toMutableList().apply { removeAt(chipToDelete) }
                        showDeleteDialog = false
                        chipToDelete = -1
                    }
                ) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text(text = "No")
                }
            }
        )
    }
}




