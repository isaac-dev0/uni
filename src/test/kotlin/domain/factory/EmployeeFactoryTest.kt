package domain.factory

import domain.model.Account
import domain.model.Group
import domain.model.Job
import domain.model.Skill
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class EmployeeFactoryTest {

    @Test
    fun testCreateEmployee() {
        val employeeId = "1"
        val employeeName = "Jimmy Crow"
        val job = Job.MID_LEVEL_DEVELOPER
        val account = Account(
            username = "jimmy.crow@audit.com",
            password = "password",
            group = Group.ADMINISTRATOR
        )
        val skills = listOf(
            Skill(
                id = "101",
                name = "JUnit",
                description = "JUnit is a Java testing framework.",
                category = "Java",
                proficiency = null,
                notes = null,
                expiryDate = null
            )
        )
        val parent = "tim.smith@audit.com"
        val children = listOf("jim.bob@audit.com")

        val employee = EmployeeFactory.createEmployee(
            id = employeeId,
            name = employeeName,
            job = job,
            account = account,
            skills = skills,
            parent = parent,
            children = children
        )

        assertNotNull(employee)
        assertEquals(employeeId, employee.id)
        assertEquals(employeeName, employee.name)
        assertEquals(job, employee.job)
        assertEquals(account, employee.account)
        assertEquals(skills, employee.skills)
        assertEquals(parent, employee.parent)
        assertEquals(children, employee.children)
    }
}