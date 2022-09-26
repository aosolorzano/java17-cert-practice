package com.hiperium.java.cert.prep.chapter._5;

import java.util.ArrayList;
import java.util.List;

public class Equality {

    /**
     * Calling == on String objects will check whether they point to the same object in the pool.
     * Calling equals() on String objects will check whether the sequence of characters is the same.
     * <p>
     * Calling == on StringBuilder objects will check whether they are pointing to the same StringBuilder object in the pool.
     * Calling equals() on StringBuilder objects will check whether they are pointing to the same object rather than
     * looking at the values inside.
     */
    public static void main(String[] args) {
        stringEquality();
        stringBuilderEquality();
        arraysEquality();
    }

    private static void stringEquality() {
        System.out.println("**** STRING EQUALITY ****");
        String textOne = "hawk", textTwo = "robin", textThree = "robin";

        System.out.println("textOne == textTwo? " + (textOne == textTwo));               // FALSE
        System.out.println("textTwo == textThree? " + (textTwo == textThree));           // TRUE

        textThree = new String("robin");
        System.out.println("textTwo == textThree? " + (textTwo == textThree));           // FALSE
        System.out.println("textTwo.equals(textThree)? " + (textTwo.equals(textThree))); // TRUE
    }

    private static void stringBuilderEquality() {
        System.out.println("**** STRING BUILDER EQUALITY ****");
        StringBuilder builderOne = new StringBuilder("hawk");
        StringBuilder builderTwo = new StringBuilder("robin");
        StringBuilder builderThree = new StringBuilder("robin");

        System.out.println("builderOne == builderTwo? " + (builderOne == builderTwo));               // FALSE
        System.out.println("builderTwo == builderThree? " + (builderTwo == builderThree));           // FALSE
        System.out.println("builderTwo.equals(builderThree)? " + (builderTwo.equals(builderThree))); // FALSE

        builderTwo = builderThree;
        System.out.println("builderTwo == builderThree? " + (builderTwo == builderThree));           // TRUE
        System.out.println("builderTwo.equals(builderThree)? " + (builderTwo.equals(builderThree))); // TRUE
        // THE LAST TWO COMPARISONS LOOKUP FOR EQUALITY AT REFERENCE LEVEL RATHER THAN EQUALITY IN ITS CONTENT
        // BECAUSE IT IS ENOUGH TO KNOW THAT TWO VARIABLES MAKE REFERENCE TO THE SAME OBJECT TO CONCLUDE THAT THEY
        // ARE THE SAME AT STRING CONTENT LEVEL
    }

    private static void arraysEquality() {
        List<String> one = new ArrayList<>();
        one.add("abc");
        List<String> two = new ArrayList<>();
        two.add("abc");
        if (one == two)
            System.out.println("A");
        else if (one.equals(two))
            System.out.println("B"); // two lists are defined to be equal if they contain the same elements in the same order.
        else
            System.out.println("C");

    }
}
