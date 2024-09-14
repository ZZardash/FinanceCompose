package com.example.financecompose.presentation.menu.transaction.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financecompose.domain.model.Category
import com.google.accompanist.flowlayout.FlowRow
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryChipGroup(
    selectedCategory: String?,
    onCategorySelected: (String, Color) -> Unit
) {
    var chipTexts by remember { mutableStateOf(listOf<String>()) }
    var colors by remember { mutableStateOf(listOf<Color>()) }

    var showDialog by remember { mutableStateOf(false) }
    var newCategoryTitle by remember { mutableStateOf("") }
    var newCategoryColor by remember { mutableStateOf(Color.Gray) }
    var selectedIcon by remember { mutableStateOf<String?>(null) } // State to hold selected icon
    var isTitleError by remember { mutableStateOf(false) }

    val firestore = FirebaseFirestore.getInstance()
    val currentUser = FirebaseAuth.getInstance().currentUser


    LaunchedEffect(Unit) {
        currentUser?.let { user ->
            try {
                withContext(Dispatchers.IO) {
                    firestore.collection("users")
                        .document(user.uid)
                        .collection("categories")
                        .get()
                        .addOnSuccessListener { documents ->
                            val fetchedChipTexts = mutableListOf<String>()
                            val fetchedColors = mutableListOf<Color>()
                            val fetchedIcons = mutableListOf<String?>()

                            for (document in documents) {
                                val category = document.toObject(Category::class.java)
                                fetchedChipTexts.add(category.categoryName ?: "")
                                fetchedColors.add(Color(android.graphics.Color.parseColor(category.categoryColor)))
                            }

                            // Update the states in the main thread
                            chipTexts = fetchedChipTexts
                            colors = fetchedColors
                        }
                        .addOnFailureListener { e ->
                            e.printStackTrace() // Handle error
                        }
                }
            } catch (e: Exception) {
                e.printStackTrace() // Handle any exceptions
            }
        }
    }

    // Reset the new category fields when the dialog is dismissed
    LaunchedEffect(showDialog) {
        if (!showDialog) {
            newCategoryTitle = ""
            newCategoryColor = Color.Gray
            selectedIcon = null
            isTitleError = false
        }
    }

    if (showDialog) {
        NewCategoryDialog(
            onDismissRequest = { showDialog = false },
            onSave = { title, color, icon ->
                chipTexts = chipTexts + title
                colors = colors + color

                // Save the new category to Firestore
                val newCategory = Category(
                    categoryName = title,
                    categoryColor = String.format("#%06X", (0xFFFFFF and color.toArgb())),
                )
                currentUser?.let { user ->
                    firestore.collection("users")
                        .document(user.uid)
                        .collection("categories")
                        .add(newCategory)
                        .addOnSuccessListener {
                            // Category successfully saved to Firestore
                        }
                        .addOnFailureListener { e ->
                            e.printStackTrace()
                        }
                }

                // Call the callback function
                onCategorySelected(title, color)
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
                targetValue = if (selectedCategory == text) colors[index] else colors[index].copy(
                    alpha = 0.2f
                )
            )
            val textColor by animateColorAsState(
                targetValue = if (selectedCategory == text) Color.White else colors[index]
            )
            val iconTint by animateColorAsState(
                targetValue = if (selectedCategory == text) Color.White else colors[index]
            )

            Box(
                modifier = Modifier
                    .background(backgroundColor, shape = RoundedCornerShape(16.dp))
                    .combinedClickable(
                        onClick = {
                            onCategorySelected(
                                text,
                                colors[index]
                            )
                        }
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Display the category text on the right
                    Text(
                        text = text,
                        style = TextStyle(
                            color = textColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    // Display a check icon if the category is selected
                    if (selectedCategory == text) {
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
}









