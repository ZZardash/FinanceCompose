package com.example.financecompose.presentation.menu.add.viewmodel

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financecompose.R
import com.example.financecompose.ui.theme.Gray50

@Composable
fun SelectableOptionsRow() {
    var selectedOption by remember { mutableStateOf("One\nTime") }

    val options = listOf(
        stringResource(R.string.one_time) to R.drawable.one_time,
        stringResource(R.string.daily) to R.drawable.daily,
        stringResource(R.string.weekly) to R.drawable.weekly,
        stringResource(R.string.monthly) to R.drawable.monthly
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(Gray50, shape = RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            options.forEachIndexed { index, pair ->
                val (text, drawable) = pair
                val isSelected = selectedOption == text
                val backgroundColor by animateColorAsState(
                    targetValue = if (isSelected) Color.Gray else Color.Transparent
                )

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                selectedOption = text
                            })
                        }
                        .padding(horizontal = 2.dp)
                        .background(
                            color = backgroundColor,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = drawable),
                        contentDescription = text,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = text,
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily(Font(R.font.fonce))
                        )
                    )
                }

                if (index < options.size - 1) {
                    Divider(
                        modifier = Modifier
                            .height(24.dp)
                            .width(1.dp),
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
