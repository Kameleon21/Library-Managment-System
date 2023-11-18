package controllers

import models.Person

class PersonController {
    private val persons = mutableListOf<Person>()

    fun addPerson(person: Person): Boolean {
        if (persons.any { it.email == person.email && it.personId == person.personId }) return false
        return persons.add(person)
    }

    fun numberOfPersons(): Int {
        return persons.size
    }


    fun register(ID: Int, name: String, email: String, password: String, role: String): Boolean {
        if (persons.any { it.email == email }) return false
        val person = Person(
            ID, name, email, password, role
        )
        return addPerson(person)
    }

    fun login(email: String, password: String): Person? {
        return persons.firstOrNull { it.email == email && it.password == password }
    }

}