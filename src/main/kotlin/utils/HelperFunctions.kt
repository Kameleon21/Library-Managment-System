package utils

import models.Book

/**
 * A utility object containing helper functions used across the library system.
 */
object HelperFunctions {

    /**
     * Checks if a given index is valid for a specified list.
     *
     * @param index The index to check.
     * @param list The list against which to check the index.
     * @return True if the index is within the bounds of the list, false otherwise.
     */
    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean = (index >= 0 && index < list.size)

    /**
     * Capitalizes the first letter of a string and makes the rest of the characters lowercase.
     *
     * @param string The string to be capitalized.
     * @return The string with its first letter capitalized and the rest in lowercase.
     */
    @JvmStatic
    fun capitalizeFirstLetter(string: String): String {
        return string.substring(0, 1).uppercase() + string.substring(1).lowercase()
    }

    /**
     * Formats a list of books into a readable string layout.
     * Each book is represented by its index, title, author, and genre in the list.
     *
     * @param books The list of books to format.
     * @return A formatted string representing the list of books, or a message if no books are found.
     */

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