package ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.repository.EmployeeRepository
import domain.repository.EmployeeRepository.Companion.employeeCollection
import ui.components.Alert

class LoginScreen : Screen {

    @Composable
    override fun Content() {

        var username: String by remember { mutableStateOf("") }
        var password: String by remember { mutableStateOf("") }

        val navigator = LocalNavigator.currentOrThrow
        val showErrorAlert = remember { mutableStateOf(false) }
        val employeeRepository = EmployeeRepository(employeeCollection)
        val employee = employeeRepository.getEmployee(username)

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "Sign into your account here.",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.size(8.dp))
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") }
            )
            Spacer(modifier = Modifier.size(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.size(8.dp))
            TextButton(
                onClick = {
                    navigator.push(RegisterScreen())
                }
            ) {
                Text("Don't have an account? Click here.")
            }
            Spacer(modifier = Modifier.size(8.dp))
            Button(
                onClick = {
                    if (username.isNotEmpty() && password.isNotEmpty()) {
                        if (employee != null) {
                            if (employee.account.username == username && employee.account.password == password) {
                                navigator.push(ProfileScreen(employee))
                            } else {
                                showErrorAlert.value = true
                            }
                        }
                    } else {
                        showErrorAlert.value = true
                    }
                }
            ) {
                Text("Login")
            }
        }
        if (showErrorAlert.value) {
            val alert = Alert()
            alert.getComponent(
                title = "Unable to login",
                message = "You have provided invalid login details.",
                onDismissRequest = {
                    showErrorAlert.value = false
                }
            )
        }
    }
}