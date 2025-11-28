package com.shreyash.banking.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static String url;
    private static String user;
    private static String password;

    static {
        // load application.properties from classpath
        try (InputStream in = DBConnection.class.getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (in == null) {
                // helpful error message if file isn't found
                throw new RuntimeException("application.properties NOT found on classpath. " +
                        "Make sure src/main/resources/application.properties exists and is marked as Resources Root.");
            }

            Properties p = new Properties();
            p.load(in);

            url = p.getProperty("db.url");
            user = p.getProperty("db.user");
            password = p.getProperty("db.password");

            if (url == null || user == null) {
                throw new RuntimeException("Database properties missing in application.properties. " +
                        "Expected keys: db.url, db.user, db.password");
            }

        } catch (Exception e) {
            // print and rethrow as unchecked so you notice at startup
            e.printStackTrace();
            throw new RuntimeException("Failed to load DB config: " + e.getMessage(), e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
