package com.example.financecompose.presentation.menu.home.ui

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.financecompose.domain.model.Transaction

@Composable
fun PieChart(
    data: List<Transaction>, // List of transactions
    radiusOuter: Dp = 140.dp,
    chartBarWidth: Dp = 35.dp,
    animDuration: Int = 1000
) {
    val totalSum = data.sumOf { it.transactionAmount?.toDouble() ?: 0.0 }

    val floatValues = mutableListOf<Float>()

    // Calculate the angle for each segment
    data.forEach { transaction ->
        floatValues.add(360 * (transaction.transactionAmount?.toFloat() ?: 0f) / totalSum.toFloat())
    }

    // Animation states
    var animationPlayed by remember { mutableStateOf(false) }
    var lastValue = 0f

    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 360f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Pie Chart using Canvas Arc
        Box(
            modifier = Modifier.size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(radiusOuter * 2f)
                    .rotate(animateRotation)
            ) {
                /*
                data.forEachIndexed { index, transaction ->
                    val colorHex = transaction.transactionCategoryColor
                    colorHex?.let { hexToColor(it) }?.let {
                        drawArc(
                            color = it,
                            startAngle = lastValue,
                            sweepAngle = floatValues[index],
                            useCenter = false,
                            style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
                        )
                    }
                    lastValue += floatValues[index]
                }
             */
            }
        }

        // Details for each segment
        DetailsPieChart(data = data)
    }
}

@Composable
fun DetailsPieChart(
    data: List<Transaction>
) {
    Column(
        modifier = Modifier
            .padding(top = 80.dp)
            .fillMaxWidth()
    ) {
        // Create the data items
        /*
        data.forEach { transaction ->
            transaction.transactionAmount?.let { Pair(transaction.transactionTitle, it.toInt()) }?.let {
                transaction.transactionCategoryColor?.let { it1 -> hexToColor(it1) }?.let { it2 ->
                    DetailsPieChartItem(
                        data = it,
                        color = it2
                    )
                }
            }
        }
         */
    }
}

@Composable
fun DetailsPieChartItem(
    data: Pair<String?, Int>, // Pair of transaction title and amount
    color: Color
) {
    Row(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Colored circle
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color, shape = CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Title and amount text
        Text(
            text = "${data.first}: ${data.second}",
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
        )
    }
}

fun hexToColor(hex: String): Color {
    val color = android.graphics.Color.parseColor(hex)
    return Color(color)
}


fun colorToHex(color: Color): String {
    // Convert Color to ARGB integer value
    val argb = color.toArgb()

    // Extract red, green, blue components (excluding alpha)
    val red = (argb shr 16) and 0xFF
    val green = (argb shr 8) and 0xFF
    val blue = argb and 0xFF

    // Convert to hex string with RGB format
    return "#${red.toUInt().toString(16).padStart(2, '0')}${green.toUInt().toString(16).padStart(2, '0')}${blue.toUInt().toString(16).padStart(2, '0')}"
}

