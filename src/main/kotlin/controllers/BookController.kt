package controllers

import models.Book
import utils.HelperFunctions.isValidListIndex

class BookController {
    private val books = mutableListOf<Book>()

    fun addBook(book: Book): Boolean {
        if (books.contains(book)) return false
        return books.add(book)
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
        } else books.joinToString ( separator = "\n" )  { book -> books.indexOf(book).toString() + ":" + book.toString()}


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

}