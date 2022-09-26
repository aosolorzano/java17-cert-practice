package com.hiperium.java.cert.prep.chapter._2;

public class DeclaringVariables {

    private static char code;
    private String _color$;
    private int _amount_;
    private long age;
    private float colorPercentage;
    private boolean Public;
    private double cost;

    // fake constructor
    public void DeclaringVariables() {
        this._color$ = "blue";
        _amount_ = 3_100_89;      // VALID
        _amount_ = 0b11;          // VALID
        this._amount_ = 0xE;      // VALID
        this.colorPercentage = 0;
        this.cost = 1 / 0;        // VALID
        this.age = 1200;
        this.Public = true;

        // valid var declarations
        var $_ = 6_000;
        var $2 = 6_000f;
        var var = 3_0_00.0;
    }

    public static void main(String[] args) {
        var p = new DeclaringVariables();
        var q = new DeclaringVariables();
        p._color$ = "green";
        p.age = 1400;
        p = q;
        System.out.println("Q1 char = " + code);
        System.out.println("Q2 Str = " + q._color$);
        System.out.println("Q3 int = " + q._amount_);
        System.out.println("Q4 float = " + q.colorPercentage);
        System.out.println("Q5 long = " + q.age);
        System.out.println("Q6 bool = " + q.Public);
        System.out.println("Q7 double = " + q.cost);
        System.out.println("P1 = " + p._color$);
        System.out.println("P2 = " + p.age);

        printMoreVars();
    }

    // private static void printMoreVars(int vars = 3) NOT VALID
    private static void printMoreVars() {
        var night = new Object();
        System.out.println("Night (Generic Obj) = " + night);

        int capybara = 2, mouse, beaver = -1;
        System.out.println("Capybara = " + capybara);
        // System.out.println("mouse = " + mouse); VAR mouse INIT ERROR
        System.out.println("Beaver = " + beaver);

        night = "";
        System.out.println("Night (Empty Str) = " + night);

        double weight = 2;
        System.out.println("Weight (assigned int value) = " + weight); // PRINTS 2.0
    }

}