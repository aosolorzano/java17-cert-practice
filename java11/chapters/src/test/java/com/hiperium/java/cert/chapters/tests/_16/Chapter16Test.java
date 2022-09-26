package com.hiperium.java.cert.chapters.tests._16;

import org.junit.Assert;
import org.junit.Test;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * QUESTION 1:
 *
 * Which of the following classes contain at least one compiler error?
 */
class Danger extends RuntimeException {
    public Danger(String message) {
        super();
    }
    public Danger(int value) {
        super((String) null);           // NOTE: This is valid at compile time.
    }
}
class Catastrophe extends Exception {
    public Catastrophe(Throwable c) throws RuntimeException {
        super(new Exception());
        c.printStackTrace();
    }
}
class Emergency extends Danger {
    // public Emergency() {}            ERROR: There is no default constructor available in 'Danger'.
    public Emergency(String message) {
        super(message);
    }
}

/**
 * QUESTION 3
 *
 * NOTE: We can use 'AutoCloseable' or 'Closeable' interfaces for a try-with-resources statement.
 */
class EntertainmentCenter {
    static class TV implements AutoCloseable {
        public void close() {
            System.out.print("D");
        }
    }
    static class MediaStreamer implements Closeable {
        public void close() {
            System.out.print("W");
        }
    }

    public static void main() {
        var w = new MediaStreamer();
        try {
            TV d = new TV();
            // w;                           ERROR: Not a statement.
        }
        // { System.out.print("T"); }       ERROR: 'catch' or 'finally' expected.
        catch (Exception e) {
            System.out.print("E");
        } finally {
            System.out.print("F");
        }
        // ADDING TESTING
        System.out.println();
        try (var mediaStreamer = new MediaStreamer(); var tv = new TV();) {
            System.out.println("try-with-resources");
        } finally {
            System.out.print("F");
        }
    }
}

/**
 * QUESTION 4
 */
class Problem extends Exception {
    public Problem() {}
}
class YesProblem extends Problem {}
class MyDatabase {
    // public static void connectToDatabase() throw Problem {}      ERROR: Unexpected token 'throw'.
    public static void connectToDatabase() throws Problem {
        // throws new YesProblem();                                 ERROR: Unexpected token 'throws'.
        throw new YesProblem();
    }
    // public static void main() throw Exception {}                 ERROR: Unexpected token 'throw'.
    public static void main() throws Exception {
        connectToDatabase();
    }
}

/**
 * QUESTION 10
 */
class StuckTurkeyCage implements AutoCloseable {
    public void close() throws IOException {
        throw new FileNotFoundException("Cage not closed...");
    }
    public static void main() {
        try (StuckTurkeyCage t = new StuckTurkeyCage()) {
            System.out.println("Put turkeys in.");
        } catch (IOException e) {
            System.out.println("Try-with-resources catch block: " + e.getMessage());
        }
    }
}

/**
 * QUESTION 11
 */
class EnterPark extends Exception {
    public EnterPark(String message) {
        super();
    }
    private static void checkInput(String[] v) {
        if (v.length <= 3)
            assert(false) : "Invalid input";
    }
    public static void run(String... args) {
        checkInput(args);
        System.out.println(args[0] + args[1] + args[2]);
    }
}

/**
 * QUESTION 13:
 *
 * Which of the following, when inserted independently in the blank, use locale parameters that are properly formatted?
 *
 * C. new Locale("qw");
 * D. new Locale("wp", "VW");
 */
class ReadMap implements AutoCloseable {
    private Locale locale;
    private boolean closed = false;
    void check() {
        assert !closed;
    }
    @Override public void close() {
        check();
        System.out.println("Folding map");
        locale = null;
        closed = true;
    }
    public void open() {
        check();
        // this.locale = ____________;              <<<<<<===
        this.locale = new Locale("en");
    }
    public void use() {
        // Implementation omitted
    }
}

/**
 * QUESTION 11
 */
class FamilyCar {
    static class Door implements AutoCloseable {
        public void close() {
            System.out.print("D");
        }
    }
    static class Window implements Closeable {
        public void close() {
            System.out.print("W");
            throw new RuntimeException("Window exception...");
        }
    }
    public static void main() {
        var d = new Door();
        try (d; var w = new Window()) {
            System.out.print("T");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("E");
        } finally {
            System.out.println("F");
        }
    }
}

/**
 * QUESTION 25
 *
 * NOTE: The primary exception was thrown by try block. Then, the 'close()' method is executed twice, suppressing them
 * by the primary exception. For that reason, the suppressed length is 2 in the primary exception.
 */
class SnowStorm {
    static class WalkToSchool implements AutoCloseable {
        public void close() {
            System.out.println("Closing WalkToSchool...");
            throw new RuntimeException("flurry");
        }
    }
    public static void main() {
        WalkToSchool walk1 = new WalkToSchool();
        try (walk1; WalkToSchool walk2 = new WalkToSchool()) {  // ERROR: 'walk1' should be final or effectively final.
            System.out.println("Trying to WalkToSchool...");
            throw new RuntimeException("blizzard");
        } catch(Exception e) {
            System.out.println("Printing the stack trace: ");
            e.printStackTrace();
            System.out.println(e.getMessage() + " " + e.getSuppressed().length);    // NOTE: the suppressed length is 2.
        }
        // walk1 = null;    FIXING COMPILING ERROR
    }
}

/**
 * QUESTION 25
 */
class Wallet {
    private double money;
    Wallet(double money) { this.money = money; }

