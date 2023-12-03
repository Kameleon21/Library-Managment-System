package controllers

import models.Book
import models.Person
import persistance.Serializer
import utils.HelperFunctions.isValidListIndex

/**
 * Controller class for managing a collection of persons (members) in a library system.
 * Provides methods to add, list, update, delete, and manage book borrowing for members.
 *
 * @param serializerType The serializer to use for persisting person data.
 */
class PersonController(serializerType: Serializer) {
    private val serializer = serializerType
    private var persons = ArrayList<Person>()

    /**
     * Adds a person to the collection if a person with the same email and ID does not already exist.
     *
     * @param person The person to be added.
     * @return True if the person was added successfully, false if a person with the same email and ID already exists.
     */
    fun addPerson(person: Person): Boolean {
        if (persons.any { it.email == person.email && it.personId == person.personId }) return false
        return persons.add(person)
    }

    /**
     * Lists all members in the collection.
     *
     * @return A string representation of all members or a message if no members are found.
     */
    fun listAllMembers(): String {
        return if (persons.isEmpty()) {
            "No members found"
        } else {
            persons.joinToString(separator = "\n") { member ->
                "${persons.indexOf(member)}:Person(personId=${member.personId}, name=${member.name}, email=${member.email}, password=${member.password}, role=${member.role})"
            }
        }
    }


    /**
     * Updates the information of a member identified by their index in the collection.
     *
     * @param index The index of the member to update.
     * @param name The new name of the member.
     * @param email The new email of the member.
     * @param password The new password of the member.
     * @return True if the update was successful, false if the member was not found.
     */
    fun updateMember(index: Int, name: String, email: String, password: String): Boolean {
        val foundMember = findPerson(index)
        return if (foundMember != null) {
            foundMember.name = name
            foundMember.email = email
            foundMember.password = password
            true
        } else false
    }

    /**
     * Deletes a member from the collection identified by their index.
     *
     * @param index The index of the member to delete.
     * @return The deleted member if successful, or null if the index is invalid.
     */
    fun deleteMember(index: Int): Person? {
        return if (isValidListIndex(index, persons)) {
            persons.removeAt(index)
        } else null
    }

    /**
     * Returns the total number of persons in the collection.
     *
     * @return The number of persons.
     */
    fun numberOfPersons(): Int {
        return persons.size
    }

    /**
     * Finds and returns a person by their index in the collection.
     * Returns null if the index is not valid.
     *
     * @param index The index of the person in the collection.
     * @return The person at the specified index, or null if the index is invalid.
     */
    fun findPerson(index: Int): Person? {
        return if (isValidListIndex(index, persons)) {
            persons[index]
        } else null
    }

    /**
     * Registers a new member and adds them to the collection.
     * Checks for existing members with the same email before adding.
     *
     * @param iD The ID of the new member.
     * @param name The name of the new member.
     * @param email The email of the new member.
     * @param password The password of the new member.
     * @param role The role of the new member.
     * @return True if the member was successfully registered, false if a member with the same email already exists.
     */
    fun register(iD: Int, name: String, email: String, password: String, role: String): Boolean {
        if (persons.any { it.email == email }) return false
        val person = Person(
            iD, name, email, password, role
        )
        return addPerson(person)
    }

    /**
     * Authenticates a member's login using their email and password.
     *
     * @param email The email of the member.
     * @param password The password of the member.
     * @return The person if authentication is successful, null otherwise.
     */
    fun login(email: String, password: String): Person? {
        return persons.firstOrNull { it.email == email && it.password == password }
    }

    /**
     * Handles the logic for a member borrowing a book.
     * Checks various conditions like membership validity, book availability, and borrowing limits.
     *
     * @param memberId The ID of the member borrowing the book.
     * @param book The book to be borrowed.
     * @return A string message indicating the result of the borrowing action.
     */
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

    /**
     * Lists all books borrowed by a specific member.
     *
     * @param index The index of the member in the collection.
     * @return A string representation of all books borrowed by the member or a message if the member is not found or has no borrowed books.
     */
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

    /**
     * Handles the logic for a member returning a book.
     * Ensures that the member has actually borrowed the book before returning.
     *
     * @param memberId The ID of the member returning the book.
     * @param book The book to be returned.
     * @return A string message indicating the result of the return action.
     */
    fun returnBookLogic(memberId: Int, book: Book): String {
        val foundMember = findPerson(memberId) ?: return "Member not found"
        if (!foundMember.booksBorrowed.contains(book)) {
            return "Book not borrowed by member \n"
        }
        foundMember.booksBorrowed.remove(book)
        book.availableCopies++
        return "Book returned successfully \n"
    }

    /**
     * Shows a list of all borrowed books by all members.
     *
     * @return A string representation of all borrowed books by all members, or a message if no books are borrowed.
     */

    fun showBorrowedBooksByAllMembers(): String {
        val borrowedBooksInfo = persons.filter { it.booksBorrowed.isNotEmpty() }
            .joinToString(separator = "\n\n") { person ->
                val booksList = person.booksBorrowed.joinToString(separator = "; \n") { book ->
                    "Book ID: ${book.bookID}, Title: ${book.title}, Author: ${book.author}, Published: ${book.publicationYear}"
                }
                val numberOfBooks = person.booksBorrowed.size
                "Member ID: ${person.personId}, Name: ${person.name}, Total Borrowed Books: $numberOfBooks\nBorrowed Books: $booksList"
            }

        return if (borrowedBooksInfo.isEmpty()) {
            "No books borrowed"
        } else {
            borrowedBooksInfo
        }
    }

    /**
     * Saves the current state of the person collection to persistent storage.
     *
     * @throws Exception if there is an error during saving.
     */
    @Throws(Exception::class)
    fun save() {
        serializer.write(persons)
    }

    /**
     * Loads the state of the person collection from persistent storage.
     *
     * @throws Exception if there is an error during loading.
     */

    @Throws(Exception::class)
    fun load() {
        persons = serializer.read() as ArrayList<Person>
    }
}