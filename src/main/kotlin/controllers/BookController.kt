package controllers

import models.Book

class BookController {
    private val books = mutableListOf<Book>()

    fun addBook(book: Book): Boolean {
        if (books.contains(book)) return false
        return books.add(book)
    }

    fun numberOfBooks(): Int {
        return books.size
    }

}