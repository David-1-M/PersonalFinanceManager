package com.david.finance;

import com.david.finance.model.Transaction;
import com.david.finance.service.TransactionService;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        TransactionService transactionService = new TransactionService();

        transactionService.addTransaction(
                new Transaction(
                        1,
                        "INCOME",
                        "Salary",
                        15000.00,
                        "Monthly salary",
                        LocalDate.now()
                )
        );

        transactionService.addTransaction(
                new Transaction(
                        2,
                        "EXPENSE",
                        "Food",
                        850.00,
                        "Groceries",
                        LocalDate.now()
                )
        );

        transactionService.addTransaction(
                new Transaction(
                        3,
                        "EXPENSE",
                        "Transport",
                        1200.00,
                        "Fuel and transport",
                        LocalDate.now()
                )
        );

        System.out.println("=================================");
        System.out.println("   Personal Finance Manager");
        System.out.println("=================================");

        System.out.println();

        System.out.println("Total Income:   R" +
                transactionService.getTotalIncome());

        System.out.println("Total Expenses: R" +
                transactionService.getTotalExpenses());

        System.out.println("Balance:        R" +
                transactionService.getBalance());

        System.out.println();
        System.out.println("Transactions:");

        for (Transaction transaction :
                transactionService.getAllTransactions()) {

            System.out.println(transaction);
        }
    }
}