package com.example.financecompose.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.example.financecompose.R

@Composable
fun EntranceTextField(
    stringState: MutableState<String>,
    text: String,
    fontFamily: FontFamily?,
    textType: KeyboardType,
    textSize: Int,
    leadingIcon: ImageVector,
    leadIconDesc: String,
    ) {
    OutlinedTextField(
        value = stringState.value,
        onValueChange = { stringState.value = it },
        label = {
            Text(
                text,
                fontFamily = fontFamily
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = textType),
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(
            fontSize = textSize.sp,
            fontFamily = fontFamily
        ),
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = leadIconDesc
            )
        },
        trailingIcon = {
            if (stringState.value.isNotEmpty()) {
                IconButton(onClick = { stringState.value = "" }) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        contentDescription = stringResource(R.string.clear_text)
                    )
                }
            }
        }
    )

}
