# Library Manager

Library Manager is a Spring Boot-based application designed to manage Book, Student and transactions in a library system. It handles book issuance, returns, fine calculations, and CRUD operations for book management.

## Features

- **Book Issuance**: Allows students to borrow books if they have not exceeded the maximum limit.
- **Book Return**: Processes book returns and calculates any overdue fines.
- **Fine Calculation**: Automatically calculates fines for overdue books based on the configured return duration and fine per day.
- **CRUD Operations**: Supports Create, Read, Update, and Delete operations for books and Students

## Configuration

The application uses the following configuration properties:

- `students.books.max-allowed`: Maximum number of books a student can borrow.
- `books.return.duration`: Number of days a student can keep a borrowed book before fines are applied.
- `fine.per-day`: Fine amount charged per day for overdue books.

## Installation

1. **Clone the repository**:

    ```sh
    git clone https://github.com/your-username/minor-project-1.git
    cd minor-project-1
    ```

2. **Configure the application**:

    Update the `application.properties` file with the appropriate values:

    ```properties
    students.books.max-allowed=5
    books.return.duration=14
    fine.per-day=10
    ```

3. **Build and run the application**:

    ```sh
    ./mvnw clean install
    ./mvnw spring-boot:run
    ```
