package com.hiperium.java.cert.prep.chapter._16;

import java.util.Scanner;

class JammedTurkeyCage implements AutoCloseable {
    public void close() throws IllegalStateException {
        throw new IllegalStateException("Cage door does not close.");
    }
}

/**
 * Inheriting Exception Classes:
 *
 * When evaluating catch blocks, the inheritance of the exception types can be important. For the exam, you should know
 * that NumberFormatException inherits from IllegalArgumentException. You should also know that FileNotFoundException
 * and NotSerializableException both inherit from IOException. This comes up often in multi‐catch expressions. For
 * example, why does the following not compile?
 *
 *      try {
 *          throw new IOException();
 *      } catch (IOException | FileNotFoundException e) {} // DOES NOT COMPILE
 *
 * Since FileNotFoundException is a subclass of IOException, listing both in a multi‐catch expression is redundant,
 * resulting in a compilation error.
 *
 * Ordering of exceptions in consecutive catch blocks matters too. Do you understand why the following does not compile?
 *
 *      try {
 *          throw new IOException();
 *      } catch (IOException e) {
 *      } catch (FileNotFoundException e) {} // DOES NOT COMPILE
 *
 * For the exam, remember that trying to catch a more specific exception (after already catching a broader exception)
 * results in unreachable code and a compiler error.
 *
 */
public class AutomaticResourceMgmt {

    public static void main(String[] args) {
        try (JammedTurkeyCage t = new JammedTurkeyCage()) {
            System.out.println("Put turkeys in");
        }
        // NOTE: If close() will throw a checked exception, then it should be caught here. RTE do not need to be caught.
        catch (IllegalStateException e) {
            System.out.println("Caught: " + e.getMessage());    // PRINT => Caught: Cage door does not close.
        }
        suppressedExceptions();
        usingScanner();
    }

    /**
     * What happens if the try block also throws an exception? When multiple exceptions are thrown, all but the first
     * are called suppressed exceptions. The idea is that Java treats the first exception as the primary one and tacks
     * on any that come up while automatically closing.
     *
     * Try block throws the primary exception. At this point, the try clause ends, and Java automatically calls the
     * close() method. Catch block on JammedTurkeyCage class throws an IllegalStateException, which is added as a
     * suppressed exception. Then catch block in this method, catches the primary exception, and then prints the message
     * for the primary exception. Next, the code iterate through any suppressed exceptions and print them.
     *
     * Keep in mind that the catch block looks for matches on the primary exception. If we throw a RuntimeException,
     * then Java calls the close() method and adds a suppressed exception. The primary exception will be the RTE.
     * Since this does not match the catch clause in the next example, the exception is thrown to the caller.
     */
    public static void suppressedExceptions() {
        try (JammedTurkeyCage t = new JammedTurkeyCage()) {
            // throw new RuntimeException("Turkeys run off.");          // RTE will not be caught by the catch block.
            throw new IllegalStateException("Turkeys run off.");
        } catch (IllegalStateException e) {
            System.out.println("Caught: " + e.getMessage());            // PRINT: Caught: Turkeys run off.
            for (Throwable t : e.getSuppressed()) {
                System.out.println("Suppressed: " + t.getMessage());    // PRINT: Suppressed: Cage door does not close.
            }
        }
    }

    public static void usingScanner() {
        try (Scanner s = new Scanner(System.in)) {
            System.out.println(1);
            s.nextLine();           // Read the entire line of characters enter by the user.
            System.out.print(2);
            // s = null;            ERROR: Cannot assign a value to final variable 's'.
        } catch (IllegalArgumentException | NullPointerException x) {
            // s.nextLine();        ERROR: Cannot resolve symbol 's'.
            System.out.print(3);
        } finally {
            // s.nextLine();        ERROR: Cannot resolve symbol 's'.
            System.out.print(4);
        }
        System.out.println(5);
    }
}
