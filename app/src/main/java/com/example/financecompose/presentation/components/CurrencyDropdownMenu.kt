package com.example.financecompose.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.financecompose.R
import com.example.financecompose.data.enums.Currency

@Composable
fun CurrencyDropdownMenu(
    selectedCurrency: String,
    onCurrencySelected: (String) -> Unit,
    showDisplayName: Boolean = true,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    var selectedOption by remember { mutableStateOf(Currency.USD.code) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                textFieldSize = coordinates.size.toSize()
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(57.dp)
                .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), RoundedCornerShape(5.dp))
                .clickable { expanded = true }
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "Currency icon",
                    modifier = Modifier.size(24.dp).alpha(0.8f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = selectedOption,
                    fontFamily = FontFamily(Font(R.font.eina)),
                    modifier = Modifier.alpha(0.6f)
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.alpha(0.8f)
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                .heightIn(max = 300.dp) // Adjust maximum height as needed
        ) {
            Currency.entries.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        selectedOption = option.code
                        onCurrencySelected(option.code)
                        expanded = false
                    },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = if (showDisplayName) "${option.displayName} (${option.code})" else "${option.code} (${option.symbol})")
                        }
                    }
                )
            }
        }
    }
}
