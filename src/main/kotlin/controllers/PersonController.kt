package controllers

import models.Book
import models.Person
import persistance.Serializer
import utils.HelperFunctions.isValidListIndex

class PersonController(serializerType: Serializer) {
    private val serializer = serializerType
    private var persons = ArrayList<Person>()

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

    fun borrowBookLogic(memberId: Int, book: Book): String {
        val foundMember = findPerson(memberId) ?: return "Member not found"
        if (foundMember.booksBorrowed.contains(book)) {
            return "Book already borrowed \n"
        }
        if (foundMember.booksBorrowed.size >= 3) {
            return "Member has borrowed 3 books already \n"
        }
        if (book.availableCopies <= 0) {
            return "No copies of this book available \n"
        }
        foundMember.booksBorrowed.add(book)
        book.availableCopies--
        return "Book borrowed successfully \n"
    }

    fun listBorrowedBooks(index: Int): String {
        val foundMember = findPerson(index)
        return if (foundMember != null) {
            if (foundMember.booksBorrowed.isEmpty()) {
                "No books borrowed"
            } else {
                foundMember.booksBorrowed.joinToString(separator = "\n") { book ->
                    "${foundMember.booksBorrowed.indexOf(book)}:Book(bookId=${book.bookID}, title=${book.title}, author=${book.author}, yearPublished=${book.publicationYear})"
                }
            }
        } else "Member not found"
    }

    @Throws(Exception::class)
    fun save() {
        serializer.write(persons)
    }

    @Throws(Exception::class)
    fun load() {
        persons = serializer.read() as ArrayList<Person>
    }
}