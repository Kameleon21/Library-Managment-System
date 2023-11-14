package utils

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.assertFalse

class InputValidationTest {

    @Test
    fun `validateEmail returns true when email is valid`() {
        val email = "tim@gamil.com"
        val result = InputValidation.validateEmail(email)
        assertTrue(result)
    }

    @Test
    fun `validateEmail returns false when email is invalid`() {
        val email = "tim"
        val result = InputValidation.validateEmail(email)
        assertFalse(result)
    }

    @Test
    fun `validatePassword returns true when password is valid`() {
        val password = "password123"
        val result = InputValidation.validatePassword(password)
        assertTrue(result)
    }

    @Test
    fun `validatePassword returns false when password is invalid`() {
        val password = "pass"
        val result = InputValidation.validatePassword(password)
        assertFalse(result)
    }

    @Test
    fun `validateName returns true when name is valid`() {
        val name = "tim"
        val result = InputValidation.validateName(name)
        assertTrue(result)
    }

    @Test
    fun `validateName returns false when name is invalid`() {
        val name = "t"
        val result = InputValidation.validateName(name)
        assertFalse(result)
    }
}