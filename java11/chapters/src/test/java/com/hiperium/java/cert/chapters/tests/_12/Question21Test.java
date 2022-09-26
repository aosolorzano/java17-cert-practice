package com.hiperium.java.cert.chapters.tests._12;

import org.junit.Assert;
import org.junit.Test;

class Zebra {
    private int x = 24;
    public int hunt() {
        String message = "x is ";
        abstract class Stripes {
            private int x = 0;
            public void print() {
                // Variable 'message' needs to be final or effectively final.
                System.out.println(message + Zebra.this.x); // VALID SYNTAX: it accesses to var x = 24 on Zebra object.
            }
        }
        var s = new Stripes() {};
        s.print();
        return x;
    }
}

public class Question21Test {

    @Test
    public void question11() {
        new Zebra().hunt();
        Assert.assertTrue(true);
    }
}
