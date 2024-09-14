package com.example.financecompose.presentation.menu.child.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financecompose.R
import com.example.financecompose.domain.model.Child
import com.example.financecompose.presentation.menu.child.viewmodel.ChildViewModel

@Composable
fun ChildItem(
    child: Child,
    viewModel: ChildViewModel,
    onSeeDetails: () -> Unit
) {
    val showDeleteDialog = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE5EAF3), RoundedCornerShape(8.dp))
            .padding(vertical = 8.dp)
            .clickable { onSeeDetails() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = child.childName ?: "",
                    fontFamily = FontFamily(Font(R.font.eina)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Text(
                    text = "Balance: ${child.childBalance ?: ""}",
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.cabrion)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { onSeeDetails() },
                    modifier = Modifier
                        .border(1.dp, Color.Black)
                        .height(30.dp)
                        .align(Alignment.Start),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = "See Details",
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                    )
                }

            }


            Box(
                modifier = Modifier
                    .clickable(onClick = {showDeleteDialog.value = true})
                    .padding(0.dp)
                    .align(Alignment.Top)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Delete Child",
                    tint = Color.Red
                )
            }
        }
    }

    if (showDeleteDialog.value) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog.value = false },
            title = { Text("Do you want to delete this child?") },
            confirmButton = {
                Button(onClick = {
                    viewModel.deleteChild(child.id) // Çocuğu silmek için ViewModel fonksiyonunu çağır
                    showDeleteDialog.value = false
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog.value = false }) {
                    Text("No")
                }
            }
        )
    }
}

