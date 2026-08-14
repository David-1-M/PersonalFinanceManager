package com.david.finance.util;

import com.david.finance.model.Transaction;

public class TransactionValidator {

    public static void validate(Transaction transaction) {

        if (transaction == null) {
            throw new IllegalArgumentException(
                    "Transaction cannot be null."
            );
        }

        if (transaction.getAmount() <= 0) {
            throw new IllegalArgumentException(
                    "Transaction amount must be greater than zero."
            );
        }

        if (transaction.getType() == null ||
                (!transaction.getType().equalsIgnoreCase("INCOME")
                && !transaction.getType().equalsIgnoreCase("EXPENSE"))) {

            throw new IllegalArgumentException(
                    "Transaction type must be INCOME or EXPENSE."
            );
        }

        if (transaction.getCategory() == null ||
                transaction.getCategory().isBlank()) {

            throw new IllegalArgumentException(
                    "Transaction category cannot be empty."
            );
        }

        if (transaction.getDate() == null) {
            throw new IllegalArgumentException(
                    "Transaction date cannot be null."
            );
        }
    }
}