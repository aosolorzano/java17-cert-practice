package com.hiperium.java.cert.prep.chapter._4;

public class ForLoop {

    public static void main(String[] args) {
        int x = 0;
        for (long y = 0, z = 4; x < 5 && y < 10; x++, y++)
            System.out.print(y + " ");
        System.out.println(x + " ");

        // Initialize x again to 0
        for (x = 0; x < 10; ) {     // VALID STRUCTURE
            x++;
        }
        System.out.println("x = " + x);

        String[] names = new String[3];
        for (String name : names) {
            System.out.println(name + " ");
        }
    }
}
