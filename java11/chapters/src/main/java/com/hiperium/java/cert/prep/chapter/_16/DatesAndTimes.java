package com.hiperium.java.cert.prep.chapter._16;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * ----------------------------------------------------------------------------------------------|
 * Class                   | Description                              | Example                  |
 * ------------------------|------------------------------------------|--------------------------|
 * java.time.LocalDate     | Date with day, month, year.              | Birth date.              |
 * ------------------------|------------------------------------------|--------------------------|
 * java.time.LocalTime     | Time of day.                             | Midnight.                |
 * ------------------------|------------------------------------------|--------------------------|
 * java.time.LocalDateTime | Day and time with no time zone.          | 10 a.m. next Monday.     |
 * ------------------------|------------------------------------------|--------------------------|
 * java.time.ZonedDateTime | Date and time with a specific time zone. | 9 a.m. EST on 2/20/2021. |
 * ------------------------|------------------------------------------|--------------------------|
 */
public class DatesAndTimes {

    /**
     * Each of these types contains a static method called now() that allows you to get the current value.
     * The third method contains both a date and a time. Java uses 'T' to separate the date and time when converting
     * LocalDateTime to a String.
     */
    public static void main(String[] args) {
        System.out.println(LocalDate.now());        // PRINT: 2021-10-09
        System.out.println(LocalTime.now());        // PRINT: 17:31:38.523819
        System.out.println(LocalDateTime.now());    // PRINT: 2021-10-09T17:31:38.523908
        System.out.println(ZonedDateTime.now());    // PRINT: 2021-10-09T17:31:38.524505-05:00[America/Guayaquil]

        // The date and time classes support many methods to get data out of them.
        LocalDate date = LocalDate.of(2021, Month.OCTOBER, 9);
        System.out.println("Day of the week: " + date.getDayOfWeek());      // SATURDAY
        System.out.println("Month: " + date.getMonth());                    // OCTOBER
        System.out.println("Year: " + date.getYear());                      // 2021
        System.out.println("Day of the year: " + date.getDayOfYear());      // 282

        ofMethod();
        formattingDatesAndTimes();
    }

    public static void ofMethod() {
        // We can create some date and time values using the of() methods in each class.
        LocalDate date1 = LocalDate.of(2020, Month.DECEMBER, 20);
        LocalDate date2 = LocalDate.of(2020, 10, 20);
        System.out.println("Date1: " + date1);      // PRINT: 2020-12-20
        System.out.println("Date2: " + date2);      // PRINT: 2020-10-20

        // When creating a time, we can choose how detailed you want to be.
        LocalTime time1 = LocalTime.of(6, 15);                                  // PRINT: 06:15
        LocalTime time2 = LocalTime.of(6, 15, 30);                       // PRINT: 06:15:30
        LocalTime time3 = LocalTime.of(6, 15, 30, 200);     // PRINT: 06:15:30.000000200
        System.out.println("Time1: " + time1);
        System.out.println("Time2: " + time2);
        System.out.println("Time3: " + time3);

        // We can combine dates and times in multiple ways.
        var dt1 = LocalDateTime.of(2020, Month.DECEMBER, 20, 6, 15, 30);
        var dt2 = LocalDateTime.of(date2, time3);
        System.out.println("DateTime1: " + dt1);        // PRINT: 2020-12-20T06:15:30
        System.out.println("DateTime2: " + dt2);        // PRINT: 22020-10-20T06:15:30.000000200
    }

