package controllers

import models.Book
import models.Person
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import persistance.XMLSerializer
import java.io.File
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
        populatedLibrary = PersonController(XMLSerializer(File("persons.xml")))
        emptyLibrary = PersonController(XMLSerializer(File("persons.xml")))

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

        File("persons.xml").delete()
        File("persons.yaml").delete()
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

    @Nested
    inner class listingMethods {
        @Test
        fun `should return no members when list is empty`() {
            assertEquals("No members found", emptyLibrary!!.listAllMembers())
        }

        @Test
        fun `should return all members when there are members`() {
            val expected =
                "0:Person(personId=1, name=John Doe, email=john@gmail.com, password=password, role=member)\n" +
                        "1:Person(personId=2, name=Jane Doe, email=jane@gamil.com, password=password, role=member)\n" +
                        "2:Person(personId=3, name=Billy, email=billy@gmail.com, password=password, role=admin)"
            assertEquals(expected, populatedLibrary!!.listAllMembers())
        }

        @Test
        fun `listBorrowedBooks should return no books when no books are borrowed`() {
            assertEquals("No books borrowed", populatedLibrary!!.listBorrowedBooks(1))
        }

        @Test
        fun `listBorrowedBooks should return all books when there are books borrowed`() {
            @Test
            fun `listBorrowedBooks should return all books when there are books borrowed`() {
                // Create test-specific book instances
                val book1 = Book(1, "The Hobbit", "J.R.R Tolkien", "Fantasy", "123456789", "1937", 1, 1)
                val book2 = Book(2, "The Lord of the Rings", "J.R.R Tolkien", "Fantasy", "123456789", "1954", 1, 1)
                val book3 = Book(3, "The Silmarillion", "J.R.R Tolkien", "Fantasy", "123456789", "1977", 1, 1)

                // Simulate borrowing books
                populatedLibrary!!.borrowBookLogic(1, book1)
                populatedLibrary!!.borrowBookLogic(1, book2)
                populatedLibrary!!.borrowBookLogic(1, book3)

                val expected = "0:Book(bookId=1, title=The Hobbit, author=J.R.R Tolkien, yearPublished=1937)\n" +
                        "1:Book(bookId=2, title=The Lord of the Rings, author=J.R.R Tolkien, yearPublished=1954)\n" +
                        "2:Book(bookId=3, title=The Silmarillion, author=J.R.R Tolkien, yearPublished=1977)"

                assertEquals(expected, populatedLibrary!!.listBorrowedBooks(1))
            }

        }
    }

    @Nested
    inner class updateMember {
        @Test
        fun `should return true on successfulmember update`() {
            val updateResult = populatedLibrary!!.updateMember(1, "Jane Smith", "jane@gmail.com", "newpassword")
            assertTrue(updateResult)
        }

        @Test
        fun `should return false for invalid member index`() {
            val updateResult = populatedLibrary!!.updateMember(10, "none", "none", "none")
            assertFalse(updateResult)
        }
    }

    @Nested
    inner class deleteMember {
        @Test
        fun `should return deleted member on successful deletion`() {
            val deleteResult = populatedLibrary!!.deleteMember(1)
            assertEquals(person2, deleteResult)
        }

        @Test
        fun `should return null for invalid member index`() {
            val deleteResult = populatedLibrary!!.deleteMember(10)
            assertNull(deleteResult)
        }
    }

    @Nested
    inner class borrowMethods {
        @Test
        fun `borrowBookLogic should return book borrowed successfully when book is borrowed`() {
            val book = Book(1, "The Hobbit", "J.R.R Tolkien", "Fantasy", "123456789", "1937", 1, 1)
            val borrowResult = populatedLibrary!!.borrowBookLogic(1, book)
            assertEquals("Book borrowed successfully \n", borrowResult)
        }

        @Test
        fun `borrowBookLogic should return book already borrowed when book is already borrowed`() {
            val book = Book(1, "The Hobbit", "J.R.R Tolkien", "Fantasy", "123456789", "1937", 1, 1)
            populatedLibrary!!.borrowBookLogic(1, book)
            val borrowResult = populatedLibrary!!.borrowBookLogic(1, book)
            assertEquals("Book already borrowed \n", borrowResult)
        }

        @Test
        fun `borrowBookLogic should return member has borrowed 3 books already when member has borrowed 3 books already`() {
            val book1 = Book(1, "The Hobbit", "J.R.R Tolkien", "Fantasy", "123456789", "1937", 1, 1)
            val book2 = Book(2, "The Lord of the Rings", "J.R.R Tolkien", "Fantasy", "123456789", "1954", 1, 1)
            val book3 = Book(3, "The Silmarillion", "J.R.R Tolkien", "Fantasy", "123456789", "1977", 2, 2)
            val book4 = Book(4, "The Children of Hurin", "J.R.R Tolkien", "Fantasy", "123456789", "2007", 1, 1)
            populatedLibrary!!.borrowBookLogic(1, book1)
            populatedLibrary!!.borrowBookLogic(1, book2)
            populatedLibrary!!.borrowBookLogic(1, book3)
            val borrowResult = populatedLibrary!!.borrowBookLogic(1, book4)
            assertEquals("Member has borrowed 3 books already \n", borrowResult)
        }

        @Test
        fun `borrowBookLogic should return no copies of this book available when no copies of this book are available`() {
            val book = Book(1, "The Hobbit", "J.R.R Tolkien", "Fantasy", "123456789", "1937", 0, 1)
            val borrowResult = populatedLibrary!!.borrowBookLogic(1, book)
            assertEquals("No copies of this book available \n", borrowResult)
        }

        @Test
        fun `borrowBookLogic should return member not found when member is not found`() {
            val book = Book(1, "The Hobbit", "J.R.R Tolkien", "Fantasy", "123456789", "1937", 1, 1)
            val borrowResult = populatedLibrary!!.borrowBookLogic(10, book)
            assertEquals("Member not found", borrowResult)
        }

        @Test
        fun `returnBookLogic should return book returned successfully when book is returned`() {
            val book = Book(1, "The Hobbit", "J.R.R Tolkien", "Fantasy", "123456789", "1937", 1, 1)
            populatedLibrary!!.borrowBookLogic(1, book)
            val returnResult = populatedLibrary!!.returnBookLogic(1, book)
            assertEquals("Book returned successfully \n", returnResult)
        }

        @Test
        fun `returnBookLogic should return book not borrowed when book is not borrowed`() {
            val book = Book(1, "The Hobbit", "J.R.R Tolkien", "Fantasy", "123456789", "1937", 1, 1)
            val returnResult = populatedLibrary!!.returnBookLogic(1, book)
            assertEquals("Book not borrowed by member \n", returnResult)
        }

        @Test
        fun `returnBookLogic should return member not found when member is not found`() {
            val book = Book(1, "The Hobbit", "J.R.R Tolkien", "Fantasy", "123456789", "1937", 1, 1)
            val returnResult = populatedLibrary!!.returnBookLogic(10, book)
            assertEquals("Member not found", returnResult)
        }

        @Test
        fun `showBorrowedBooksByAllMembers should return no books borrowed when no books are borrowed`() {
            assertEquals("No books borrowed", populatedLibrary!!.showBorrowedBooksByAllMembers())
        }

        @Test
        fun `showBorrowedBooksByAllMembers should return all books borrowed when there are books borrowed`() {
            // Create test-specific book instances
            val book1 = Book(1, "The Hobbit", "J.R.R Tolkien", "Fantasy", "123456789", "1937", 1, 1)
            val book2 = Book(2, "The Lord of the Rings", "J.R.R Tolkien", "Fantasy", "123456789", "1954", 1, 1)
            val book3 = Book(3, "The Silmarillion", "J.R.R Tolkien", "Fantasy", "123456789", "1977", 1, 1)

            // Simulate borrowing books
            populatedLibrary!!.borrowBookLogic(0, book1)
            populatedLibrary!!.borrowBookLogic(0, book2)
            populatedLibrary!!.borrowBookLogic(0, book3)

            val expected = "Member ID: 1, Name: John Doe, Total Borrowed Books: 3\nBorrowed Books: " +
                    "Book ID: 1, Title: The Hobbit, Author: J.R.R Tolkien, Published: 1937; \n" +
                    "Book ID: 2, Title: The Lord of the Rings, Author: J.R.R Tolkien, Published: 1954; \n" +
                    "Book ID: 3, Title: The Silmarillion, Author: J.R.R Tolkien, Published: 1977"

            assertEquals(expected, populatedLibrary!!.showBorrowedBooksByAllMembers())
        }
    }

    @Nested
    inner class Persistance {
        @Test
        fun `saving and loading an empty person collection in XML doesn't crash app`() {
            val savePersons = PersonController(XMLSerializer(File("persons.xml")))
            savePersons.save()

            val loadPersons = PersonController(XMLSerializer(File("persons.xml")))
            loadPersons.load()

            assertEquals(0, loadPersons.numberOfPersons())
            assertEquals(0, savePersons.numberOfPersons())
            assertEquals("No members found", loadPersons.listAllMembers())
        }

        @Test
        fun `saving and loading an loaded person collection in XML doesn't loose data`() {
            val savedPersons = PersonController(XMLSerializer(File("persons.xml")))
            savedPersons.addPerson(person1!!)
            savedPersons.addPerson(person2!!)
            savedPersons.addPerson(person3!!)
            savedPersons.save()

            val loadedPersons = PersonController(XMLSerializer(File("persons.xml")))
            loadedPersons.load()

            assertEquals(3, loadedPersons.numberOfPersons())
            assertEquals(3, savedPersons.numberOfPersons())
            assertEquals(savedPersons.numberOfPersons(), loadedPersons.numberOfPersons())
            assertEquals(savedPersons.findPerson(0), loadedPersons.findPerson(0))
            assertEquals(savedPersons.findPerson(1), loadedPersons.findPerson(1))
            assertEquals(savedPersons.findPerson(2), loadedPersons.findPerson(2))
        }

        @Test
        fun `saving and loading an empty person collection in YAML doesn't crash app`() {
            val savePersons = PersonController(XMLSerializer(File("persons.yaml")))
            savePersons.save()

            val loadPersons = PersonController(XMLSerializer(File("persons.yaml")))
            loadPersons.load()

            assertEquals(0, loadPersons.numberOfPersons())
            assertEquals(0, savePersons.numberOfPersons())
            assertEquals("No members found", loadPersons.listAllMembers())
        }

        @Test
        fun `saving and loading an loaded person collection in YAML doesn't loose data`() {
            val savedPersons = PersonController(XMLSerializer(File("persons.yaml")))
            savedPersons.addPerson(person1!!)
            savedPersons.addPerson(person2!!)
            savedPersons.addPerson(person3!!)
            savedPersons.save()

            val loadedPersons = PersonController(XMLSerializer(File("persons.yaml")))
            loadedPersons.load()

            assertEquals(3, loadedPersons.numberOfPersons())
            assertEquals(3, savedPersons.numberOfPersons())
            assertEquals(savedPersons.numberOfPersons(), loadedPersons.numberOfPersons())
            assertEquals(savedPersons.findPerson(0), loadedPersons.findPerson(0))
            assertEquals(savedPersons.findPerson(1), loadedPersons.findPerson(1))
            assertEquals(savedPersons.findPerson(2), loadedPersons.findPerson(2))
        }
    }
}