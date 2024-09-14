package com.example.financecompose.presentation.menu.child.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.financecompose.R
import com.example.financecompose.domain.model.Child
import com.example.financecompose.presentation.menu.child.viewmodel.ChildViewModel
import com.example.financecompose.ui.theme.Purple90
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
@Composable
fun ChildScreen(
    navController: NavController,
    viewModel: ChildViewModel = hiltViewModel()
) {
    val children by viewModel.children.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    // Layout
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // Yatayda düzgün hizalama
            ) {

                IconButton(
                    onClick = { /* Menü butonuna tıklanma işlemi */ }
                ) {
                    Icon(
                        modifier = Modifier.padding(bottom = 3.dp),
                        imageVector = Icons.Default.Menu,
                        contentDescription = stringResource(R.string.menu)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = stringResource(R.string.child),
                    Modifier.weight(1f),
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.eina)),
                        fontSize = 20.sp
                    )
                )


                IconButton(
                    onClick = { /* Search butonuna tıklanma işlemi */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.search)
                    )
                }


                Spacer(modifier = Modifier.width(8.dp))


                IconButton(
                    onClick = { /* + butonuna tıklanma işlemi */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.add_child),
                        modifier = Modifier.padding(bottom = 4.dp).size(27.dp)
                    )
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(24.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Purple90,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "Add Child",
                        fontSize = 16.sp
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            if (children.isEmpty()) {
                EmptyChildListScreen() // Boş liste durumu için özel ekran
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    itemsIndexed(children as List<Child>) { index, child ->
                        ChildItem(
                            child = child,
                            viewModel = viewModel,
                            onSeeDetails = {
                                // Navigate to the See Child Details Screen
                                //navController.navigate("child_details/${child.id}")
                            }
                        )
                        Spacer(modifier = Modifier.height(15.dp))

                    }
                }
            }


        }
    }

    if (showDialog) {
        AddChildDialog(
            onAdd = { childName, childBalance ->
                viewModel.addChild(childName, childBalance)
                showDialog = false
            }
        ) { showDialog = false }
    }
}

