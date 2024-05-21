package domain.factory

import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class SkillFactoryTest {

    @Test
    fun testCreateSkill() {
        val skillId = "1"
        val skillName = "JUnit"
        val skillDescription = "JUnit is a Java testing framework."
        val skillCategory = "Testing"

        val skill = SkillFactory.createSkill(skillId, skillName, skillDescription, skillCategory)

        assertNotNull(skill)
        assertEquals(skillId, skill.id)
        assertEquals(skillName, skill.name)
        assertEquals(skillDescription, skill.description)
        assertEquals(skillCategory, skill.category)
        assertEquals(null, skill.proficiency)
        assertEquals(null, skill.expiryDate)
        assertEquals(null, skill.notes)
    }
}