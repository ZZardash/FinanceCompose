package com.example.financecompose.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.github.skydoves.colorpicker.compose.*

@Composable
fun ColorPicker(onColorSelected: (Color) -> Unit) {
    val controller = rememberColorPickerController()
    var selectedColor by remember { mutableStateOf(Color.White) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(all = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(10.dp),
            controller = controller,
            onColorChanged = {
                // Check if the color is white or very close to white
                val hsv = FloatArray(3)
                ColorUtils.colorToHSL(it.color.toArgb(), hsv)
                // Filter out white (hue and saturation near 0, high lightness)
                if (!(hsv[1] < 0.1f && hsv[2] > 0.9f)) {
                    selectedColor = it.color
                    onColorSelected(it.color)
                }
            }
        )
        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .width(200.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(selectedColor)
        )

    }
}