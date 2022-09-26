package com.hiperium.java.cert.prep.chapter._16;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * Internationalization is the process of designing your program, so it can be adapted. This involves placing strings
 * in a properties file and ensuring the proper data formatters are used.
 *
 * Localization means actually supporting multiple locales or geographic regions. You can think of a locale as being
 * like a language and country pairing. Localization includes translating strings to different languages. It also
 * includes outputting dates and numbers in the correct format for that locale.
 */
public class I18nAndLocalization {

    public static void main(String[] args) {
        peekingLocale();
        formattingNumbers();
        parsingNumbers();
        customNumberFormatter();
        localizingDates();
        specifyLocaleCategory();
    }

    /**
     * FORMAT (en_EC): first comes the lowercase language code. The language is always required. Then comes an
     * underscore followed by the uppercase country code. The country is optional. In this sense, the format could be:
     * 'en' or 'en_EC' for this example.
     */
    public static void peekingLocale() {
        // Find user's current locale.
        Locale locale = Locale.getDefault();
        System.out.println(locale);             // PRINT: en_EC

        // There are 3 common ways to select a locale other than the default one.
        // The first is to use the built‐in constants in the Locale class, available for some common locales.
        System.out.println(Locale.GERMAN);      // PRINT: de
        System.out.println(Locale.GERMANY);     // PRINT: de_DE

        // The second way of selecting a Locale is to use the constructors to create a new object. You can pass just a
        // language, or both a language and country:
        System.out.println(new Locale("fr"));                   // PRINT: 'fr'
        System.out.println(new Locale("es", "EC"));     // PRINT: es_EC

        // There's a third way to create a Locale that is more flexible. The builder design pattern lets you set all
        // the properties that you care about and then build it at the end.
        Locale ecLocale = new Locale.Builder()
                .setLanguage("en")
                .setRegion("EC")
                .build();
        System.out.println(ecLocale);      // PRINT: en_EC

        // When testing a program, we might need to use a Locale other than the default of our computer.
        System.out.println("Default locale: " + Locale.getDefault());           // PRINT: en_EC
        Locale locale1 = new Locale("fr");
        Locale.setDefault(locale1);
        System.out.println("Getting new locale: " + Locale.getDefault());       // PRINT: 'fr'

        Locale.setDefault(ecLocale);
        System.out.println("Getting back locale: " + Locale.getDefault());       // PRINT: 'en_EC'
    }

    public static void formattingNumbers() {
        int attendeesPerYear = 3_200_00;
        int attendeesPerMonth = attendeesPerYear / 12;

        var numberFormat1 = NumberFormat.getInstance(Locale.US);
        System.out.println(numberFormat1.format(attendeesPerMonth));                    // PRINT: 26,666

        var numberFormat2 = NumberFormat.getInstance(Locale.GERMANY);
        System.out.println(numberFormat2.format(attendeesPerMonth));                    // PRINT: 26.666

        var numberFormat3 = NumberFormat.getInstance(Locale.CANADA_FRENCH);
        System.out.println(numberFormat3.format(attendeesPerMonth));                    // PRINT: 26 666

        // Formatting currency works the same way.
        double price = 48;
        var numberFormat4 = NumberFormat.getCurrencyInstance();
        System.out.println(numberFormat4.format(price));                                // PRINT: $48.00

        var numberFormat5 = NumberFormat.getCurrencyInstance(Locale.UK);
        System.out.println(numberFormat5.format(price));                                // PRINT: £48.00
    }

