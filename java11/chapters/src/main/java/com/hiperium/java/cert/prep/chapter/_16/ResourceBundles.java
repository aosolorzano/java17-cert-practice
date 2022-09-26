package com.hiperium.java.cert.prep.chapter._16;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A resource bundle contains the locale‐specific objects to be used by a program. It is like a map with keys and values.
 * The resource bundle is commonly stored in a properties file. One of the benefits of using resource bundles is to
 * decouple the application code from the locale‐specific text data.
 *
 * One excellent approach is to have all the properties files in a separate properties JAR or folder and load them
 * in the classpath at runtime. In this manner, a new language can be added without changing the application JAR.
 */
public class ResourceBundles {

    public static void main(String[] args) {
        createResourceBundle();
        iterateResourceBundle();
        pickingResourceBundle();
        selectingValues();
        formattingMessages();
    }

    /**
     * The filenames match the name of our resource bundle, Zoo. They are then followed by an underscore ( _), target
     * locale, and .properties file extension. We can write our very first program that uses a resource bundle to print
     * this information.
     */
    public static void createResourceBundle() {
        var us = new Locale("en", "US");
        printWelcomeMessage(us);                            // Hello, The zoo is open
        var fr = new Locale("fr", "FR");
        printWelcomeMessage(fr);                            // Bonjour, Le zoo est ouvert

    }

    private static void printWelcomeMessage(Locale locale) {
        var rb = ResourceBundle.getBundle("Zoo", locale);
        System.out.println(rb.getString("hello") + ", " + rb.getString("open"));
    }

    public static void iterateResourceBundle() {
        var us = new Locale("en", "US");
        ResourceBundle rb = ResourceBundle.getBundle("Zoo", us);
        rb.keySet().stream()
                .map(k -> k + ": " + rb.getString(k))
                .forEach(System.out::println);
    }

    /**
     * Java handles the logic of picking the best available resource bundle for a given key. It tries to find the most
     * specific value. The next table shows what Java goes through when asked for resource bundle Zoo with the locale
     * new Locale("fr", "FR") when the default locale is U.S. English.
     *
     * *****************************************************************************************|
     * Step     | Looks for file             | Reason                                           |
     * *********|****************************|**************************************************|
     * 1        | Zoo_fr_FR.properties       | The requested locale.                            |
     * ---------|----------------------------|--------------------------------------------------|
     * 2        | Zoo_fr.properties          | The language we requested with no country.       |
     * ---------|----------------------------|--------------------------------------------------|
     * 3        | Zoo_en_US.properties       | The default locale.                              |
     * ---------|----------------------------|--------------------------------------------------|
     * 4        | Zoo_en.properties          | The default locale's language with no country.   |
     * ---------|----------------------------|--------------------------------------------------|
     * 5        | Zoo.properties             | No locale at all—the default bundle.             |
     * ---------|----------------------------|--------------------------------------------------|
     * 6        | If still not found, throw  | No locale or default bundle available.           |
     *          | MissingResourceException.  |                                                  |
     * ---------|----------------------------|--------------------------------------------------|
     *
     * What is the maximum number of files that Java would need to consider finding the appropriate resource bundle
     * with the following code?
     */
    public static void pickingResourceBundle() {
        Locale.setDefault(new Locale("en"));
        ResourceBundle rb = ResourceBundle.getBundle("Zoo", new Locale("hi"));
        System.out.println(rb.getString("hello") + ", " + rb.getString("open"));    // Hello, The zoo is open.
        // The answer is three:
        // 1. Zoo_hi.properties
        // 2. Zoo_en.properties
        // 3. Zoo.properties
    }

    /**
     * NOTE: Java isn't required to get all the keys from the same resource bundle. It can get them from any parent of
     * the matching resource bundle. A parent resource bundle in the hierarchy just removes components of the name until
     * it gets to the top. The next table shows how to do this.
     *
     * -----------------------------------------------------------------|
     * Matching resource bundle  | Properties files keys can come from  |
     * --------------------------|--------------------------------------|
     * Zoo_fr_FR                 | Zoo_fr_FR.properties                 |
     *                           | Zoo_fr.properties                    |
     *                           | Zoo.properties                       |
     * --------------------------|--------------------------------------|
     *
     * Assume the requested locale is fr_FR and the default is en_US. The JVM will provide data from an en_US only if
     * there is no matching fr_FR or fr resource bundles. If it finds a fr_FR or fr resource bundle, then only those
     * bundles, along with the default bundle, will be used.
     *
     * Let's put all of this together and print some information about our zoos. We have a number of properties files
     * this time.
     */
    public static void selectingValues() {
        Locale.setDefault(new Locale("en", "US"));
        Locale locale = new Locale("en", "CA");
        ResourceBundle rb = ResourceBundle.getBundle("Zoo", locale);
        System.out.print(rb.getString("hello"));        // Hello
        System.out.print(". ");
        System.out.print(rb.getString("name"));         // Vancouver Zoo
        System.out.print(" ");
        System.out.print(rb.getString("open"));         // The zoo is open
        System.out.print(" ");
        System.out.println(rb.getString("visitors"));   // Canada visitors

        // What if a property is not found in any resource bundle? Then, an exception is thrown. For example, attempting
        // to call rb.getString("close") in the previous program results in a MissingResourceException at runtime.
    }

    /**
     * Suppose that we had this property defined:
     *
     *      helloByName = Hello, {0} and {1}
     *
     * In Java, we can read in the value normally. After that, we can run it through the MessageFormat class to
     * substitute the parameters. The second parameter to format() is a vararg, allowing you to specify any number of
     * input values.
     */
    public static void formattingMessages() {
        ResourceBundle rb = ResourceBundle.getBundle("Zoo");
        String message = rb.getString("helloByName");
        System.out.println(MessageFormat.format(message, "Tammy", "Henry"));    // Hello, Tammy and Henry.
    }
}
