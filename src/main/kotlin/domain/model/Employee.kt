package domain.model

import domain.repository.EmployeeRepository
import domain.repository.EmployeeRepository.Companion.employeeCollection

data class Employee(

    val id: String,
    val name: String,
    var job: Job,
    val account: Account,
    val skills: MutableList<Skill>,
    val parent: String?,
    val children: MutableList<String?>

) {

    fun hasPermission(username: String, permission: Permission): Boolean {
        val employeeRepository = EmployeeRepository(employeeCollection)
        val employee = employeeRepository.getEmployee(username)
        return employee?.let { permission in it.account.group.permissions } ?: false
    }

    fun hasSkill(username: String, skill: Skill): Boolean {
        val employeeRepository = EmployeeRepository(employeeCollection)
        val employee = employeeRepository.getEmployee(username)
        return employee?.let { skill in it.skills } ?: false
    }

    fun addSkill(name: String, expiryDate: String, notes: String, proficiency: Proficiency) {
        val employeeRepository = EmployeeRepository(employeeCollection)
        val employee = employeeRepository.getEmployee(account.username)
        if (employee != null) {
            val existingSkill = employee.skills.find { it.name.trim().equals(name.trim(), ignoreCase = true) }
            if (existingSkill != null) {
                println("Skill with $name already exists on user ${account.username}.")
                return
            }
            val newSkill = Skill(
                id = id,
                name = name,
                description = null,
                category = null,
                expiryDate = expiryDate,
                proficiency = proficiency,
                notes = notes
            )
            skills.add(newSkill)
            employeeRepository.updateEmployeeSkills(employee, newSkill)
        } else {
            println("Employee with ${account.username} not found.")
        }
    }

}