package ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import domain.model.Skill

class EditSkillPopup {

    @Composable
    fun getComponent(
        skill: Skill,
        onUpdateSkill: (Skill) -> Unit,
        onDismiss: () -> Unit
    ) {
        var newName by remember { mutableStateOf(skill.name) }
        var newDescription by remember { mutableStateOf(skill.description) }
        var newCategory by remember { mutableStateOf(skill.category) }

        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text("Edit Skill", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = newName,
                        onValueChange = { newName = it },
                        label = { Text("Title") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    newDescription?.let {
                        TextField(
                            value = it,
                            onValueChange = { newDescription = it },
                            label = { Text("Description") }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    newCategory?.let {
                        TextField(
                            value = it,
                            onValueChange = { newCategory = it },
                            label = { Text("Category") }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(onClick = {
                            val updatedSkill = skill.copy(
                                name = newName,
                                description = newDescription,
                                category = newCategory
                            )
                            onUpdateSkill(updatedSkill)
                        }) {
                            Text("Save Changes")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = onDismiss) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }
    }
}