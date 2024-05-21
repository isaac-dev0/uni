package ui.components

import domain.model.Group
import domain.model.Job
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

class CreateEmployeePopup {

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun getComponent(
        onDismissRequest: () -> Unit,
        onConfirmation: (String, String, String, Group, Job) -> Unit
    ) {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var name by remember { mutableStateOf("") }
        var group by remember { mutableStateOf(Group.EMPLOYEE) }
        var job by remember { mutableStateOf(Job.JUNIOR_DEVELOPER) }

        var isGroupItemExpanded by remember { mutableStateOf(false) }
        var isJobItemExpanded by remember { mutableStateOf(false) }

        Dialog(
            onDismissRequest = { onDismissRequest() }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
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
                        Text("Create User")
                        Spacer(
                            modifier = Modifier
                                .size(16.dp)
                        )
                        TextField(
                            value = username,
                            modifier = Modifier
                                .fillMaxWidth(),
                            onValueChange = { username = it },
                            label = { Text("Username") }
                        )
                        Spacer(
                            modifier = Modifier
                                .size(16.dp)
                        )
                        TextField(
                            value = password,
                            modifier = Modifier
                                .fillMaxWidth(),
                            onValueChange = { password = it },
                            label = { Text("Password") }
                        )
                        Spacer(
                            modifier = Modifier
                                .size(16.dp)
                        )
                        TextField(
                            value = name,
                            modifier = Modifier
                                .fillMaxWidth(),
                            onValueChange = { name = it },
                            label = { Text("Name") }
                        )
                        Spacer(
                            modifier = Modifier
                                .size(16.dp)
                        )
                        Spacer(
                            modifier = Modifier
                                .size(16.dp)
                        )
                        ExposedDropdownMenuBox(
                            expanded = isGroupItemExpanded,
                            onExpandedChange = {
                                isGroupItemExpanded = it
                            }
                        ) {
                            TextField(
                                value = group.toString(),
                                onValueChange = { },
                                readOnly = true,
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = isGroupItemExpanded
                                    )
                                },
                                colors = ExposedDropdownMenuDefaults.textFieldColors()
                            )
                            ExposedDropdownMenu(
                                expanded = isGroupItemExpanded,
                                onDismissRequest = {
                                    isGroupItemExpanded = false
                                }
                            ) {
                                Group.entries.forEach { title ->
                                    DropdownMenuItem(
                                        onClick = {
                                            group = title
                                            isGroupItemExpanded = false
                                        }
                                    ) {
                                        Text(title.toString())
                                    }
                                }
                            }
                        }
                        ExposedDropdownMenuBox(
                            expanded = isJobItemExpanded,
                            onExpandedChange = {
                                isJobItemExpanded = it
                            }
                        ) {
                            TextField(
                                value = job.name,
                                onValueChange = { },
                                readOnly = true,
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = isJobItemExpanded
                                    )
                                },
                                colors = ExposedDropdownMenuDefaults.textFieldColors()
                            )
                            ExposedDropdownMenu(
                                expanded = isJobItemExpanded,
                                onDismissRequest = {
                                    isJobItemExpanded = false
                                }
                            ) {
                                Job.entries.forEach { title ->
                                    DropdownMenuItem(
                                        onClick = {
                                            job = title
                                            isJobItemExpanded = false
                                        }
                                    ) {
                                        Text(title.name)
                                    }
                                }
                            }
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            TextButton(
                                onClick = {
                                    onDismissRequest()
                                },
                                modifier = Modifier
                                    .padding(16.dp)
                            ) {
                                Text("Cancel")
                            }
                            TextButton(
                                onClick = {
                                    onConfirmation(username, password, name, group, job)
                                },
                                modifier = Modifier
                                    .padding(16.dp)
                            ) {
                                Text("Add Employee")
                            }
                        }
                    }
                }
            }
        }
    }
}