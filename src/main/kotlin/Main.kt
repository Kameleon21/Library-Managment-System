import controllers.LibraryAPI
import mu.KotlinLogging
import utils.InputValidation.promptForValidEmail
import utils.InputValidation.promptForValidName
import utils.InputValidation.promptForValidPassword
import kotlin.system.exitProcess
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine

private val logger = KotlinLogging.logger {}
private val LibraryAPI = LibraryAPI()
fun main() {
    runMenu()
}

fun showMainMenu(): Int {
    return readNextInt(
        """
        Welcome to the Personal Library Management System
        
        Please choose an option:
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
        Please choose an option:
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
    val loggedIn = LibraryAPI.login(email, password)

    if (loggedIn != null) {
        println("Login successful \n")
        if (loggedIn.role == "admin") {
            showAdminMenu(loggedIn.name)
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
    val name = promptForValidName()
    val email = promptForValidEmail()
    val password = promptForValidPassword()
    val registered = LibraryAPI.registerMember(name, email, password)
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

