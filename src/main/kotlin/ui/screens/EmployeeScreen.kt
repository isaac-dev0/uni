package ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import domain.model.*
import domain.repository.EmployeeRepository
import domain.repository.EmployeeRepository.Companion.employeeCollection
import ui.Dashboard
import ui.components.CreateEmployeePopup
import java.util.UUID

class EmployeeScreen(employee: Employee?) : Dashboard(employee) {

    @Composable
    override fun getScreen() {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top
        ) {
            displayEmployees(employee)
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun editUser(
        employee: Employee,
        onUpdateUser: (Employee) -> Unit,
        onDismiss: () -> Unit
    ) {
        var newUsername by remember { mutableStateOf(employee.account.username) }
        var newPassword by remember { mutableStateOf(employee.account.password) }
        var newName by remember { mutableStateOf(employee.name) }
        var newGroup by remember { mutableStateOf(employee.account.group) }
        var newJob by remember { mutableStateOf(employee.job) }

        var isGroupItemExpanded by remember { mutableStateOf(false) }
        var isJobItemExpanded by remember { mutableStateOf(false) }

        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Text("Edit User", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            value = newUsername,
                            onValueChange = { newUsername = it },
                            label = { Text("Username") }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            value = newPassword,
                            onValueChange = { newPassword = it },
                            label = { Text("Password") }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            value = newName,
                            onValueChange = { newName = it },
                            label = { Text("Name") }
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        ExposedDropdownMenuBox(
                            expanded = isGroupItemExpanded,
                            onExpandedChange = { isGroupItemExpanded = it }
                        ) {
                            TextField(
                                value = newGroup.toString(),
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isGroupItemExpanded)
                                },
                                colors = ExposedDropdownMenuDefaults.textFieldColors()
                            )
                            ExposedDropdownMenu(
                                expanded = isGroupItemExpanded,
                                onDismissRequest = { isGroupItemExpanded = false }
                            ) {
                                Group.entries.forEach { group ->
                                    DropdownMenuItem(onClick = {
                                        newGroup = group
                                        isGroupItemExpanded = false
                                    }) {
                                        Text(group.toString())
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        // Job Dropdown
                        ExposedDropdownMenuBox(
                            expanded = isJobItemExpanded,
                            onExpandedChange = { isJobItemExpanded = it }
                        ) {
                            TextField(
                                value = newJob.getTitle(),
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isJobItemExpanded)
                                },
                                colors = ExposedDropdownMenuDefaults.textFieldColors()
                            )
                            ExposedDropdownMenu(
                                expanded = isJobItemExpanded,
                                onDismissRequest = { isJobItemExpanded = false }
                            ) {
                                Job.entries.forEach { job ->
                                    DropdownMenuItem(onClick = {
                                        newJob = job
                                        isJobItemExpanded = false
                                    }) {
                                        Text(job.getTitle())
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                val updatedEmployee = employee.copy(
                                    account = employee.account.copy(
                                        username = newUsername,
                                        password = newPassword,
                                        group = newGroup
                                    ),
                                    name = newName,
                                    job = newJob
                                )
                                onUpdateUser(updatedEmployee)
                                onDismiss()
                            }
                        ) {
                            Text("Save Changes")
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun displayEmployees(currentEmployee: Employee?) {
        val employeeRepository = remember { EmployeeRepository(employeeCollection) }
        var isCreateEmployeeVisible by remember { mutableStateOf(false) }
        var showEmployeeEditDialog by remember { mutableStateOf(false) }
        var selectedEmployee by remember { mutableStateOf<Employee?>(null) }
        val hasEditStaff = currentEmployee?.hasPermission(currentEmployee.account.username, Permission.EDIT_STAFF) ?: false

        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Users",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                if (hasEditStaff) {
                    TextButton(onClick = { isCreateEmployeeVisible = true }) {
                        Text("+ Create Employee")
                    }
                }
            }
            LazyColumn {
                items(employeeRepository.getEmployees()) { employee ->
                    if (employee != null) {
                        employeeCard(
                            employee = employee,
                            onEdit = {
                                selectedEmployee = employee
                                showEmployeeEditDialog = true
                            },
                            canEdit = hasEditStaff
                        )
                    }
                }
            }

        }

        if (showEmployeeEditDialog && selectedEmployee != null) {
            editUser(
                employee = selectedEmployee!!,
                onUpdateUser = { updatedEmployee ->
                    employeeRepository.updateEmployee(updatedEmployee)
                    showEmployeeEditDialog = false
                },
                onDismiss = { showEmployeeEditDialog = false }
            )
        }

        if (isCreateEmployeeVisible) {
            CreateEmployeePopup().getComponent(
                onDismissRequest = { isCreateEmployeeVisible = false },
                onConfirmation = { username, password, name, group, job ->
                    employeeRepository.createEmployee(
                        Employee(
                            id = UUID.randomUUID().toString(),
                            account = Account(
                                username = username,
                                password = password,
                                group = group
                            ),
                            name = name,
                            skills = mutableListOf(),
                            job = job,
                            parent = "",
                            children = mutableListOf()
                        )
                    )
                    isCreateEmployeeVisible = false
                }
            )
        }
    }

    @Composable
    fun employeeCard(employee: Employee, onEdit: () -> Unit, canEdit: Boolean) {

        val employeeRepository = EmployeeRepository(employeeCollection)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = employee.name,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Username: ${employee.account.username}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Job: ${employee.job.getTitle()}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Group: ${employee.account.group}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (canEdit) {
                    IconButton(onClick = onEdit) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Employee")
                    }
                    IconButton(onClick = {
                        employeeRepository.deleteEmployee(employee)
                        println("Successfully deleted ${employee.name} from the database.")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp)
                        )
                    }
                }
            }
        }
    }
}
