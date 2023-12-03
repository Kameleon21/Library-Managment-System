package controllers

import models.Book
import persistance.Serializer
import utils.HelperFunctions.bookListLayout
import utils.HelperFunctions.isValidListIndex

/**
 * Controller for managing books in a library system.
 *
 * @param serializerType The serializer to use for persisting book data.
 */
class BookController(serializerType: Serializer) {
    private val serializer = serializerType
    private var books = ArrayList<Book>()

    /**
     * Adds a new book to the collection.
     *
     * @param book The book to add.
     * @return True if the book was added successfully, false if a book with the same ISBN already exists.
     */
    fun addBook(book: Book): Boolean {
        if (books.any { it.ISBN == book.ISBN }) return false
        return books.add(book)
    }

    /**
     * Creates and adds a new book to the collection.
     *
     * @param bookId Unique identifier for the book.
     * @param title Title of the book.
     * @param author Author of the book.
     * @param genre Genre of the book.
     * @param ISBN ISBN of the book.
     * @param pubYear Publication year of the book.
     * @param availableCopies Number of available copies of the book.
     * @param totalCopies Total copies of the book.
     * @return True if the book was successfully created and added, false if a book with the same ISBN already exists.
     */
    fun createBook(
        bookId: Int,
        title: String,
        author: String,
        genre: String,
        ISBN: String,
        pubYear: String,
        availableCopies: Int,
        totalCopies: Int,
    ): Boolean {
        if (books.any { it.ISBN == ISBN }) return false
        val book = Book(
            bookId,
            title,
            author,
            genre,
            ISBN,
            pubYear,
            availableCopies,
            totalCopies
        )
        return addBook(book)
    }

    /**
     * Returns the number of books in the collection.
     *
     * @return The total number of books.
     */
    fun numberOfBooks(): Int {
        return books.size
    }

    /**
     * Finds and returns a book by its index in the library.
     * Returns null if the index is invalid.
     *
     * @param index The index of the book in the library.
     * @return The book if found, null otherwise.
     */
    fun findBook(index: Int): Book? {
        return if (isValidListIndex(index, books)) {
            books[index]
        } else null
    }

    /**
     * Lists all books in the library.
     * Returns a formatted string of all books or a message if no books are available.
     *
     * @return A string representation of all books in the library.
     */
    fun listAllBooks(): String =
        if (books.isEmpty()) {
            "No books in library"
        } else books.joinToString(separator = "\n") { book -> books.indexOf(book).toString() + ":" + book.toString() }


    /**
     * Updates the title of a specific book in the library.
     * The book is identified by its index.
     *
     * @param index The index of the book to be updated.
     * @param bookTitle The new title for the book.
     * @return Boolean indicating whether the update was successful.
     */
    fun updateBookTitle(index: Int, bookTitle: String): Boolean {
        val foundBook = findBook(index)
        return if (foundBook != null) {
            foundBook.title = bookTitle
            true
        } else false
    }

    /**
     * Updates the ISBN of a book identified by its index in the collection.
     *
     * @param index The index of the book to update.
     * @param bookISBN The new ISBN of the book.
     * @return True if the update was successful, false if the book was not found.
     */
    fun updateBookISBN(index: Int, bookISBN: String): Boolean {
        val foundBook = findBook(index)
        return if (foundBook != null) {
            foundBook.ISBN = bookISBN
            true
        } else false
    }

    /**
     * Updates the number of available copies of a book identified by its index in the collection.
     *
     * @param index The index of the book to update.
     * @param bookAvailableCopies The new number of available copies of the book.
     * @return True if the update was successful, false if the book was not found.
     */
    fun updateBookAvailableCopies(index: Int, bookAvailableCopies: Int): Boolean {
        val foundBook = findBook(index)
        return if (foundBook != null) {
            foundBook.availableCopies = bookAvailableCopies
            true
        } else false
    }

    /**
     * Updates the total number of copies of a book identified by its index in the collection.
     *
     * @param index The index of the book to update.
     * @param bookTotalCopies The new total number of copies of the book.
     * @return True if the update was successful, false if the book was not found.
     */
    fun updateBookTotalCopies(index: Int, bookTotalCopies: Int): Boolean {
        val foundBook = findBook(index)
        return if (foundBook != null) {
            foundBook.totalCopies = bookTotalCopies
            true
        } else false
    }

    /**
     * Deletes a book from the collection identified by its index.
     *
     * @param index The index of the book to delete.
     * @return The deleted book if successful, or null if the index is invalid.
     */
    fun deleteBook(index: Int): Book? {
        return if (isValidListIndex(index, books)) {
            books.removeAt(index)
        } else null
    }

    /**
     * Searches for and returns a book by its title.
     *
     * @param title The title of the book to search for.
     * @return The book if found, or null if no book with the given title exists.
     */
    fun searchBookByTitle(title: String): Book? {
        return books.find { it.title == title }
    }

    /**
     * Searches for and returns a book by its author.
     *
     * @param author The author of the book to search for.
     * @return The book if found, or null if no book with the given author exists.
     */
    fun searchBookByAuthor(author: String): Book? {
        return books.find { it.author == author }
    }

    /**
     * Searches for and returns a book by its ISBN.
     *
     * @param ISBN The ISBN of the book to search for.
     * @return The book if found, or null if no book with the given ISBN exists.
     */
    fun searchBookByISBN(ISBN: String): Book? {
        return books.find { it.ISBN == ISBN }
    }

    /**
     * Lists all available books in the collection.
     *
     * @return A string representation of all available books.
     */
    fun availableBooks(): String {
        return bookListLayout(books.filter { it.availableCopies > 0 })
    }

    /**
     * Saves the current state of the book collection to persistent storage.
     *
     * @throws Exception if there is an error during saving.
     */
    @Throws(Exception::class)
    fun save() {
        serializer.write(books)
    }

    /**
     * Loads the state of the book collection from persistent storage.
     *
     * @throws Exception if there is an error during loading.
     */
    @Throws(Exception::class)
    fun load() {
        books = serializer.read() as ArrayList<Book>
    }
}