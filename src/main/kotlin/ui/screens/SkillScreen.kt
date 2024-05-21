package ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.collectAsState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import component.CreateSkillPopup
import domain.factory.SkillFactory
import domain.model.Employee
import domain.model.Permission
import domain.model.Skill
import domain.repository.SkillRepository
import domain.repository.SkillRepository.Companion.skillCollection
import ui.Dashboard
import ui.components.EditSkillPopup
import java.util.UUID

class SkillScreen(employee: Employee?) : Dashboard(employee) {

    @Composable
    override fun getScreen() {

        val hasSkillView by remember {
            mutableStateOf(employee?.hasPermission(employee.account.username, Permission.VIEW_SKILLS))
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (hasSkillView == true) {
                    displaySkills()
                } else {
                    Text("Welp, you do not have permission to see this content!")
                }
            }
        }
    }

    @Composable
    fun displaySkills() {

        val isCreateSkillVisible = remember { mutableStateOf(false) }
        val createSkillPopup = CreateSkillPopup()
        val skillRepository = SkillRepository(skillCollection)

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
                    text = "Skills",
                    fontSize = 24.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = {
                        isCreateSkillVisible.value = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Create Skill"
                    )
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            Column {
                skillRepository.getSkills().forEach { skill ->
                    skill?.let { skillCard(it) }
                }
            }
        }

        if (isCreateSkillVisible.value) {
            createSkillPopup.getComponent(
                onDismissRequest = { isCreateSkillVisible.value = false },
                onConfirmation = { name, description, category ->
                    val skill = SkillFactory.createSkill(
                        id = UUID.randomUUID().toString(),
                        name = name,
                        description = description,
                        category = category
                    )
                    skillRepository.createSkill(skill)
                    isCreateSkillVisible.value = false
                }
            )
        }
    }

    @Composable
    fun skillCard(skill: Skill) {

        var showSkillEditDialog by remember { mutableStateOf(false) }
        val skillRepository = SkillRepository(skillCollection)
        val editSkillPopup = EditSkillPopup()
        val hasEditSkill by remember {
            mutableStateOf(employee?.hasPermission(employee.account.username, Permission.EDIT_SKILLS))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = skill.name,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Description: ${skill.description}",
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Category: ${skill.category}",
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top
            ) {
                if (hasEditSkill == true) {
                    IconButton(onClick = { showSkillEditDialog = true }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Skill")
                    }
                }
                IconButton(onClick = { skillRepository.deleteSkill(skill) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Skill")
                }
            }
        }

        if (showSkillEditDialog) {
            editSkillPopup.getComponent(
                skill = skill,
                onUpdateSkill = { updatedSkill ->
                    skillRepository.updateSkill(updatedSkill)
                    showSkillEditDialog = false
                },
                onDismiss = { showSkillEditDialog = false }
            )
        }
    }


}
