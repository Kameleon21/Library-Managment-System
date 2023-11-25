package controllers

import models.Person
import utils.HelperFunctions.isValidListIndex

class PersonController {
    private val persons = mutableListOf<Person>()

    fun addPerson(person: Person): Boolean {
        if (persons.any { it.email == person.email && it.personId == person.personId }) return false
        return persons.add(person)
    }

    fun listAllMembers(): String {
        return if (persons.isEmpty()) {
            "No members found"
        } else {
            persons.joinToString(separator = "\n") { member ->
                "${persons.indexOf(member)}:Person(personId=${member.personId}, name=${member.name}, email=${member.email}, password=${member.password}, role=${member.role})"
            }
        }
    }


    fun updateMember(index: Int, name: String, email: String, password: String): Boolean {
        val foundMember = findPerson(index)
        return if (foundMember != null) {
            foundMember.name = name
            foundMember.email = email
            foundMember.password = password
            true
        } else false
    }

    fun deleteMember(index: Int): Person? {
        return if (isValidListIndex(index, persons)) {
            persons.removeAt(index)
        } else null
    }


    fun numberOfPersons(): Int {
        return persons.size
    }

    fun findPerson(index: Int): Person? {
        return if (isValidListIndex(index, persons)) {
            persons[index]
        } else null
    }


    fun register(iD: Int, name: String, email: String, password: String, role: String): Boolean {
        if (persons.any { it.email == email }) return false
        val person = Person(
            iD, name, email, password, role
        )
        return addPerson(person)
    }

    fun login(email: String, password: String): Person? {
        return persons.firstOrNull { it.email == email && it.password == password }
    }

}