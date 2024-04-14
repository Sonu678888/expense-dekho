package com.example.expense

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate


data class Addscreen @RequiresApi(Build.VERSION_CODES.O) constructor(
    val amount:String=" ",
    val date:LocalDate= LocalDate.now(),
    val note: String= " ",
    val category: String?=null,
)
class AddViewModel :ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    private val _uiState= MutableStateFlow(Addscreen(
        amount = " ",
        date= LocalDate.now()
    ))
    @RequiresApi(Build.VERSION_CODES.O)
    val uiState:StateFlow<Addscreen> = _uiState.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun setAmount(amount:String){
        _uiState.update {currentState->
            currentState.copy(amount=amount.trim())
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun setDate(date: LocalDate) {
        _uiState.update { currentState ->
            currentState.copy(date=date)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun setnote(note: String) {
        _uiState.update { currentState ->
            currentState.copy(note=note)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun setcategory(category: String?) {
        _uiState.update { currentState ->
            currentState.copy(category=category)
        }
    }
    fun submit(){

    }


}