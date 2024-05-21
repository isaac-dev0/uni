package utility.validation.employee

import domain.model.Employee

interface EmployeeValidator {

    fun validate(employee: Employee): Boolean

}