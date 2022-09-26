package com.hiperium.java.cert.prep.chapter._2;

public class InstanceInitializer {

    /* THIS DOES NOT COMPILE. ORDER MATTERS
    { System.out.println("Assigned instance number: " + number); } */

    private int number = 3;

    {
        System.out.println("***** Instance Initializer 1st Block *****");
    }

    {
        System.out.println("***** Instance Initializer 2nd Block *****");
        System.out.println("Assigned instance number: " + number);
        System.out.println("Updating instance number to 4");
        number = 4;
    }

    public InstanceInitializer() {
        System.out.println("***** Class constructor *****");
        System.out.println("Getting instance number: " + number);
        System.out.println("Updating instance number to 5");
        number = 5;
    }

    public static void main(String[] args) {
        InstanceInitializer egg = new InstanceInitializer();
        System.out.println("=====>>>>> Final instance number: " + egg.number);
    }

}
