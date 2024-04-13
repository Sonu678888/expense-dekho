package com.example.expense

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expense.components.TableRow
import com.example.expense.ui.theme.BackgroundElevated
import com.example.expense.ui.theme.DividerColor
import com.example.expense.ui.theme.topAppBarBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(navController: NavController){
    Scaffold(
        topBar = {
            MediumTopAppBar(title = { Text("Settings") }, colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = topAppBarBackground
            ))
        },
        content={innerPadding->
            Column(modifier= Modifier.padding(innerPadding)){
                Column(modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(BackgroundElevated)
                    .fillMaxWidth()){
                    TableRow(" Categories",modifier=Modifier.clickable { navController.navigate("settings/Categories")},Arrow=true)
                    Divider(modifier=Modifier.padding(start=16.dp),thickness=1.dp,color= DividerColor)
                    TableRow(" Remove",  isdestroy = true)

                }

            }

        }
    )


}