package component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

class CreateSkillPopup {

    @Composable
    fun getComponent(
        onDismissRequest: () -> Unit,
        onConfirmation: (String, String, String) -> Unit
    ) {

        var name by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        var category by remember { mutableStateOf("") }

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
                    Text("Create Skill")
                    Spacer(
                        modifier = Modifier
                            .size(16.dp)
                    )
                    TextField(
                        value = name,
                        modifier = Modifier
                            .wrapContentWidth(),
                        onValueChange = { name = it },
                        label = { Text("Name") }
                    )
                    Spacer(
                        modifier = Modifier
                            .size(16.dp)
                    )
                    TextField(
                        value = description,
                        modifier = Modifier
                            .wrapContentWidth(),
                        onValueChange = { description = it },
                        label = { Text("Description") }
                    )
                    Spacer(
                        modifier = Modifier
                            .size(16.dp)
                    )
                    TextField(
                        value = category,
                        modifier = Modifier
                            .wrapContentWidth(),
                        onValueChange = { category = it },
                        label = { Text("Category") }
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
                                onConfirmation(name, description, category)
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