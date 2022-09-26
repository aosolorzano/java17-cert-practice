package com.hiperium.java.cert.chapters.tests._8;

import org.junit.Assert;
import org.junit.Test;

class Arachnid {
    static StringBuilder sb = new StringBuilder();

    {
        sb.append("c");
    }

    static {
        sb.append("u");
    }

    {
        sb.append("r");
    }

    public Arachnid(int t) {
    } // No default constructor. Must be called from inhered class.
}

class Scorpion extends Arachnid {
    static {
        sb.append("q");
    }

    {
        sb.append("m");
    }

    Scorpion() {
        super(1);
    }

    public static void main(String[] args) {
        System.out.print(Scorpion.sb + " "); // uq
        System.out.print(Scorpion.sb + " "); // uq uq
        new Arachnid(1);                   // uqcr
        new Scorpion();                      // uqcrcrm
        System.out.print(Scorpion.sb);       // uq uq uqcrcrm
    }
}

/**
 * What is the result of the following?
 */
public class Question15Test {

    /**
     * We need to run the code in the main method of the Scorpion class, otherwise, the output here
     * will be different.
     */
    @Test
    public void test() {
        Scorpion.main(null);
        Assert.assertTrue(true);
    }
}
