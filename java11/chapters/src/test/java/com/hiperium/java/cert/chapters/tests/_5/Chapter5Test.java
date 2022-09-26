package com.hiperium.java.cert.chapters.tests._5;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Chapter5Test {

    /**
     * What is output by the following code?
     */
    @Test
    public void question2() {
        var s = "Hello";
        var t = new String(s);
        if ("Hello".equals(s)) System.out.println("one");      // EQUALS
        if (t == s) System.out.println("two");
        if (t.intern() == s) System.out.println("three");      // EQUALS
        if ("Hello" == s) System.out.println("four");          // EQUALS
        if ("Hello".intern() == t) System.out.println("five");
    }

    /**
     * Which of the following return the number 5 when run independently?
     * <p>
     * REMEMBER: end index param of replace() function must be subtracted 1.
     * charAt(number) function throws IndexOutOfBoundsException if 'number' param is greater than string length.
     */
    @Test
    public void question7() {
        var string = "12345";
        var builder = new StringBuilder("12345");
        System.out.println("A. builder.charAt(4) = " + builder.charAt(4));  // OK
        System.out.println("B. builder.replace(2, 4, '6').charAt(3) = " + builder.replace(2, 4, "6").charAt(3));  // OK
        System.out.println("C. builder.replace(2, 5, '6').charAt(2) = " + builder.replace(2, 5, "6").charAt(2));
        System.out.println("D. string.charAt(5) = THROWS IndexOutOfBoundsException");
        System.out.println("E. string.length = " + string.length());  // ERROR: length its a method and needs parenthesis
        System.out.println("F. string.replace(\"123\", \"1\").charAt(2) = "
                + string.replace("123", "1").charAt(2));
    }

    /**
     * What is output by the following code?
     * <p>
     * REMEMBER: end index param of substring() function must be subtracted 1.
     * substring() function can be applied to StringBuilder objects too.
     */
    @Test
    public void question8() {
        String numbers = "012345678";
        System.out.println("numbers.substring(1, 3) = " + numbers.substring(1, 3)); // 12
        System.out.println("numbers.substring(7, 7) = " + numbers.substring(7, 7)); // BLANK
        System.out.println("numbers.substring(7) = " + numbers.substring(7));       // 78
    }

    /**
     * Which of these statements are true?
     */
    @Test
    public void question10() {
        var letters = new StringBuilder("abcdefg");
        System.out.println("substring(1, 2) return single character: " + letters.substring(1, 2));
        System.out.println("substring(2, 2) return empty string: " + letters.substring(2, 2));
        // System.out.println(letters.substring(6, 5)); StringIndexOutOfBoundsException
    }

    /**
     * What is output by the following code?
     */
    @Test
    public void question11() {
        StringBuilder numbers = new StringBuilder("0123456789");
        numbers.delete(2, 8);
        numbers.append("-").insert(2, "+");
        System.out.println(numbers); // 01+89-
    }

    /**
     * What is the result of the following code?
     */
    @Test
    public void question12() {
        // StringBuilder b = "rumble"; COMPILATION ERROR AT THIS LINE
        StringBuilder b = new StringBuilder("rumble");
        b.append(4).deleteCharAt(3).delete(3, b.length() - 1);
        System.out.println(b); // rum4
    }

    /**
     * Which of the following can replace line 93 to print "avaJ"?
     */
    @Test
    public void question13() {
        // StringBuilder b = "rumble"; COMPILATION ERROR AT THIS LINE
        StringBuilder puzzle = new StringBuilder("Java");
        // INSERT CODE HERE
        puzzle.reverse();  // =====> OK
        // puzzle.append("vaJ$").substring(0, 4); // =====> substring(0, 4) doesn't change the string value
        // puzzle.append("vaJ$").delete(0, 3).deleteCharAt(puzzle.length() - 1); // =====> OK
        // puzzle.append("vaJ$").delete(0, 3).deleteCharAt(puzzle.length());     // =====> StringIndexOutOfBoundsException
        System.out.println(puzzle);
    }

    /**
     * Which of the lines contain a compiler error?
     */
    @Test
    public void question20() {
        double one = Math.pow(1, 2);
        // int two = Math.round(1.0);    ERROR: ROUND OF DOUBLE RETURNS LONG
        int two = Math.round(1.0f);      // OK ===> ROUND OF FLOAT RETURNS INT
        // float three = Math.random();  ERROR: RANDOM RETURNS DOUBLE
        double three = Math.random();
        var doubles = new double[]{one, two, three};

        String[] names = {"Tom", "Dick", "Harry"};
        // List<String> list = names.asList();         ERROR: asList() its part of UsingArrays utility class
        List<String> list = Arrays.asList(names);      // OK ===> List.of(names) its another option
        var other = Arrays.asList(names);  // OK
        System.out.println("Initial var list: " + other);
        other.set(0, "Sue");   // ALLOWED
        System.out.println("Var list after set a value: " + other);
        // other.add("Test");  UnsupportedOperationException ===> 'other' its a fixed-size list backed by 'names' array
    }

    /**
     * What is the result of the following?
     */
    @Test
    public void question21() {
        List<String> hex = Arrays.asList("30", "8", "3A", "FF");
        Collections.sort(hex);
        System.out.println("Ordered Array List: " + hex); // [30, 3A, 8, FF]
        int x = Collections.binarySearch(hex, "8");
        int y = Collections.binarySearch(hex, "3A");
        int z = Collections.binarySearch(hex, "4F");
        System.out.println(x + " " + y + " " + z);        // 2 1 -3 ===> '4F' will be in the 2 position of the array
    }

    /**
     * Which of the following are true statements about the following code?
     */
    @Test(expected = NullPointerException.class)
    public void question22() {
        List<Integer> ages = new ArrayList<>();
        ages.add(Integer.parseInt("5"));
        ages.add(Integer.valueOf("6"));
        ages.add(7);
        ages.add(null);
        for (int age : ages)  // ERROR: NPE because we trying to assign a null value into an int number
            System.out.print(age);
    }
}
