package com.example.expense

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.expense.components.TableRow
import com.example.expense.ui.theme.BackgroundElevated
import com.example.expense.ui.theme.DividerColor
import com.example.expense.ui.theme.Shapes
import com.example.expense.ui.theme.button_back
import com.example.expense.ui.theme.topAppBarBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(navController: NavController) {
    Scaffold(
        topBar = {
            MediumTopAppBar(title = { Text("Settings") }, colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = topAppBarBackground
            ))
        },
        content = {innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Column(modifier = Modifier
                    .padding(16.dp)
                    .clip(Shapes.large)
                    .background(BackgroundElevated)
                    .fillMaxWidth()
                ) {
                    TableRow(label = "Categories", Arrow = true, modifier = Modifier.clickable {
                        navController.navigate("settings/categories")
                    })
                    Divider(modifier = Modifier
                        .padding(start = 16.dp), thickness = 1.dp, color = DividerColor)
                    TableRow(label = "Remove", isdestroy = true)
                }
            }
        }
    )
}