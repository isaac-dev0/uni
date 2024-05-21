package utility.validation.skill

import domain.model.Skill

interface SkillValidator {

    fun validate(skill: Skill): Boolean

}