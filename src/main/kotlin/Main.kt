import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cafe.adriel.voyager.navigator.Navigator
import domain.factory.EmployeeFactory
import domain.factory.SkillFactory
import domain.model.Account
import domain.model.Group
import domain.model.Job
import domain.model.Skill
import domain.repository.EmployeeRepository
import domain.repository.EmployeeRepository.Companion.employeeCollection
import domain.repository.SkillRepository
import domain.repository.SkillRepository.Companion.skillCollection
import ui.screens.RegisterScreen
import java.util.*

@Composable
@Preview
fun App() {

    val skill = SkillFactory.createSkill(UUID.randomUUID().toString(), "Teamwork", "Teamwork is cool.", "Soft Skills")
    SkillRepository(skillCollection).createSkill(skill)

    MaterialTheme {
        Navigator(RegisterScreen())
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