    private String openWallet() {
        // Locale.setDefault(Category.DISPLAY,        new Locale.Builder().setRegion("us"));        ERROR: Cannot resolve symbol 'Category'.
        // Locale.setDefault(Locale.Category.DISPLAY, new Locale.Builder().setRegion("us"));        ERROR: Required type: Locale -- Provided: Builder.
        Locale.setDefault(Locale.Category.DISPLAY, new Locale.Builder().setRegion("us").build());
        Locale.setDefault(Locale.Category.FORMAT, new Locale.Builder().setLanguage("en").build());
        return NumberFormat.getCurrencyInstance(Locale.GERMANY).format(money);
    }
    public void printBalance() {
        System.out.println(openWallet());   // Fixing the errors, it prints: 2,40 €
    }
    public static void main() {
        new Wallet(2.4).printBalance();
    }
}

/**
 *
 */
public class Chapter16Test {

    /**
     * What is the output of the following code?
     */
    @Test
    public void question3() {
        EntertainmentCenter.main();         // With the fixing, print: 'try-with-resources DWF'
        Assert.assertTrue(true);
    }

    /**
     * Which statement about the following class is correct?
     *
     * R./ The code does not compile.
     */
    @Test
    public void question4() {
        try {
            MyDatabase.main();
        } catch (Exception e) {
            System.out.println("Question 4 Test catch block.");
        }
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following code?
     */
    @Test
    public void question5() {
        // DateTimeParseException: Text '2020-04-30' could not be parsed at index 10
        // LocalDate date = LocalDate.parse("2020-04-30", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDate date = LocalDate.parse("2020-04-30", DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println("LocalDate: " + date);       // PRINT: 2020-04-30
        System.out.println(date.getYear() + " "         // PRINT: 2020
                + date.getMonth() + " "                 // PRINT: APRIL
                + date.getDayOfMonth());                // PRINT: 30
        Assert.assertTrue(true);
    }

    /**
     * For what value of pattern will the following print <005.21> <008.49> <1,234.0>?
     */
    @Test
    public void question7() {
        // String pattern = "##.#";             PRINT:     <5.2>      <8.5>   <1234>
        // String pattern = "#,###.0";          PRINT:     <5.2>      <8.5>  <1,234.0>
        // String pattern = "0,000.0#";         PRINT: <0,005.21> <0,008.49> <1,234.0>
        String pattern = "#,###,000.0#";     // PRINT:   <005.21>   <008.49> <1,234.0>
        var message = DoubleStream.of(5.21, 8.49, 1234)
                .mapToObj(v -> new DecimalFormat(pattern).format(v))
                .collect(Collectors.joining("> <"));
        System.out.println("<" + message + ">");
        Assert.assertTrue(true);
    }

    /**
     * Which of the following changes when made independently would make this code compile?
     *
     * B. Add throws Exception to the declaration on line 6. (main method)
     * C. Change line 9 to } catch (Exception e) {}.         (try inside main method)
     */
    @Test
    public void question10() {
        StuckTurkeyCage.main();
        Assert.assertTrue(true);
    }

    /**
     * What is the result of running java EnterPark.java bird sing with the following code?
     *
     * C. The code throws an ArrayIndexOutOfBoundsException.
     *
     * NOTE: If we enable assertions, the output will be an AssertionError at runtime.
     */
    @Test(expected = AssertionError.class)
    public void question11() {
        EnterPark.run("bird sing");
    }

    /**
     * Which of the following can be inserted into the blank to allow the code to compile and run without throwing an
     * exception?
     */
    @Test
    public void question15() {
        // var f = DateTimeFormatter.ofPattern("hh o'clock");   IllegalArgumentException: Unknown pattern letter: o.
        var fixed = DateTimeFormatter.ofPattern("hh 'o''clock'");
        System.out.println(fixed.format(LocalDateTime.now()));  // 06 o'clock
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following code?
     */
    @Test
    public void question18() {
        FamilyCar.main();                   // PRINT: "TWDEF"
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following code?
     */
    @Test
    public void question22() {
        LocalDateTime ldt = LocalDateTime.of(2020, 5, 10, 11, 22, 33);
        var f = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        System.out.println(ldt.format(f));      // 11:22 AM

        // MORE TESTS
        var formatter1 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        System.out.println("Short datetime formatter: " + ldt.format(formatter1));  // 5/10/20, 11:22 AM

        var formatter2 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        System.out.println("Medium datetime formatter: " + ldt.format(formatter2)); // May 10, 2020, 11:22:33 AM

        // IMPORTANT: For LONG and FULL format style, we MUST have the Zone ID assigned.
        ZoneId zoneId = ZoneId.of("Europe/London");
        var zdt = ZonedDateTime.of(ldt, zoneId);

        var formatter3 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
        System.out.println("Long datetime formatter: " + zdt.format(formatter3)); // May 10, 2020 at 11:22:33 AM BST

        var formatter4 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL);
        System.out.println("Full datetime formatter: " + zdt.format(formatter4)); // Sunday, May 10, 2020 at 11:22:33 AM British Summer Time

        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following method if props contains {veggies=brontosaurus, meat=velociraptor}?
     */
    private static void question24(Properties props) {
        // System.out.println(props.get("veggies", "none") + " " + props.get("omni", "none"));      ERROR
        System.out.println(props.get("veggies") + " " + props.get("omni"));
        System.out.println(props.getProperty("veggies", "none") + " " + props.getProperty("omni", "none"));
        System.out.println(props.getOrDefault("veggies", "none") + " " + props.getOrDefault("omni", "none"));
    }

    /**
     * What is the output of the following code?
     */
    @Test
    public void question25() {
        SnowStorm.main();                   // Fixing the error, it prints: blizzard 2
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following code?
     *
     * E. The code does not compile.
     */
    @Test
    public void question27() {
        Wallet.main();
        Assert.assertTrue(true);
    }
}
