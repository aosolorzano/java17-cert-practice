package com.hiperium.java.cert.prep.chapter._9;

public class InnerClass {

    private interface Paper {       // private access allowed for inner class member.
        static public int MAX = 3;

        public String getId();
    }

    class Ticket implements Paper { // package-private access allowed for inner class too.
        private String serialNumber;

        public String getId() {
            return serialNumber;
        }
    }

    public Ticket sellTicket(String serialNumber) {
        var t = new Ticket();
        t.serialNumber = serialNumber;
        return t;
    }

    public static void main(String... unused) {
        var z = new InnerClass();
        var t = z.sellTicket("12345"); // 't' is an InnerClass.Ticket type.
        System.out.println(t.getId() + " Ticket sold!");
    }

}
