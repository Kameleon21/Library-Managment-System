import controllers.BookController
import controllers.PersonController
import mu.KotlinLogging
import persistance.XMLSerializer
import persistance.YAMLSerializer
import utils.InputValidation.promptForValidEmail
import utils.InputValidation.promptForValidName
import utils.InputValidation.promptForValidPassword
import kotlin.system.exitProcess
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File

private val logger = KotlinLogging.logger {}
private val bookAPI = BookController(YAMLSerializer(File("books.yaml")))
private val personAPI = PersonController(YAMLSerializer(File("persons.yaml")))
fun main() {
    load()
    runMenu()
}

fun showMainMenu(): Int {
    return readNextInt(
        """
        Welcome to the Personal Library Management System
        
        Please choose a number option:
        1. Login 
        2. Register
        3. Exit 
        
    """.trimIndent()
    )
}

fun showUserMenu(userName: String, ID: Int): Int {
    return readNextInt(
        """
        Logged in as: $userName $ID
        Please choose a number option:
        1. View Available Books
        2. Search Books
        3. Borrow Book
        4. Return Book
        5. View My Borrowed Books
        6. Logout
        
    """.trimIndent()
    )
}

fun showAdminMenu(adminName: String): Int {
    return readNextInt(
        """
        Logged in as: Admin $adminName
        ADMINISTRATOR MENU
        1. View all books
        2. Add new book
        3. Update book Menu 
        4. Delete book
        5. View all members
        6. Add new member
        7. Update member details
        8. Delete member
        9. View Borrowing records
        10. Logout
        
    """.trimIndent()
    )

}

fun runMenu() {
    do {
        when (val option = showMainMenu()) {
            1 -> login()
            2 -> register()
            3 -> exit()
            else -> println("Invalid option $option \n")
        }
    } while (true)
}

fun login() {
    logger.info { "Login function called" }
    val email = readNextLine("Enter your email: ")
    val password = readNextLine("Enter your password: ")
    val loggedIn = personAPI.login(email, password)

    if (loggedIn != null) {
        println("Login successful \n")
        if (loggedIn.role == "admin") {
            do {
                val option = showAdminMenu(loggedIn.name)
                when (option) {
                    1 -> viewAllBooks()
                    2 -> addNewBook()
                    3 -> updateBookMenu()
                    4 -> deleteBook()
                    5 -> viewAllMembers()
                    6 -> addNewMember()
                    7 -> updateMemberDetails()
                    8 -> deleteMember()
                    9 -> viewBorrowingRecords()
                    10 -> break
                    else -> println("Invalid option")
                }
            } while (true)
        } else {
            do {
                val option = showUserMenu(loggedIn.name, loggedIn.personId)
                when (option) {
                    1 -> viewAvailableBooks()
                    2 -> searchBookOption()
                    3 -> borrowBook()
                    4 -> returnBook()
                    5 -> viewMyBorrowedBooks()
                    6 -> break
                    else -> println("Invalid option")
                }
            } while (true)
        }
    } else {
        println("Login failed \n")
    }
}


fun register() {
    logger.info { "Register function called" }
    val ID = personAPI.numberOfPersons() + 1
    val name = promptForValidName()
    val email = promptForValidEmail()
    val password = promptForValidPassword()
    val role = readNextLine("Enter your role: ")
    val registered = personAPI.register(ID, name, email, password, role)
    if (registered) {
        println(
            """
            Registration successful
            Please login to continue 
            
        """.trimIndent()
        )
    } else {
        println("Registration failed \n")
    }
}

fun exit() {
    logger.info { "Exit function called" }
    println("You chose to exit")
    save()
    exitProcess(0)
}

fun viewAvailableBooks() {
    logger.info { "View Available Books function called" }
    println(bookAPI.availableBooks())
}

fun searchBookOption() {
    logger.info { "Search Books function called" }
    do {
        val option = readNextInt(
            """
            Please choose a number option:
            1. Search by title
            2. Search by author
            3. Search by ISBN
            4. Return to previous menu
            
        """.trimIndent()
        )
        when (option) {
            1 -> {
                val title = readNextLine("Enter book title: ")
                val book = bookAPI.searchBookByTitle(title)
                if (book != null) {
                    println(book)
                } else {
                    println("Book not found")
                }
            }

            2 -> {
                val author = readNextLine("Enter book author: ")
                val book = bookAPI.searchBookByAuthor(author)
                if (book != null) {
                    println(book)
                } else {
                    println("Book not found")
                }
            }

            3 -> {
                val ISBN = readNextLine("Enter book ISBN: ")
                val book = bookAPI.searchBookByISBN(ISBN)
                if (book != null) {
                    println(book)
                } else {
                    println("Book not found")
                }
            }

            4 -> break
            else -> println("Invalid option")
        }
    } while (true)
}

fun borrowBook() {
    logger.info { "Borrow Book function called" }
    println(bookAPI.availableBooks())
    val bookID = readNextInt("Enter book ID: ")
    val memberID = readNextInt("Enter member ID: ") - 1
    val book = bookAPI.findBook(bookID)
    if (book != null) {
        val message = personAPI.borrowBookLogic(memberID, book)
        println(message)
    } else {
        println("Book not found \n")
    }
}

