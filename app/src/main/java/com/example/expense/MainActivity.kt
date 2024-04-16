package com.example.expense

import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.expense.ui.theme.ExpenseTheme
import com.example.expense.ui.theme.topAppBarBackground

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContent {
            ExpenseTheme {
                var showBottomBar by rememberSaveable { mutableStateOf(true) }
                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()

                showBottomBar = when (backStackEntry?.destination?.route) {
                    "settings/categories" -> false
                    else -> true
                }

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            NavigationBar(containerColor = topAppBarBackground) {
                                NavigationBarItem(
                                    selected = backStackEntry?.destination?.route == "expenses",
                                    onClick = { navController.navigate("expenses") },
                                    label = {
                                        Text("Expenses")
                                    },
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
                                    label = {
                                        Text("Analysis")
                                    },
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
                                    label = {
                                        Text("Add")
                                    },
                                    icon = {
                                        Icon(
                                            painterResource(id = R.drawable.add_fill0_wght400_grad0_opsz24),
                                            contentDescription = "Add"
                                        )
                                    }
                                )
                                NavigationBarItem(
                                    selected = backStackEntry?.destination?.route?.startsWith("settings")
                                        ?: false,
                                    onClick = { navController.navigate("settings") },
                                    label = {
                                        Text("Settings")
                                    },
                                    icon = {
                                        Icon(
                                            painterResource(id = R.drawable.settings_fill0_wght400_grad0_opsz24),
                                            contentDescription = "Settings"
                                        )
                                    }
                                )
                            }
                        }
                    },
                    content = { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = "expenses"
                        ) {
                            composable("expenses") {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                ) {
                                    Expenses(navController)
                                }
                            }
                            composable("reports") {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                ) {
                                    Reports()
                                }
                            }
                            composable("add") {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                ) {
                                    Add(navController)
                                }
                            }
                            composable("settings") {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                ) {
                                    Settings(navController)
                                }
                            }
                            composable("settings/categories") {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding),
                                ) {
                                    Categories(navController)
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}
