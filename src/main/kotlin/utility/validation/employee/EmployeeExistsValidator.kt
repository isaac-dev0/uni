package utility.validation.employee

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import domain.model.Employee

class EmployeeExistsValidator(

    private val collection: MongoCollection<Employee>

) : EmployeeValidator {

    override fun validate(employee: Employee): Boolean {
        return collection.find(Filters.eq("id", employee.id)).firstOrNull() != null
    }

}