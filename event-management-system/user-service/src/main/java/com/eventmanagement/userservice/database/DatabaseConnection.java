package com.eventmanagement.userservice.database;

import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * SINGLETON PATTERN IMPLEMENTATION
 *
 * Ensures only one database connection exists throughout the application.
 * In Spring, @Component with default scope creates a singleton bean.
 */
@Component
public class DatabaseConnection {

    private static DatabaseConnection instance;
    private final DataSource dataSource;
    private static int connectionCount = 0;

    public DatabaseConnection(DataSource dataSource) {
        if (instance != null) {
            throw new IllegalStateException("DatabaseConnection instance already exists!");
        }
        this.dataSource = dataSource;
        instance = this;
        System.out.println("DatabaseConnection instance created");
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            throw new IllegalStateException("DatabaseConnection not initialized by Spring");
        }
        System.out.println("Returning existing DatabaseConnection instance");
        return instance;
    }

    public Connection getConnection() throws SQLException {
        connectionCount++;
        System.out.println("Connection requested. Total connections served: " + connectionCount);
        return dataSource.getConnection();
    }
}