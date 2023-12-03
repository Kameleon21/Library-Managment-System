package utils

import utils.HelperFunctions.capitalizeFirstLetter
import utils.ScannerInput.readNextLine

/**
 * Utility object for validating user input in various formats such as name, email, and password.
 */
object InputValidation {

    /**
     * Prompts for and validates a full name input from the user.
     * The name must be at least 2 characters long. The first letter is capitalized.
     *
     * @return A valid full name.
     */
    fun promptForValidName(): String {
        var name: String
        do {
            name = capitalizeFirstLetter(readNextLine("Enter your full name ( minimum 2 characters): "))
            if (!validateName(name)) println("Invalid name")
        } while (!validateName(name))
        return name
    }

    /**
     * Prompts for and validates an email input from the user.
     * The email must contain an '@' symbol and a '.' character.
     *
     * @return A valid email address.
     */
    fun promptForValidEmail(): String {
        var email: String
        do {
            email = readNextLine("Enter your email (e.g user@example.com ):  ")
            if (!validateEmail(email)) println("Invalid email")
        } while (!validateEmail(email))
        return email
    }

    /**
     * Prompts for and validates a password input from the user.
     * The password must be at least 8 characters long.
     *
     * @return A valid password.
     */
    fun promptForValidPassword(): String {
        var password: String
        do {
            password = readNextLine("Enter your password (minimum 8 characters): ")
            if (!validatePassword(password)) println("Invalid password")
        } while (!validatePassword(password))
        return password
    }

    /**
     * Validates an email address.
     * Checks if the email contains an '@' symbol and a '.' character.
     *
     * @param email The email address to validate.
     * @return True if the email is valid, false otherwise.
     */
    fun validateEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }

    /**
     * Validates a password.
     * Checks if the password is at least 8 characters long.
     *
     * @param password The password to validate.
     * @return True if the password is valid, false otherwise.
     */
    fun validatePassword(password: String): Boolean {
        return password.length >= 8
    }

    /**
     * Validates a name.
     * Checks if the name is at least 2 characters long.
     *
     * @param name The name to validate.
     * @return True if the name is valid, false otherwise.
     */
    fun validateName(name: String): Boolean {
        return name.length >= 2
    }
}