package com.hiperium.java.cert.prep.chapter._8;

interface HasTail {
    public abstract boolean isTailStreped();
}

class Primate {
    public boolean hasHair() {
        return true;
    }
}

class Lemur extends Primate implements HasTail {
    int age = 10;

    @Override
    public boolean isTailStreped() {
        return false;
    }
}

public class Polymorphism {
    public static void main(String[] args) {
        Lemur lemur = new Lemur();
        System.out.println("Lemur, age: " + lemur.age);

        HasTail hasTail = lemur;
        System.out.println("HasTail, is striped?: " + hasTail.isTailStreped());

        Primate primate = lemur;
        System.out.println("Primate, has hair?: " + primate.hasHair());
        // casting();
    }

    public static void casting() {
        Primate primate = new Primate();
        Lemur lemur = (Lemur) primate;      // Explicit cast must be required, but ClassCastException at Runtime
        if (primate instanceof Lemur) {
            //This is always false but, prevents ClassCastException at Runtime
        }

        Primate primate2 = new Lemur();   // Implicit Cast
        // Lemur lemur2 = primate;        // DOES NOT COMPILE
        Lemur lemur2 = (Lemur) primate2;  // Explicit Cast
        System.out.println("Lemur obj 2, age: " + lemur2.age);
    }
}
