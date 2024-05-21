package ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import domain.model.Proficiency
import domain.repository.SkillRepository
import domain.repository.SkillRepository.Companion.skillCollection

class AddSkillPopup {

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun getComponent(
        onDismissRequest: () -> Unit,
        onConfirmation: (String, String, String, Proficiency) -> Unit
    ) {

        var isSkillDialogExpanded by remember { mutableStateOf(false) }
        var isProficiencyDialogExpanded by remember { mutableStateOf(false) }

        val skillRepository = SkillRepository(skillCollection)

        var skill by remember { mutableStateOf("") }
        var notes by remember { mutableStateOf("") }
        var expiryDate by remember { mutableStateOf("") }
        var proficiency by remember { mutableStateOf(Proficiency.NONE) }

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
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Add Skill")
                    Spacer(
                        modifier = Modifier
                            .size(16.dp)
                    )
                    ExposedDropdownMenuBox(
                        expanded = isSkillDialogExpanded,
                        onExpandedChange = {
                            isSkillDialogExpanded = it
                        }
                    ) {
                        TextField(
                            value = skill,
                            onValueChange = { },
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = isSkillDialogExpanded
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )
                        ExposedDropdownMenu(
                            expanded = isSkillDialogExpanded,
                            onDismissRequest = {
                                isSkillDialogExpanded = false
                            }
                        ) {
                            skillRepository.getSkillTitles().forEach { name ->
                                DropdownMenuItem(
                                    onClick = {
                                        skill = name
                                        isSkillDialogExpanded = false
                                    }
                                ) {
                                    Text(name)
                                }
                            }
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .size(16.dp)
                    )
                    ExposedDropdownMenuBox(
                        expanded = isProficiencyDialogExpanded,
                        onExpandedChange = {
                            isProficiencyDialogExpanded = it
                        }
                    ) {
                        TextField(
                            value = proficiency.title,
                            onValueChange = { },
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = isProficiencyDialogExpanded
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )
                        ExposedDropdownMenu(
                            expanded = isProficiencyDialogExpanded,
                            onDismissRequest = {
                                isProficiencyDialogExpanded = false
                            }
                        ) {
                            Proficiency.entries.forEach { title ->
                                DropdownMenuItem(
                                    onClick = {
                                        proficiency = title
                                        isProficiencyDialogExpanded = false
                                    }
                                ) {
                                    Text(title.title)
                                }
                            }
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .size(16.dp)
                    )
                    TextField(
                        value = notes,
                        modifier = Modifier
                            .wrapContentWidth(),
                        onValueChange = { notes = it },
                        label = { Text("Notes") }
                    )
                    Spacer(
                        modifier = Modifier
                            .size(16.dp)
                    )
                    TextField(
                        value = expiryDate,
                        modifier = Modifier
                            .wrapContentWidth(),
                        onValueChange = { expiryDate = it },
                        label = { Text("Expiry Date") }
                    )
                    Spacer(
                        modifier = Modifier
                            .size(16.dp)
                    )
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
                                onConfirmation(skill, expiryDate, notes, proficiency)
                            },
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text("Add Skill")
                        }
                    }
                }
            }
        }

    }

}