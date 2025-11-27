package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Singleton Pattern Implementation
 * Ensures only one database connection instance exists throughout the application
 */
public class DatabaseConnection {
    // Static instance - the single instance of DatabaseConnection
    private static DatabaseConnection instance;

    // Database connection object
    private Connection connection;

    // Database configuration
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/event_managment";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "faijv";

    /**
     * Private constructor prevents instantiation from other classes
     */
    private DatabaseConnection() {
        try {
            // Initialize database connection
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Database connection established successfully");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Failed to establish database connection: " + e.getMessage());
            throw new RuntimeException("Database connection failed", e);
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
            System.out.println("New DatabaseConnection instance created");
        } else {
            System.out.println("Returning existing DatabaseConnection instance");
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Execute a SELECT query
     */
    public ResultSet executeQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("Query execution failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * Execute INSERT, UPDATE, or DELETE query
     */
    public int executeUpdate(String query) {
        try {
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate(query);
            System.out.println("Update executed successfully. Rows affected: " + result);
            return result;
        } catch (SQLException e) {
            System.err.println("Update execution failed: " + e.getMessage());
            return -1;
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Failed to close connection: " + e.getMessage());
        }
    }
}