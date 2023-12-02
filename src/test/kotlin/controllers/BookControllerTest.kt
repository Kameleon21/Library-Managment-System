package controllers

import models.Book
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import persistance.XMLSerializer
import java.io.File
import kotlin.test.assertFalse

class BookControllerTest {
    private var book1: Book? = null
    private var book2: Book? = null
    private var book3: Book? = null
    private var book4: Book? = null
    private var book5: Book? = null
    private var populatedLibrary: BookController? = null
    private var emptyLibrary: BookController? = null

    @BeforeEach
    fun setUp() {
        populatedLibrary = BookController(XMLSerializer(File("books.xml")))
        emptyLibrary = BookController(XMLSerializer(File("books.xml")))

        book1 = Book(1, "The Fellowship of the Ring", "J.R.R. Tolkien", "978-0547928210", "1954", 1, 1)
        book2 = Book(2, "The Two Towers", "J.R.R. Tolkien", "978-0547928203", "1954", 1, 1)
        book3 = Book(3, "The Return of the King", "J.R.R. Tolkien", "978-0547928197", "1955", 1, 1)
        book4 = Book(4, "The Hobbit", "J.R.R. Tolkien", "978-0547928227", "1937", 1, 1)
        book5 = Book(5, "The Militarisation", "J.R.R. Tolkien", "978-0547928224", "1977", 1, 1)

        populatedLibrary!!.addBook(book1!!)
        populatedLibrary!!.addBook(book2!!)
        populatedLibrary!!.addBook(book3!!)
        populatedLibrary!!.addBook(book4!!)
        populatedLibrary!!.addBook(book5!!)
    }

    @AfterEach
    fun tearDown() {
        populatedLibrary = null
        book1 = null
        book2 = null
        book3 = null
        book4 = null
        book5 = null

        File("books.xml").delete()
        File("books.yaml").delete()
    }

    @Nested
    inner class addingBooks {
        @Test
        fun `should add a book to the library`() {
            val book = Book(6, "The Silmarillion", "J.R.R. Tolkien", "978-0547928234", "1977", 1, 1)
            assertTrue(emptyLibrary!!.addBook(book))
        }

        @Test
        fun `should not add a book to the library if it already exists`() {
            assertFalse(populatedLibrary!!.addBook(book1!!))
        }
    }

    @Nested
    inner class returnNumberOfBooks {
        @Test
        fun `should return the number of books in the library`() {
            assertEquals(5, populatedLibrary!!.numberOfBooks())
        }
    }

    @Nested
    inner class findBook {
        @Test
        fun `should return the book at the given index`() {
            assertEquals(book1, populatedLibrary!!.findBook(0))
        }

        @Test
        fun `should return null if the index is out of bounds`() {
            assertEquals(null, populatedLibrary!!.findBook(5))
        }
    }

    @Nested
    inner class listtingMethods {
        @Test
        fun `should return a string of all books in the library`() {
            assertEquals(5, populatedLibrary!!.numberOfBooks())
            val booksStored = populatedLibrary!!.listAllBooks().lowercase()
            assertTrue(booksStored.contains("the fellowship of the ring"))
            assertTrue(booksStored.contains("the two towers"))
            assertTrue(booksStored.contains("the return of the king"))
            assertTrue(booksStored.contains("the hobbit"))
            assertTrue(booksStored.contains("the militarisation"))
        }

        @Test
        fun `should return a message if there are no books in the library`() {
            assertEquals("No books in library", emptyLibrary!!.listAllBooks())
        }
    }

    @Nested
    inner class Persistence {
        @Test
        fun `saving and loading an empty library in XML doesn't crash app`() {
            val saveBooks = BookController(XMLSerializer(File("books.xml")))
            saveBooks.save()

            val loadBooks = BookController(XMLSerializer(File("books.xml")))
            loadBooks.load()

            assertEquals(0, loadBooks.numberOfBooks())
            assertEquals(0, saveBooks.numberOfBooks())
            assertEquals("No books in library", loadBooks.listAllBooks())
        }

        @Test
        fun `saving and loading an loaded library in XML doesn't loose data`() {
            val savedBooks = BookController(XMLSerializer(File("books.xml")))
            savedBooks.addBook(book1!!)
            savedBooks.addBook(book2!!)
            savedBooks.addBook(book3!!)
            savedBooks.addBook(book4!!)
            savedBooks.addBook(book5!!)
            savedBooks.save()

            val loadedBooks = BookController(XMLSerializer(File("books.xml")))
            loadedBooks.load()

            assertEquals(5, loadedBooks.numberOfBooks())
            assertEquals(5, savedBooks.numberOfBooks())
            assertEquals(savedBooks.numberOfBooks(),loadedBooks.numberOfBooks())
            assertEquals(savedBooks.findBook(0),loadedBooks.findBook(0))
            assertEquals(savedBooks.findBook(1),loadedBooks.findBook(1))
            assertEquals(savedBooks.findBook(2),loadedBooks.findBook(2))
        }

        @Test
        fun `saving and loading an empty library in YAML doesn't crash app`() {
            val saveBooks = BookController(XMLSerializer(File("books.yaml")))
            saveBooks.save()

            val loadBooks = BookController(XMLSerializer(File("books.yaml")))
            loadBooks.load()

            assertEquals(0, loadBooks.numberOfBooks())
            assertEquals(0, saveBooks.numberOfBooks())
            assertEquals("No books in library", loadBooks.listAllBooks())
        }

        @Test
        fun `saving and loading an loaded library in YAML doesn't loose data`() {
            val savedBooks = BookController(XMLSerializer(File("books.yaml")))
            savedBooks.addBook(book1!!)
            savedBooks.addBook(book2!!)
            savedBooks.addBook(book3!!)
            savedBooks.addBook(book4!!)
            savedBooks.addBook(book5!!)
            savedBooks.save()

            val loadedBooks = BookController(XMLSerializer(File("books.yaml")))
            loadedBooks.load()

            assertEquals(5, loadedBooks.numberOfBooks())
            assertEquals(5, savedBooks.numberOfBooks())
            assertEquals(savedBooks.numberOfBooks(),loadedBooks.numberOfBooks())
            assertEquals(savedBooks.findBook(0),loadedBooks.findBook(0))
            assertEquals(savedBooks.findBook(1),loadedBooks.findBook(1))
            assertEquals(savedBooks.findBook(2),loadedBooks.findBook(2))
        }
    }
}