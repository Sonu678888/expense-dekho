package com.example.expense

import androidx.compose.ui.graphics.Color
import com.example.expense.pages.Category
import io.github.serpro69.kfaker.Faker
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

val faker = Faker()

val mockCategories = listOf(
    Category(
        "Food",
        Color(
            faker.random.nextInt(0, 255),
            faker.random.nextInt(0, 255),
            faker.random.nextInt(0, 255)
        )
    ),

)

val mockExpenses: List<Expense> = List(30) { index ->
    Expense(
        id = index,
        amount = faker.random.nextInt(min = 1, max = 999)
            .toDouble() + faker.random.nextDouble(),
        date = LocalDateTime.now().minus(
            faker.random.nextInt(min = 300, max = 345600).toLong(),
            ChronoUnit.SECONDS
        ),
        recurrence = faker.random.randomValue(
            listOf(
                Recurrence.None,
                Recurrence.Daily,
                Recurrence.Monthly,
                Recurrence.Weekly,
                Recurrence.Yearly
            )
        ),
        note = faker.food.fruits(),
        category = faker.random.randomValue(mockCategories)
    )
}