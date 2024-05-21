package utility.validation

import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class ValidationTest {

    @Test
    fun testValidUsername() {
        assertTrue(Validation.isValidUsername("user@example.com"))
        assertTrue(Validation.isValidUsername("user123@example.co.uk"))
        assertTrue(Validation.isValidUsername("user.name@example.com"))
        assertTrue(Validation.isValidUsername("user_name123@example.com"))
    }

    @Test
    fun testInvalidUsername() {
        assertFalse(Validation.isValidUsername("user.com"))
        assertFalse(Validation.isValidUsername("@example.com"))
    }

    @Test
    fun testValidPassword() {
        assertTrue(Validation.isValidPassword("Password123!"))
        assertTrue(Validation.isValidPassword("P@ssw0rd!"))
        assertTrue(Validation.isValidPassword("Test1234@"))
    }

    @Test
    fun testInvalidPassword() {
        assertFalse(Validation.isValidPassword("password"))
        assertFalse(Validation.isValidPassword("Password"))
        assertFalse(Validation.isValidPassword("password123"))
        assertFalse(Validation.isValidPassword("PASSWORD123"))
        assertFalse(Validation.isValidPassword("Password!"))
        assertFalse(Validation.isValidPassword("Password123"))
        assertFalse(Validation.isValidPassword("!"))
    }

}