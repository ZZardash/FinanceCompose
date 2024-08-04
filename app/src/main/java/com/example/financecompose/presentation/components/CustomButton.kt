package com.example.financecompose.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.financecompose.ui.theme.TextColor

sealed class ButtonContent {

    data class IconContent(
        val imageVector: ImageVector,
        val contentDescription: String?,
        val tint: Color = Color.Unspecified) : ButtonContent()

    data class ImageContent(
        val painter: Painter,
        val contentDescription: String,
        val imageModifier: Modifier
    ) : ButtonContent()
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonColor: ButtonColors = ButtonDefaults.buttonColors(),
    isOutlined: Boolean = false,
    buttonContent: ButtonContent,
    text: String,
    fontFamily: FontFamily? = null,
    tint: Color
) {
    if (isOutlined) {
        OutlinedButton(onClick = onClick, modifier = modifier) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(text = text, fontFamily = fontFamily, color = TextColor)
                Spacer(modifier = Modifier.width(8.dp))
                when (buttonContent) {
                    is ButtonContent.IconContent -> {
                        androidx.compose.material3.Icon(
                            imageVector = buttonContent.imageVector,
                            contentDescription = buttonContent.contentDescription,
                            tint = tint
                        )
                    }

                    is ButtonContent.ImageContent -> {
                        androidx.compose.foundation.Image(
                            painter = buttonContent.painter,
                            contentDescription = buttonContent.contentDescription,
                            modifier = buttonContent.imageModifier
                        )
                    }
                }
            }
        }
    } else {
        Button(onClick = onClick, colors = buttonColor, modifier = modifier) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(text = text, fontFamily = fontFamily)
                Spacer(modifier = Modifier.width(8.dp))
                when (buttonContent) {
                    is ButtonContent.IconContent -> {
                        androidx.compose.material3.Icon(
                            imageVector = buttonContent.imageVector,
                            contentDescription = buttonContent.contentDescription,
                            tint = tint
                        )
                    }

                    is ButtonContent.ImageContent -> {
                        androidx.compose.foundation.Image(
                            painter = buttonContent.painter,
                            contentDescription = buttonContent.contentDescription,
                            modifier = buttonContent.imageModifier
                        )
                    }
                }
            }
        }
    }
}
