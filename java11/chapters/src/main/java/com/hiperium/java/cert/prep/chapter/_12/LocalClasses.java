package com.hiperium.java.cert.prep.chapter._12;

class PrintNumbers {
    private int length = 5;

    public void calculate() {
        final int width = 20;  // It may not have the 'final' keyword, but it must not change its initial value.
        class MyLocalClass {   // Local classes must not have access modifiers.
            public void multiply() {
                System.out.println(length * width); // It can access final or effectively final class/local variables.
            }
        }
        MyLocalClass local = new MyLocalClass();
        local.multiply();
    }
}

public class LocalClasses {
    public static void main(String[] args) {
        PrintNumbers outer = new PrintNumbers();
        outer.calculate();
    }
}
