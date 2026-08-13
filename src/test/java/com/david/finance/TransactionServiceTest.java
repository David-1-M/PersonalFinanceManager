package com.david.finance;

import com.david.finance.model.Transaction;
import com.david.finance.service.TransactionService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionServiceTest {

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
}