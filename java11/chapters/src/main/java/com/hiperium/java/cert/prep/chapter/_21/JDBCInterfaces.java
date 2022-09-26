package com.hiperium.java.cert.prep.chapter._21;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JDBCInterfaces {

    public static final String JDBC_DERBY_CONN_URL = "jdbc:derby:zoo";
    public static final String JDBC_HSQL_CONN_URL = "jdbc:hsqldb:file:zoo";

    public static void main(String[] args) {
        usingAllInterfaces();
        modifyingData();
        readingDataWithExecute();
        workingWithParameters();
        bulkUpdate();
        readingResultSet();
        gettingDataFromColumn();
        usingBindVariables();
        callableStatements();
    }

    private static void usingAllInterfaces() {
        System.out.println("*** Using All Interfaces ***");
        try (var conn = DriverManager.getConnection(JDBC_DERBY_CONN_URL);
             var ps = conn.prepareStatement("SELECT name FROM names");
             var rs = ps.executeQuery()) {
            while (rs.next())
                System.out.println("rs.getString(1) = " + rs.getString(1));
        } catch (SQLException e) {
            System.out.println("ERROR accessing to derby: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("SQL Error Code: " + e.getErrorCode());
        }
    }

    /**
     * Let's start out with statements that change the data in a table. That would be SQL statements that begin with
     * DELETE, INSERT, or UPDATE. They typically use a method called executeUpdate(). It returns the number of rows
     * that were inserted, deleted, or changed.
     */
    private static void modifyingData() {
        System.out.println("*** Modifying Data ***");
        try (var conn = DriverManager.getConnection(JDBC_DERBY_CONN_URL)) {
            var insertSQL = "INSERT INTO exhibits VALUES (10, 'Deer', 3)";
            try (var ps = conn.prepareStatement(insertSQL)){
                int result = ps.executeUpdate();
                System.out.println("Insert result = " + result);                        // PRINT: 1
            } catch (SQLException e) {
                System.out.println("Error inserting an exhibit: " + e.getMessage());
                System.out.println("SQL State: " + e.getSQLState());
                System.out.println("SQL Error Code: " + e.getErrorCode());
            }

            var updateSQL = "UPDATE exhibits SET name = '' WHERE name = 'None'";
            try (var ps = conn.prepareStatement(updateSQL)){
                int result = ps.executeUpdate();
                System.out.println("Update result = " + result);                        // PRINT: 0
            } catch (SQLException e) {
                System.out.println("Error updating an exhibit: " + e.getMessage());
                System.out.println("SQL State: " + e.getSQLState());
                System.out.println("SQL Error Code: " + e.getErrorCode());
            }

            var deleteSQL = "DELETE FROM exhibits WHERE id = 10";
            try (var ps = conn.prepareStatement(deleteSQL)){
                int result = ps.executeUpdate();
                System.out.println("Delete result = " + result);                        // PRINT: 1
            } catch (SQLException e) {
                System.out.println("Error deleting an exhibit: " + e.getMessage());
                System.out.println("SQL State: " + e.getSQLState());
                System.out.println("SQL Error Code: " + e.getErrorCode());
            }
        } catch (SQLException e) {
            System.out.println("ERROR accessing to derby: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("SQL Error Code: " + e.getErrorCode());
        }
    }

    /**
     * NOTE: There's a third method called execute() that can run either a query or an update. It returns a boolean so
     * that we know whether there is a ResultSet. That way, we can call the proper method to get more detail.
     */
    private static void readingDataWithExecute() {
        System.out.println("*** Reading Data with Execute() ***");
        var sql = "SELECT * FROM exhibits";
        try (var conn = DriverManager.getConnection(JDBC_DERBY_CONN_URL);
             var ps = conn.prepareStatement(sql)) {
            boolean isResultSet = ps.execute();
            if (isResultSet) {
                try (var rs = ps.getResultSet()){
                    System.out.println("Ran a query.");
                }
            } else {
                int result = ps.getUpdateCount();
                System.out.println("Ran an update.");
            }
        } catch (SQLException e) {
            System.out.println("ERROR accessing to derby: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("SQL Error Code: " + e.getErrorCode());
        }
    }

    private static void workingWithParameters() {
        System.out.println("*** Working with Parameters ***");
        try (var conn = DriverManager.getConnection(JDBC_DERBY_CONN_URL)) {
            var sql1 = "INSERT INTO exhibits VALUES(?,?,?)";
            try (var ps = conn.prepareStatement(sql1)){
                ps.setInt(1, 3);
                ps.setString(2, "England Bulldog");
                ps.setDouble(3, 0.5);
                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error inserting an exhibit: " + e.getMessage());
                System.out.println("SQL State: " + e.getSQLState());
                System.out.println("SQL Error Code: " + e.getErrorCode());
            }

            // setObject() method works with any Java type. If you pass a primitive, it will be auto-boxed.
            var sql2 = "INSERT INTO names VALUES(?,?,?)";
            try (var ps = conn.prepareStatement(sql2)){
                ps.setObject(1, 20);
                ps.setObject(2, 3);
                ps.setObject(3, "Emma");
                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error inserting a name: " + e.getMessage());
                System.out.println("SQL State: " + e.getSQLState());
                System.out.println("SQL Error Code: " + e.getErrorCode());
            }
        } catch (SQLException e) {
            System.out.println("ERROR accessing to derby: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("SQL Error Code: " + e.getErrorCode());
        }
    }

    private static void bulkUpdate() {
        System.out.println("*** Bulk Update ***");
        try (var conn = DriverManager.getConnection(JDBC_DERBY_CONN_URL)) {
            register(conn, 100, 3, "Amin", "Samira", "Viper", "Hansel", "Gretel");
        } catch (SQLException e) {
            System.out.println("ERROR accessing to derby: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("SQL Error Code: " + e.getErrorCode());
        }
    }

    private static void register(Connection conn, int firstId, int type, String... names) {
        var sql = "INSERT INTO names VALUES(?,?,?)";
        var nextIndex = firstId;

        try (var ps = conn.prepareStatement(sql)) {
            ps.setInt(2, type);
            for (String name : names) {
                ps.setInt(1, nextIndex);
                ps.setString(3, name);
                ps.addBatch();
                nextIndex++;
            }
            int[] result = ps.executeBatch();
            System.out.println("UsingArrays.toString(result) = " + Arrays.toString(result));
        } catch (SQLException e) {
            System.out.println("ERROR performing Bulk Update: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("SQL Error Code: " + e.getErrorCode());
        }
    }

    /**
     * It is important to remember the following:
     *  - Always use an if statement or while loop when calling "rs.next()".
     *  - Column indexes begin with 1.
     */
    private static void readingResultSet() {
        System.out.println("*** Reading Result Set ***");

        String sql1 = "SELECT id, name FROM names";
        Map<Integer, String> idToNameMap = new HashMap<>();
        try (var conn = DriverManager.getConnection(JDBC_DERBY_CONN_URL);
             var ps = conn.prepareStatement(sql1);
             var rs = ps.executeQuery()) {
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                idToNameMap.put(id, name);
            }
            System.out.println("idToNameMap = " + idToNameMap);
        } catch (SQLException e) {
            System.out.println("ERROR accessing 'names' data: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("SQL Error Code: " + e.getErrorCode());
        }

        String sql2 = "SELECT COUNT(*) FROM names";
        try (var conn = DriverManager.getConnection(JDBC_DERBY_CONN_URL);
             var ps = conn.prepareStatement(sql2);
             var rs = ps.executeQuery()) {
            if(rs.next()) {
                int count = rs.getInt(1);
                System.out.println("count = " + count);
            }
        } catch (SQLException e) {
            System.out.println("ERROR counting 'names' data: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("SQL Error Code: " + e.getErrorCode());
        }
    }

    /**
     * ResultSet get methods
     *
     * *****************|***************|
     * Method name      | Return type   |
     * *****************|***************|
     * getBoolean       | boolean       |
     * -----------------|---------------|
     * getDouble        | double        |
     * -----------------|---------------|
     * getInt           | int           |
     * -----------------|---------------|
     * getLong          | long          |
     * -----------------|---------------|
     * getObject        | Object        |
     * -----------------|---------------|
     * getString        | String        |
     * -----------------|---------------|
     *
     * There are "getByte()" and "getFloat()" methods, but you don't need to know about them for the exam. There is no
     * "getChar()" method.
     */
    private static void gettingDataFromColumn() {
        System.out.println("*** Getting Data from Column ***");
        String sql = "SELECT id, name FROM names";
        try (var conn = DriverManager.getConnection(JDBC_DERBY_CONN_URL);
             var ps = conn.prepareStatement(sql);
             var rs = ps.executeQuery()) {
            while(rs.next()) {
                Object idField = rs.getObject("id");
                Object nameField = rs.getObject("name");
                if (idField instanceof Integer) {
                    int id = (Integer) idField;
                    System.out.println("id = " + id);
                }
                if (nameField instanceof String) {
                    String name = (String) nameField;
                    System.out.println("name = " + name);
                }
            }
        } catch (SQLException e) {
            System.out.println("ERROR accessing derby: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("SQL Error Code: " + e.getErrorCode());
        }
    }
    /**
     * We've been creating the PreparedStatement and ResultSet in the same try‐with‐resources statement. This doesn't
     * work if you have bind variables because they need to be set in between. Luckily, we can nest try‐with‐resources
     * to handle this.
     */
    private static void usingBindVariables() {
        System.out.println("*** Using Bind Variables ***");
        String sql = "SELECT id FROM names WHERE name = ?";
        try (var conn = DriverManager.getConnection(JDBC_DERBY_CONN_URL);
             var ps = conn.prepareStatement(sql)) {
            ps.setString(1, "Emma");
            try (var rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    System.out.println("id = " + id);
                }
            }
        } catch (SQLException e) {
            System.out.println("ERROR accessing derby: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("SQL Error Code: " + e.getErrorCode());
        }
    }

    private static void callableStatements()  {
        System.out.println("*** Calling CallableStatement ***");
        try (var conn = DriverManager.getConnection(JDBC_HSQL_CONN_URL)) {
            System.out.println("Reading names");
            String sql1 = "{call read_e_names()}";
            try (var cs = conn.prepareCall(sql1)){
                ResultSet rs = cs.executeQuery();
                while (rs.next())
                    System.out.println("rs.getString(3) = " + rs.getString(3));
            } catch (SQLException e) {
                System.out.println("Error trying to execute SP1: " + e.getMessage());
                System.out.println("SQL State: " + e.getSQLState());
                System.out.println("SQL Error Code: " + e.getErrorCode());
            }

            System.out.println("Reading names by letter");
            String sql2 = "{call read_names_by_letter(?)}";
            try (var cs = conn.prepareCall(sql2)){
                cs.setString("prefix", "Z");
                try (var rs = cs.executeQuery()) {
                    while (rs.next())
                        System.out.println("rs.getString(3) = " + rs.getString(3));
                }
            } catch (SQLException e) {
                System.out.println("Error trying to execute SP2: " + e.getMessage());
                System.out.println("SQL State: " + e.getSQLState());
                System.out.println("SQL Error Code: " + e.getErrorCode());
            }

            // We included two special characters (?=) to specify that the stored procedure has an output value. This is
            // optional since we have the OUT parameter, but it does aid in readability.
            System.out.println("Returning an OUT parameter");
            String sql3 = "{?= call magic_number(?)}";
            try (var cs = conn.prepareCall(sql3)) {
                cs.registerOutParameter(1, Types.INTEGER);
                // We call "execute()" instead of "executeQuery()" since we are not returning a ResultSet from our SP.
                cs.execute();
                System.out.println("cs.getInt('num') = " + cs.getInt("num"));
            } catch (SQLException e) {
                System.out.println("Error trying to execute SP3: " + e.getMessage());
                System.out.println("SQL State: " + e.getSQLState());
                System.out.println("SQL Error Code: " + e.getErrorCode());
            }

            System.out.println("Working with INOUT parameter");
            String sql4 = "{call double_number(?)}";
            try (var cs = conn.prepareCall(sql4)) {
                cs.setInt(1, 8);
                cs.registerOutParameter(1, Types.INTEGER);
                cs.execute();
                System.out.println("cs.getInt('num') = " + cs.getInt("num"));
            } catch (SQLException e) {
                System.out.println("Error trying to execute SP4: " + e.getMessage());
                System.out.println("SQL State: " + e.getSQLState());
                System.out.println("SQL Error Code: " + e.getErrorCode());
            }
        } catch (SQLException e) {
            System.out.println("Error trying to connect to HSQL: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("SQL Error Code: " + e.getErrorCode());
        }
    }
}
