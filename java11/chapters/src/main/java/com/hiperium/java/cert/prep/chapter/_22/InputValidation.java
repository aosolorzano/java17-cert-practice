package com.hiperium.java.cert.prep.chapter._22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.stream.Stream;

/**
 * IMPORTANT:
 *      - Injection is an attack where dangerous input runs in a program as part of a command.
 *      - An exploit is an attack that takes advantage of weak security.
 *      - Command injection is another type that uses operating system commands to do something unexpected.
 */
public class InputValidation {

    private static final Path homePath = Paths.get("/Users/asolorzano/");

    public static void main(String[] args) {
        sqlInjection();
        commandAttack();
    }

    private static void sqlInjection() {
        sqlInjectionUsingStatement();
        sqlInjectionUsingPrepareStatement();
    }

    private static void sqlInjectionUsingStatement() {
        System.out.println("*** SQL INJECTION USING STATEMENT ***");
        System.out.println("getOpening('monday') = " +
                getOpeningWithStatement("monday"));                                         // PRINT: 10
        System.out.println("getOpening(monday' OR day IS NOT NULL OR day = 'sunday) = " +
                getOpeningWithStatement("monday' OR day IS NOT NULL OR day = 'sunday"));    // PRINT: 9
    }

    private static int getOpeningWithStatement(String day) {
        try (var conn = DriverManager.getConnection("jdbc:derby:zoo")) {
            String sql = "SELECT opens FROM hours WHERE day = '" + day + "'";
            try (var stmt = conn.createStatement()) {
                var rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    return rs.getInt("opens");
                }
            } catch (SQLException e) {
                System.out.println("ERROR creating a statement: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("ERROR accessing to derby: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("SQL Error Code: " + e.getErrorCode());
        }
        return -1;
    }

    private static void sqlInjectionUsingPrepareStatement() {
        System.out.println("*** SQL INJECTION USING PREPARE STATEMENT ***");
        System.out.println("getOpening(monday' OR day IS NOT NULL OR day = 'sunday) = " +
                getOpeningWithPrepareStatement("monday' OR day IS NOT NULL OR day = 'sunday"));     // PRINT: -1
    }

    private static int getOpeningWithPrepareStatement(String day) {
        try (var conn = DriverManager.getConnection("jdbc:derby:zoo")) {
            String sql = "SELECT opens FROM hours WHERE day = ?";
            try (var ps = conn.prepareStatement(sql)) {
                ps.setString(1, day);
                try (var rs = ps.executeQuery()) {
                    if (rs.next())
                        return rs.getInt("opens");
                }
            } catch (SQLException e) {
                System.out.println("ERROR creating a statement: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("ERROR accessing to derby: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("SQL Error Code: " + e.getErrorCode());
        }
        return -1;
    }

    private static void commandAttack() {
        // Console console = System.console();              We can use the console
        // String userDirectory = console.readLine();       To get User Input
        String userDirectory = "Downloads";
        listingDirectoriesWithoutInputValidation(userDirectory);
        listingDirectoriesWithoutInputValidation(userDirectory.concat("/../Documents"));
        listingDirectoriesWithInputValidation(userDirectory.concat("/../Documents"));
    }

    private static void listingDirectoriesWithoutInputValidation(String dirName) {
        System.out.println("*** LISTING DIRS WITHOUT INPUT VALIDATION ***");
        System.out.println(">>>> " + dirName);
        try (Stream<Path> stream = Files.walk(homePath.resolve(dirName))) {
            stream
                    .filter(p -> p.toString().endsWith(".pdf"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("ERROR trying to walk into directory: " + e.getMessage());
        }
    }

    /**
     * Now we will use a whitelist that allows us to specify which values are allowed.
     */
    private static void listingDirectoriesWithInputValidation(String directoryName) {
        System.out.println("*** LISTING DIRS WITH INPUT VALIDATION ***");
        System.out.println(">>> " + directoryName);
        if (directoryName.equals("Downloads") || directoryName.equals("Public")) {
            try (Stream<Path> stream = Files.walk(homePath.resolve(directoryName))) {
                stream
                        .filter(p -> p.toString().endsWith(".pdf"))
                        .forEach(System.out::println);
            } catch (IOException e) {
                System.out.println("ERROR trying to walk into directory: " + e.getMessage());
            }
        } else {
            System.out.println("User directory input is not allowed. We can throw an exception here.");
        }

    }
}
