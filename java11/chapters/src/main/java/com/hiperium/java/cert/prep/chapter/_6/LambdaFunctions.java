package com.hiperium.java.cert.prep.chapter._6;

import java.util.function.Predicate;

interface Climb {
    boolean isTooHigh(int height, int limit);
}

public class LambdaFunctions {

    int age;

    public static void main(String[] args) {
        LambdaFunctions lambdaFunction = new LambdaFunctions();
        lambdaFunction.age = 1;
        check(lambdaFunction, l -> l.age < 5);
        // checkClimb((h, m) -> h.append(m).isEmpty(), 5);  ===> ERROR: Cannot resolve method 'append(int)'
        checkClimb((h, l) -> {
            return h > l;
        }, 5);
    }

    private static void check(LambdaFunctions lambdaFunction, Predicate<LambdaFunctions> pred) {
        String result = pred.test(lambdaFunction) ? "match" : "not match";
        System.out.println(result);
    }

    private static void checkClimb(Climb climb, int height) {
        if (climb.isTooHigh(height, 10)) {
            System.out.println("Is too high!!");
        } else {
            System.out.println("Its OK!!");
        }
    }
}
