package com.hiperium.java.cert.prep.chapter._12;

/**
 * A functional interface is an interface that contains a single abstract method. The acronym SAM can
 * help you remember this because it is officially known as a Single Abstract Method (SAM).
 */
@FunctionalInterface
interface Sprint {
    void sprint(int speed);
}

/**
 * This is not a Functional Interface since toString() is a public method implemented in Object,
 * it does not count toward the single abstract method. The same with equals() and hashCode() methods.
 */
interface Soar {
    abstract String toString();
}

public class FunctionalInterfaces implements Sprint {

    public static void main(String[] args) {
        FunctionalInterfaces functionalInterfaces = new FunctionalInterfaces();
        functionalInterfaces.sprint(40);
        Sprint anonymousClass = new Sprint() {
            @Override
            public void sprint(int speed) {
                System.out.println("Anonymous speed: " + speed);
            }
        };
        anonymousClass.sprint(60);
        Sprint lambdaExpression = (var speed) -> System.out.println("Lambda speed: " + speed);
        lambdaExpression.sprint(80);
    }

    @Override
    public void sprint(int speed) {
        System.out.println("Instance speed: " + speed);
    }
}
