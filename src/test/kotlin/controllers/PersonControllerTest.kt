package controllers

import models.Person
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class PersonControllerTest {

    private var person1: Person? = null
    private var person2: Person? = null
    private var person3: Person? = null
    private var populatedLibrary: PersonController? = null
    private var emptyLibrary: PersonController? = null

    @BeforeEach
    fun setUp() {
        populatedLibrary = PersonController()
        emptyLibrary = PersonController()

        person1 = Person(1, "John Doe", "john@gmail.com", "password", "member")
        person2 = Person(2, "Jane Doe", "jane@gamil.com", "password", "member")
        person3 = Person(3, "Billy", "billy@gmail.com", "password", "admin")

        populatedLibrary!!.addPerson(person1!!)
        populatedLibrary!!.addPerson(person2!!)
        populatedLibrary!!.addPerson(person3!!)
    }

    @AfterEach
    fun tearDown() {
        populatedLibrary = null
        person1 = null
        person2 = null
        person3 = null
    }


    @Nested
    inner class addPerson {
        @Test
        fun `should return true when person is added`() {
            assertTrue(emptyLibrary!!.addPerson(person1!!))
        }

        @Test
        fun `should return false when person is not added`() {
            populatedLibrary!!.addPerson(person1!!)
            assertFalse(populatedLibrary!!.addPerson(person1!!))
        }
    }

    @Nested
    inner class numberOfPersons {
        @Test
        fun `should return 0 when no persons are added`() {
            assertEquals(0, emptyLibrary!!.numberOfPersons())
        }

        @Test
        fun `should return 1 when 1 person is added`() {
            emptyLibrary!!.addPerson(person1!!)
            assertEquals(1, emptyLibrary!!.numberOfPersons())
        }

        @Test
        fun `should return 2 when 2 persons are added`() {
            emptyLibrary!!.addPerson(person1!!)
            emptyLibrary!!.addPerson(person2!!)
            assertEquals(2, emptyLibrary!!.numberOfPersons())
        }
    }


    @Nested
    inner class register {
        @Test
        fun `should return true when person is registered`() {
            assertTrue(emptyLibrary!!.register(1, "John Doe", "john@gmail.com", "password", "member"))
        }

        @Test
        fun `should return false when person is not registered`() {
            populatedLibrary!!.register(1, "John Doe", "john@gmail.com", "password", "member")
        }
    }

    @Nested
    inner class login {
        @Test
        fun `should return person when person is logged in`() {
            assertNotNull(populatedLibrary!!.login("john@gmail.com", "password"))
        }

        @Test
        fun `should return null when person is not logged in`() {
            assertNull(populatedLibrary!!.login("mark@gamil.com", "password"))
        }
    }
}