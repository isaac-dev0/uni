package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.Employee
import ui.screens.EmployeeScreen
import ui.screens.LoginScreen
import ui.screens.ProfileScreen
import ui.screens.SkillScreen

abstract class Dashboard(
    val employee: Employee?
): Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        val items = listOf(
            Pair(Icons.Default.Home, "Profile"),
            Pair(Icons.Default.Person, "Employees"),
            Pair(Icons.Default.Build, "Skills"),
            Pair(Icons.Default.ExitToApp, "Logout")
        )

        val onItemClick: (String) -> Unit = { clickedItem ->
            when (clickedItem) {
                "Profile" -> navigator.push(ProfileScreen(employee))
                "Employees" -> navigator.push(EmployeeScreen(employee))
                "Skills" -> navigator.push(SkillScreen(employee))
                "Logout" -> navigator.push(LoginScreen())
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
            ) {
                DashboardNavigator().getComponent(items, onItemClick)
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    getScreen()
                }
            }
        }
    }

    @Composable
    abstract fun getScreen()

}