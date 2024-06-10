package com.example.expense

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.expense.ui.theme.LightPink
import com.example.expense.ui.theme.Shapes
import com.example.expense.ui.theme.paleGreen
import com.example.expense.ui.theme.turqoise

data class OnboardingItem(val imagesRes:Int,val title: String, val description: String)

val listdata = listOf(
    OnboardingItem(R.drawable.welcomescreen,"Welcome to Xpense Dekho", "Your personal finance assistant, here to help you track and manage your expenses effortlessly.",
        ),
    OnboardingItem(R.drawable.screen2,"Easy Expense Tracking", "Quickly log your daily expenses with just a few taps. Keep track of where your money goes and stay on top of your spending.",
        ),
    OnboardingItem(R.drawable.analysis,"Visual Insights", "Understand your spending habits with intuitive charts and graphs. See your expenses broken down by category, and identify areas where you can save.",
        ),

)


@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color,
    unSelectedColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(totalDots) { index ->
            Box(
                modifier = Modifier
                    .size(15.dp)
                    .clip(CircleShape)
                    .background(if (index == selectedIndex) selectedColor else unSelectedColor)
                    .padding(horizontal = 4.dp)
            )

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}

