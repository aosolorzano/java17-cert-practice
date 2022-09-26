package com.hiperium.java.cert.chapters.tests._8;

import org.junit.Assert;
import org.junit.Test;

class Arthropod {
    protected void printName(long input) {
        System.out.println("Arthropod");
    }

    void printName(int input) {
        System.out.println("Spooky");
    }
}

class Spider extends Arthropod {
    /*
    ** If we uncomment the following method, line 35 will call this method instead.
    **
    @Override
    protected void printName(long input) {
        System.out.println("Spider long");
    } */
    @Override // the int version of parent class
    protected void printName(int input) {
        System.out.println("Spider integer");
    }
}

/**
 * What is the output of the following code?
 */
public class Question5Test {
    @Test
    public void test() {
        Arthropod a = new Spider();
        a.printName((short) 4); // Spider integer
        a.printName(4);  // Spider integer
        a.printName(5L); // Arthropod
        Assert.assertTrue(true);
    }
}
