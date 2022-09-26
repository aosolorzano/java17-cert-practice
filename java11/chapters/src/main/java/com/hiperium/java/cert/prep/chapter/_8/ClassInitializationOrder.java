package com.hiperium.java.cert.prep.chapter._8;

class GiraffeFamily {

    static {
        System.out.print("A");
    }   // FIRST

    {
        System.out.print("B");
    }          // THIRD

    public GiraffeFamily(String name) { // FOURTH
        this(1);
        System.out.print("C");
    }

    public GiraffeFamily() {  // NEVER
        System.out.print("D");
    }

    public GiraffeFamily(int stripes) {  // FIFTH
        System.out.print("E");
    }
}

class Okapi extends GiraffeFamily {

    static {
        System.out.print("F");
    } // SECOND

    public Okapi(int stripes) {
        super("sugar");
        System.out.print("G");
    }

    {
        System.out.print("H");
    } // SIXTH
}

public class ClassInitializationOrder {

    private String name = "Best Zoo";

    {
        System.out.println("First instance init block... var 'name' = " + name);
    }

    private static int COUNT;

    static {
        System.out.println("Static init block... var 'COUNT' = " + COUNT);
    } // ======>>>>>> PRINTS FIRST

    {
        COUNT++;
        System.out.println("Second instance init block... var 'COUNT' = " + COUNT);
    }

    public ClassInitializationOrder() {
        System.out.println("Constructor");
    }

    public static void main(String[] args) {
        System.out.println("Ready...");
        new ClassInitializationOrder();
        System.out.println("Finish...");

        new Okapi(1);   // PRINTS ALL STATIC AND INSTANCE INITIALIZATION BLOCKS ===>>> "AFBECHG"
        System.out.println();
        new Okapi(2);   // PRINTS ALL INSTANCE INITIALIZATION BLOCKS ===>>> "BECHG"
        System.out.println();

    }
}


