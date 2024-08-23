package com.example.expense
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expense.ui.theme.ExpenseTheme
import com.example.expense.ui.theme.Primary
import com.example.expense.ui.theme.circlecolor
import com.example.expense.ui.theme.loginback
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogIn(navController:NavController,authViewModel: AuthViewModel) {

    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val systemUiController = rememberSystemUiController()
    val loginbackcolor = loginback
    DisposableEffect(systemUiController, loginbackcolor) {
        systemUiController.setStatusBarColor(
            color = loginbackcolor
        )
        onDispose {
            // Optionally reset the status bar color when the composable disposes
            systemUiController.setStatusBarColor(
                color = Primary
            )
        }
    }
    val authstate= authViewModel.authState.observeAsState()
    val context= LocalContext.current
    LaunchedEffect(authstate.value ){
        when(authstate.value) {
            is AuthState.Authenticated -> navController.navigate("expenses")
            is AuthState.Error -> Toast.makeText(context,
                (authstate.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }

    }
    Column(
        modifier = Modifier.background(loginback).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.circle_top1),
                    contentDescription = "circle 1",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
                Text(
                    text = "Welcome",
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(
                            horizontal = 25.dp,
                            vertical = 95.dp
                        ),
                    color = Color.White,
                    fontSize = 65.sp,
                )

            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 330.dp),
            ) {
                TextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 36.dp, vertical = 10.dp)
                        .background(
                            Color.White,
                            RoundedCornerShape(20.dp)
                        ),
                    label = { Text("Email", fontWeight = FontWeight.Bold) },
                    singleLine = true,
                    textStyle = TextStyle(
                        color = Color.Black
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Transparent
                    )
                )
                TextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 36.dp, vertical = 10.dp)
                        .background(
                            Color.White,
                            RoundedCornerShape(20.dp)
                        ),
                    label = {
                        Text(
                            "Password",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    singleLine = true,
                    textStyle = TextStyle(
                        color = Color.Black
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            authViewModel.login(email.value,password.value)
                        },
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(top = 20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = circlecolor )
                    ) {
                        Text(
                            text = "Sign In",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Text("Don't have an account ? SignUp",
                    modifier= Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 30.dp)
                        .clickable { navController.navigate("create") },
                    fontWeight = FontWeight.SemiBold)


            }
            Image(
                painter = painterResource(id = R.drawable.circle_bottom1),
                contentDescription = "circle 3",
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LogInPreview() {
    ExpenseTheme {
        val navController= rememberNavController()
        LogIn(navController, authViewModel = AuthViewModel())
    }
}
