package domain.model

import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class JobTest {

    @Test
    fun testGetTitle() {
        assertEquals("Junior Developer", Job.JUNIOR_DEVELOPER.getTitle())
        assertEquals("Mid-level Developer", Job.MID_LEVEL_DEVELOPER.getTitle())
        assertEquals("Senior Developer", Job.SENIOR_DEVELOPER.getTitle())
        assertEquals("Project Manager", Job.PROJECT_MANAGER.getTitle())
    }
}