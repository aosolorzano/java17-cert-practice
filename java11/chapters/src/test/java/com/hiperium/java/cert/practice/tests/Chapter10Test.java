package com.hiperium.java.cert.practice.tests;

import org.junit.Assert;
import org.junit.Test;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

public class Chapter10Test {

    /**
     * QUESTION 21:
     *
     * Which statements about executing the following program are correct?
     *
     * C. The code is not susceptible to tainted inputs from the user.
     * E. The code is not susceptible to an injection attack.
     *
     * NOTE: A functional example can be found at "misc/practice_chapter_10" folder.
     */
    private static class PrintScores {
        private static final String CODE = "12345";
        private static final String SCORES = "test.scores";

        public static String getScores(String accessCode) {
            return AccessController.doPrivileged(new PrivilegedAction<String>() {
                public String run() {
                    if(accessCode.equals(CODE))                         // m1
                        return System.getProperty(SCORES);              // m2
                    throw new SecurityException("Incorrect code");
                }
            });
        }
    }
    @Test
    public void question21() {
        System.out.println("score = " + PrintScores.getScores("12345"));
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 37:
     *
     * The following code prints false. Which statements best describe the Fruit class?
     *
     * B. It performs a deep copy.
     * D. It overrides clone().
     * E. It implements Cloneable.
     */
    private static class Fruit implements Cloneable {
        private List<String> sweet;
        public Fruit() {
            this.sweet = new ArrayList<>();;
        }
        @Override
        public Object clone() {
            Fruit fruit = new Fruit();
            sweet = new ArrayList<>(this.sweet);
            return fruit;
        }
    }
    @Test
    public void question37() {
        var original = new Fruit();
        original.sweet = new ArrayList<>();
        var cloned = (Fruit) original.clone();
        System.out.print(original.sweet == cloned.sweet);
        System.out.println("original.sweet == cloned.sweet ?: " + (original.sweet == cloned.sweet));
        Assert.assertTrue(true);
    }
}
