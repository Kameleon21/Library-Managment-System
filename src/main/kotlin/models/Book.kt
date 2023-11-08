package models

data class Book(
    val bookId: Int,
    val title: String,
    val author: String,
    val ISBN: String,
    val genre: String,
    val pubYear: Int,
    val copiesAvailable: Int,
    val totalCopies: Int
)
