package utils

import models.Book
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class HelperFunctionsTest {

    @Test
    fun `isValidListIndex returns true when index is valid`() {
        val index = 0
        val list = listOf("a", "b", "c")
        val result = HelperFunctions.isValidListIndex(index, list)
        assertTrue(result)
    }

    @Test
    fun `isValidListIndex returns false when index is invalid`() {
        val index = 3
        val list = listOf("a", "b", "c")
        val result = HelperFunctions.isValidListIndex(index, list)
        assertFalse(result)
    }

    @Test
    fun `capitalizeFirstLetter returns string with first letter capitalized`() {
        val string = "hello"
        val result = HelperFunctions.capitalizeFirstLetter(string)
        assertTrue(result == "Hello")
    }

    @Test
    fun `bookListLayout returns string with book list layout`() {
        val books = listOf(Book(1, "harry", "potter", "fantasy", "978-3-16-148410-0", "2021-01-01", 1, 1))
        val result = HelperFunctions.bookListLayout(books)
        assertTrue(result == "0, Title=harry,Author=potter,Genre=fantasy")
    }

    @Test
    fun `bookListLayout returns string with no books found`() {
        val books = listOf<Book>()
        val result = HelperFunctions.bookListLayout(books)
        assertTrue(result == "No books found")
    }

    @Test
    fun `bookListLayout returns string with multiple books`() {
        val books = listOf(
            Book(1, "harry", "potter", "fantasy", "978-3-16-148410-0", "2021-01-01", 1, 1),
            Book(2, "harry", "potter", "fantasy", "978-3-16-148410-0", "2021-01-01", 1, 1)
        )
        val result = HelperFunctions.bookListLayout(books)
        assertTrue(result == "0, Title=harry,Author=potter,Genre=fantasy\n1, Title=harry,Author=potter,Genre=fantasy")
    }

}