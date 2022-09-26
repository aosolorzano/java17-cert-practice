package com.hiperium.java.cert.prep.chapter._21;

import java.sql.*;

public class SetupDerbyDatabase {

    public static void main(String[] args) throws Exception {
        String url = "jdbc:derby:zoo;create=true";
        try (Connection conn = DriverManager.getConnection(url)) {
            // run(conn,"DROP TABLE names");        To delete names tables
            // run(conn,"DROP TABLE exhibits");     To delete exhibits table
            run(conn,"CREATE TABLE exhibits ("
                    + "id INTEGER PRIMARY KEY, "
                    + "name VARCHAR(255), "
                    + "num_acres DECIMAL(4,1))");

            run(conn,"CREATE TABLE names ("
                    + "id INTEGER PRIMARY KEY, "
                    + "species_id integer REFERENCES exhibits (id), "
                    + "name VARCHAR(255))");

            // FOR CHAPTER 22 - SECURITY
            run(conn,"CREATE TABLE hours ("
                    + "day VARCHAR(20) PRIMARY KEY, "
                    + "opens integer, "
                    + "closes integer)");

            run(conn,"INSERT INTO exhibits VALUES (1, 'African Elephant', 7.5)");
            run(conn,"INSERT INTO exhibits VALUES (2, 'Zebra', 1.2)");

            run(conn,"INSERT INTO names VALUES (1, 1, 'Elsa')");
            run(conn,"INSERT INTO names VALUES (2, 2, 'Zelda')");
            run(conn,"INSERT INTO names VALUES (3, 1, 'Ester')");
            run(conn,"INSERT INTO names VALUES (4, 1, 'Eddie')");
            run(conn,"INSERT INTO names VALUES (5, 2, 'Zoe')");

            // FOR CHAPTER 22 - SECURITY
            run(conn,"INSERT INTO hours VALUES ('sunday', 9, 6)");
            run(conn,"INSERT INTO hours VALUES ('monday', 10, 4)");
            run(conn,"INSERT INTO hours VALUES ('tuesday', 10, 4)");
            run(conn,"INSERT INTO hours VALUES ('wednesday', 10, 5)");
            run(conn,"INSERT INTO hours VALUES ('thrusday', 10, 4)");
            run(conn,"INSERT INTO hours VALUES ('friday', 10, 6)");
            run(conn,"INSERT INTO hours VALUES ('saturday', 9, 6)");



            printCount(conn,"SELECT count(*) FROM names");
        }
    }

    private static void run(Connection conn, String sql) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }

    private static void printCount(Connection conn, String sql) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            rs.next();
            System.out.println(rs.getInt(1));
        }
    }
}
