package com.david.finance.service;

import com.david.finance.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    private final List<Transaction> transactions;

    public TransactionService() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    public double getTotalIncome() {

        return transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("INCOME"))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalExpenses() {

        return transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("EXPENSE"))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getBalance() {
        return getTotalIncome() - getTotalExpenses();
    }
}