package com.hiperium.java.cert.chapters.tests._12;

import org.junit.Assert;
import org.junit.Test;

enum Flavors {
    VANILLA, CHOCOLATE, STRAWBERRY;           // In this case, a semicolon is needed.
    static final Flavors DEFAULT = CHOCOLATE; // DEFAULT is not part of the enumeration values, but we can access to it.
}

class Movie {
    private int butter = 5;
    private Movie() {}
    protected class Popcorn {
        // public static int butter = 10; ERROR ===>>> Static declarations in inner classes are not supported.
        private Popcorn() {}
        public void startMovie() { System.out.println(butter); }
    }
    public static void run() {
        var movie = new Movie();
        Movie.Popcorn in = new Movie().new Popcorn();
        in.startMovie();
    }
}

public class Chapter12Test {
    @Test
    public void question2() {
        for (final var e : Flavors.values()) {
            System.out.println(e + " " + e.ordinal());
        }
        Assert.assertTrue(true);
    }

    @Test
    public void question3() {
        Movie.run();
        Assert.assertTrue(true);
    }

    @Test(expected = NullPointerException.class)
    public void question7() {
        Flavors STRAWBERRY = null;
        switch (STRAWBERRY) {
            case VANILLA: System.out.print("v");
            case CHOCOLATE: System.out.print("c");
            case STRAWBERRY: System.out.print("s");
                break;
            default: System.out.println("missing flavor");
        }
    }

    @Test
    public void fixedQuestion7() {
        Flavors STRAWBERRY = Flavors.DEFAULT; // CHOCOLATE
        System.out.println("It begin at CHOCOLATE and continue until STRAWBERRY.");
        switch (STRAWBERRY) {
            case VANILLA: System.out.println("v");
            case CHOCOLATE: System.out.println("c");
            default: System.out.println("missing flavor");
            case STRAWBERRY: System.out.println("s");
                break;
        }
        STRAWBERRY = Flavors.VANILLA;
        System.out.println("Now it begin at the default case and continue to STRAWBERRY.");
        switch (STRAWBERRY) {
            case CHOCOLATE: System.out.println("c");
            default: System.out.println("missing flavor");
            case STRAWBERRY: System.out.println("s");
                break;
        }
        Assert.assertTrue(true);
    }

    private interface Sing {
        boolean isTooLoud(int volume, int limit);
    }

    @Test
    public void question12(){
        // check((h, l) -> h.toString(), 5); ERROR 1: primitive int 'h' does not have toString() method.
        // check((h, l) -> h.toString(), 5); ERROR 2: if 'h' was Integer object, lambda expression must return boolean.
        check((var, l) -> { return var > l; }, 5);
    }

    private void check(Sing sing, int volume) {
        if (sing.isTooLoud(volume, 10)) {
            System.out.println("Not so great");
        } else {
            System.out.println("great");
        }
    }

    private enum Food { APPLES, BERRIES, GRASS }
    protected class Diet {
        private Food getFavorite() { return Food.BERRIES; }
    }

    @Test
    public void question14() {
        runQuestion14();
        Assert.assertTrue(true);
    }

    private static void runQuestion14() {
        // switch (new Diet().getFavorite()) {                  ERROR: Chapter12Test.this cannot be referenced from a static context.
        switch (new Chapter12Test().new Diet().getFavorite()) { // OK: getFavorite() return an enumerated value (constant).
            case APPLES:
                System.out.println("a");
            case BERRIES:
                System.out.println("b");
            default:
                System.out.println("c");
        }
    }
}
