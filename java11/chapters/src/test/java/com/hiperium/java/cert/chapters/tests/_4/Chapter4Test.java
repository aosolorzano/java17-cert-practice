package com.hiperium.java.cert.chapters.tests._4;

import org.junit.Assert;
import org.junit.Test;

import java.time.DayOfWeek;

public class Chapter4Test {

    /**
     * Which statements, when inserted independently into the following blank, will cause the code to print
     * 2 at runtime?
     */
    @Test
    public void question6() {
        int count = 0;
        BUNNY:
        for (int row = 1; row <= 3; row++)
            RABBIT:for (int col = 0; col < 3; col++) {
                System.out.println("row = " + row + ", col = " + col);
                if ((col + row) % 2 == 0) {
                    System.out.println("BREAK");
                    break RABBIT;   // OPTION 1
                    // continue BUNNY; // OPTION 2
                    // break;          // OPTION 3
                }
                System.out.println("count++");
                count++;
            }
        System.out.println("count = " + count);
        Assert.assertTrue(true);
    }

    /**
     * Given the following method, how many lines contain compilation errors?
     */
    @Test
    public void question7() {
        getWeekDay(1, 3);
        Assert.assertTrue(true);
    }

    private DayOfWeek getWeekDay(int day, final int thursday) {
        int otherDay = day;
        int Sunday = 0;
        switch (otherDay) {
            default:
            case 1: // continue;                               // 1ST ERROR -> Continue outside of loop
                // case thursday: return DayOfWeek.THURSDAY;       // 2ND ERROR -> Constant expression required
            case 2:
                break;
            // case Sunday: return DayOfWeek.SUNDAY;           // 3RD ERROR -> Constant expression required
            // case DayOfWeek.MONDAY: return DayOfWeek.MONDAY; // 4TH ERROR -> Required: int - Provided: enum constant
        }
        return DayOfWeek.FRIDAY;
    }

    /**
     * Which statements, when inserted into the following blanks, allow the code to compile and run without
     * entering an infinite loop?
     */
    @Test
    public void question15() {
        int height = 1;
        L1:
        while (height++ < 10) {
            long humidity = 12;
            L2:
            do {
                if (humidity-- % 12 == 0) break L2; // VALID OPTIONS -> BREAK L2 and CONTINUE L2
                System.out.println("humidity = " + humidity);

                int temperature = 30;
                L3:
                for (; ; ) { // INFINITY LOOP
                    temperature++;
                    if (temperature > 50) continue L2; // VALID OPTIONS -> CONTINUE L2
                    System.out.println("temperature = " + temperature);
                }
            } while (humidity > 4);
        }
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following code snippet?
     * R: 5 2 1
     */
    @Test
    public void question16() {
        var tailFeathers = 3;
        final var one = 1;
        switch (tailFeathers) {
            case one:
                System.out.print(3 + " ");
            default:
            case 3:
                System.out.print(5 + " ");
        }
        while (tailFeathers > 1)
            System.out.print(--tailFeathers + " ");
        Assert.assertTrue(true);
    }
}
