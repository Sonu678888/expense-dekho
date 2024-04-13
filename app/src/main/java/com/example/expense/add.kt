package com.example.expense

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expense.components.TableRow
import com.example.expense.ui.theme.BackgroundElevated
import com.example.expense.ui.theme.DividerColor
import com.example.expense.ui.theme.button_back
import com.example.expense.ui.theme.button_text

import com.example.expense.ui.theme.topAppBarBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Add(navController: NavController){
    Scaffold(
        topBar = {
            MediumTopAppBar(title = { Text("ADD") }, colors = TopAppBarDefaults.mediumTopAppBarColors(
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
                    TableRow(" Amount",content={
                        SimpleTextField(value = "Amount", onValueChange ={}, textStyle = TextStyle(textAlign= TextAlign.End ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    })
                    Divider(modifier=Modifier.padding(start=16.dp),thickness=1.dp,color= DividerColor)
                    TableRow(" Date")
                    Divider(modifier=Modifier.padding(start=16.dp),thickness=1.dp,color= DividerColor)
                    TableRow(" Note",content={
                        SimpleTextField(value = "", onValueChange ={},
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), modifier=Modifier.fillMaxWidth()
                        )
                    })
                    Divider(modifier=Modifier.padding(start=16.dp),thickness=1.dp,color= DividerColor)
                    TableRow(" category")
                }
                Button(onClick = {},
                    modifier=Modifier.align(Alignment.CenterHorizontally),colors=ButtonDefaults.buttonColors(containerColor = button_back)

                ) {
                    Text("Add Expense", style = TextStyle(color=Color.Black, fontSize = 18.sp))
                }


        }


}
    )
}