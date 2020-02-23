package com.omnius.omniuseventstore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgreSqlExample {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/omnius_event_store", "omnius_event_store", "omnius_event_store")) {
            System.out.println("Java JDBC PostgreSQL Example");
            System.out.println("Connected to PostgreSQL database!");
            Statement statement = connection.createStatement();
            System.out.println("Reading car records...");
            System.out.printf("%-30.30s  %-30.30s%n", "Model", "Price");

        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    }
}
