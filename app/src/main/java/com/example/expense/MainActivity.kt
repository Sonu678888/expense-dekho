package com.example.expense

import OnboardingScreen
import android.os.Bundle
import android.widget.Toast
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.expense.ui.theme.ExpenseTheme
import com.example.expense.ui.theme.LightPink
import com.example.expense.ui.theme.OffWhite
import com.example.expense.ui.theme.PoppinsFontFamily
import com.example.expense.ui.theme.Primary
import com.example.expense.ui.theme.beige
import com.example.expense.ui.theme.button_back
import com.example.expense.ui.theme.exoFontFamily
import com.example.expense.ui.theme.logo
import com.example.expense.ui.theme.topAppBarBackground
import com.example.expense.ui.theme.turqoise
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        setContent {
            ExpenseTheme {
                val systemUiController = rememberSystemUiController()
                val navController = rememberNavController()
                NavHostSetup(navController,systemUiController, authViewModel = AuthViewModel())
            }
        }
    }

    @Composable
    fun NavHostSetup(navController: NavHostController, systemUiController: SystemUiController, authViewModel: AuthViewModel) {
        var showBottomBar by rememberSaveable { mutableStateOf(false) }
        val backStackEntry by navController.currentBackStackEntryAsState()
        val authstate = authViewModel.authState.observeAsState()
        val context = LocalContext.current
        val sharedPreferences = context.getSharedPreferences("onboarding_prefs", MODE_PRIVATE)

        LaunchedEffect(navController) {
            navController.currentBackStackEntryFlow.collect { entry ->
                showBottomBar =
                    !(entry.destination.route == "splashscreen" || entry.destination.route == "onboarding"
                            || entry.destination.route == "Login" || entry.destination.route == "create")
            }
        }

        LaunchedEffect(authstate.value) {
            when (authstate.value) {
                is AuthState.Authenticated -> {
                    val hasCompletedOnboarding = sharedPreferences.getBoolean("completed_onboarding", false)
                    if (hasCompletedOnboarding) {
                        navController.navigate("expenses") {
                            popUpTo("splashscreen") { inclusive = true }
                        }
                    } else {
                        navController.navigate("onboarding") {
                            popUpTo("splashscreen") { inclusive = true }
                        }
                    }
                }
                is AuthState.Error -> Toast.makeText(context, (authstate.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
                else -> Unit
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.weight(1f)) {
                NavHost(
                    navController = navController,
                    startDestination = "splashscreen"
                ) {
                    composable("splashscreen") {
                        SplashScreen(navController, systemUiController)
                    }
                    composable("expenses") {
                        Expenses(navController,authViewModel= AuthViewModel())
                    }
                    composable("onboarding") {
                        OnboardingScreen(pages = listdata, systemUiController, navController, onComplete = {
                            sharedPreferences.edit().putBoolean("completed_onboarding", true).apply()
                            navController.navigate("expenses") {
                                popUpTo("onboarding") { inclusive = true }
                            }
                        })
                    }
                    composable("reports") {
                        Reports()
                    }
                    composable("add") {
                        Add(navController)
                    }
                    composable("settings") {
                        Settings(navController)
                    }
                    composable("settings/categories") {
                        Categories(navController)
                    }
                    composable("create") {
                        SignInUi(navController, authViewModel = AuthViewModel())
                    }
                    composable("Login") {
                        LogIn(navController, authViewModel = AuthViewModel())
                    }
                }
            }

            if (showBottomBar) {
                BottomBar(navController, backStackEntry)
            }
        }
    }


    @Composable
    fun BottomBar(navController: NavHostController, backStackEntry: NavBackStackEntry?) {
        NavigationBar(containerColor = Color.Black) {
            NavigationBarItem(
                selected = backStackEntry?.destination?.route == "expenses",
                onClick = { navController.navigate("expenses") },
                label = { Text("Expenses") },
                icon = {
                    Icon(
                        painterResource(id = R.drawable.home_fill0_wght400_grad0_opsz24),
                        contentDescription = "Upload"
                    )
                }
            )
            NavigationBarItem(
                selected = backStackEntry?.destination?.route == "reports",
                onClick = { navController.navigate("reports") },
                label = { Text("Analysis") },
                icon = {
                    Icon(
                        painterResource(id = R.drawable.analytics_fill0_wght400_grad0_opsz24),
                        contentDescription = "Reports"
                    )
                }
            )
            NavigationBarItem(
                selected = backStackEntry?.destination?.route == "add",
                onClick = { navController.navigate("add") },
                label = { Text("Add") },
                icon = {
                    Icon(
                        painterResource(id = R.drawable.add_fill0_wght400_grad0_opsz24),
                        contentDescription = "Add"
                    )
                }
            )
            NavigationBarItem(
                selected = backStackEntry?.destination?.route?.startsWith("settings") ?: false,
                onClick = { navController.navigate("settings") },
                label = { Text("Settings") },
                icon = {
                    Icon(
                        painterResource(id = R.drawable.settings_fill0_wght400_grad0_opsz24),
                        contentDescription = "Settings"
                    )
                }
            )
        }
    }

    @Composable
    fun SplashScreen(navController: NavHostController,systemUiController: SystemUiController) {
        val splashColor= logo
        LaunchedEffect(systemUiController) {
            systemUiController.setStatusBarColor(
                color = splashColor,
                )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(splashColor)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img4),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(300.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .padding(bottom = 16.dp)
                )
                Spacer(modifier=Modifier.height(1.dp))

                Text("Xpense Dekho",
                    fontFamily= exoFontFamily,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 40.sp,
                    color= Color.DarkGray)

            }
        }
        LaunchedEffect(Unit) {
            delay(1000) //
            navController.navigate("onboarding") {
                popUpTo("splashscreen") { inclusive = true }
            }
        }
    }

    @Composable
    @Preview(showBackground = true)
    fun splashScreenPreview() {
        val navController = rememberNavController()
        ExpenseTheme {
            val systemUiController= rememberSystemUiController()
            SplashScreen(navController = navController, systemUiController =systemUiController )
            }

    }
}

