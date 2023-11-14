package controllers

import models.Book
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

class LibraryAPITest {

    private var book1: Book? = null
    private var book2: Book? = null
    private var book3: Book? = null
    private var book4: Book? = null
    private var book5: Book? = null
    private var person1: Person? = null
    private var person2: Person? = null
    private var person3: Person? = null
    private var populatedLibrary: LibraryAPI? = null
    private var emptyLibrary: LibraryAPI? = null

    @BeforeEach
    fun setUp() {
        populatedLibrary = LibraryAPI()
        emptyLibrary = LibraryAPI()

        book1 = Book(1, "The Fellowship of the Ring", "J.R.R. Tolkien", "978-0547928210", "1954", 1, 1)
        book2 = Book(2, "The Two Towers", "J.R.R. Tolkien", "978-0547928203", "1954", 1, 1)
        book3 = Book(3, "The Return of the King", "J.R.R. Tolkien", "978-0547928197", "1955", 1, 1)
        book4 = Book(4, "The Hobbit", "J.R.R. Tolkien", "978-0547928227", "1937", 1, 1)
        book5 = Book(5, "The Militarisation", "J.R.R. Tolkien", "978-0547928227", "1977", 1, 1)
        person1 = Person(1, "John Doe", "john@gmail.com", "password", "member")
        person2 = Person(2, "Jane Doe", "jane@gmail.com,", "password", "member")
        person3 = Person(3, "Billy", "bily@gmail.com", "password", "admin")

        populatedLibrary!!.addBook(book1!!)
        populatedLibrary!!.addBook(book2!!)
        populatedLibrary!!.addBook(book3!!)
        populatedLibrary!!.addBook(book4!!)
        populatedLibrary!!.addBook(book5!!)
        populatedLibrary!!.addPerson(person1!!)
        populatedLibrary!!.addPerson(person2!!)
        populatedLibrary!!.addPerson(person3!!)
    }

    @AfterEach
    fun tearDown() {
        populatedLibrary = null
        book1 = null
        book2 = null
        book3 = null
        book4 = null
        book5 = null
        person1 = null
        person2 = null
        person3 = null
    }

    @Nested
    inner class AddingMethods {
        @Test
        fun `should add book to library`() {
            val book = Book(6, "The Silmarillion", "J.R.R. Tolkien", "978-0547928189", "1977", 1, 1)
            assertEquals(5, populatedLibrary!!.numberOfBooks())
            assertTrue(populatedLibrary!!.addBook(book))
            assertEquals(6, populatedLibrary!!.numberOfBooks())
        }

        @Test
        fun `should add a book to an empty library`() {
            val book = Book(6, "The Silmarillion", "J.R.R. Tolkien", "978-0547928189", "1977", 1, 1)
            assertEquals(0, emptyLibrary!!.numberOfBooks())
            assertTrue(emptyLibrary!!.addBook(book))
            assertEquals(1, emptyLibrary!!.numberOfBooks())
        }

        @Test
        fun `should add person to library`() {
            val person = Person(4, "mike", "mike@gamil.com", "password", "member")
            assertEquals(3, populatedLibrary!!.numberOfPersons())
            assertTrue(populatedLibrary!!.addPerson(person))
            assertEquals(4, populatedLibrary!!.numberOfPersons())
        }

        @Test
        fun `should add person to an empty library`() {
            val person = Person(4, "mike", "mike@gamil.com", "password", "member")
            assertEquals(0, emptyLibrary!!.numberOfPersons())
            assertTrue(emptyLibrary!!.addPerson(person))
        }

    }

    @Nested
    inner class RegisterAndLoginMethods {
        @Test
        fun `register method should add a new member to the library`() {
            val newPerson = Person(4, "Alice", "alice@gamil.com", "alicepass", "member")
            assertTrue(
                emptyLibrary!!.register(
                    newPerson.personId,
                    newPerson.name,
                    newPerson.email,
                    newPerson.password,
                    newPerson.role
                )
            )
            assertEquals(1, emptyLibrary!!.numberOfPersons())
        }

        @Test
        fun `register method should not add a new member with the existing email`() {
            val initialCount = populatedLibrary!!.numberOfPersons()
            assertFalse(populatedLibrary!!.register(4, "John Smith", "john@gmail.com", "newpassword", "member"))
            assertEquals(initialCount, populatedLibrary!!.numberOfPersons())
        }

        @Test
        fun `login method should return a person when email and password are correct`() {
            val loggedInPerson = populatedLibrary!!.login("john@gmail.com", "password")
            assertNotNull(loggedInPerson)
            assertEquals("John Doe", loggedInPerson.name)
        }

        @Test
        fun `login method should return null when email is incorrect`() {
            val loggedInPerson = populatedLibrary!!.login("nonexist@gmail.com", "password")
            assertNull(loggedInPerson)
        }

        @Test
        fun `login method should return null when password is incorrect`() {
            val loggedInPerson = populatedLibrary!!.login("john@gmail.com", "wrongpassword")
            assertNull(loggedInPerson)
        }
    }

    @Nested
    inner class NumberOfEntitiesMethods {
        @Test
        fun `numberOfBooks should return correct count`() {
            assertEquals(5, populatedLibrary!!.numberOfBooks())
        }

        @Test
        fun `number of books should return zero for empty library`() {
            assertEquals(0, emptyLibrary!!.numberOfBooks())
        }

        @Test
        fun `numberOfPersons should return correct count`() {
            assertEquals(3, populatedLibrary!!.numberOfPersons())
        }

        @Test
        fun `number of persons should return zero for empty library`() {
            assertEquals(0, emptyLibrary!!.numberOfPersons())
        }
    }
}