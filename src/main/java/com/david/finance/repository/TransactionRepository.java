package com.david.finance.repository;

import com.david.finance.database.DatabaseManager;
import com.david.finance.model.Transaction;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    public TransactionRepository() {
    DatabaseManager.initializeDatabase();
    }

    public void save(Transaction transaction) {

        String sql = """
                INSERT INTO transactions
                (type, category, amount, description, date)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, transaction.getType());
            statement.setString(2, transaction.getCategory());
            statement.setDouble(3, transaction.getAmount());
            statement.setString(4, transaction.getDescription());
            statement.setString(5, transaction.getDate().toString());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to save transaction.",
                    e
            );
        }
    }

    public List<Transaction> findAll() {

        String sql = """
                SELECT id, type, category, amount, description, date
                FROM transactions
                ORDER BY date DESC
                """;

        List<Transaction> transactions = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Transaction transaction = mapTransaction(resultSet);

                transactions.add(transaction);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to retrieve transactions.",
                    e
            );
        }

        return transactions;
    }

    public Transaction findById(int id) {

        String sql = """
                SELECT id, type, category, amount, description, date
                FROM transactions
                WHERE id = ?
                """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapTransaction(resultSet);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to find transaction.",
                    e
            );
        }

        return null;
    }

    public void delete(int id) {

        String sql = """
                DELETE FROM transactions
                WHERE id = ?
                """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Failed to delete transaction.",
                    e
            );
        }
    }

    private Transaction mapTransaction(ResultSet resultSet)
            throws SQLException {

        return new Transaction(
                resultSet.getInt("id"),
                resultSet.getString("type"),
                resultSet.getString("category"),
                resultSet.getDouble("amount"),
                resultSet.getString("description"),
                LocalDate.parse(resultSet.getString("date"))
        );
    }
}