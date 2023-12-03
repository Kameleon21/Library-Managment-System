package models

/**
 * Data class representing a person in a library system.
 *
 * @property personId The unique identifier of the person.
 * @property name The name of the person.
 * @property email The email of the person.
 * @property password The password of the person.
 * @property role The role of the person.
 * @property booksBorrowed The books borrowed by the person.
 */
data class Person(
    var personId: Int = 0,
    var name: String = " ",
    var email: String = " ",
    var password: String = " ",
    var role: String = "member",
    var booksBorrowed: ArrayList<Book> = ArrayList()
) {
}