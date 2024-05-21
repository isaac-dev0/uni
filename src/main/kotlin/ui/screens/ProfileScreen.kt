package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.*
import domain.repository.EmployeeRepository
import domain.repository.EmployeeRepository.Companion.employeeCollection
import domain.repository.SkillRepository
import domain.repository.SkillRepository.Companion.skillCollection
import ui.Dashboard
import ui.components.AddSkillPopup

class ProfileScreen(employee: Employee?) : Dashboard(employee) {

    private val employeeRepository = EmployeeRepository(employeeCollection)

    @Composable
    override fun getScreen() {

        var employeeState by remember { mutableStateOf(employee!!) }

        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                createLayout(employeeState, onUpdateEmployee = { updatedEmployee ->
                    employeeState = updatedEmployee
                    employeeRepository.updateEmployee(updatedEmployee)
                })
            }
        }
    }

    @Composable
    fun createLayout(employee: Employee, onUpdateEmployee: (Employee) -> Unit) {

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    displayPersonalInformation(employee)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    displayEditInformation(
                        employee = employee,
                        onUpdateEmployee = onUpdateEmployee
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    displaySkills()
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    displayEmployees(employeeRepository, employee)
                }
            }
        }
    }

    @Composable
    fun displayPersonalInformation(employee: Employee) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(96.dp)
                        .background(
                            Color.Gray,
                            shape = CircleShape
                        )
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = employee.name,
                        fontSize = 24.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = employee.account.username,
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                    )
                    Text(
                        text = employee.job.getTitle(),
                        fontSize = 16.sp,
                        lineHeight = 16.sp,
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun displayEditInformation(
        employee: Employee,
        onUpdateEmployee: (Employee) -> Unit
    ) {

        var newName by remember { mutableStateOf(employee.name) }
        var newUsername by remember { mutableStateOf(employee.account.username) }
        var newPassword by remember { mutableStateOf(employee.account.password) }
        var newJob by remember { mutableStateOf(employee.job) }

        var isJobItemExpanded by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Edit Profile",
                fontSize = 24.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = newName,
                onValueChange = { newName = it },
                label = { Text("First Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = newUsername,
                onValueChange = { newUsername = it },
                label = { Text("Email Address") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text("New Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            ExposedDropdownMenuBox(
                expanded = isJobItemExpanded,
                onExpandedChange = {
                    isJobItemExpanded = it
                }
            ) {
                TextField(
                    value = newJob.getTitle(),
                    onValueChange = { },
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = isJobItemExpanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = isJobItemExpanded,
                    onDismissRequest = {
                        isJobItemExpanded = false
                    }
                ) {
                    Job.entries.forEach { job ->
                        DropdownMenuItem(
                            onClick = {
                                newJob = job
                                isJobItemExpanded = false
                            }
                        ) {
                            Text(job.getTitle())
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val updatedEmployee = employee.copy(
                        name = newName,
                        account = employee.account.copy(username = newUsername, password = newPassword),
                        job = newJob
                    )
                    onUpdateEmployee(updatedEmployee)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Save Changes")
            }
        }
    }

    @Composable
    fun displaySkills() {

        val isAddSkillVisible = remember { mutableStateOf(false) }
        val addSkillPopup = AddSkillPopup()

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Employee Skills",
                    fontSize = 24.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = {
                        isAddSkillVisible.value = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Skill"
                    )
                }
                if (isAddSkillVisible.value) {
                    addSkillPopup.getComponent(
                        onDismissRequest = { isAddSkillVisible.value = false },
                        onConfirmation = { name, expiryDate, notes, proficiency ->
                            employee?.addSkill(name, expiryDate, notes, proficiency)
                            isAddSkillVisible.value = false
                        }
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .size(16.dp)
            )
            Column {
                employee?.skills?.forEach { skill ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = skill.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Description: ${skill.description}")
                            Text(text = "Proficiency: ${skill.proficiency}")
                            Text(text = "Notes: ${skill.notes}")
                            Text(text = "Expiry Date: ${skill.expiryDate}")
                        }
                    }
                    TextButton(
                        onClick = {
                            employee.skills.remove(skill)
                            println("Successfully removed ${skill.name} from user: ${employee.account.username}.")
                        }
                    ) {
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

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun displayEmployees(employeeRepository: EmployeeRepository, employee: Employee?) {
        val hasViewStaff by remember { mutableStateOf(employee?.hasPermission(employee.account.username, Permission.VIEW_STAFF)) }
        val availableGroups = listOf(Group.EMPLOYEE, Group.MANAGER, Group.ADMINISTRATOR)

        var selectedGroup by remember { mutableStateOf<Group?>(null) }
        var selectedSkill by remember { mutableStateOf<Skill?>(null) }

        var groupDropdownExpanded by remember { mutableStateOf(false) }
        var skillDropdownExpanded by remember { mutableStateOf(false) }

        var childrenEmployees by remember { mutableStateOf<List<Employee?>>(emptyList()) }

        LaunchedEffect(employee) {
            if (employee != null) {
                val children = employee.children.mapNotNull { childId ->
                    childId.let { it?.let { it1 -> employeeRepository.getEmployee(it1) } }
                }
                childrenEmployees = children
                println("Fetched children: ${children.map { it.name }}")
            }
        }

        if (hasViewStaff == true) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Employees",
                        fontSize = 24.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    ExposedDropdownMenuBox(
                        expanded = groupDropdownExpanded,
                        onExpandedChange = { expanded ->
                            groupDropdownExpanded = expanded
                        }
                    ) {
                        TextField(
                            value = selectedGroup?.toString() ?: "Select Group",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = groupDropdownExpanded
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )
                        ExposedDropdownMenu(
                            expanded = groupDropdownExpanded,
                            onDismissRequest = { groupDropdownExpanded = false }
                        ) {
                            availableGroups.forEach { group ->
                                DropdownMenuItem(onClick = {
                                    selectedGroup = group
                                    groupDropdownExpanded = false
                                }) {
                                    Text(group.toString())
                                }
                            }
                        }
                    }
                    ExposedDropdownMenuBox(
                        expanded = skillDropdownExpanded,
                        onExpandedChange = { expanded ->
                            skillDropdownExpanded = expanded
                        }
                    ) {
                        TextField(
                            value = selectedSkill?.name ?: "Select Skill",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = skillDropdownExpanded
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )
                        ExposedDropdownMenu(
                            expanded = skillDropdownExpanded,
                            onDismissRequest = { skillDropdownExpanded = false }
                        ) {
                            SkillRepository(skillCollection).getSkills().forEach { skill ->
                                DropdownMenuItem(onClick = {
                                    selectedSkill = skill
                                    skillDropdownExpanded = false
                                }) {
                                    if (skill != null) {
                                        Text(skill.name)
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (childrenEmployees.isEmpty()) {
                    Text("No employees found.")
                } else {
                    val filteredChildren = childrenEmployees.filter { child ->
                        (selectedGroup?.let { group -> child?.account?.group == group } ?: true) &&
                                (selectedSkill?.let { skill -> child?.hasSkill(child.account.username, skill) ?: false } ?: true)
                    }

                    filteredChildren.forEach { child ->
                        if (child != null) {
                            employeeCard(child)
                        }
                    }
                }
            }
        } else {
            Text("You do not have permission to view staff.")
        }
    }

    @Composable
    fun employeeCard(employee: Employee) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
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
            Text(
                text = "Skills: ${employee.skills.joinToString(", ") { it.name }}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }


}
