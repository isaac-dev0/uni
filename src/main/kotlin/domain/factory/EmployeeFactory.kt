package domain.factory

import domain.model.Account
import domain.model.Skill
import domain.model.Employee
import domain.model.Job

object EmployeeFactory {

    fun createEmployee(
        id: String,
        name: String,
        job: Job,
        account: Account,
        skills: List<Skill>,
        parent: String?,
        children: List<String?>
    ) : Employee {
        return Employee(id, name, job, account, skills.toMutableList(), parent, children.toMutableList())
    }

}