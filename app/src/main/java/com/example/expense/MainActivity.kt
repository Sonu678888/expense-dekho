package com.example.expense

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.expense.ui.theme.ExpenseTheme
import com.example.expense.ui.theme.topAppBarBackground

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContent {
            ExpenseTheme {
                var showbottom by rememberSaveable{
                    mutableStateOf(true)
                }
                val navController = rememberNavController()

                val backStackEntry by navController.currentBackStackEntryAsState()

                showbottom= when (backStackEntry?.destination?.route){
                    "settings/categories"-> false
                    else->true
                }


                Scaffold(
                    bottomBar = {
                        if(showbottom){
                            NavigationBar(containerColor = topAppBarBackground) {
                                NavigationBarItem(
                                    selected =backStackEntry?.destination?.route=="expenses" ,
                                    onClick = {navController.navigate("expenses")},
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.home_fill0_wght400_grad0_opsz24),
                                            contentDescription = "expenses")

                                    },
                                    label = {
                                        Text("Home")
                                    })
                                NavigationBarItem(
                                    selected =backStackEntry?.destination?.route=="Analysis" ,
                                    onClick = {navController.navigate("Analysis")},
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.analytics_fill0_wght400_grad0_opsz24),
                                            contentDescription = "Analysis")

                                    },
                                    label = {
                                        Text("Analysis")
                                    })
                                NavigationBarItem(
                                    selected =backStackEntry?.destination?.route=="ADD" ,
                                    onClick = {navController.navigate("ADD")},
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.add_fill0_wght400_grad0_opsz24),
                                            contentDescription = "ADD")

                                    },
                                    label = {
                                        Text("Add")

                                    })
                                NavigationBarItem(
                                    selected =backStackEntry?.destination?.route?.startsWith("settings")?:false ,
                                    onClick = {navController.navigate("settings")},
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.settings_fill0_wght400_grad0_opsz24),
                                            contentDescription = "Settings")

                                    },
                                    label = {
                                        Text("Settings")
                                    })

                            }
                        }

                    },
                    content = {innerpadding->
                       NavHost(navController = navController , startDestination = "expenses"){
                           composable("expenses"){
                               Surface(modifier = Modifier
                                   .fillMaxSize()
                                   .padding(innerpadding)){
                                  expenses(navController =navController , name ="expenses" )
                               }
                           }
                           composable("Analysis"){
                               Surface(modifier = Modifier
                                   .fillMaxSize()
                                   .padding(innerpadding)){
                                   expenses(navController =navController , name ="Analysis" )
                               }
                           }
                           composable("ADD"){
                               Surface(modifier = Modifier
                                   .fillMaxSize()
                                   .padding(innerpadding)){
                                   Add(navController =navController )
                               }
                           }
                           composable("settings"){
                               Surface(modifier = Modifier
                                   .fillMaxSize()
                                   .padding(innerpadding)){
                                   Settings(navController =navController )
                               }
                           }
                           composable("settings/categories"){
                               Surface(modifier= Modifier
                                   .fillMaxSize()
                                   .padding(innerpadding)){
                                   Categories(navController = navController)
                               }
                           }
                       }
                    }
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = " hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExpenseTheme {
        Greeting("Sonu")
    }
}