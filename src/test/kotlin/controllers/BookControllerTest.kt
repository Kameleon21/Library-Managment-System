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

        book1 = Book(1, "The Fellowship of the Ring", "J.R.R. Tolkien","Fantasy", "978-0547928210", "1954", 1, 1)
        book2 = Book(2, "The Two Towers", "J.R.R. Tolkien","Sci-Fi", "978-0547928203", "1954", 1, 1)
        book3 = Book(3, "The Return of the King", "J.R.R. Tolkien","Music","978-0547928197", "1955", 1, 1)
        book4 = Book(4, "The Hobbit", "J.R.R. Tolkien","Pop","978-0547928227", "1937", 0, 2)
        book5 = Book(5, "The Militarisation", "J.R.R. Tolkien","Self-Help","978-0547928224", "1977", 1, 1)

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
    inner class addingBooksMethods {
        @Test
        fun `should add a book to the library`() {
            val book = Book(6, "The Silmarillion", "J.R.R. Tolkien","Sci-Fi", "978-0547928234", "1977", 1, 1)
            assertTrue(emptyLibrary!!.addBook(book))
        }

        @Test
        fun `should not add a book to the library if it already exists`() {
            assertFalse(populatedLibrary!!.addBook(book1!!))
        }

        @Test
        fun `should return true when book was able to be created`() {
            assertTrue(emptyLibrary!!.createBook(6, "The Silmarillion", "J.R.R. Tolkien","Self-Help","978-0547928234", "1977", 1, 1))
        }

        @Test
        fun `should return false when book was not able to be created`() {
            val isDuplicate = populatedLibrary!!.createBook(1, "The Silmarillion", "J.R.R. Tolkien", "Sci-Fi","978-0547928203", "1977", 1, 1)
            assertFalse(isDuplicate)
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

        @Test
        fun `should return all books with available copies greater 0` () {
            assertEquals(5, populatedLibrary!!.numberOfBooks())
            val booksStored = populatedLibrary!!.availableBooks().lowercase()
            assertTrue(booksStored.contains("the fellowship of the ring"))
            assertTrue(booksStored.contains("the two towers"))
            assertTrue(booksStored.contains("the return of the king"))
            assertTrue(booksStored.contains("the militarisation"))
        }

        @Test
        fun `should not return books with available copies equal 0` () {
            assertEquals(5, populatedLibrary!!.numberOfBooks())
            val booksStored = populatedLibrary!!.availableBooks().lowercase()
            assertFalse(booksStored.contains("the hobbit"))
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

    @Nested
    inner class searchFunctions {
        @Test
        fun `should return the book with the given title`() {
            assertEquals(book1, populatedLibrary!!.searchBookByTitle("The Fellowship of the Ring"))
        }

        @Test
        fun `should return the book with the given author`() {
            assertEquals(book1, populatedLibrary!!.searchBookByAuthor("J.R.R. Tolkien"))
        }

        @Test
        fun `should return the book with the given ISBN`() {
            assertEquals(book1, populatedLibrary!!.searchBookByISBN("978-0547928210"))
        }

        @Test
        fun `should return null if no book with the given title exists`() {
            assertEquals(null, populatedLibrary!!.searchBookByTitle("The Silmarillion"))
        }

        @Test
        fun `searchBookByAuthor should return null if no book with the given author exists`() {
            assertEquals(null, populatedLibrary!!.searchBookByAuthor("J.K. Rowling"))
        }

        @Test
        fun `searchBookByISBN should return null if no book with the given ISBN exists`() {
            assertEquals(null, populatedLibrary!!.searchBookByISBN("978-0547928234"))
        }
    }

    @Nested
    inner class updateMethods {
        @Test
        fun `updateBookTitle should return true if book was updated`() {
            assertTrue(populatedLibrary!!.updateBookTitle(0, "The Silmarillion"))
        }

        @Test
        fun `updateBookTitle should return false if book was not updated`() {
            assertFalse(populatedLibrary!!.updateBookTitle(5, "The Silmarillion"))
        }

        @Test
        fun `updateBookISBN should return true if book was updated`() {
            assertTrue(populatedLibrary!!.updateBookISBN(0, "978-0547928234"))
        }

        @Test
        fun `updateBookISBN should return false if book was not updated`() {
            assertFalse(populatedLibrary!!.updateBookISBN(5, "978-0547928234"))
        }

        @Test
        fun `updateBookAvailableCopies should return true if book was updated`() {
            assertTrue(populatedLibrary!!.updateBookAvailableCopies(0, 2))
        }

        @Test
        fun `updateBookAvailableCopies should return false if book was not updated`() {
            assertFalse(populatedLibrary!!.updateBookAvailableCopies(5, 2))
        }

        @Test
        fun `updateBookTotalCopies should return true if book was updated`() {
            assertTrue(populatedLibrary!!.updateBookTotalCopies(0, 2))
        }

        @Test
        fun `updateBookTotalCopies should return false if book was not updated`() {
            assertFalse(populatedLibrary!!.updateBookTotalCopies(5, 2))
        }
    }

    @Nested
    inner class deleteMethods {
        @Test
        fun `should return the deleted book if successful`() {
            assertEquals(book1, populatedLibrary!!.deleteBook(0))
        }

        @Test
        fun `should return null if the index is invalid`() {
            assertEquals(null, populatedLibrary!!.deleteBook(5))
        }
    }
}