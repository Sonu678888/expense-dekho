package com.example.expense.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expense.Expense
import com.example.expense.Recurrence
import com.example.expense.groupedByDayOfWeek
import com.example.expense.simplifyNumber

import com.example.expense.ui.theme.LabelSecondary
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.BarChartData.Bar
import com.github.tehras.charts.bar.renderer.bar.SimpleBarDrawer
import com.github.tehras.charts.bar.renderer.label.SimpleValueDrawer
import com.github.tehras.charts.bar.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import java.time.DayOfWeek

@Composable
fun WeeklyChart(expenses: List<Expense>) {
    val groupedExpenses = expenses.groupedByDayOfWeek()

    BarChart(
        barChartData = BarChartData(
            bars = listOf(
                Bar(
                    label = DayOfWeek.MONDAY.name.substring(0, 1),
                    value = groupedExpenses[DayOfWeek.MONDAY.name]?.total?.toFloat()
                        ?: 0f,
                    color = Color.Cyan,
                ),
                Bar(
                    label = DayOfWeek.TUESDAY.name.substring(0, 1),
                    value = groupedExpenses[DayOfWeek.TUESDAY.name]?.total?.toFloat() ?: 0f,
                    color = Color.Cyan,
                ),
                Bar(
                    label = DayOfWeek.WEDNESDAY.name.substring(0, 1),
                    value = groupedExpenses[DayOfWeek.WEDNESDAY.name]?.total?.toFloat() ?: 0f,
                    color = Color.Cyan,
                ),
                Bar(
                    label = DayOfWeek.THURSDAY.name.substring(0, 1),
                    value = groupedExpenses[DayOfWeek.THURSDAY.name]?.total?.toFloat() ?: 0f,
                    color = Color.Cyan,
                ),
                Bar(
                    label = DayOfWeek.FRIDAY.name.substring(0, 1),
                    value = groupedExpenses[DayOfWeek.FRIDAY.name]?.total?.toFloat() ?: 0f,
                    color =Color.Cyan,
                ),
                Bar(
                    label = DayOfWeek.SATURDAY.name.substring(0, 1),
                    value = groupedExpenses[DayOfWeek.SATURDAY.name]?.total?.toFloat() ?: 0f,
                    color = Color.Cyan,
                ),
                Bar(
                    label = DayOfWeek.SUNDAY.name.substring(0, 1),
                    value = groupedExpenses[DayOfWeek.SUNDAY.name]?.total?.toFloat() ?: 0f,
                    color = Color.Cyan,
                ),
            )
        ),
        labelDrawer = LabelDrawer(recurrence = Recurrence.Weekly),
        yAxisDrawer = SimpleYAxisDrawer(
            labelTextColor = LabelSecondary,
            labelValueFormatter = ::simplifyNumber,
            labelRatio = 7,
            labelTextSize = 14.sp
        ),
        barDrawer = BarDrawer(recurrence = Recurrence.Weekly),
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxSize()
    )
}