fun returnBook() {
    logger.info { "Return Book function called" }
    println("Return Book function called \n")
}

fun viewMyBorrowedBooks() {
    logger.info { "View My Borrowed Books function called" }
    val memberID = readNextInt("Enter member ID: ") - 1
    val member = personAPI.findPerson(memberID)
    if (member != null) {
        println(personAPI.listBorrowedBooks(memberID) + "\n")
    } else {
        println("Member not found \n")
    }
}

fun viewAllBooks() {
    logger.info { "View All Books function called" }
    println(bookAPI.listAllBooks())
}

fun returnBookDetails() {
    logger.info { "Return Book Details function called" }
    println("Return Book Details function called \n")
}

fun deleteBook() {
    logger.info { "Delete Book function called" }
    println(bookAPI.listAllBooks())
    val bookID = readNextInt("Enter book ID: ")
    val deleted = bookAPI.deleteBook(bookID)
    if (deleted != null) {
        println("Book deleted successfully \n")
    } else {
        println("Book not deleted \n")
    }
}

fun viewAllMembers() {
    logger.info { "View All Members function called" }
    println(personAPI.listAllMembers())
}

fun addNewMember() {
    logger.info { "Add New Member function called" }
    val ID = personAPI.numberOfPersons() + 1
    val name = promptForValidName()
    val email = promptForValidEmail()
    val password = promptForValidPassword()
    val role = readNextLine("Enter your role: ")
    val registered = personAPI.register(ID, name, email, password, role)
    if (registered) {
        println("Member added successfully \n")
    } else {
        println("Member not added \n")
    }
}

fun updateMemberDetails() {
    logger.info { "Update Member Details function called" }
    val memberID = readNextInt("Enter member ID: ") - 1
    val name = promptForValidName()
    val email = promptForValidEmail()
    val password = promptForValidPassword()
    val updated = personAPI.updateMember(memberID,name, email, password)
    if (updated) {
        println("Member details updated successfully \n")
    } else {
        println("Member details not updated \n")
    }
}


fun deleteMember() {
    logger.info { "Delete Member function called" }
    println(personAPI.listAllMembers())
    val memberID = readNextInt("Enter member ID: ")
    val deleted = personAPI.deleteMember(memberID)
    if (deleted != null) {
        println("Member deleted successfully \n")
    } else {
        println("Member not deleted \n")
    }
}

fun viewBorrowingRecords() {
    logger.info { "View Borrowing Records function called" }
    println("View Borrowing Records function called \n")
}

fun addNewBook() {
    logger.info { "Add New Book function called" }
    val bookID = bookAPI.numberOfBooks() + 1
    val title = readNextLine("Enter book title: ")
    val author = readNextLine("Enter book author: ")
    val genre = readNextLine("Enter book genre: ")
    val ISBN = readNextLine("Enter book ISBN: ")
    val publicationYear = readNextLine("Enter book publication year: ")
    val availableCopies = readNextInt("Enter number of available copies: ")
    val totalCopies = readNextInt("Enter number of total copies: ")
    val added = bookAPI.createBook(bookID, title, author, genre, ISBN, publicationYear, availableCopies, totalCopies)
    if (added) {
        println("Book added successfully \n")
    } else {
        println("Book not added \n")
    }
}

fun updateBookMenu() {
    logger.info { "Update Book Details function called" }
    do {
        println(bookAPI.listAllBooks())
        val option = readNextInt(
            """
            Please choose a number option:
            1. Update book title
            2. Update book ISBN
            3. Update number of available copies
            4. Update number of total copies
            5. Return to previous menu
            
        """.trimIndent()
        )
        when (option) {
            1 -> {
                val bookID = readNextInt("Enter book ID: ")
                val title = readNextLine("Enter book title: ")
                val updated = bookAPI.updateBookTitle(bookID, title)
                if (updated) {
                    println("Book title updated successfully \n")
                } else {
                    println("Book not updated \n")
                }
            }

            2 -> {
                val bookID = readNextInt("Enter book ID: ")
                val ISBN = readNextLine("Enter book ISBN: ")
                val updated = bookAPI.updateBookISBN(bookID, ISBN)
                if (updated) {
                    println("Book ISBN updated successfully \n")
                } else {
                    println("Book not updated \n")
                }
            }

            3 -> {
                val bookID = readNextInt("Enter book ID: ")
                val availableCopies = readNextInt("Enter number of available copies: ")
                val updated = bookAPI.updateBookAvailableCopies(bookID, availableCopies)
                if (updated) {
                    println("Book available copies updated successfully \n")
                } else {
                    println("Book not updated \n")
                }
            }

            4 -> {
                val bookID = readNextInt("Enter book ID: ")
                val totalCopies = readNextInt("Enter number of total copies: ")
                val updated = bookAPI.updateBookTotalCopies(bookID, totalCopies)
                if (updated) {
                    println("Book total copies updated successfully \n")
                } else {
                    println("Book not updated \n")
                }
            }

            5 -> break
            else -> println("Invalid option")
        }
    } while (true)
}

fun load() {
    logger.info { "Load function called" }
    try {
        personAPI.load()
        bookAPI.load()
    } catch (e: Exception) {
        logger.error { e.message }
    }
}

fun save() {
    logger.info { "Save function called" }
    personAPI.save()
    bookAPI.save()
}

