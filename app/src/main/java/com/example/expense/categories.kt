package com.example.expense

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.expense.components.TableRow
import com.example.expense.ui.theme.BackgroundElevated
import com.example.expense.ui.theme.DividerColor
import com.example.expense.ui.theme.Primary
import com.example.expense.ui.theme.Shapes
import com.example.expense.ui.theme.Surface
import com.example.expense.ui.theme.Typography
import com.example.expense.ui.theme.destroy
import com.example.expense.ui.theme.topAppBarBackground
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Categories(navController: NavController,vm:CategoriesViewModel=viewModel()) {
    val uiState by vm.uiState.collectAsState()
    val colorPickerController = rememberColorPickerController()
    Scaffold(
        topBar = {
            MediumTopAppBar(title = { Text("Categories ") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = topAppBarBackground
                ),
                navigationIcon = {
                    Surface(
                        onClick = { navController.popBackStack() },
                        color = Color.Transparent,

                        ) {
                        Row(modifier = Modifier.padding(vertical = 10.dp)) {


                            Icon(Icons.Rounded.KeyboardArrowLeft, contentDescription = null)
                            Text("Settings")

                        }
                    }
                }


            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween
            )
            {
                Column(modifier=Modifier.weight(1f)){
                    LazyColumn(
                        modifier = Modifier
                            .padding(16.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(BackgroundElevated)
                            .fillMaxWidth()
                    )

                    {

                        itemsIndexed(uiState.categories, key = {_,category->category.name}) { index, category ->
                            val swipeableState= rememberDismissState(
                                positionalThreshold ={float->float/3},
                                confirmValueChange = {value->
                                    if(value== DismissValue.valueOf("DismissedToStart")){
                                        vm.deleteCategory(category)
                                        true
                                    }
                                    else{
                                        false
                                    }
                                }
                            )
                            SwipeableActionsBox(
                                endActions = listOf(
                                    SwipeAction(
                                        icon= painterResource(id = R.drawable.delete_fill0_wght400_grad0_opsz24),
                                        background = Color.Red,
                                        onSwipe = {vm.deleteCategory(category)}
                                    )
                                )
                            ) {
                                TableRow(modifier = Modifier.background(
                                    BackgroundElevated)){
                                    Row(verticalAlignment = Alignment.CenterVertically,modifier=Modifier.padding(horizontal=10.dp)) {
                                        Surface(color=category.color,shape= CircleShape,
                                            border= BorderStroke(width=1.dp,color=Color.White
                                            ),
                                            modifier=Modifier.size(12.dp)

                                        ) {}
                                        Text(category.name,modifier=Modifier.padding(horizontal = 16.dp, vertical = 10.dp), style = Typography.bodyMedium)
                                    }
                                }

                            }



                            if (index < uiState.categories.size - 1) {
                                Divider(
                                    modifier = Modifier.padding(start = 16.dp),
                                    thickness = 1.dp,
                                    color = DividerColor
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (uiState.colorPickerShow) {
                        Dialog(onDismissRequest = vm::HideColorPicker) {

                            Surface(color = BackgroundElevated, shape = Shapes.large) {
                                Column(
                                    modifier = Modifier
                                        .padding(all = 30.dp)
                                ) {
                                    Text("Select color", style = Typography.titleLarge)
                                    Spacer(modifier = Modifier.padding(12.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),

                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        AlphaTile(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(60.dp)
                                                .clip(RoundedCornerShape(6.dp)),
                                            controller = colorPickerController
                                        )
                                    }
                                    HsvColorPicker(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(300.dp)
                                            .padding(10.dp),
                                        controller = colorPickerController,
                                        onColorChanged = { envelope ->
                                            vm.setNewCategoryColor(envelope.color)
                                        }
                                    )
                                    BrightnessSlider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp)
                                            .height(35.dp),
                                        controller = colorPickerController,
                                    )
//
                                    TextButton(
                                        onClick = vm::HideColorPicker, modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 24.dp)
                                    ) {
                                        Text(text = "Done")
// }
                                    }


                                }
                            }
                        }
                    }

                    Surface(
                        onClick = vm::ShowColorPicker,
                        shape = CircleShape,
                        color = uiState.newCategoryColor,
                        border = BorderStroke(width = 2.dp, color = Color.White),
                        modifier = Modifier.size(width = 24.dp, height = 24.dp),


                        ) {}
                    Surface(
                        color = BackgroundElevated,
                        modifier = Modifier
                            .height(44.dp)
                            .width(300.dp)
                            .padding(start = 16.dp),
                        shape = Shapes.large
                    )


                    {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            SimpleTextField(
                                value = uiState.newCategoryName,
                                onValueChange = vm::setNewCategoryName,
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 1
                            )

                        }

                    }
                    IconButton(
                        onClick = vm::CreateNewCategory,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Icon(Icons.Rounded.Add, contentDescription = null)

                    }


                }

            }

        }
    )
}


