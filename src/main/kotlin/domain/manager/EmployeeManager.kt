package domain.manager

import domain.model.Employee
import domain.model.Skill

interface EmployeeManager {

    fun createEmployee(employee: Employee)

    fun getEmployee(username: String): Employee?

    fun getEmployees(): List<Employee?>

    fun updateEmployee(employee: Employee)

    fun updateEmployeeSkills(employee: Employee, skill: Skill)

    fun deleteEmployee(employee: Employee)

}