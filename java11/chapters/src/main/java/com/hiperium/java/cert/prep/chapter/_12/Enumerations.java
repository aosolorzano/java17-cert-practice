package com.hiperium.java.cert.prep.chapter._12;

enum Season {
    WINTER, SPRING, SUMMER, FALL // Does not need semicolon if only declare the fields.
}

enum SeasonTraffic {
    WINTER("Low") { @Override public void printExpectedVisitors() { System.out.println("1000"); }},
    SPRING("Medium"),
    SUMMER("High"),
    FALL("Medium"); // semicolon needed because this kind implementation.

    private final String expectedVisitors;
    /* public */ SeasonTraffic(String expectedVisitors) { // ENUM CONSTRUCTORS MUST NOT BE PUBLIC OR PROTECTED.
        this.expectedVisitors = expectedVisitors;
    }
    public void printExpectedVisitors() {
        System.out.println(this.expectedVisitors);
    }
}

enum SeasonHoursWithAbstractImplementation {
    WINTER {
        @Override
        public String getHours() {
            return "10am - 3pm";
        }
    },
    SPRING {
        @Override
        public String getHours() {
            return "9am - 5pm";
        }
    },
    SUMMER {
        @Override
        public String getHours() {
            return "9am - 7pm";
        }
    },
    FALL {
        @Override
        public String getHours() {
            return "9am - 5pm";
        }
    };

    public abstract String getHours(); // Each enumeration needs to implement this method.
}

/**
 * Each field can override the enumeration method getHours(). It must not be a final method too.
 */
enum SeasonHoursWithDefaultImplementation {

    WINTER {
        @Override
        public String getHours() {
            return "10am - 3pm";
        }
    },
    SUMMER {
        @Override
        public String getHours() {
            return "9am - 7pm";
        }
    },
    SPRING, FALL;

    public String getHours() {
        return "9am - 5pm";
    }
}

public class Enumerations {
    public static void main(String[] args) {
        System.out.print("Traffic on winter: "); SeasonTraffic.WINTER.printExpectedVisitors();
        System.out.print("Traffic on spring: "); SeasonTraffic.SPRING.printExpectedVisitors();
        System.out.println("Traffic hours on winter: " + SeasonHoursWithAbstractImplementation.WINTER.getHours());
        System.out.println("Traffic hours on fall: " + SeasonHoursWithDefaultImplementation.FALL.getHours());
        enumsOnSwitchStatement(Season.SPRING);
    }

    public static void enumsOnSwitchStatement(Season seasons) {
        switch (seasons) {
            default:
            // case SUMMER | WINTER: Operator '|' cannot be applied to Season, Season.
        }
    }
}
