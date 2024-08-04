package com.example.financecompose.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financecompose.R

@Composable
fun CustomIconTextField(
    stringState: MutableState<String>,
    text: String,
    readOnlyOption: Boolean = false,
    fontFamily: FontFamily?,
    textType: KeyboardType,
    textSize: Int,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector = Icons.Default.Clear,
    trailerOnClick: () -> Unit = { stringState.value = "" },
    onClick: () -> Unit = { },
    leadIconDesc: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    height: Int = 60,
    singleLine: Boolean = true,
    leadIconAlign: Alignment = Alignment.Center,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() },
    characterLimit: Int? = null,
    showCharacterCounter: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .heightIn(min = height.dp + if (showCharacterCounter) 20.dp else 0.dp) // Ensures space for the counter
            .padding(bottom = if (showCharacterCounter) 8.dp else 0.dp) // Extra padding if the counter is shown
    ) {
        OutlinedTextField(
            value = stringState.value,
            onValueChange = {
                if (characterLimit == null || it.length <= characterLimit) {
                    stringState.value = it
                }
            },
            label = {
                Text(
                    text,
                    fontFamily = fontFamily,
                    modifier = Modifier.alpha(0.8f)
                )
            },
            readOnly = readOnlyOption,
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions(keyboardType = textType),
            textStyle = TextStyle(
                fontSize = textSize.sp,
                fontFamily = fontFamily
            ),
            leadingIcon = {
                leadingIcon?.let {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(17.dp),
                        contentAlignment = leadIconAlign
                    ) {
                        Icon(
                            imageVector = it,
                            contentDescription = leadIconDesc,
                        )
                    }
                }
            },
            trailingIcon = {
                if (textType == KeyboardType.Password) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(if (passwordVisible) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24),
                            contentDescription = if (passwordVisible) stringResource(id = R.string.clear_text) else stringResource(id = R.string.show_password)
                        )
                    }
                } else {
                    if (stringState.value.isNotEmpty()) {
                        IconButton(onClick = trailerOnClick) {
                            Icon(
                                imageVector = trailingIcon,
                                contentDescription = stringResource(R.string.clear_text)
                            )
                        }
                    }
                }
            },
            visualTransformation = if (textType == KeyboardType.Password && !passwordVisible) {
                PasswordVisualTransformation()
            } else {
                visualTransformation
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(height.dp) // This sets the height of the TextField itself
        )
        // Show character counter if enabled
        if (showCharacterCounter && characterLimit != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "${stringState.value.length} / $characterLimit",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = fontFamily
                    ),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}




