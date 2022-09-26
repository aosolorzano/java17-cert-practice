package com.hiperium.java.cert.prep.chapter._7;

public class StaticInitializer {

    private static final int NUM_SECONDS_PER_MINUTE;
    private static final int NUM_MINUTES_PER_HOUR;
    private static final int NUM_SECONDS_PER_HOUR;
    // private static final int four;   DOES NOT COMPILE ==> MUST BE INITIALIZED HERE OR IN STATIC INITIALIZERS
    private static int count; // DEFAULT 0

    static {
        NUM_SECONDS_PER_MINUTE = 60;
        NUM_MINUTES_PER_HOUR = 60;
    }

    static {
        NUM_SECONDS_PER_HOUR = NUM_SECONDS_PER_MINUTE * NUM_MINUTES_PER_HOUR;
    }

    static {
        count = 10; // WE CAN ASSIGN A NEW VALUE
    }

    public StaticInitializer() {
        count++;
    }

    public static void main(String[] args) {
        StaticInitializer c1 = new StaticInitializer();
        StaticInitializer c2 = new StaticInitializer();
        StaticInitializer c3 = new StaticInitializer();
        System.out.println("Class counter = " + count);
        System.out.println("Object 1 counter = " + c1.getCount());
        System.out.println("Object 2 counter = " + c2.getCount());
        System.out.println("Object 3 counter = " + c3.getCount());

        System.out.println("**** Incrementing in 1 the count static var from Object 1 ****");
        c1.incrementStaticCount();
        System.out.println("Object 2 counter = " + c2.getStaticCount());
        System.out.println("Object 3 counter = " + c3.getStaticCount());
    }

    public int getCount() {
        return count;
    }

    // CLASS INSTANCES CAN CALL STATIC METHODS
    public static int getStaticCount() {
        return count;
    }

    public void incrementStaticCount() {
        count++;
    }

    // METHOD WITH VARARGS
    public void moreE(String[] values, String... nums) {
    }

}
