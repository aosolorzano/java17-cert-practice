package com.hiperium.java.cert.practice.tests;

import org.junit.Assert;
import org.junit.Test;

/**
 * Controlling Program Flow:
 *
 *      - Create and use loops, if/else, and switch statements
 */
public class Chapter2Test {

    /**
     * What is output by the following?
     *
     * F. None of the above.
     */
    public void question3() {
        int m = 0, n = 0;
        while (m < 5) {
            n++;
            if (m == 3) continue;   // NOTE: This line make the code to run indefinitely whe "m" gets the value of 3.
            switch (m) {
                case 0:
                case 1:
                    n++;
                default:
                    n++;
            }
            m++;    // NOTE: When "m" gets the value of 3, the loop continues with "m" never incremented again.
        }
        System.out.println("m = " + m + ", n = " + n);
    }

    /**
     * Given the following, which can fill in the blank and allow the code to compile?
     */
    @Test
    public void question4() {
        // var quest = new int[]{2};       VALID: an int array with 1 element of value 2.
        // var quest = new int[2]{1,2};    ERROR: cannot create a fixed array and initialized it at the same time.
        // var quest = List.of(3);         VALID: an unmodifiable List of integers with 1 element of value 3.
        // var quest = new Integer[4];     VALID: an array of Integer objects with 3 null elements.
        var quest = new String[3];      // VALID: an array of Strings objects with 3 null elements.
        for (var zelda : quest) {
            System.out.println(zelda);
        }
        Assert.assertTrue(true);
    }

    /**
     * Given the following, which can fill in the blank and allow the code to compile? (Choose three.)
     */
    @Test
    public void question8() {
        var plan = 1;
        plan = plan++ + --plan;                             // First execute the plan++, then --plan, and finally sum.
        System.out.println("plan = " + plan);               // PRINT: 2
        if (plan == 1) {
            System.out.print("Plan A");
        } else {
            if (plan == 2) System.out.print("Plan B");
        } // else System.out.print("Plan C"); }             ERROR: An 'else' without 'if'.
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following application?
     */
    @Test
    public void question13() {
        int pterodactyl = 8;
        long triceratops = 3;
        boolean condition = pterodactyl % 3 > 1 + 1;    // FALSE
        if (condition)
            triceratops++;
            triceratops--;
        System.out.print(triceratops);                  // PRINTS: 2
        Assert.assertTrue(true);
    }

    /**
     * How many lines of the magic() method contain compilation errors?
     */
    public void question15() {
        do {
            int trick = 0;
            LOOP: do {
                trick++;
         // } while (trick < 2--);      ERROR: Variable expected (2-- it is not allowed).
            } while (trick < 2);
         // continue LOOP;              ERROR: Undefined label: 'LOOP' (at this point).
        } while (1 > 2);
     // System.out.println(trick);      ERROR: Cannot resolve symbol 'trick'.
    }

    /**
     * Which of the following statements compile and create infinite loops at runtime? (Choose three.)
     */
    public void question18() {
        while (!false) {}    // VALID: starts an infinity loop right here.
        // do {} while (true);  VALID: infinity loop.
        // for ( ; ; ) {}       VALID: infinity loop
        // do {}                ERROR: 'while' expected.
        // for(:) {}            ERROR: Statement expected.
        // while {}             ERROR: Condition parentheses expected.
    }

    /**
     * What is the output of the following application?
     */
    @Test
    public void question20() {
        int count = 0, iterator = 0;
        var stops = new String[]{"Washington", "Monroe", "Jackson", "LaSalle"};
        while (count < stops.length) {
            iterator = stops[++count].length();           // gets the length of the string at index 1 in the array.
            System.out.println("iterator = " + iterator); // PRINTS: 6 ("Monroe").
            if (iterator < 8)
                break;                                    // ends the while loop.
            else continue;
        }
        System.out.println("count = " + count);           // PRINTS: 1
        Assert.assertTrue(true);
    }

    /**
     * The code contains six pairs of curly braces. How many pairs can be removed without changing the behavior?
     * <p>
     * R./ Only 3 pairs for the following statements can be removed: 'for', 'while' and 'if'.
     *     The switch statement requires a pair of brackets.
     *     The code runs indefinitely.
     */
    public void question28() {
        int secret = 0;
        for (int i = 0; i < 10; i++)
            while (i < 10)
                if (i == 5)
                    System.out.println("if");
                else {
                    System.out.println("in");
                    System.out.println("else");
                }
        switch (secret) {
            case 0: System.out.println("zero");
        }
        Assert.assertTrue(true);
    }
}
