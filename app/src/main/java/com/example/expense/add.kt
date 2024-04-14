package com.example.expense

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.expense.components.TableRow
import com.example.expense.ui.theme.BackgroundElevated
import com.example.expense.ui.theme.DividerColor
import com.example.expense.ui.theme.Primary
import com.example.expense.ui.theme.Typography
import com.example.expense.ui.theme.button_back
import com.example.expense.ui.theme.button_text

import com.example.expense.ui.theme.topAppBarBackground
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun Add(navController: NavController,addViewModel: AddViewModel=viewModel()){
    val state by addViewModel.uiState.collectAsState()
    val categories= listOf("Canteen","Dine out","Travel","Shopping","Lending","Trip")
    var selectedCategory by remember {
        mutableStateOf(categories[0])
    }

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
                    TableRow(label=" Amount", detailContent = {
                        SimpleTextField(value = state.amount,
                            onValueChange ={addViewModel.setAmount(it)},
                            modifier=Modifier.fillMaxWidth(),
                            placeholder = {Text("0")},
                            textStyle = TextStyle(textAlign= TextAlign.End ),
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    })
                    Divider(modifier=Modifier.padding(start=16.dp),thickness=1.dp,color= DividerColor)
                    var dateshow by remember {
                        mutableStateOf(false)
                    }
                    TableRow(label=" Date", detailContent = {
                        TextButton(onClick = { dateshow=true },shape= RoundedCornerShape(8.dp)) {

                            Text(state.date.toString())

                        }
                        if(dateshow){
                            com.marosseleng.compose.material3.datetimepickers.date.ui.dialog.DatePickerDialog(
                                onDismissRequest = { dateshow=false },
                                onDateChange = {it->
                                    addViewModel.setDate(it)
                                    dateshow=false

                                },
                                initialDate = LocalDate.now(),
                                title = {(Text("Select Date",style= Typography.titleMedium))}
                            )
//
//                        )
                        }
                    }){

//

                    }
                    Divider(modifier=Modifier.padding(start=16.dp),thickness=1.dp,color= DividerColor)
                    TableRow(label=" Note",detailContent={
                        SimpleTextField(value = state.note,
                            onValueChange ={addViewModel.setnote(it)},
                            modifier=Modifier.fillMaxWidth(),
                            textStyle = TextStyle(textAlign = TextAlign.End),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                    })
                    Divider(modifier=Modifier.padding(start=16.dp),thickness=1.dp,color= DividerColor)
                    TableRow(label=" category", detailContent = {var categoryopened by remember {
                        mutableStateOf(false)
                    }
                        TextButton(onClick = { categoryopened=true },shape= RoundedCornerShape(8.dp) ) {
                            Text(state.category?:" Choose Category ", textAlign = TextAlign.End)
                            DropdownMenu(expanded = categoryopened, onDismissRequest = { categoryopened=false }) {
                                categories.forEach {category ->
                                    DropdownMenuItem(text = { Text(category) }, onClick = { addViewModel.setcategory(category)
                                        categoryopened=false})
                                }
                            }

                        }}){


                    }
                }
                Button(onClick = {addViewModel::submit

                },
                    modifier=Modifier.align(Alignment.CenterHorizontally),colors=ButtonDefaults.buttonColors(containerColor = button_back)

                ) {
                    Text("Add Expense", style = TextStyle(color=Color.Black, fontSize = 18.sp))
                }


        }


}
    )
}