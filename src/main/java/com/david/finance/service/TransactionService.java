package com.david.finance.service;

import com.david.finance.model.Transaction;
import com.david.finance.repository.TransactionRepository;
import com.david.finance.util.TransactionValidator;

import java.util.List;

public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService() {
        transactionRepository = new TransactionRepository();
    }

    public void addTransaction(Transaction transaction) {
        TransactionValidator.validate(transaction);
        transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public double getTotalIncome() {
        return transactionRepository.findAll()
                .stream()
                .filter(t -> t.getType().equalsIgnoreCase("INCOME"))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalExpenses() {
        return transactionRepository.findAll()
                .stream()
                .filter(t -> t.getType().equalsIgnoreCase("EXPENSE"))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getBalance() {
        return getTotalIncome() - getTotalExpenses();
    }
}