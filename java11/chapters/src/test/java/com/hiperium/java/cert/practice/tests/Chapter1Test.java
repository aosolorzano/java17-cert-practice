package com.hiperium.java.cert.practice.tests;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Working with Java Data Types:
 *
 *     - Use primitives and wrapper classes, including, operators, parentheses, type promotion and casting.
 *     - Handle text using String and StringBuilder classes.
 *     - Use local variable type inference, including as lambda parameters.
 */
public class Chapter1Test {

    /**
     * Which of the following are not valid variable names?
     */
    @Test
    public void question1() {
        // int _;
        // int 2blue;
        int blue, Blue, blue2;
        int blue$, $blue, $, $$, $blue$;
        int _blue, blue_, __, _blue_;
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following?
     */
    @Test
    public void question4() {
        var b = "12";
        b += "3";
        // b.reverse(); ERROR: Cannot resolve method 'reverse' in 'String'.
        System.out.println(b.toString());
        this.question3Fixed();
        Assert.assertTrue(true);
    }

    private void question3Fixed() {
        var b = new StringBuilder("12");
        b.append("3");
        b.reverse();
        System.out.println(b.toString());
    }

    /**
     * What is the output of the following?
     */
    @Test
    public void question5() {
        var line = new StringBuilder("-");
        var anotherLine = line.append("-");
        System.out.print(line == anotherLine);
        System.out.print(" ");
        System.out.println(line.length());  // PRINT: true 2
        this.question5Practice(line);
        Assert.assertTrue(true);
    }

    private void question5Practice(StringBuilder line) {
        StringBuilder anotherLine = new StringBuilder(line.toString());
        System.out.println("anotherLine: " + anotherLine);
        System.out.println(line == anotherLine);         // false
        System.out.println(line.equals(anotherLine));    // false: equals() do not compare the values on StringBuilder objects.
        System.out.println(line.compareTo(anotherLine)); // 0    : Compares the objects lexicographically.
    }

    @Test
    public void question7() {
        var line = new String("-");
        var anotherLine = line.concat("-");
        System.out.print(line == anotherLine);
        System.out.print(" ");
        System.out.print(line.length());    // PRINT: false 1
        Assert.assertTrue(true);
    }

    /**
     * Which can fill in the blank? (Choose two.)
     */
    @Test
    public void question8() {
        // byte pi = 3.14;  COMPILATION ERROR
        double pi = 3.14;
        // float pi = 3.14; COMPILATION ERROR: it needs an 'f' letter at the end of the number to create a float.
        // short pi = 3.14; COMPILATION ERROR
        var pi2 = 3.14;
        Assert.assertTrue(true);
    }

    /**
     * The author of this method forgot to include the data type. Which of the following reference types can best fill in
     * the blank to complete this method? (parameter type in secret() method)
     */
    @Test
    public void question11() {
        StringBuilder mystery = new StringBuilder("its a mystery...");
        // secret(new String("its a mystery..."));  ERROR: String class does not have the insert() method.
        secret(mystery);
        Assert.assertTrue(true);
    }

    private static void secret(StringBuilder mystery) {
        char ch = mystery.charAt(3);
        mystery = mystery.insert(1, "more");
        int num = mystery.length();
    }

    /**
     * Which of the following declarations does not compile?
     */
    @Test
    public void question13() {
        // double num1, int num2 = 0;
        int num1, num2;
        int num3, num4 = 0;
        int num5 = 0, num6 = 0;
        // System.out.println(num1); ERROR: Variable 'num1' might not have been initialized.
        Assert.assertTrue(true);
    }

    /**
     * Which of the following can fill in the blank so the code prints true?
     */
    @Test
    public void question16() {
        var happy = " :) - (: ";
        System.out.println("happy.length = " + happy.length());
        var really = happy.trim();
        System.out.println("really.length = " + really.length());
        var question = happy.substring(1, happy.length() -1);
        System.out.println(really.equals(question));
        Assert.assertTrue(true);
    }

    /**
     * How many of the following lines contain a compiler error?
     */
    @Test
    public void question17() {
        double num1 = 2.718;
        // double num2 = 2_.718;    COMPILATION ERROR
        // double num2 = 2._718;    COMPILATION ERROR
        double num3 = 2.7_1_8;
        // double num4 = _2.718;    COMPILATION ERROR
        // double num4 = 2.718_;    COMPILATION ERROR
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following class?
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void question19() {
        var builder = "54321";
        builder = builder.substring(4);
        System.out.println("new builder: " + builder);                  // PRINTS: 1
        System.out.println("builder.charAt(2): " + builder.charAt(2));  // Throws IndexOutOfBoundsException
    }

    /**
     * What is the output of the following application?
     */
    @Test
    public void question20() {
        int init = 11;
        int split = 3;
        int partA = init / split;
        System.out.println("partA = " + partA);
        int partB = init % split;
        System.out.println("partB = " + partB);
        int result = split * (partB + partA);
        System.out.println("result: " + result);
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following code?
     */
    @Test
    public void question21() {
        // var sb = new StringBuilder("radical").insert(sb.length(), "robots"); ERROR: Variable 'sb' might not have been initialized.
        var sb = new StringBuilder("radical");
        sb.insert(sb.length(), "robots");
        System.out.println(sb);
        Assert.assertTrue(true);
    }

    /**
     * Given the following code snippet, what is the value of dinner after it is executed?
     */
    @Test
    public void question22() {
        int time = 9;
        int day = 3;
        var dinner = ++time >= 10 ? day-- <= 2 ? "Takeout" : "Salad" : "Leftovers";
        System.out.println("dinner: " + dinner); // PRINTS: Salad
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following code snippet?
     */
    @Test
    public void question25() {
        int height = 2, length = 3;
        boolean w = height > 1 | --length < 4;
        var x = height != 2 ? length++ : height;
        boolean z = height % length == 0;
        System.out.println(w + "-" + x + "-" + z);
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following code snippet?
     */
    @Test
    public void question26() {
        var sb = new StringBuilder();
        sb.append("red");
        sb.deleteCharAt(0);     // sb = ed
        sb.delete(1, 2);
        System.out.println(sb); // PRINTS: e
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following code snippet?
     */
    @Test
    public void question28() {
        boolean carrot = true;
        Boolean potato = false;
        var broccoli = true;
        carrot = carrot & potato;               // false
        broccoli = broccoli ? !carrot : potato; // true
        potato = !broccoli ^ carrot;            // false: The XOR operator returns true if the operands are different.
        System.out.println(carrot + "," + potato + "," + broccoli); // PRINTS: false, false, true
        Assert.assertTrue(true);
    }

    /**
     * What does this code output?
     */
    @Test
    public void question29() {
        var babies = Arrays.asList("chick", "cygnet", "duckling");
        babies.replaceAll(x -> { var newValue = "baby"; return newValue; });
        System.out.println(babies); // PRINTS: [baby, baby, baby]
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following class?
     */
    @Test
    public void question30() {
        var builder = new StringBuilder("54321");
        builder.substring(2);              // Returns a new String that contains a subsequence of characters.
        System.out.println(builder.charAt(1));  // PRINTS: 4
        Assert.assertTrue(true);
    }
}
