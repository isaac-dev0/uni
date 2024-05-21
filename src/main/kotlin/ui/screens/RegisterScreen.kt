package ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import domain.factory.EmployeeFactory
import domain.model.Account
import domain.model.Group
import domain.model.Job
import domain.repository.EmployeeRepository
import domain.repository.EmployeeRepository.Companion.employeeCollection
import ui.components.Alert
import utility.validation.Validation
import java.util.UUID

class RegisterScreen : Screen {

    @Composable
    override fun Content() {

        var name: String by remember { mutableStateOf("") }
        var username: String by remember { mutableStateOf("") }
        var password: String by remember { mutableStateOf("") }

        val navigator = LocalNavigator.currentOrThrow
        val showErrorAlert = remember { mutableStateOf(false) }
        val employeeRepository = EmployeeRepository(employeeCollection)

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Register",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "Create an account with us!",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.size(8.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") }
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
                    navigator.push(LoginScreen())
                }
            ) {
                Text("Already have an account? Click here.")
            }
            Spacer(modifier = Modifier.size(8.dp))
            Button(
                onClick = {
                    if (Validation.isValidUsername(username) && Validation.isValidPassword(password)) {
                        val account = Account(username, password, Group.MANAGER)
                        val employee = EmployeeFactory.createEmployee(
                            id = UUID.randomUUID().toString(),
                            name = name,
                            job = Job.JUNIOR_DEVELOPER,
                            account = account,
                            skills = emptyList(),
                            parent = null,
                            children = emptyList()
                        )
                        employeeRepository.createEmployee(employee)
                        navigator.push(LoginScreen())
                    } else {
                        showErrorAlert.value = true
                    }
                }
            ) {
                Text("Register")
            }
        }
        if (showErrorAlert.value) {
            val alert = Alert()
            alert.getComponent(
                title = "Unable to create an account",
                message = "You have provided invalid registration parameters. Your username must be in an email format. Your password must also be greater than 8 character, contain 1 character, digit, lowercase and uppercase letter.",
                onDismissRequest = {
                    showErrorAlert.value = false
                }
            )
        }
    }
}