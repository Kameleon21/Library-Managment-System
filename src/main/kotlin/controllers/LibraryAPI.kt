package controllers

import models.Book
import models.Person

class LibraryAPI {
    private val books = mutableListOf<Book>()
    private val persons = mutableListOf<Person>()

    fun addBook(book: Book): Boolean {
        return books.add(book)
    }

     fun addPerson(person: Person): Boolean {
        return persons.add(person)
    }

    fun numberOfBooks(): Int {
        return books.size
    }

    fun numberOfPersons(): Int {
        return persons.size
    }

    fun register(ID: Int, name: String, email: String, password: String, role: String): Boolean {
        if (persons.any { it.email == email }) return false
        val person = Person(
            ID,
            name,
            email,
            password,
            role
        )
        return addPerson(person)
    }

    fun login(email: String, password: String): Person? {
        return persons.firstOrNull { it.email == email && it.password == password }
    }

}