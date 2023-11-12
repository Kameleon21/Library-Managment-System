package controllers

import models.Book
import models.Person

class LibraryAPI {
    private val books = mutableListOf<Book>()
    private val persons = mutableListOf<Person>()

    fun addBook(book: Book): Boolean {
        return books.add(book)
    }

    private fun addPerson(person: Person): Boolean {
        return persons.add(person)
    }

    fun registerMember( name: String, email: String, password: String): Boolean {
        val personId = persons.size + 1
        val role = "member"
        val person = Person(personId, name, email, password, role)
        return addPerson(person)
    }

    fun login(email: String, password: String): Person? {
        return persons.firstOrNull { it.email == email && it.password == password }
    }

}