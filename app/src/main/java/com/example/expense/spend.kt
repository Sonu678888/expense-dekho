package com.example.expense

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expense.spendList.ExpensesList
import com.example.expense.ui.theme.LabelSecondary
import com.example.expense.ui.theme.Typography
import com.example.expense.ui.theme.button_back
import com.example.expense.ui.theme.topAppBarBackground
import java.net.Authenticator
import java.text.DecimalFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Expenses(
    navController: NavController,
    vm: ExpensesViewModel = viewModel(),
    authViewModel: AuthViewModel
) {
    val recurrences = listOf(
        Recurrence.Daily,
        Recurrence.Weekly,
        Recurrence.Monthly,
        Recurrence.Yearly
    )

    val state by vm.uiState.collectAsState()
    var recurrenceMenuOpened by remember { mutableStateOf(false) }
    val context= LocalContext.current
    val authstate= authViewModel.authState.observeAsState()
    LaunchedEffect(authstate.value ){
        when(authstate.value) {
            is AuthState.Unathenticated -> navController.navigate("Login")
            is AuthState.Error -> Toast.makeText(context,
                (authstate.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }

    }

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text("Expenses") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = topAppBarBackground
                ),
                actions = {
                    // Sign-Out Button
                    Button(onClick = {
                        authViewModel.Signout()


                    },colors = ButtonDefaults.buttonColors(
                        containerColor = button_back
                    ), modifier = Modifier.padding(20.dp)) {
                        Text("Sign Out")
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "See for :",
                        style = Typography.bodyMedium,
                    )
                    chooser(
                        state.recurrence.target ?: Recurrence.None.target,
                        onClick = { recurrenceMenuOpened = !recurrenceMenuOpened },
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    DropdownMenu(expanded = recurrenceMenuOpened,
                        onDismissRequest = { recurrenceMenuOpened = false }) {
                        recurrences.forEach { recurrence ->
                            DropdownMenuItem(text = { Text(recurrence.target) }, onClick = {
                                vm.setRecurrence(recurrence)
                                recurrenceMenuOpened = false
                            })
                        }
                    }
                }
                Row(modifier = Modifier.padding(vertical = 32.dp)) {
                    Text(
                        "₹ ",
                        style = Typography.bodyMedium,
                        color = LabelSecondary,
                        modifier = Modifier.padding(end = 4.dp, top = 4.dp)
                    )
                    Text(
                        DecimalFormat("0.#").format(state.sumTotal.toInt()),
                        style = Typography.titleLarge
                    )
                }
                ExpensesList(
                    expenses = state.expenses,
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                )
            }
        }
    )
}

