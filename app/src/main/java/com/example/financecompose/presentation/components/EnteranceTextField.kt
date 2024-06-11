package com.example.financecompose.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.example.financecompose.R

@Composable
fun EntranceTextField(
    stringState: MutableState<String>,
    text: String,
    readOnlyOption: Boolean = false,
    fontFamily: FontFamily?,
    textType: KeyboardType,
    textSize: Int,
    leadingIcon: ImageVector,
    trailingIcon: ImageVector = Icons.Default.Clear,
    trailerOnClick: () -> Unit = { stringState.value = "" },
    onClick: () -> Unit = { },
    leadIconDesc: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    showPasswordToggle: Boolean = false
) {

    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = stringState.value,
        onValueChange = { stringState.value = it },
        label = {
            Text(
                text,
                fontFamily = fontFamily
            )
        },
        readOnly = readOnlyOption,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = textType),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
            if (textType == KeyboardType.Password){
                IconButton(onClick = { passwordVisible = !passwordVisible}) {
                    Icon(
                        painter = painterResource(if (passwordVisible) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24),
                        contentDescription = if (passwordVisible) stringResource(id = R.string.clear_text) else stringResource(id = R.string.show_password)
                    )
                }
            }
            else{
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
        }
    )
}
