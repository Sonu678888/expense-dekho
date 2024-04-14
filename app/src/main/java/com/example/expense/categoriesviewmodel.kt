package com.example.expense

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.expense.pages.Category
import com.example.expense.ui.theme.Primary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CategoriesState(
    val newCategoryColor: Color = Color.White,
    val newCategoryName: String= "",
    val colorPickerShow:Boolean=false,
    val categories: MutableList<Category> = mutableListOf(
        Category("Canteen",Color.White),
        Category("Dine out",Color.Green),
        Category("Travel",Color.Blue),
        Category("Shopping",Color.Red),
        Category("Trip",Color.Magenta),
        ),

    )
class CategoriesViewModel:ViewModel(){
    private val _uiState= MutableStateFlow(CategoriesState())
    val uiState:StateFlow<CategoriesState> = _uiState.asStateFlow()
    fun setNewCategoryColor(color:Color) {
        _uiState.update { currentState ->
            currentState.copy(
                newCategoryColor = color

            )
        }
    }
  fun setNewCategoryName(name:String){
            _uiState.update { currentState->
                currentState.copy(
                    newCategoryName = name

                )
            }
    }
    fun ShowColorPicker(){
        _uiState.update { currentState->
            currentState.copy(
                colorPickerShow = true

            )
        }
    }
    fun HideColorPicker(){
        _uiState.update { currentState->
            currentState.copy(
               colorPickerShow = false

            )
        }
    }
        fun CreateNewCategory(){
            val newCategories= mutableListOf(Category(
                _uiState.value.newCategoryName,
                _uiState.value.newCategoryColor

            ))
            newCategories.addAll(
                _uiState.value.categories,
            )
            _uiState.update { currentState->
                currentState.copy(categories=newCategories,
                    newCategoryName = "",
                    newCategoryColor = Color.White)


            }



        }

}

