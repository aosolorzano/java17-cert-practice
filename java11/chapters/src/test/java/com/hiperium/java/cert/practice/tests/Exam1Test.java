package com.hiperium.java.cert.practice.tests;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

public class Exam1Test {

    /**
     * How many of these variables are true?
     *
     * NOTE: "smiley" and "smirk" are both false, since a String should be compared with a method, rather than "==",
     * specially when not comparing 2 values from the String pool.
     * The variables "wink" and "yarn" are true because they do not look at the case.
     */
    @Test
    public void question25() {
        var lol = "lol";
        var smiley = lol.toUpperCase() == lol;
        var smirk = lol.toUpperCase() == lol.toUpperCase();
        var blush = lol.toUpperCase().equals(lol);
        var cool = lol.toUpperCase().equals(lol.toUpperCase());
        var wink = lol.toUpperCase().equalsIgnoreCase(lol);
        var yawn = lol.toUpperCase().equalsIgnoreCase(lol.toUpperCase());

        System.out.println("smiley = " + smiley);   // PRINT: false
        System.out.println("smirk = " + smirk);     // PRINT: false
        System.out.println("blush = " + blush);     // PRINT: false
        System.out.println("cool = " + cool);       // PRINT: true
        System.out.println("wink = " + wink);       // PRINT: true
        System.out.println("yawn = " + yawn);       // PRINT: true
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 50:
     *
     * What is the output of the following.
     */
    static class Goat {
        private String foot;
        public Goat(String foot) { this.foot = foot; }
        public String getFoot() { return foot;}
        public void setFoot(String foot) { this.foot = foot; }
        @Override
        public String toString() { return "Goat{foot='" + foot + '\'' + '}'; }
    }
    @Test
    public void question50() {
        var goats = List.of(
                new Goat("can"),
                new Goat("hay"),
                new Goat("shorts"),
                new Goat("hay")
        );
        goats.stream()
                .collect(Collectors.groupingBy(Goat::getFoot))
                .entrySet()
                .stream()
                .peek(e -> System.out.println("Entry = " + e))
        //       hay=[Goat{foot='hay'}, Goat{foot='hay'}], can=[Goat{foot='can'}], shorts=[Goat{foot='shorts'}]
                .filter(e -> e.getValue().size() == 2)
                .map(e -> e.getKey())
                .peek(s -> System.out.println("mapped = " + s))                         // PRINT: hay
                .collect(Collectors.partitioningBy(e -> e.isEmpty()))
                .get(false)
                .stream()
                .peek(s -> System.out.println("collected 'false' from map = " + s))     // PRINT: hay
                .sorted()
                .forEach(System.out::println);                                          // PRINT: hay
        Assert.assertTrue(true);
    }
}