    /**
     * When we parse data, we convert it from a String to a structured object or primitive value. The NumberFormat.parse()
     * method accomplishes this and takes the locale into consideration.
     */
    public static void parsingNumbers() {
        // The following code parses a discounted ticket price with different locales. The parse() method actually
        // throws a checked ParseException, so make sure to handle or declare it in your own code.
        String discount = "40.45";
        try {
            var numberFormat1 = NumberFormat.getInstance(Locale.US);
            System.out.println(numberFormat1.parse(discount));      // PRINT: 40.45

            var numberFormat2 = NumberFormat.getInstance(Locale.FRANCE);
            System.out.println(numberFormat2.parse(discount));      // PRINT: 40
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }

        // The parse() method is also used for parsing currency.
        String income = "$92,807.99";
        var numberFormat3 = NumberFormat.getCurrencyInstance();
        try {
            double value = (Double) numberFormat3.parse(income);    // PRINT: 92807.99
            System.out.println(value);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * We can also create our own number format strings using the DecimalFormat class, which extends NumberFormat.
     * When creating a DecimalFormat object, we use a constructor rather than a factory method. We pass the pattern that
     * we would like to use.
     *
     * DECIMAL FORMAT SYMBOLS
     * ------------------------------------------------------------------------|
     * Symbol | Meaning                                            | Examples  |
     * -------|----------------------------------------------------|-----------|
     *   #    | Omit the position if no digit exists for it.       | $2.2      |
     *   0    | Put a 0 in the position if no digit exists for it. | $002.20   |
     * -------|----------------------------------------------------|-----------|
     */
    public static void customNumberFormatter() {
        double number = 1234567.467;
        NumberFormat numberFormat1 = new DecimalFormat("###,###,###.0");
        System.out.println(numberFormat1.format(number));   // 1,234,567.5

        NumberFormat numberFormat2 = new DecimalFormat("000,000,000.00000");
        System.out.println(numberFormat2.format(number));   // 001,234,567.46700

        NumberFormat numberFormat3 = new DecimalFormat("$#,###,###.##");
        System.out.println(numberFormat3.format(number));   // $1,234,567.47
    }

    /**
     *
     * Like numbers, date formats can vary by locale.
     *
     * *********************************************************************************************|
     * Description                    | Using default Locale                                        |
     * *******************************|*************************************************************|
     * For formatting dates           | DateTimeFormatter.ofLocalizedDate(dateStyle)                |
     * -------------------------------|-------------------------------------------------------------|
     * For formatting times           | DateTimeFormatter.ofLocalizedTime(timeStyle)                |
     * -------------------------------|-------------------------------------------------------------|
     * For formatting dates and times | DateTimeFormatter.ofLocalizedDateTime(dateStyle, timeStyle) |
     *                                | DateTimeFormatter.ofLocalizedDateTime(dateTimeStyle)        |
     * *******************************|*************************************************************|
     *
     * Each method in the table, takes a FormatStyle parameter, with possible values SHORT, MEDIUM, LONG, and FULL.
     * What if you need a formatter for a specific locale? Easy enough—just append withLocale(locale) to the method call.
     */
    public static void localizingDates() {
        var italy = new Locale("it", "IT");
        var dt = LocalDateTime.of(2021, Month.OCTOBER, 20, 15, 12, 34);
        print(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT), dt, italy);     // 10/20/21, 20/10/21
        print(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT), dt, italy);     // 3:12 PM, 15:12
        print(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT,
                FormatStyle.SHORT), dt, italy);                                     // 10/20/21, 3:12 PM, 20/10/21, 15:12
    }

    private static void print(DateTimeFormatter dtf, LocalDateTime dt, Locale locale) {
        // NOTE: The first part is printed with Default Locale
        System.out.println(dtf.format(dt) + ", " + dtf.withLocale(locale).format(dt));
    }

    /**
     * If you require finer‐grained control of the default locale, Java actually subdivides the underlying formatting
     * options into distinct categories, with the Locale.Category enum which supports distinct locales for displaying
     * and formatting data.
     *
     * -------------------------------------------------------------------------|
     * Value      | Description                                                 |
     * -----------|-------------------------------------------------------------|
     * DISPLAY    | Category used for displaying data about the locale.         |
     * -----------|-------------------------------------------------------------|
     * FORMAT     | Category used for formatting dates, numbers, or currencies. |
     * -----------|-------------------------------------------------------------|
     *
     * NOTE: We just need to know that we can set parts of the locale independently. We should also know that calling
     * Locale.setDefault(us) after the following code, will change both locale categories to en_EC.
     */
    public static void specifyLocaleCategory() {
        var spain = new Locale("es", "ES");
        var money = 1.23;
        printCurrency(spain, money);    // $1.23, Spanish

        Locale.setDefault(Locale.Category.DISPLAY, spain);
        printCurrency(spain, money);    // $1.23, español

        Locale.setDefault(Locale.Category.FORMAT, spain);
        printCurrency(spain, money);    // 1,23 €, español
    }

    private static void printCurrency(Locale locale, double money) {
        System.out.println(NumberFormat.getCurrencyInstance().format(money) + ", " + locale.getDisplayLanguage());
    }
}
