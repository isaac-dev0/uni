package utility.validation.skill

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import domain.model.Skill

class SkillExistsValidator(

    private val collection: MongoCollection<Skill>

) : SkillValidator {

    override fun validate(skill: Skill): Boolean {
        return collection.find(Filters.eq("name", skill.name)).firstOrNull() != null
    }

}