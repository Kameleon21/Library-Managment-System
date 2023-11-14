package utils

import utils.ScannerInput.readNextLine

object InputValidation {
    fun promptForValidName(): String {
        var name: String
        do {
            name = readNextLine("Enter your full name ( minimum 2 characters): ")
            if (!validateName(name)) println("Invalid name")
        } while (!validateName(name))
        return name
    }

    fun promptForValidEmail(): String {
        var email: String
        do {
            email = readNextLine("Enter your email (e.g user@example.com ):  ")
            if (!validateEmail(email)) println("Invalid email")
        } while (!validateEmail(email))
        return email
    }

    fun promptForValidPassword(): String {
        var password: String
        do {
            password = readNextLine("Enter your password (minimum 8 characters): ")
            if (!validatePassword(password)) println("Invalid password")
        } while (!validatePassword(password))
        return password
    }

    fun validateEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }

    fun validatePassword(password: String): Boolean {
        return password.length >= 8
    }

    fun validateName(name: String): Boolean {
        return name.length >= 2
    }
}