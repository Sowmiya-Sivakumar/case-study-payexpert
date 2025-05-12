package com.hexaware.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnUtil {

    /**
     * Establishes a database connection using the provided properties.
     * Expects properties containing keys: db.url, db.username, db.password
     * 
     * @param props Properties object containing DB connection parameters
     * @return Connection object
     * @throws Exception If connection cannot be established
     */
    public static Connection getConnection(Properties props) throws Exception {
        String url = props.getProperty("db.url");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        if (url == null || username == null || password == null) {
            throw new Exception("Database connection information missing in properties");
        }

        // Load MySQL JDBC Driver (optional for newer JDBC versions)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new Exception("MySQL JDBC Driver not found", e);
        }

        return DriverManager.getConnection(url, username, password);
    }
}
