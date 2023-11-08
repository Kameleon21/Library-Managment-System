# Personal Library Management System

## Overview
This application is a Personal Library Management System written in Kotlin, designed to manage library operations such as logging in, registering, viewing available books, borrowing, and returning books. It offers a menu-driven interface for users and additional administrative functionalities for librarians.

## System Requirements
- Java Virtual Machine (JVM)
- Java 8

## Setup and Installation
1. Ensure you have Java 8 or higher installed on your system.
2. Clone the repository to your local machine.

## Building the Application
This project uses Gradle for building and managing dependencies:

```shell
./gradlew build
```

## Running the Application
The application can be run using the following command:

```shell
./gradlew run
```

## Usage

Upon running the application, you will be presented with a main menu with the following options:

1. Login: Access your user account.
2. Register: Create a new user account.
3. Exit: Exit the application.

Authenticated users will have additional options to view and interact with the library's catalog.

## Features

- User login and registration system.
- View available books.
- Search for books by various criteria.
- Borrow and return books.
- View borrowed books (for users).
- Admin functionalities for managing the library's catalog (for librarians)

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
This project is licensed under the MIT License - see the LICENSE.md file for details.