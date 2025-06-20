package com.cms.util;

import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Database connection utility class using Apache Commons DBCP2
 * for connection pooling in the Complaint Management System
 */
public class DatabaseConnection {
    private static final BasicDataSource dataSource = new BasicDataSource();

    static {
        // Database configuration
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(
                "jdbc:mysql://root:1234@95.111.248.142:6300/pearlgims?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        // Connection pool configuration
        dataSource.setInitialSize(5);
        dataSource.setMaxTotal(20);
        dataSource.setMaxIdle(10);
        dataSource.setMinIdle(5);
        dataSource.setMaxWaitMillis(30000);

        // Connection validation
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(true);
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
    }

    /**
     * Get a database connection from the pool
     * 
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Get the data source for advanced operations
     * 
     * @return DataSource object
     */
    public static DataSource getDataSource() {
        return dataSource;
    }

    /**
     * Close the connection pool (call this during application shutdown)
     */
    public static void closePool() {
        try {
            dataSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}