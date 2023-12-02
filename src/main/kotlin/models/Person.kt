package models

data class Person(
    var personId: Int = 0,
    var name: String = " ",
    var email: String = " ",
    var password: String = " ",
    var role: String = "member",
) {
}