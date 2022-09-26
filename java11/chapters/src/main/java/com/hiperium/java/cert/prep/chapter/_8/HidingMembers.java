package com.hiperium.java.cert.prep.chapter._8;

class Marsupial {
    protected int age = 2;

    public static boolean isBiped() {
        return false;
    }
}

/**
 * Class with hidden members.
 */
class Kangaroo extends Marsupial {
    protected int age = 6;            // hidden variable

    public static boolean isBiped() { // hidden method, not overridden: Only static methods can be hidden.
        return true;
    }
}

public class HidingMembers {
    public static void main(String[] args) {
        Kangaroo joey = new Kangaroo();
        System.out.println("Is Joey biped?: " + joey.isBiped()); // true
        System.out.println("Joey age: " + joey.age);             // 6

        Marsupial moey = joey;
        System.out.println("Is Moey biped?: " + moey.isBiped()); // false
        System.out.println("Moey age: " + moey.age);             // 2
    }
}
