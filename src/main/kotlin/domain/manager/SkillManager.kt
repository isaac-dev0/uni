package domain.manager

import domain.model.Skill

interface SkillManager {

    fun createSkill(skill: Skill)

    fun getSkill(name: String): Skill?

    fun getSkills(): List<Skill?>

    fun getSkillTitles(): List<String>

    fun updateSkill(skill: Skill)

    fun deleteSkill(skill: Skill)

}