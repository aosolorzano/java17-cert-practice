package com.hiperium.java.cert.prep.chapter._4;

public class FlowControl {

    public static void main(String[] args) {
        var dayOfTheWeek = 5;    // PRINTS: Weekday Saturday
        // var dayOfTheWeek = 6;    PRINTS: Saturday
        // var dayOfTheWeek = 0;    PRINTS: Sunday Weekday Saturday
        switch (dayOfTheWeek) {
            case 0: System.out.println("Sunday");
            default: System.out.println("Weekday");
            case 6: System.out.println("Saturday"); break;
         // default:                COMPILATION ERROR: Duplicate 'default' label
        }
        switch (dayOfTheWeek) {
            default: System.out.println("Sunday");
        }
        feetAnimals();
        getSortOrder("Andres", "Solorzano");
    }

    static int getCookies() {
        return 4;
    }

    static void feetAnimals() {
        final int bananas = 1;
        int apples = 2;
        final int applesFinal = 2;
        int numberOfAnimals = 3;
        final int cookies = getCookies();
        final int cookiesTwo = 2;
        final int cookiesFinal = 4;

        switch (numberOfAnimals) {
         // case apples:       DOES NOT COMPILE: Constant expression required.
         // case getCookies(): DOES NOT COMPILE: VALUE ITS EVALUATED AT RUNTIME.
         // case cookiesTwo:   DOES NOT COMPILE: Duplicate value 2 (applesFinal).
         // case cookies:      DOES NOT COMPILE: Constant expression required.
         // case 10, 20:       DOES NOT COMPILE: Enhanced 'switch' blocks are not supported
            case bananas:      System.out.println("bananas case");
            case applesFinal:  System.out.println("applesFinal case");
            case cookiesFinal: System.out.println("cookiesFinal case");
            case 3 * 5:        System.out.println("15 case");
            case 2 | 3:        System.out.println("1 or 2 case");
        }
    }

    public static void getSortOrder(String firstName, final String lastName) {
        String middleName = "Patricia";
        final String middleNameFinal = "Patty";
        final String suffix = "JR";
        int id = 0;
        switch (firstName) {                // SWITCH WITHOUT A 'default' CASE.
         // case middleName:                DOES NOT COMPILE -> Constant expression required.
         // case lastName:                  DOES NOT COMPILE -> Constant expression required.
            case "Test": id = 52;
            case middleNameFinal: id = 55;
            case suffix:
                id = 10;
                break;
        }
        System.out.println("Id: " + id);
    }
}
