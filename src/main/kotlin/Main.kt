import controllers.BookController
import controllers.PersonController
import mu.KotlinLogging
import persistance.XMLSerializer
import utils.InputValidation.promptForValidEmail
import utils.InputValidation.promptForValidName
import utils.InputValidation.promptForValidPassword
import kotlin.system.exitProcess
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File

private val logger = KotlinLogging.logger {}
private val bookAPI = BookController(XMLSerializer(File("books.xml")))
private val personAPI = PersonController(XMLSerializer(File("persons.xml")))
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

fun showUserMenu(userName: String): Int {
    return readNextInt(
        """
        Logged in as: $userName
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
        3. Update book details
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
                   3 -> updateBookDetails()
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
                val option = showUserMenu(loggedIn.name)
                when (option) {
                    1 -> viewAvailableBooks()
                    2 -> searchBooks()
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
    val ID = readNextInt("Enter your ID: ")
    val name = promptForValidName()
    val email = promptForValidEmail()
    val password = promptForValidPassword()
    val role = readNextLine("Enter your role: ")
    val registered = personAPI.register(ID,name, email, password, role)
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
    println("View Available Books function called \n")
}

fun searchBooks() {
    logger.info { "Search Books function called" }
    println("Search Books function called \n")
}

fun borrowBook() {
    logger.info { "Borrow Book function called" }
    println("Borrow Book function called \n")
}

fun returnBook() {
    logger.info { "Return Book function called" }
    println("Return Book function called \n")
}

fun viewMyBorrowedBooks() {
    logger.info { "View My Borrowed Books function called" }
    println("View My Borrowed Books function called \n")
}

fun viewAllBooks() {
    logger.info { "View All Books function called" }
    println("View All Books function called \n")
}

fun returnBookDetails() {
    logger.info { "Return Book Details function called" }
    println("Return Book Details function called \n")
}

fun deleteBook() {
    logger.info { "Delete Book function called" }
    println("Delete Book function called \n")
}

fun viewAllMembers() {
    logger.info { "View All Members function called" }
    println("View All Members function called \n")
}

fun addNewMember() {
    logger.info { "Add New Member function called" }
    println("Add New Member function called \n")
}

fun updateMemberDetails() {
    logger.info { "Update Member Details function called" }
    println("Update Member Details function called \n")
}


fun deleteMember() {
    logger.info { "Delete Member function called" }
    println("Delete Member function called \n")
}

fun viewBorrowingRecords() {
    logger.info { "View Borrowing Records function called" }
    println("View Borrowing Records function called \n")
}

fun addNewBook() {
    logger.info { "Add New Book function called" }
    println("Add New Book function called \n")
}

fun updateBookDetails() {
    logger.info { "Update Book Details function called" }
    println("Update Book Details function called \n")
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

