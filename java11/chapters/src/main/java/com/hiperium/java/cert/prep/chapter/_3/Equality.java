package com.hiperium.java.cert.prep.chapter._3;

import java.io.File;

public class Equality {

    public static void main(String[] args) {
        File monday = new File("schedule.txt");
        File tuesday = new File("schedule.txt");
        System.out.println("monday == tuesday -> " + (monday == tuesday)); // FALSE
        System.out.println("monday.equals(tuesday) -> " + monday.equals(tuesday)); // TRUE

        tuesday = monday;
        System.out.println("monday == tuesday -> " + (monday == tuesday)); // TRUE
        System.out.println("monday.equals(tuesday) -> " + monday.equals(tuesday)); // TRUE

        System.out.println("null == null -> " + (null == null));
    }
}
