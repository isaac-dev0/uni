package domain.factory

import domain.model.Skill

object SkillFactory {

    fun createSkill(
        id: String,
        name: String,
        description: String,
        category: String
    ) : Skill {
        return Skill(id, name, description, category, null, null, null)
    }

}