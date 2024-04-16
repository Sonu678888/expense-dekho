package com.example.expense.spendList

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expense.DayExpenses
import com.example.expense.formatDay
import com.example.expense.ui.theme.LabelSecondary
import com.example.expense.ui.theme.Typography
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ExpensesDayGroup(
    date: LocalDate,
    dayExpenses: DayExpenses,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            date.formatDay(),
            style = Typography.bodyMedium,
            color = LabelSecondary
        )
        Divider(modifier = Modifier.padding(top = 10.dp, bottom = 4.dp))
        dayExpenses.expenses.forEach { expense ->
            ExpenseRow(
                expense = expense,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
        Divider(modifier = Modifier.padding(top = 16.dp, bottom = 4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(" Total :", style = Typography.bodyMedium, color = LabelSecondary)
            Text(
                DecimalFormat("₹ 0.#").format(dayExpenses.total.toInt()),
                style = Typography.headlineMedium,
                color = LabelSecondary
            )
        }
    }
}