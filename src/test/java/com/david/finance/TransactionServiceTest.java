package com.david.finance;

import com.david.finance.model.Transaction;
import com.david.finance.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import com.david.finance.database.DatabaseManager;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionServiceTest {

        @BeforeEach
        void setUp() {

        DatabaseManager.setDatabaseUrl("jdbc:sqlite:test_finance.db");

        DatabaseManager.initializeDatabase();

        try (var connection = DatabaseManager.getConnection();
                var statement = connection.createStatement()) {

                statement.execute("DELETE FROM transactions");

        } catch (Exception e) {
                throw new RuntimeException("Failed to reset test database.", e);
        }
        }

    @Test
    void shouldCalculateTotalIncome() {

        TransactionService service = new TransactionService();

        service.addTransaction(
                new Transaction(
                        1,
                        "INCOME",
                        "Salary",
                        15000.00,
                        "Monthly salary",
                        LocalDate.now()
                )
        );

        service.addTransaction(
                new Transaction(
                        2,
                        "INCOME",
                        "Freelance",
                        2500.00,
                        "Freelance work",
                        LocalDate.now()
                )
        );

        assertEquals(
                17500.00,
                service.getTotalIncome(),
                0.01
        );
    }

    @Test
    void shouldCalculateTotalExpenses() {

        TransactionService service = new TransactionService();

        service.addTransaction(
                new Transaction(
                        1,
                        "EXPENSE",
                        "Food",
                        850.00,
                        "Groceries",
                        LocalDate.now()
                )
        );

        service.addTransaction(
                new Transaction(
                        2,
                        "EXPENSE",
                        "Transport",
                        1200.00,
                        "Fuel",
                        LocalDate.now()
                )
        );

        assertEquals(
                2050.00,
                service.getTotalExpenses(),
                0.01
        );
    }

    @Test
    void shouldCalculateBalance() {

        TransactionService service = new TransactionService();

        service.addTransaction(
                new Transaction(
                        1,
                        "INCOME",
                        "Salary",
                        15000.00,
                        "Monthly salary",
                        LocalDate.now()
                )
        );

        service.addTransaction(
                new Transaction(
                        2,
                        "EXPENSE",
                        "Food",
                        850.00,
                        "Groceries",
                        LocalDate.now()
                )
        );

        assertEquals(
                14150.00,
                service.getBalance(),
                0.01
        );
    }

    @Test
        void shouldRejectNegativeAmount() {

        TransactionService service = new TransactionService();

        Transaction transaction = new Transaction(
                1,
                "EXPENSE",
                "Food",
                -500.00,
                "Invalid expense",
                LocalDate.now()
        );

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.addTransaction(transaction)
        );
        }

        @Test
        void shouldRejectInvalidTransactionType() {

        TransactionService service = new TransactionService();

        Transaction transaction = new Transaction(
                1,
                "BANANA",
                "Food",
                500.00,
                "Invalid type",
                LocalDate.now()
        );

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.addTransaction(transaction)
        );
        }

        @Test
        void shouldRejectEmptyCategory() {

        TransactionService service = new TransactionService();

        Transaction transaction = new Transaction(
                1,
                "EXPENSE",
                "",
                500.00,
                "Missing category",
                LocalDate.now()
        );

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> service.addTransaction(transaction)
        );
        }

        
        @Test
        void shouldUpdateExistingTransaction() {

        TransactionService service = new TransactionService();

        Transaction transaction = new Transaction(
                1,
                "EXPENSE",
                "Food",
                850.0,
                "Groceries",
                LocalDate.now()
        );

        service.addTransaction(transaction);

        Transaction updatedTransaction = new Transaction(
                1,
                "EXPENSE",
                "Food",
                950.0,
                "Updated groceries",
                LocalDate.now()
        );

        boolean updated = service.updateTransaction(updatedTransaction);

        assertTrue(updated);

        Transaction savedTransaction =
                service.getAllTransactions().get(0);

        assertEquals(950.0, savedTransaction.getAmount());
        assertEquals("Updated groceries", savedTransaction.getDescription());
        }

        
        @Test
        void shouldReturnFalseWhenUpdatingNonexistentTransaction() {

        TransactionService service = new TransactionService();

        Transaction transaction = new Transaction(
                9999,
                "EXPENSE",
                "Food",
                500.0,
                "Nonexistent transaction",
                LocalDate.now()
        );

        boolean updated = service.updateTransaction(transaction);

        assertFalse(updated);
        }


}