package utils

import models.Book

object HelperFunctions {

    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean = (index >= 0 && index < list.size)

    @JvmStatic
    fun capitalizeFirstLetter(string: String): String {
        return string.substring(0, 1).uppercase() + string.substring(1).lowercase()
    }

    @JvmStatic
    fun bookListLayout(books: List<Book>): String {
        return if (books.isEmpty()) {
            "No books found"
        } else {
            books.joinToString(separator = "\n") { book ->
                "${books.indexOf(book)}, Title=${book.title},Author=${book.author},Genre=${book.genre}"
            }
        }
    }
}