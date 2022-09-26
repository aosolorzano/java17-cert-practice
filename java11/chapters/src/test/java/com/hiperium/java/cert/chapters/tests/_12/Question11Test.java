package com.hiperium.java.cert.chapters.tests._12;

import org.junit.Assert;
import org.junit.Test;

interface CanWalk {
    default void walk() {
        System.out.println("CanWalk walk() default implementation...");
    }
    private void testWalk() {}
}

interface CanRun {
    abstract public void run();
    private void testWalk() {}
    default void walk() {
        System.out.println("CanRun walk() default implementation...");
    }
}

/**
 * The method walk() has the same signature in the 2 previous interfaces, and we need to specify here
 * our own method implementation to avoid duplication problems.
 */
interface CanSprint extends CanWalk, CanRun {
    void sprint();
    default void walk(int speed) {  //===>>> This is an overloaded version of method walk().
        System.out.println("CanSprint walk() overloading default implementation with speed: " + speed);
    }
    private void testWalk() {}

    @Override
    default void walk() {           // REQUIRED OVERRIDING.
        System.out.println("CanSprint walk() default implementation...");
    }
}

public class Question11Test implements CanSprint {

    @Test
    public void question11() {
        CanWalk canWalk = new CanWalk() {
            @Override
            public void walk() {
                CanWalk.super.walk();
            }
        };
        canWalk.walk();
        CanRun canRunAnonymous = new CanRun() {
            @Override
            public void run() {
                System.out.println("Question11 run() anonymous...");
            }
            @Override
            public void walk() {
                System.out.println("Question11 walk() anonymous...");
            }
        };
        canRunAnonymous.run();
        canRunAnonymous.walk();
        this.walk();
        Assert.assertTrue(true);
    }

    @Override
    public void sprint() { // REQUIRED OVERRIDING.
        System.out.println("Question11 sprint() implementation...");
    }

    @Override
    public void run() {    // REQUIRED OVERRIDING.
        System.out.println("Question11 run() implementation...");
    }

    @Override
    public void walk() {   // NOT REQUIRED OVERRIDING.
        System.out.println("Question11 walk() implementation with interface calling:");
        CanSprint.super.walk();
        CanSprint.super.walk(20);
    }
}
