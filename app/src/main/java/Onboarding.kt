import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expense.DotsIndicator
import com.example.expense.OnboardingItem
import com.example.expense.listdata
import com.example.expense.ui.theme.ExpenseTheme
import com.example.expense.ui.theme.FillTertiary
import com.example.expense.ui.theme.LightCoral
import com.example.expense.ui.theme.LightPink
import com.example.expense.ui.theme.OffWhite
import com.example.expense.ui.theme.PoppinsFontFamily
import com.example.expense.ui.theme.Primary
import com.example.expense.ui.theme.TextPrimary
import com.example.expense.ui.theme.beige
import com.example.expense.ui.theme.button_back
import com.example.expense.ui.theme.exoFontFamily
import com.example.expense.ui.theme.lightGreen
import com.example.expense.ui.theme.logo
import com.example.expense.ui.theme.mint
import com.example.expense.ui.theme.paleGreen
import com.example.expense.ui.theme.purple
import com.example.expense.ui.theme.topAppBarBackground
import com.example.expense.ui.theme.turqoise
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import me.saket.swipe.rememberSwipeableActionsState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(pages: List<OnboardingItem>,systemUiController: SystemUiController,navController: NavController,
                     onComplete: () -> Unit) {
    val pagerState = rememberPagerState()
    val scope= rememberCoroutineScope()
    val systemUiController = rememberSystemUiController()
    val onboardingBackColor = LightPink


    DisposableEffect(systemUiController, onboardingBackColor) {
        systemUiController.setStatusBarColor(
            color = onboardingBackColor
        )
        onDispose {
            // Optionally reset the status bar color when the composable disposes
            systemUiController.setStatusBarColor(
                color = Primary
            )
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(onboardingBackColor)
    ) {
        HorizontalPager(
            count = pages.size,
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    color = Color.Transparent,
                ) {
                    Image(
                        painter = painterResource(id = pages[page].imagesRes),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = pages[page].title,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = PoppinsFontFamily,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = pages[page].description,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontFamily = PoppinsFontFamily,
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(80.dp))
                if(pagerState.currentPage==2) {
                    ElevatedButton(onClick = {
                        onComplete()
                        navController.navigate("Login")
                    },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(width = 327.dp, height = 54.dp)
                    ) {
                        Text("GET STARTED",
                            fontFamily = exoFontFamily,
                            fontWeight = FontWeight.Bold,
                            color=Color.White,
                            fontSize = 20.sp,
                            )
                    }
                }
                else{
                    ElevatedButton(onClick = {scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }},modifier= Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(width = 150.dp, height = 45.dp)
                    ) {
                        Text(text = "Next",
                            fontFamily = exoFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color=Color.White,
                            fontSize = 20.sp)

                    }
                }
            }
        }

        DotsIndicator(
            totalDots = pages.size,
            selectedIndex = pagerState.currentPage,
            selectedColor = Color.Black,
            unSelectedColor = Color.Gray,
            modifier = Modifier.padding(16.dp)
        )
    }
}



@Composable
@Preview(showBackground = true)
fun OnboardingPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        ExpenseTheme {
            val systemUiController= rememberSystemUiController()
            val navController= rememberNavController()
            OnboardingScreen(pages = listdata,systemUiController,navController, onComplete = {})
        }
    }
}