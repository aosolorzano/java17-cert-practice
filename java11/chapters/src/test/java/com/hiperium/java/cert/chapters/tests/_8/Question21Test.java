package com.hiperium.java.cert.chapters.tests._8;

import org.junit.Assert;
import org.junit.Test;

class Bird {
    int feathers = 0;

    Bird(int x) {
        this.feathers = x;
    }

    Bird fly() {
        return new Bird(1);
    }
}

class Parrot extends Bird {
    // int feathers = 10;
    protected Parrot(int y) {
        super(y);
    }

    protected Parrot fly() {
        return new Parrot(2);
    }
}

class Macaw extends Parrot {
    public Macaw(int z) {
        super(z);
    }

    public Macaw fly() {
        return new Macaw(3);
    }
}

/**
 * Which statement about the following program is correct?
 * <p>
 * R./ The program compiles and prints 3.
 */
public class Question21Test {
    @Test
    public void test() {
        Bird p = new Macaw(4);
        // If we uncomment line 15, the output will be 10
        System.out.println(((Parrot) p.fly()).feathers);
        Assert.assertTrue(true);
    }
}
