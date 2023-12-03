package controllers

import models.Book
import persistance.Serializer
import utils.HelperFunctions.isValidListIndex

class BookController(serializerType: Serializer) {
    private val serializer = serializerType
    private var books = ArrayList<Book>()

    fun addBook(book: Book): Boolean {
        if (books.any { it.ISBN == book.ISBN }) return false
        return books.add(book)
    }

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

    fun numberOfBooks(): Int {
        return books.size
    }

    fun findBook(index: Int): Book? {
        return if (isValidListIndex(index, books)) {
            books[index]
        } else null
    }

    fun listAllBooks(): String =
        if (books.isEmpty()) {
            "No books in library"
        } else books.joinToString(separator = "\n") { book -> books.indexOf(book).toString() + ":" + book.toString() }


    fun updateBookTitle(index: Int, bookTitle: String): Boolean {
        val foundBook = findBook(index)
        return if (foundBook != null) {
            foundBook.title = bookTitle
            true
        } else false
    }

    fun deleteBook(index: Int): Book? {
        return if (isValidListIndex(index, books)) {
            books.removeAt(index)
        } else null
    }

    fun searchBookByTitle(title: String): Book? {
        return books.find { it.title == title }
    }

    fun searchBookByAuthor(author: String): Book? {
        return books.find { it.author == author }
    }

    fun searchBookByISBN(ISBN: String): Book? {
        return books.find { it.ISBN == ISBN }
    }

    fun availableBooks(): String {
        return books.filter { it.availableCopies > 0 }
            .joinToString(separator = "\n") { book -> books.indexOf(book).toString() + ":" + book.toString() }
    }

    @Throws(Exception::class)
    fun save() {
        serializer.write(books)
    }

    @Throws(Exception::class)
    fun load() {
        books = serializer.read() as ArrayList<Book>
    }
}