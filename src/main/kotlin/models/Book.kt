package models

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