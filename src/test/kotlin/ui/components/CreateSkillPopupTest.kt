package ui.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import component.CreateSkillPopup
import org.junit.Rule
import org.junit.Test

class CreateSkillPopupTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDialogCreation() {
        composeTestRule.setContent {
            CreateSkillPopup().getComponent({}, { _, _, _ -> })
        }
        composeTestRule.onNodeWithText("Create Skill").assertExists()
    }

    @Test
    fun testInputFields() {

        composeTestRule.setContent {
            CreateSkillPopup().getComponent(
                onDismissRequest = { },
                onConfirmation = { _, _, _ -> }
            )
        }

        val name = "Kotlin"
        composeTestRule.onNodeWithText("Name").performTextInput(name)

        val description = "A modern programming language"
        composeTestRule.onNodeWithText("Description").performTextInput(description)

        val category = "Programming"
        composeTestRule.onNodeWithText("Category").performTextInput(category)

    }

}