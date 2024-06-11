package com.example.financecompose.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.example.financecompose.R

@Composable
fun PasswordValidator(
    stringState: MutableState<String>,
) {
    var isLengthValid by remember { mutableStateOf(false) }
    var containsSpecialChar by remember { mutableStateOf(false) }

    val password = stringState.value

    // Update validation status as the user types
    LaunchedEffect(password) {
        isLengthValid = password.length >= 10
        containsSpecialChar = password.contains(Regex("[^A-Za-z0-9]"))
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 16.dp)
    ) {
        Icon(
            imageVector = if (isLengthValid) Icons.Rounded.Check else Icons.Rounded.Close,
            contentDescription = stringResource(R.string.password_valid),
            tint = if (isLengthValid) Color.Green else Color.Gray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = " At least 10 characters",
            fontSize = 12.sp
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 16.dp)
    ) {
        Icon(
            imageVector = if (containsSpecialChar) Icons.Rounded.Check else Icons.Rounded.Close,
            contentDescription = stringResource(R.string.password_invalid),
            tint = if (containsSpecialChar) Color.Green else Color.Gray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = " Contains a special character",
            fontSize = 12.sp
        )
    }
}

