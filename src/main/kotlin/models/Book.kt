package models

class Book(
    val bookID: Int,
    val title: String,
    val author: String,
    val ISBN: String,
    val publicationYear: String,
    val availableCopies: Int,
    val totalCopies: Int
) {
}