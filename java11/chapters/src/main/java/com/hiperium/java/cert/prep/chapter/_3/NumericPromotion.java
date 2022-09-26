package com.hiperium.java.cert.prep.chapter._3;

public class NumericPromotion {

    public static void main(String[] args) {
        double x = 39.21;
        float y = 2.1f;   // MUST HAVE AN 'f' AT THE END, OTHERWISE COMPILER ERROR
        var z = x + y;
        System.out.println("z (double) = " + z);

        // float egg = 2.0 / 9; ERROR -> REQUIRED: FLOAT - PROVIDED: DOUBLE
        // int frog = 3 - 2.0;  ERROR -> REQUIRED: INT - PROVIDED: DOUBLE

        int pig = (short) 4;
        pig = pig++;
        long goat = (int) 2;
        goat -= 1.0;
        // goat = goat - 1.0; ERROR -> REQUIRED: LONG - PROVIDED: DOUBLE
        // goat = (long) (goat - 1.0); VALID
        System.out.println(pig + " - " + goat);

        //int note = 1 * 2 + (long) 3; ERROR -> REQUIRED: INT - PROVIDED: LONG
        int note = 5;
        short melody = (byte) (double) (note *= 2);
        double song = melody;
        float symphony = (float) ((song == 1_000f) ? song * 2l : song);
        System.out.println(melody + " - " + symphony);

        int ticketsSold = 3;
        ticketsSold += (long) 1; // VALID
        // ticketsSold = (int) (ticketsSold + (long)1);

        System.out.print(addCandy(1.4, 2.4f) + "-");
        System.out.print(addCandy(1.9, (float) 4) + "-");
        System.out.print(addCandy((long) (int) (short) 2, (float) 4));
    }

    static long addCandy(double fruit, float vegetables) {
        // return (int) fruit + vegetables; ERROR -> REQUIRED: LONG, PROVIDED: FLOAT
        return (long) ((int) fruit + vegetables);
    }
}
