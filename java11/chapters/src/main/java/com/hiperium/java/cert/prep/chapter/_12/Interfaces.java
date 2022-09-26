package com.hiperium.java.cert.prep.chapter._12;

interface Walk {
    default int getSpeed() { return 5; }
}
interface Run {
    default int getSpeed() { return 8; }
}
interface Swim {
    private static void breathe(String type) {
        System.out.println("Inhale");
        System.out.println("Performing stroke: " + type);
        System.out.println("Exhale");
    }
    static void butterfly()     { breathe("butterfly"); }
    default void backstroke()   { breathe("backstroke"); }
    private void breaststroke() { breathe("breaststroke"); }

    abstract String getName();
    static int hunt()    { /* getName(); */ return 5; }           // ERROR: 'getName()' cannot be referenced from a static context
    private int rest()   { return 2; }
    default void climb() { rest(); }
    private void roar()  { getName(); climb(); hunt(); }
    private static boolean sneak() { /* roar(); */ return true; } // ERROR: 'roar()' cannot be referenced from a static context
    default void getStatus() {
        System.out.println(this.getName() + " " + this.rest() + " " + hunt());
    }

}

public class Interfaces implements Walk, Run { // We need to override the conflicting duplicate method.

    public static void main(String[] args) {
        Interfaces interfaces = new Interfaces();
        System.out.println(interfaces.getSpeed());     // Calling an Interface's version of getSpeed().
        System.out.println(interfaces.getWalkSpeed()); // Calling a Walk's version of getSpeed() but through an instance.
        Swim swim = () -> "butterfly";                 // Overriding the abstract method getName().
        swim.getStatus();
    }

    @Override
    public int getSpeed() {
        return 1;
    }

    public int getWalkSpeed() {
        return Walk.super.getSpeed(); // Calling a Walk's version of getSpeed()
    }
}
