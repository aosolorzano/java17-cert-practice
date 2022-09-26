package com.hiperium.java.cert.chapters.tests._10;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Chapter10Test {

    public String name;

    /**
     * What is printed by the following?
     * R./ The code does not compile because error in exceptions declarations on line 30.
     * <p>
     * Fixing this error, the code prints out:
     * 1
     * 2
     * 4
     * The stack trace for a NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void question3() {
        System.out.println("1");
        try {
            System.out.println("2");
            this.name.length();
            System.out.println("3");
        }
        // catch (IllegalArgumentException e | ClassCastException e) { }
        catch (IllegalArgumentException | ClassCastException e) { // Types in multi-catch must not be related (subclasses).
            System.out.println("4");
            throw e;
        }
        System.out.println("5");
    }

    /**
     * What does the following method print?
     * A
     * O
     */
    @Test(expected = FileNotFoundException.class)
    public void question6() throws FileNotFoundException {
        try (FileReader r = null; FileReader p = new FileReader("")) {
            System.out.println("X"); // Does not get here.
            throw new IllegalArgumentException();
        } catch (Exception e) { // check that var 'e' must not be declared before the try-catch or method parameter.
            System.out.println("A");
            throw new FileNotFoundException(); // Checked exceptions must be handled here or declared in the method.
        } finally {
            System.out.println("O");
        }
    }

    /**
     * What is printed by the following program?
     * A
     * E
     * C
     * java.lang.NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void question8() {
        System.out.println("A");
        try {
            this.stop();
        } catch (ArithmeticException e) { // NPE is not caught here and catch block is not executed.
            System.out.println("B");
        } finally {
            System.out.println("C");      // But, finally block its executed, and the program ends.
        }
        System.out.println("D");          // Does not get here.
    }

    private void stop() {
        System.out.println("E");
        Object x = null;
        x.toString();  // Throws NullPointerException.
        System.out.println("F");
    }

    /**
     * What is the output of the following snippet?
     * <p>
     * R./ The code does not compile because the order of precedence of catch-es blocks.
     * <p>
     * With the fixes, prints:
     * -1
     * done
     */
    @Test
    public void question9() {
        int a = 0, b = 0;
        try {
            System.out.println(a / b);
        } catch (RuntimeException e) {
            System.out.println(-1);
        }
        // catch (ArithmeticException e) {} ERROR: ArithmeticException has already been caught by RuntimeException.
        finally {
            System.out.println("done");
        }
        Assert.assertTrue(true);
    }

    @Test
    public void tryWithResourcesTest() {
        // try (String raspberry = new String("Olivia")) {}  ERROR ==>> String class does not implement AutoCloseable.
        try (FileReader p = new FileReader("")) { // Closeable in FileReader throws an IOException and must be caught.
            System.out.println("Does not get here...");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        // catch (FileNotFoundException | IOException e) {} ERROR: Types in multi-catch must be disjoint.
        Assert.assertTrue(true);
    }

    /**
     * What does the output of the following method contain?
     * R./
     * 1) abce
     * 2) An exception with the message set to "3"
     */
    @Test(expected = RuntimeException.class)
    public void question23() {
        System.out.print("a");
        try {
            System.out.print("b");
            throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            System.out.print("c");
            throw new RuntimeException("1"); // This exception is not handled. The program continues to finally block.
        } catch (RuntimeException e) {       // Does not get here.
            System.out.print("d");
            throw new RuntimeException("2");
        } finally {
            System.out.print("e");
            throw new RuntimeException("3");
        }
    }
}
