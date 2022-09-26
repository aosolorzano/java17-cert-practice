package com.hiperium.java.cert.prep.chapter._12;

class Alfa {
    private int x = 10;
    public static void main(String[] args) {
        // Beta b = new Beta();   ERROR: Beta cannot be referenced directly from a static context.
        // b.Alfa.boo();          ERROR: We cannot access 'Alfa' from  a 'Beta' instance.
    }
    class Beta {
        private int x = 20;
        class Gamma {
            private int x = 30;
            public void allTheX() {
                System.out.println(x);              // 30
                System.out.println(this.x);         // 30
                System.out.println(Beta.this.x);    // 20
                System.out.println(Alfa.this.x);    // 10
            }
        }
        // public static void test() {} ERROR: Static declarations in inner classes are not supported.
    }
}

class Lion {
    class Cub {}
    static class Den {}

    static void rest() {
        // var a      = new Cub();              ERROR: Lion.this cannot be referenced from a static context
        var a         = new Den();
        // Cub b      = Lion.new Cub();         ERROR: INVALID DECLARATION
        // Lion.Cub c = new Lion().Cub();       ERROR: INVALID DECLARATION
        // Lion.Den d = Lion.new Den();         ERROR: INVALID DECLARATION
        // Lion.Den e = new Lion().new Den();   ERROR: Qualified new of static class
        Lion.Cub b    = new Lion().new Cub();
        Cub c         = new Lion().new Cub();
        Lion.Den d    = new Lion.Den();
        Den e         = new Lion.Den();
    }
}


public class InnerAndNestedClasses {
    interface Wild {}
    private int count = 10;

    static class Delta implements Wild {
        private int price = 6;
        // public int stampede() { return count; } // ERROR: Non-static field 'count' cannot be referenced from a static context.
    }

    public static void main(String[] args) {
        Alfa a = new Alfa();
        Alfa.Beta b = a.new Beta();
        Alfa.Beta.Gamma c = b.new Gamma();
        c.allTheX();

        // Static nested classes
        Delta nested = new Delta();               // OK: We do not need an instance because it is static.
        System.out.println(nested.price);
    }
}
