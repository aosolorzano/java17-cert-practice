package com.hiperium.java.cert.prep.chapter._10;

public class PrintingExceptions {

    /**
     * This code results in the following output:
     * <p>
     * 1) java.lang.RuntimeException: cannot hop
     * 2) cannot hop
     * 3) java.lang.RuntimeException: cannot hop
     * at com.hiperium.java.cert.prep.chapter._10.PrintingExceptions.hop(PrintingExceptions.java:15)
     * at com.hiperium.java.cert.prep.chapter._10.PrintingExceptions.main(PrintingExceptions.java:6)
     */
    public static void main(String[] args) {
        try {
            hop();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void hop() {
        throw new RuntimeException("Cannot hop");
    }
}