    /**
     * Common date/time symbols:
     * ---------------------------------------------------------|
     *  Symbol  | Meaning          | Examples                   |
     * ---------|------------------|----------------------------|
     *    y     | Year             | 20, 2020                   |
     * ---------|------------------|----------------------------|
     *    M     | Month            | 1, 01, Jan, January        |
     * ---------|------------------|----------------------------|
     *    d     | Day              | 5, 05                      |
     * ---------|------------------|----------------------------|
     *    h     | Hour             | 9, 09                      |
     * ---------|------------------|----------------------------|
     *    m     | Minute           | 45                         |
     * ---------|------------------|----------------------------|
     *    s     | Second           | 52                         |
     * ---------|------------------|----------------------------|
     *    a     | a.m./p.m.        | AM, PM                     |
     * ---------|------------------|----------------------------|
     *    z     | Time Zone Name   | Eastern Standard Time, EST |
     * ---------|------------------|----------------------------|
     *    Z     | Time Zone Offset | ‐0400                      |
     * ---------|------------------|----------------------------|
     */
    public static void formattingDatesAndTimes() {
        // Java provides a class called DateTimeFormatter to display standard formats.
        LocalDate date1 = LocalDate.of(2021, Month.OCTOBER, 9);
        LocalTime time1 = LocalTime.of(18, 30);
        LocalDateTime dt1 = LocalDateTime.of(date1, time1);
        System.out.println("DateTime used for formatting: " + dt1);               // 2021-10-09T18:30
        System.out.println(dt1.format(DateTimeFormatter.ISO_LOCAL_DATE));         // 2021-10-09
        System.out.println(dt1.format(DateTimeFormatter.ISO_LOCAL_TIME));         // 18:30:00
        System.out.println(dt1.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));    // 2021-10-09T18:30:00

        // DateTimeFormatter supports a custom format using a date format String.
        var f = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm");
        System.out.println(dt1.format(f));              // PRINT: October 09, 2021 at 06:30

        var formatter1 = DateTimeFormatter.ofPattern("MM_yyyy_-_dd");
        System.out.println(dt1.format(formatter1));     // PRINT: 10_2021_-_09

        // Using ZoneDateTime
        var formatter2 = DateTimeFormatter.ofPattern("h:mm z");
        try {
            System.out.println(dt1.format(formatter2));
        } catch (DateTimeException e) {
            System.out.println(e.getMessage());                 // Unable to extract ZoneId from temporal.
            // ZoneId zoneId = ZoneId.of("-05:00");                PRINT: 2021-10-09T18:30-05:00
            ZoneId zoneId = ZoneId.of("Europe/London");
            ZonedDateTime zdt = ZonedDateTime.of(dt1, zoneId);
            System.out.println("ZonedDateTime: " + zdt);        // PRINT: 2021-10-09T18:30+01:00[Europe/London]
        }

        // The date/time classes contain a format() method that will take a formatter, while the formatter classes
        // contain a format() method that will take a date/time value. The result is that either of the following
        // is acceptable
        var dateTime3 = LocalDateTime.of(2020, Month.OCTOBER, 20, 6, 15, 30);
        var formatter3 = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss");
        System.out.println("Format from LocalDateTime: " + dateTime3.format(formatter3));       // 10/20/2020 06:15:30
        System.out.println("Format from DateTimeFormatter: " + formatter3.format(dateTime3));   // 10/20/2020 06:15:30

        // What if we want our format to include some custom text values? We can escape the text by surrounding it with
        // a pair of single quotes (''). Escaping text instructs the formatter to ignore the values inside the single
        // quotes and just insert them as part of the final value.
        var formatter4 = DateTimeFormatter
                .ofPattern("MMMM dd, yyyy 'at' hh:mm");
        System.out.println("With escape text: " + dt1.format(formatter4));      // October 09, 2021 at 06:30

        // What if we need to display a single quote in the output too? Java supports this by putting two single quotes
        // next to each other.
        var formatter5 = DateTimeFormatter
                .ofPattern("MMMM dd', Party''s at' hh:mm");
        System.out.println("Formatter5: " + dt1.format(formatter5));            // October 09, Party's at 06:30

        var formatter6 = DateTimeFormatter
                .ofPattern("'System''s format, hh:mm >> 'hh:mm");
        System.out.println("Formatter6: " + dt1.format(formatter6));            // System's format, hh:mm >> 06:30

        var formatter7 = DateTimeFormatter
                .ofPattern("'Happy new 'yyyy 'year!!!'");
        System.out.println("Formatter7: " + dt1.format(formatter7));            // Happy new 2021 year!!!

    }
}
