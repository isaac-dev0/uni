package domain.repository

import com.mongodb.MongoException
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import domain.model.Employee
import domain.manager.EmployeeManager
import domain.model.Skill
import org.bson.Document
import utility.Database
import utility.Database.DATABASE_NAME
import utility.validation.employee.EmployeeExistsValidator

class EmployeeRepository(

    private val collection: MongoCollection<Employee>

) : EmployeeManager {

    companion object {
        private const val COLLECTION_NAME = "employees"

        private val mongoClient: MongoClient = Database.getMongoClient()
        private val database: MongoDatabase = mongoClient.getDatabase(DATABASE_NAME)
        val employeeCollection: MongoCollection<Employee> =
            database.getCollection(COLLECTION_NAME, Employee::class.java)
    }

    override fun createEmployee(employee: Employee) {
        if (EmployeeExistsValidator(employeeCollection).validate(employee)) {
            println("Employee with username: ${employee.account.username} already exists.")
            return
        }
        try {
            val newEmployee = collection.insertOne(employee)
            println("Success! Inserted document with ID: ${newEmployee.insertedId}")
        } catch (e: MongoException) {
            throw Exception(e)
        }
    }

    override fun getEmployee(username: String): Employee? {
        return collection.find(
            Filters.eq("account.username", username)).firstOrNull()
    }

    override fun getEmployees(): List<Employee?> {
        return collection.find().toList()
    }

    override fun updateEmployee(employee: Employee) {
        val filter = Filters.eq("id", employee.id)
        val newDocument = Document(
            "\$set", Document(
                mapOf(
                    "name" to employee.name,
                    "job" to employee.job,
                    "account" to employee.account,
                    "skills" to employee.skills,
                    "children" to employee.children,
                    "parent" to employee.parent
                )
            )
        )
        collection.updateOne(filter, newDocument)
    }

    override fun updateEmployeeSkills(employee: Employee, skill: Skill) {
        val filter = Filters.eq("id", employee.id)
        val newDocument = Document(
            "\$set", Document(mapOf("skills" to employee.skills))
        )
        collection.updateOne(filter, newDocument)
    }

    override fun deleteEmployee(employee: Employee) {
        collection.deleteOneById(employee.id)
    }

    private fun MongoCollection<Employee>.deleteOneById(id: String) {
        this.findOneAndDelete(Document("id", id))
    }

}