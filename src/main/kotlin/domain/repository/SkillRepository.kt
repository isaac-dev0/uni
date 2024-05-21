package domain.repository

import com.mongodb.MongoException
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import domain.manager.SkillManager
import domain.model.Skill
import org.bson.Document
import utility.Database
import utility.validation.skill.SkillExistsValidator

class SkillRepository(

    private val collection: MongoCollection<Skill>

) : SkillManager {

    companion object {
        private const val COLLECTION_NAME = "skills"

        private val mongoClient: MongoClient = Database.getMongoClient()
        private val database: MongoDatabase = mongoClient.getDatabase(Database.DATABASE_NAME)
        val skillCollection: MongoCollection<Skill> =
            database.getCollection(COLLECTION_NAME, Skill::class.java)
    }

    override fun createSkill(skill: Skill) {
        if (SkillExistsValidator(skillCollection).validate(skill)) {
            println("Skill with name: ${skill.name} already exists.")
            return
        }
        try {
            val newSkill = collection.insertOne(skill)
            println("Success! Inserted document with ID: ${newSkill.insertedId}")
        } catch (e: MongoException) {
            throw Exception(e)
        }
    }

    override fun getSkill(name: String): Skill? {
        return collection.find(
            Filters.eq(
            "name", name
        )).firstOrNull()
    }

    override fun getSkills(): List<Skill?> {
        return collection.find().toList()
    }

    override fun getSkillTitles(): List<String> {
        return collection.distinct("name", String::class.java).toList()
    }

    override fun updateSkill(skill: Skill) {
        val filter = Filters.eq("id", skill.id)
        val newSkill = Document(
            "\$set", Document(
                mapOf(
                    "name" to skill.name,
                    "description" to skill.description,
                    "category" to skill.category
                )
            )
        )
        collection.updateOne(filter, newSkill)
    }

    override fun deleteSkill(skill: Skill) {
        collection.deleteOneByName(skill.name)
    }

    private fun MongoCollection<Skill>.deleteOneByName(name: String) {
        this.findOneAndDelete(Document("name", name))
    }

}