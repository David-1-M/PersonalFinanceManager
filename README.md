# Personal Finance Manager

A console-based personal finance management application built using Java, Maven, and SQLite. The application allows users to record income and expenses, manage transactions, and monitor their financial balance.

## Features

- Add income and expense transactions.
- View all recorded transactions.
- Update existing transactions.
- Delete transactions with confirmation.
- Calculate total income, total expenses, and remaining balance.
- Store transactions persistently using SQLite.
- Validate user inputs to prevent invalid transactions.
- Automated testing using JUnit 5.

## Technologies Used

- **Java 17** — Application development
- **Maven** — Dependency management and build automation
- **SQLite** — Persistent database storage
- **JDBC** — Database connectivity
- **JUnit 5** — Automated testing
- **Git and GitHub** — Version control

## Project Structure

```text
PersonalFinanceManager/
├── src/
│   ├── main/java/com/david/finance/
│   │   ├── Main.java
│   │   ├── model/
│   │   │   └── Transaction.java
│   │   ├── database/
│   │   │   └── DatabaseManager.java
│   │   ├── repository/
│   │   │   └── TransactionRepository.java
│   │   ├── service/
│   │   │   └── TransactionService.java
│   │   └── util/
│   │       └── TransactionValidator.java
│   └── test/java/com/david/finance/
│       └── TransactionServiceTest.java
├── pom.xml
└── README.md
```

## How to Run

**Requirements:** Java 17 or later and Apache Maven.

1. Clone the repository:

```bash
git clone https://github.com/David-1-M/PersonalFinanceManager.git
```

2. Navigate into the project directory:

```bash
cd PersonalFinanceManager
```

3. Build and test the project:

```bash
mvn clean package
```

4. Run the application:

```powershell
mvn exec:java '-Dexec.mainClass=com.david.finance.Main'
```

The launch command above is intended for PowerShell.

## Application Menu

```text
=================================
      PERSONAL FINANCE MANAGER
=================================
1. Add Transaction
2. View Transactions
3. Update Transaction
4. Delete Transaction
5. Financial Summary
0. Exit
=================================
```

## Example Financial Summary

```text
Total Income:   R15000.00
Total Expenses: R2050.00
Balance:        R12950.00
```

## Testing

Run automated tests using:

```bash
mvn clean test
```

The project includes JUnit tests covering financial calculations, validation, and transaction updates.

## Architecture

The application follows a layered architecture:

- **Presentation Layer:** Console menu and user interaction.
- **Service Layer:** Business logic and transaction management.
- **Repository Layer:** Database operations using JDBC.
- **Database Layer:** SQLite database connection and initialization.
- **Model Layer:** Transaction data representation.

## Future Improvements

Potential improvements include a graphical user interface, category-based spending reports, budgeting functionality, and financial data export.

## Author

David Mdluli

[GitHub](https://github.com/David-1-M)