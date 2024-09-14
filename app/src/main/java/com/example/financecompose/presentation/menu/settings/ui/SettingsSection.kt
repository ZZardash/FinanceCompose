package com.example.financecompose.presentation.menu.settings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financecompose.R
import com.example.financecompose.presentation.components.CurrencyDropdownMenu
import com.example.financecompose.presentation.components.LanguageDropdownMenu
import com.example.financecompose.ui.theme.Purple90

@Composable
fun SettingsSection(
    title: String,
    items: List<SettingsItem>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (title == "Profile") Icons.Outlined.AccountCircle else Icons.Outlined.Settings,
                contentDescription = "$title icon"
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = title,
                fontFamily = FontFamily(Font(R.font.eina)),
                fontSize = 20.sp,
                color = Purple90
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(Modifier.fillMaxWidth()) {
            items.forEach { item ->
                if (item.switch) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color.White)
                            .clickable {
                                item.onCheckedChange?.invoke(!(item.isChecked ?: false))
                            },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.label,
                            fontFamily = FontFamily(Font(R.font.cabrion)),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.5.sp,
                            modifier = Modifier.padding(start = 23.dp)
                        )

                        DarkModeSwitch(
                            checked = item.isChecked ?: false,
                            onCheckedChanged = item.onCheckedChange ?: {},
                            modifier = Modifier
                                .padding(10.dp)
                                .width(60.dp)
                        )
                    }
                } else if (item.showCurrencyDropdown) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color.White),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.label,
                            fontFamily = FontFamily(Font(R.font.cabrion)),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.5.sp,
                            modifier = Modifier.padding(start = 23.dp)
                        )

                        CurrencyDropdownMenu(
                            selectedCurrency = item.selectedCurrency ?: "USD",
                            onCurrencySelected = item.onCurrencySelected ?: {},
                            showIcon = false,
                            showDisplayName = false,
                            modifier = Modifier.width(105.dp).padding(8.dp) // Adjust width as necessary
                        )
                    }
                }
                else if (item.showLanguageDropdown) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color.White),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.label,
                            fontFamily = FontFamily(Font(R.font.cabrion)),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.5.sp,
                            modifier = Modifier.padding(start = 23.dp)
                        )

                        LanguageDropdownMenu(
                            selectedLanguage = item.selectedLanguage ?: "English",
                            onLanguageSelected = item.onLanguageSelected ?: {},
                            showIcon = false,
                            modifier = Modifier.width(125.dp)
                                .padding(8.dp) // Adjust width as necessary
                        )
                    }
                }

                else {
                    Button(
                        onClick = item.onClick ?: {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item.label,
                                fontFamily = FontFamily(Font(R.font.cabrion)),
                                fontWeight = FontWeight.Bold
                            )
                            Icon(imageVector = item.icon!!, contentDescription = item.label)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}
