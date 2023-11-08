import java.util.Scanner
import java.lang.System.exit
import kotlin.system.exitProcess

val scanner = Scanner(System.`in`)

// global variables for login
var isLoggedIn = false
var isLibrarian = false
fun main() {
    runMenu()
}

fun showMainMenu(): Int {
    println(
        """
        Welcome to the Personal Library Management System
        
        Please choose an option:
        1. Login
        2. Register
        3. Exit
    """.trimIndent()
    )
    return scanner.nextInt()
}

fun showUserMenu(): Int {
    println(
        """
        Logged in as: [UserName...]
        Please choose an option:
        1. View Available Books
        2. Search Books
        3. Borrow Book
        4. Return Book
        5. View My Borrowed Books
        6. Logout
    """.trimIndent()
    )
    return scanner.nextInt()
}

fun showAdminMenu(): Int {
    println(
        """
        Logged in as: [Admin Username...]
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
    return scanner.nextInt()
}

fun runMenu() {
    do {
        when(val option = showMainMenu()) {
            1 -> login()
            2 -> register()
            3 -> exit()
            else -> println("Invalid option")
        }
    }while (true)
}

fun login() {
    println("You chose to login")
}

fun register() {
    println("You chose to register")
}

fun exit() {
    println("You chose to exit")
    exitProcess(0)
}
