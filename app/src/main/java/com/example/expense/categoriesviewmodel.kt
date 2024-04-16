package com.example.expense

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.expense.pages.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CategoriesState(
    val newCategoryColor: Color = Color.White,
    val newCategoryName: String = "",
    val colorPickerShowing: Boolean = false,
    val categories: MutableList<Category> = mutableListOf(
        Category("Canteen", Color.Red),
        Category("Travel", Color.Yellow),
        Category("Dine out", Color.Blue),
        Category("Lending", Color.Cyan),
        Category("Entertainment", Color.Cyan),

    )
)

class CategoriesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CategoriesState())
    val uiState: StateFlow<CategoriesState> = _uiState.asStateFlow()

    fun setNewCategoryColor(color: Color) {
        _uiState.update { currentState ->
            currentState.copy(
                newCategoryColor = color
            )
        }
    }

    fun setNewCategoryName(name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                newCategoryName = name
            )
        }
    }

    fun showColorPicker() {
        _uiState.update { currentState ->
            currentState.copy(
                colorPickerShowing = true
            )
        }
    }

    fun hideColorPicker() {
        _uiState.update { currentState ->
            currentState.copy(
                colorPickerShowing = false
            )
        }
    }

    fun createNewCategory() {
        val newCategoriesList = mutableListOf(
            Category(
                _uiState.value.newCategoryName,
                _uiState.value.newCategoryColor
            )
        )
        newCategoriesList.addAll(
            _uiState.value.categories,
        )

        _uiState.update { currentState ->
            currentState.copy(
                categories = newCategoriesList,
                newCategoryName = "",
                newCategoryColor = Color.White,
            )
        }
    }

    fun deleteCategory(category: Category) {
        val index = _uiState.value.categories.indexOf(category)
        val newList = mutableListOf<Category>()
        newList.addAll(_uiState.value.categories)
        newList.removeAt(index)

        _uiState.update { currentState ->
            currentState.copy(
                categories = newList
            )
        }
    }
}