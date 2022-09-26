package com.hiperium.java.cert.practice.tests;

import com.hiperium.java.cert.prep.chapter._16.AutomaticResourceMgmt;
import org.junit.Assert;
import org.junit.Test;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Exception Handling:
 *
 *      - Handle exceptions using try/catch/finally clauses, try‐with‐resource, and multi‐catch statements.
 *      - Create and use custom exceptions.
 */
public class Chapter4Test {

    /**
     * Assuming Scanner is a valid class that implements AutoCloseable, what is the expected output of the following
     * code?
     */
    public void question6() {
        AutomaticResourceMgmt.usingScanner();
    }

    /**
     * QUESTION 7:
     *
     * How many constructors in WhaleSharkException compile in the following class?
     *
     * D. Three.
     */
    static class WhaleSharkException extends Exception {
        public WhaleSharkException() {
            super("Friendly shark!");
        }
        public WhaleSharkException(String message) {
            super(new Exception(new WhaleSharkException()));    // VALID: We pass a Throwable nested exception.
        }
        public WhaleSharkException(Exception cause) {}
    }

    /**
     * What is the output of the following application?
     *
     * D. ABD followed by a stack trace.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void question8() {
        try {
            System.out.print('A');
            throw new ArrayIndexOutOfBoundsException();
        } catch (RuntimeException r) {
            System.out.print('B');
            throw r;
        } catch (Exception e) {
            System.out.print('C');
        } finally {
            System.out.println('D');
        }
    }

    /**
     * QUESTION 11:
     *
     * What is the output of the following application?
     */
    static class PrintCompany {
        static class Printer implements Closeable {     // r1
            public void print() {
                System.out.println("This just in!");
            }
            public void close() {}
        }
        public void printHeadlines() {
        //  try {Printer p = new Printer()} {           r2 - ERROR: Use parenthesis instead of brackets.
            try (Printer p = new Printer()) {
                p.print();
            }
        }
        public static void main() {
            new PrintCompany().printHeadlines();        // r3
        }
    }
    @Test
    public void question11() {
        PrintCompany.main();                            // PRINT: This just in!.
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 13:
     *
     * How many lines of text does the following program print?
     *
     * C. The code does not compile because of line y1.
     */
    static class Light {
        public static void run(String[] v) throws Exception {
            try {
                new Light().turnOn();
        //  } catch (RuntimeException v)            y1 ERROR: Variable 'v' is already defined in the scope.
            } catch (RuntimeException e) {
                System.out.println(e);
                throw new IOException();            // y2
            } finally {
                System.out.println("complete");
            }
        }
        public void turnOn() throws IOException {
            new IOException("Not ready");           // y3 WARN: Checked exception is not thrown.
        }
    }
    @Test
    public void question13() {
        try {
            Light.run(null);                      // PRINT: complete.
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 15:
     *
     * How many lines of text does the following program print?
     *
     * A. One.
     */
    static class SpellingException extends RuntimeException {}
    static class SpellChecker {
        public final static void run(String... participants) {
            try {
                if(!"cat".equals("kat")) {
                    new SpellingException();    // NOTE: This line does not throw an exception.
                }
            } catch (SpellingException | NullPointerException e) {
                System.out.println("Spelling problem!");
            } catch (Exception e) {
                System.out.println("Unknown Problem!");
            } finally {
                System.out.println("Done!");
            }
        }
    }
    @Test
    public void question15() {
        SpellChecker.run();                         // PRINT: Done!
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 18:
     *
     * What is the output of the following application?
     *
     * E. The code does not compile.
     */
    static class ProblemException extends Exception {
        ProblemException(Exception e) { super(e); }
    }
    static class MajorProblemException extends ProblemException {
    //  MajorProblemException(String message) {
    //     super(message);                          ERROR >>> Required type: Exception - Provided: String.
    //  }
        MajorProblemException(String message) {
            super(new Exception(message));
        }
        // FOR QUESTION 37
        MajorProblemException(Exception e) {
            super(e);
        }
    }
    static class Unfortunate {
        public static void main() throws MajorProblemException {
            try {
                System.out.print(1);
                throw new MajorProblemException("Uh oh");
            } catch (ProblemException | RuntimeException e) {
                System.out.print(2);
                try {
                    throw new MajorProblemException("yikes");   // We need to add this exception to method declaration.
                } finally {
                    System.out.print(3);
                }
            } finally {
                System.out.println(4);
            }
        }
    }
    @Test
    public void question18() {
        try {
            Unfortunate.main();                             // PRINT: 1234 follow by the exception message.
        } catch (Exception e) {
            System.out.println(e.getSuppressed().length);   // PRINT: 0
            System.out.println(e.getMessage());             // PRINT >>> java.lang.Exception: yikes
        }
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 21:
     *
     * Which exception classes, when inserted into the blank in the Problems class, allow the code to compile?
     *
     * NOTE: The classes "MissingMoneyException" and "MissingFoodException" do not extend any exception classes;
     * therefore, they cannot be used in a method declaration.
     */
    static class MissingMoneyException {}
    static class MissingFoodException {}

    public static class Problems {
    //  public void doIHaveAProblem() throws MissingMoneyException, MissingFoodException {}  <<< ERROR
        public void doIHaveAProblem() {
            System.out.println("No problems");
        }
        public static void main() /* throws ____________ */ {
            try {
                final Problems p = new Problems();
                p.doIHaveAProblem();
            } catch (Exception e) {
                // IMPORTANT: Note that compiler does not complain to add a "throws" declaration in the main method.
                // It knows that "doIHaveAProblem()" does not throw any checked exception, but if it did, then we need
                // to add a "throws" declaration in the main method.
                throw e;
            }
        }
    }

    /**
     * QUESTION 23:
     *
     * Which expressions, when inserted into the blank in the following class, allow the code to compile?
     */
    static class Beach {
        static class TideException extends Exception {}
        public void surf() throws RuntimeException {
            try {
                throw new TideException();
        //  } catch (___________) {}
        //  } catch (Exception z) {}                               OK.
        //  } catch (TideException | IOException z) {}             ERROR: IOException is never thrown in the try block.
        //  } catch (TideException | Exception z)   {}             ERROR: Types in multi-catch must be disjoint.
            } catch (IllegalStateException | TideException ignored) {}
        }
    }

    /**
     * QUESTION 25:
     *
     * Which statement about the following program is correct?
     *
     * A. One exception is thrown to the caller at runtime.
     */
    static class Fetch {
        public int play(String name) throws RuntimeException {  // NOTE: Method does not complain to return a value.
            try {
                throw new RuntimeException(name);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        public static final void main() throws RuntimeException {
            new Fetch().play("Webby");
            new Fetch().play("Georgette");
        }
    }
    @Test(expected = RuntimeException.class)
    public void question25() {
        Fetch.main();
    }

    /**
     * QUESTION 26:
     *
     * What is the output of the following application?
     *
     * D. The code does not compile.
     */
    static class Organ {
        public void operate() throws IOException {
            throw new RuntimeException("Not supported");
        }
    }
    static class Heart extends Organ {
        // public void operate() throws Exception {}                ERROR: overridden method does not throw 'Exception'.
        public void operate() throws FileNotFoundException {
            System.out.print("beat");
        }
        public static void main() throws Exception {
            try {
                new Heart().operate();
            } finally {
                System.out.print("!");
            }
        }
    }

    /**
     * QUESTION 28:
     *
     * What is the result of compiling and executing the following class?
     *
     * D. It prints four lines.
     */
    static class Storm {
        public static void main() throws Exception {
            var weatherTracker = new AutoCloseable() {
                public void close() throws RuntimeException {
                    System.out.println("Thunder");
                }
            };
            try (weatherTracker) {
                System.out.println("Tracking");
            } catch (Exception e) {
                System.out.println("Lightning");
            } finally {
                System.out.println("Storm gone");
                weatherTracker.close();
            }
        }
    }
    @Test
    public void question28() throws Exception {
        Storm.main();                               // PRINT: 'Tracking', 'Thunder', 'Storm gone', and 'Thunder'.
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 29:
     *
     * How many of the following are valid exception declarations?
     *
     * C. Two.
     */
    class ErrorQ extends Exception {}
    class _X  extends IllegalArgumentException {}
    class _X_ extends IllegalArgumentException {}
    class X_  extends IllegalArgumentException {}
    // class 2BeOrNot2Be extends RuntimeException {}                    ERROR: Syntax error.
    // class NumberException<Integer> extends NumberFormatException {}  ERROR: Generic class may not extend 'Throwable'.
       class NumberException<Integer> {}
    // interface Worry implements NumberFormatException {}      ERROR: No 'implements' clause allowed for interfaces.

    /**
     * QUESTION 32:
     *
     * What is the output of the following application?
     */
    static class BasketBall {
        public static void main() {
            try {
                System.out.print(1);
                throw new ClassCastException();
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.print(2);
            } catch (Throwable ex) {
                System.out.print(3);
            } finally {
                System.out.print(4);
            }
            System.out.print(5);
        }
    }
    @Test
    public void question32() {
        BasketBall.main();                  // PRINT: 1345
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 34:
     *
     * What is the output of the following application?
     */
    static class ReadSign implements Closeable {
        public void close() {}
        public String get() { return "Hello"; }
    }
    static class MakeSign implements AutoCloseable {
        public void close() {}
        public void send(String message) {
            System.out.print(message);
        }
    }
    static class Translate {
        public static void main() {
            try (ReadSign r = new ReadSign();
                 MakeSign w = new MakeSign();) {
                w.send(r.get());
            }
        }
    }
    @Test
    public void question34() {
        Translate.main();                   // PRINT: Hello
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 37:
     *
     * What is the output of the following application?
     */
    static class AnotherUnfortunate {
        public static void main() throws Exception {
            try {
                System.out.print(1);
                throw new MajorProblemException(new IllegalStateException());
            } catch (ProblemException | RuntimeException e) {
                System.out.print(2);
                try {
                    throw new MajorProblemException(e);
                } finally {
                    System.out.print(3);
                }
            } finally {
                System.out.println(4);
            }
        }
    }
    @Test
    public void question37() {
        try {
            AnotherUnfortunate.main();      // PRINT: 1234 followed by an exception stack trace.
        } catch (Exception e) {
            e.printStackTrace();            // Print the MajorProblemException followed by 2 'Caused By' blocks.
        }
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 41:
     *
     * Which statement about the following application is correct?
     *
     * C. The code does not compile because of line w3.
     */
    static class CarCrash extends RuntimeException {
        CarCrash(Exception e) {}                                // w1
    }
    static class Cars {
        public static void main() throws Exception {            // w2
            try {
                throw new IOException("Auto-pilot error");
        //  } catch (Exception | CarCrash e) {}                 w3 ERROR: Types in multi-catch must be disjoint:
            } catch (CarCrash e) {
                throw e;
            } catch (Exception a) {                             // w4
                throw a;
            }
        }
    }

    /**
     * QUESTION 47:
     *
     * What is the output of the following?
     *
     * D. The code does not compile due to the declaration of IncidentReportException.
     */
    static class IncidentReportException extends RuntimeException {
        public static void main() throws Exception {
            try {
            //  throw new IncidentReportException(new IOException());    ERROR: No constructor found for this sentence.
                throw new IncidentReportException();
            } catch (RuntimeException e) {
                System.out.println(e.getCause());
            }
        }
    }

    /**
     * QUESTION 48:
     *
     * Which expression, when inserted into the blank in the following class, allows the code to compile?
     *
     * A. 'Error r'.
     */
    static class Bells {
        static class Player implements AutoCloseable {
            @Override public void close() throws RingException {}
        }
        static class RingException extends Exception {
            public RingException(String message) {}
        }
        public static void main() throws Throwable {
            try (Player p = null) {                 // VALID: No instance method 'close()' will be called.
                throw new Exception();
            } catch (Exception ignored) {
        //  } catch (_______________________) {}
        //  } catch (IllegalStateException e) {}    ERROR: Exception 'IllegalStateException' has already been caught.
        //  } catch (RuntimeException e) {}         ERROR: Exception 'RuntimeException' has already been caught.
        //  } catch (RingException e) {}            ERROR: Exception 'RingException' has already been caught.
        //  } catch (SQLException e) {}             ERROR: Exception 'SQLException' has already been caught.
            } catch (Error ignored) {}
        }
    }
    @Test
    public void question48() {
        try {
            Bells.main();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 49:
     *
     * What is the output of the following application?
     *
     * F. None of the above.
     */
    static class BigCatQ {
    //  void roar(int level) throw RuntimeException {}              // ERROR: Unexpected token 'throw'.
        void roar(int level) throws RuntimeException {
            if (level < 3) throw new IllegalArgumentException();
            System.out.print("Roar!");
        }
    }
    static class Lion extends BigCatQ {
        public void roar() {
            System.out.print("Roar!!!");
        }
        void roar(int sound) {
            System.out.print("Meow");
        }
        public static void main() {
            final BigCatQ kitty = new Lion();
            kitty.roar(2);
        }
    }
    @Test
    public void question49() {
        Lion.main();
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 50:
     *
     * Which statement about the following program is true?
     *
     * D. The code does not compile.
     */
    static class MissedCallException extends Exception {}
    static class PhoneQ {
        static void makeCall() throws RuntimeException {
            throw new ArrayIndexOutOfBoundsException("Call");
        }
        public static void main() {
            try {
                makeCall();
        //  } catch (MissedCallException e){}   ERROR: Exception 'MissedCallException' is never thrown in the try block.
            } finally {
                throw new RuntimeException("Text");
            }
        }
    }

    /**
     * QUESTION 52:
     *
     * What is the output of the following application?
     */
    static class Chair {
        public void sit() throws IllegalArgumentException {
            System.out.print("creek");
            throw new RuntimeException();               // IMPORTANT: This sentence is valid.
        }
    }
    static class Stool extends Chair {
        public void sit() throws RuntimeException {     // IMPORTANT: This override is valid.
            System.out.print("thud");
        }
        public static void main() throws Exception {
            try {
                new Stool().sit();
            } finally {
                System.out.print("?");
            }
        }
    }
    @Test
    public void question52() throws Exception {
        Stool.main();                                   // PRINT: thud?
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 55:
     *
     * What is the result of compiling and running the following application?
     */
    static class Pizza {
        Exception order(RuntimeException e) {       // h1: Is not necessary to return an Exception if we throw one.
            throw e;                                // h2: This is a valid sentence.
        }
        public static void main() {
            var p = new Pizza();
            try {
                p.order(new IllegalArgumentException());    // h3
            } catch(RuntimeException e) {                   // h4
                System.out.print(e);
            }
        }
    }
    @Test
    public void question55() {
        Pizza.main();                                       // PRINT: java.lang.IllegalArgumentException
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 59:
     *
     * Given the following application, what is the name of the class printed at line e1?
     */
    static final class FallenException extends Exception {}
    static final class HikingGear implements AutoCloseable {
        @Override public void close() throws Exception {
            throw new FallenException();
        }
    }
    static class Cliff {
        public final void climb() throws Exception {
            try (HikingGear gear = new HikingGear()) {
                throw new RuntimeException();
            }
        }
        public static void main() {
            try {
                new Cliff().climb();
            } catch (Throwable t) {
                System.out.println(t);                          // e1: java.lang.RuntimeException
                System.out.println(t.getSuppressed().length);   // PRINT: 1
            }
        }
    }
    @Test
    public void question59() {
        Cliff.main();                                           // PRINT: java.lang.RuntimeException
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 60:
     *
     * Given the following application, which specific type of exception will be printed in the stack trace at runtime?
     *
     * D. RuntimeException
     */
    static class WhackAnException {
        public static void main() {
            try {
                throw new ClassCastException();
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException();
            } catch (RuntimeException e) {
                throw new NullPointerException();
            } finally {
                throw new RuntimeException();
            }
        }
    }
    @Test(expected = RuntimeException.class)
    public void question60() {
        WhackAnException.main();
    }
}
