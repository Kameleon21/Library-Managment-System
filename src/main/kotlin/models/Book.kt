package models

/**
 * Data class representing a book in a library system.
 *
 * @property bookID The unique identifier of the book.
 * @property title The title of the book.
 * @property author The author of the book.
 * @property genre The genre of the book.
 * @property ISBN The International Standard Book Number of the book.
 * @property publicationYear The year in which the book was published.
 * @property availableCopies The number of copies of the book that are currently available for borrowing.
 * @property totalCopies The total number of copies of the book.
 */
data class Book(
    var bookID: Int = 0,
    var title: String = "",
    var author: String = "",
    var genre : String = "",
    var ISBN: String = "",
    var publicationYear: String = "",
    var availableCopies: Int = 0,
    var totalCopies: Int = 0
) {
}