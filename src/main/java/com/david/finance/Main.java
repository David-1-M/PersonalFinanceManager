
package com.david.finance;

import com.david.finance.model.Transaction;
import com.david.finance.service.TransactionService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final TransactionService service = new TransactionService();

    public static void main(String[] args) {

        boolean running = true;

        while (running) {
            showMenu();

            int choice = readInt("Choose an option: ");

            try {
                switch (choice) {
                    case 1 -> addTransaction();
                    case 2 -> viewTransactions();
                    case 3 -> updateTransaction();
                    case 4 -> deleteTransaction();
                    case 5 -> showFinancialSummary();
                    case 0 -> {
                        System.out.println("Thank you for using Personal Finance Manager!");
                        running = false;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n=================================");
        System.out.println("      PERSONAL FINANCE MANAGER");
        System.out.println("=================================");
        System.out.println("1. Add Transaction");
        System.out.println("2. View Transactions");
        System.out.println("3. Update Transaction");
        System.out.println("4. Delete Transaction");
        System.out.println("5. Financial Summary");
        System.out.println("0. Exit");
        System.out.println("=================================");
    }

    private static void addTransaction() {

        System.out.println("\n--- Add Transaction ---");

        String type = readType();
        String category = readNonBlank("Category: ");
        double amount = readAmount("Amount (R): ");

        System.out.print("Description: ");
        String description = scanner.nextLine();

        LocalDate date = readDate();

        Transaction transaction = new Transaction(
                0,
                type,
                category,
                amount,
                description,
                date
        );

        service.addTransaction(transaction);

        System.out.println("Transaction added successfully!");
    }

    private static void viewTransactions() {

        System.out.println("\n--- All Transactions ---");

        List<Transaction> transactions = service.getAllTransactions();

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.printf("%-5s %-10s %-15s %-12s %-15s %s%n",
                "ID", "Type", "Category", "Amount", "Date", "Description");

        System.out.println("--------------------------------------------------------------------------");

        for (Transaction transaction : transactions) {
            System.out.printf("%-5d %-10s %-15s R%-11.2f %-15s %s%n",
                    transaction.getId(),
                    transaction.getType(),
                    transaction.getCategory(),
                    transaction.getAmount(),
                    transaction.getDate(),
                    transaction.getDescription() == null
                            ? ""
                            : transaction.getDescription());
        }
    }

    private static void updateTransaction() {

        System.out.println("\n--- Update Transaction ---");

        int id = readInt("Enter transaction ID: ");

        Transaction existing = service.getTransactionById(id);

        if (existing == null) {
            System.out.println("Transaction not found.");
            return;
        }

        System.out.println("Current transaction: " + existing);

        System.out.println("Enter the new details:");

        String type = readType();
        String category = readNonBlank("Category: ");
        double amount = readAmount("Amount (R): ");

        System.out.print("Description: ");
        String description = scanner.nextLine();

        LocalDate date = readDate();

        Transaction updated = new Transaction(
                id,
                type,
                category,
                amount,
                description,
                date
        );

        if (service.updateTransaction(updated)) {
            System.out.println("Transaction updated successfully!");
        } else {
            System.out.println("Transaction could not be updated.");
        }
    }

    private static void deleteTransaction() {

        System.out.println("\n--- Delete Transaction ---");

        int id = readInt("Enter transaction ID: ");

        Transaction transaction = service.getTransactionById(id);

        if (transaction == null) {
            System.out.println("Transaction not found.");
            return;
        }

        System.out.println("Transaction: " + transaction);

        System.out.print("Are you sure you want to delete? (y/n): ");
        String confirmation = scanner.nextLine().trim();

        if (!confirmation.equalsIgnoreCase("y")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        if (service.deleteTransaction(id)) {
            System.out.println("Transaction deleted successfully!");
        } else {
            System.out.println("Transaction could not be deleted.");
        }
    }

    private static void showFinancialSummary() {

        System.out.println("\n--- Financial Summary ---");

        System.out.printf("Total Income:   R%.2f%n",
                service.getTotalIncome());

        System.out.printf("Total Expenses: R%.2f%n",
                service.getTotalExpenses());

        System.out.printf("Balance:        R%.2f%n",
                service.getBalance());
    }

    private static int readInt(String prompt) {

        while (true) {
            System.out.print(prompt);

            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readAmount(String prompt) {

        while (true) {
            System.out.print(prompt);

            try {
                double amount = Double.parseDouble(scanner.nextLine().trim());

                if (Double.isFinite(amount) && amount > 0) {
                    return amount;
                }
            } catch (NumberFormatException e) {
                // Display the validation message below.
            }

            System.out.println("Please enter a valid positive amount.");
        }
    }

    private static String readType() {

        while (true) {
            System.out.print("Type (INCOME/EXPENSE): ");
            String type = scanner.nextLine().trim().toUpperCase();

            if (type.equals("INCOME") || type.equals("EXPENSE")) {
                return type;
            }

            System.out.println("Type must be INCOME or EXPENSE.");
        }
    }

    private static String readNonBlank(String prompt) {

        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();

            if (!value.isBlank()) {
                return value;
            }

            System.out.println("This field cannot be empty.");
        }
    }

    private static LocalDate readDate() {

        while (true) {
            System.out.print("Date (YYYY-MM-DD, leave blank for today): ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return LocalDate.now();
            }

            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Use YYYY-MM-DD.");
            }
        }
    }
}
