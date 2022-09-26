package com.hiperium.java.cert.practice.tests;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import static java.util.Locale.Category;

/**
 * THE OCP EXAM TOPICS COVERED IN THIS PRACTICE TEST INCLUDE THE FOLLOWING:
 *
 *      * Localization
 *          - Implement Localization using Locale, resource bundles, and Java APIs to parse and format messages, dates,
 *            and numbers.
 */
public class Chapter12Test {

    /**
     * What is the output of the following code snippet?
     */
    @Test
    public void question6() {
        var d = LocalDateTime.parse("2022-01-21T12:00:00",
                DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        System.out.println("Local datetime: " + d);                         // 2022-01-21T12:00
        System.out.println(d.format(DateTimeFormatter.ISO_LOCAL_DATE));     // 2022-01-21
        Assert.assertTrue(true);
    }

    /**
     * What is the output if the solveMystery() method is applied to a Properties object loaded from mystery.properties?
     */
    @Test
    public void question7() throws IOException {
        Properties props = new Properties();
        String propFileName = "mystery.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        if (inputStream != null) {
            props.load(inputStream);
        } else {
            throw new FileNotFoundException("Property file: " + propFileName + " not found in the classpath.");
        }
        var a = props.get("mystery");
        // var b = props.get("more", null);                                 ERROR: Method declaration error.
        // var c = props.get("more", "trick");                              ERROR: Method declaration error.
        var b = props.getOrDefault("more", null);
        var c = props.getProperty("more", "trick");
        System.out.print(a + " " + b + " " + c);
        Assert.assertTrue(true);
    }

    /**
     * Fill in the blank with the option that allows the code snippet to compile and print a message without throwing an
     * exception at runtime.
     */
    @Test
    public void question8() {
        var x = LocalDate.of(2022, 3, 1);
        var y = LocalDateTime.of(2022, 3, 1, 1, 5, 55);
        var f = DateTimeFormatter.ofPattern(
                // "MMMM' at 'h'o'clock'");             IllegalArgumentException: Unknown pattern letter: l.
                   "MMMM' at 'h'o''clock'");            // FIXED
        System.out.println(f.format(y));
        Assert.assertTrue(true);
    }

    /**
     * For currency, the US uses the $ symbol, the UK uses the £ symbol, and Germany uses the € symbol. Given this
     * information, what is the expected output of the following code snippet?
     */
    @Test
    public void question11() {
        Locale.setDefault(Locale.US);
        Locale.setDefault(Category.FORMAT, Locale.GERMANY);
        System.out.println(NumberFormat.getCurrencyInstance(Locale.UK)
                .format(1.1));                                  // PRINT: £1.10
        Assert.assertTrue(true);
    }

    /**
     * Given the following four properties files, what does this code print?
     */
    @Test
    public void question12() {
        Locale.setDefault(new Locale("en"));
        var rb = ResourceBundle.getBundle("Cars",
                new Locale("de", "DE"));
        var r1 = rb.getString("engine");        // engine  (Cars_en.properties)
        var r2 = rb.getString("horses");        // 241     (Cars_en.properties)
        var r3 = rb.getString("country");       // earth   (Cars.properties)
        System.out.print(r1+ " " + r2 + " " + r3);
        Assert.assertTrue(true);
    }

    /**
     * Given the four properties files in question 12, what does this code print?
     */
    @Test(expected = MissingResourceException.class)
    public void question13() {
        Locale.setDefault(new Locale("en", "US"));
        var rb = ResourceBundle.getBundle("Cars",
                new Locale("fr", "FR"));
        var s1 = rb.getString("country");       // France  (Cars_fr_FR.properties)
        var s2 = rb.getString("horses");        // MissingResourceException: Can't find resource.
        var s3 = rb.getString("engine");        // monteur (Cars.properties)
        System.out.print(s1+ " " + s2 + " " + s3);
    }

    /**
     * Given the four properties files in question 12, what does this code print?
     */
    @Test
    public void question14() {
        Locale.setDefault(new Locale("ja","JP"));
        var rb = ResourceBundle.getBundle("Cars",
                new Locale("fr", "FR"));
        var t1 = rb.getString("engine");        // monteur   (Cars.properties)
        var t2 = rb.getString("road");          // autoroute (Cars_fr_FR.properties)
        var t3 = rb.getString("country");       // France    (Cars_fr_FR.properties)
        System.out.print(t1+ " " + t2 + " " + t3);
        Assert.assertTrue(true);
    }

    /**
     * How many lines does the following print out?
     */
    @Test
    public void question17() {
        Locale.setDefault(Locale.KOREAN);
        System.out.println(Locale.getDefault());                    // ko
        Locale.setDefault(new Locale("en", "AU"));
        System.out.println(Locale.getDefault());                    // en_AU
        Locale.setDefault(new Locale("EN"));
        System.out.println(Locale.getDefault());                    // en
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following code snippet?
     */
    @Test(expected = UnsupportedTemporalTypeException.class)
    public void question19() {
        var d = LocalDate.parse("2022-04-01", DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println("Local date: " + d);     // 2022-04-01
        System.out.println(d.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));    // ERROR => Unsupported field: HourOfDay.
    }

    /**
     * For what values of pattern will the following print <02.1> <06.9> <10,00>?
     *
     * B. ##,00.#
     * D. #,00.#
     */
    @Test
    public void question21() {
        String pattern = "##,00.#";
        var message = DoubleStream.of(2.1, 6.923, 1000)
                .mapToObj(v -> new DecimalFormat(pattern).format(v))
                .collect(Collectors.joining("> <"));
        System.out.print("<" + message + ">");
        Assert.assertTrue(true);
    }

    /**
     * What is the result of the following?
     */
    @Test
    public void question22() {
        Map<String, String> map = new TreeMap<>();
        map.put("tool", "hammer");
        map.put("problem", "nail");

        // var props = new Property();              p1 ERROR: Cannot resolve symbol 'Property'.
        var props = new Properties();
        map.forEach((k, v) -> props.put(k, v));     // p2

        String t = props.getProperty("tool");       // p3
        String n = props.getProperty("nail");       // null
        System.out.print(t + " " + n);
        Assert.assertTrue(true);
    }

    /**
     * For currency, the US uses the $ symbol, the UK uses the £ symbol, and Germany uses the € symbol. Given this
     * information, what is the expected output of the following code snippet?
     */
    @Test
    public void question25() {
        Locale.setDefault(Locale.US);
        Locale.setDefault(Category.FORMAT, Locale.GERMANY);
        Locale.setDefault(Category.DISPLAY, Locale.UK);
        System.out.print(NumberFormat.getCurrencyInstance()
                .format(6.95));                             // PRINT: 6,95 €
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following code snippet?
     */
    @Test
    public void question26() {
        var x = LocalDate.of(2022, 3, 1);
        var y = LocalDateTime.of(2022, 1, 1, 2, 55);
        var f = DateTimeFormatter.ofPattern("'yyyy-MM'");
        System.out.print(f.format(x) + " " + f.format(y));      // PRINT: yyyy-MM yyyy-MM
    }
}
