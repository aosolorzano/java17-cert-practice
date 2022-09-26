package com.hiperium.java.cert.practice.tests;

import com.hiperium.java.cert.practice.util.Hooper;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;

/**
 * Java Object‐Oriented Approach:
 *
 *      - Declare and instantiate Java objects including nested class objects, and explain objects' lifecycles
 *        (including creation, de-referencing by reassignment, and garbage collection).
 *      - Define and use fields and methods, including instance, static and overloaded methods.
 *      - Initialize objects and their members using instance and static initializer statements and constructors.
 *      - Understand variable scopes, apply encapsulation and make objects immutable.
 *      - Create and use subclasses and superclasses, including abstract classes.
 *      - Utilize polymorphism and casting to call methods, differentiate object type versus reference type.
 *      - Create and use interfaces, identify functional interfaces, and utilize private, static, and default methods.
 *      - Create and use enumerations.
 */
public class Chapter3Test {

    /**
     * QUESTION 1:
     *
     * What is the output of the following application?
     * <p>
     * NOTE: The code does not compile because Story class its final and cannot be extended by Adventure class.
     *
     final */ class Story {
        void recite(int chapter) throws Exception {}
    }
    class Adventure extends Story {
        @Override
        final void recite(final int chapter) {
            switch (chapter) {
                case 2: System.out.println(9);
                default: System.out.println(3);
            }
        }
    }
    @Test
    public void question1() {
        var bedtime = new Adventure();
        bedtime.recite(2);
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 9
     */
    static class Phone {
        private int size;
        public Phone(int size) { this.size = size; }
        public static void sendHome(/* final */ Phone p, int newSize) {
            p = new Phone(newSize);                  // We create a new object, but the original reference 'p' it keeps.
            p.size = 4;
        }
        public static void main() {
            final var phone = new Phone(3);     // final only applies in this method scope.
            // phone = new Phone(5);                // ERROR: Cannot assign a value to final variable 'phone'
            sendHome(phone,7);
            System.out.print(phone.size);
        }
    }
    @Test
    public void question9() {
        Phone.main();
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 13
     */
    static class Car {
        private static void drive() {
            // static { System.out.println("static"); }         ERROR: Not allowed.
            System.out.println("fast");
            { System.out.println("faster"); }
        }
        public static void main() {
            drive();
            drive();
        }
    }
    @Test
    public void question13() {
        Car.main();
        Assert.assertTrue(true);
    }

    /**
     * Which statements about static interface methods are correct? (Choose three.)
     */
    interface Question14 {
                static void defaultAccess()     {}
        public  static void publicAccess()      {}
        private static void privateAccess()     {}
     // public  static final void finalAccess() {}  ERROR: Illegal combination of modifiers: 'static' and 'final'.
     // protected static void protectedAccess() {}  ERROR: Modifier 'protected' not allowed here.
    }

    /**
     * What is the output of the following application?
     *
     * NOTE: The code does not compile because Bend is not a functional interface.
     */
    interface Pump { void pump(double psi); }
    interface Bend /* extends Pump */ { void bend(double tensileStrength); }
    static class Robot {
        public static final void apply(Bend instruction, double input) {
            instruction.bend(input);
        }
    }
    @Test
    public void question24() {
        final Robot robot = new Robot();
        // robot.apply(x -> System.out.println(x+" bent!"), 5);     WARN: Static member accessed via instance reference.
        Robot.apply(x -> System.out.println(x+" bent!"), 5);  // PRINT: 5.0 bent!
        Assert.assertTrue(true);
    }

    /**
     * Which statement about the following interface is correct?
     *
     * R./ The code does not compile because of line k2.
     */
    public interface Question27 {
        String DEFAULT = "Diving!";     // k1
        abstract int breath();
        private static void stroke() {
            // if (breath() == 1)       // k2: Non-static method 'breath()' cannot be referenced from a static context.
            System.out.print(dive());   // k3
        }
        static String dive() {
            return DEFAULT;             // k4
        }
    }

    /**
     * What is true of the following code? (Choose three.)
     *
     * A. It compiles as is.
     * C. Removing line 2 (1st enum Baby) would create an additional compiler error.
     * E. Removing the static modifier on line 3 (2nd enum Baby) would create an additional compiler error.
     */
    enum Baby { EGG }
    static class Chick {    // E. If we remove static access, then an error appears like follows:
        enum Baby { EGG }   // ERROR: Static declarations in inner classes are not supported.
    }
    @Test
    public void question33() {
        boolean match = false;
        Baby egg = Baby.EGG;
        switch (egg) {
            case EGG: match = true;
        }
        Assert.assertTrue(true);
    }

    /**
     * How many lines will not compile?
     */
    private void printVarargs(String... names) {
        System.out.println(Arrays.toString(names));
    }
    private void printArray(String[] names) {
        System.out.println(Arrays.toString(names));
    }
    @Test
    public void question40() {
        printVarargs("Arlene");         // PRINT: [Arlene]
        printVarargs(new String[]{"Bret"});     // PRINT: [Bret]
        printVarargs(null);             // PRINT: null
        // printArray("Cindy");                    ERROR
        printArray(new String[]{"Don"});        // PRINT: [Don]
        printArray(null);                // PRINT: null
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 42:
     *
     * What is the minimum number of lines that need to be removed to make this code compile?
     * <p>
     * NOTE: The code compiles as is.
     */
    @FunctionalInterface
    public interface Question42 {
        public static void baseball() {}
        private static void soccer() {}
        default void play() {}
        void fun();
    }

    /**
     * QUESTION 45
     */
    static class Bottle {
        static class Ship {
            private enum Sail {                                     // w1
                TALL { protected int getHeight() { return 100; }},
                SHORT { protected int getHeight() { return 2; }};
                protected abstract int getHeight();
            }
            public Sail getSail() {
                return Sail.TALL;
            }
        }
        public static void main() {
            var bottle = new Bottle();
            // Ship q = bottle.new Ship();     w2 ERROR: A static nested class must be instantiated in a static manner.
            Ship q = new Bottle.Ship();
            System.out.print(q.getSail());
        }
    }

    /**
     * QUESTION 50:
     *
     * Which statement about the following program is correct? (Choose two.)
     * <p>
     * B. The code does not compile because of line u2.
     * D. The code does not compile because of line u4.
     */
    class Leader {}
    class Follower {}
    abstract class Dancer {
        public Leader getPartner() { return new Leader(); }
        abstract public Leader getPartner(int count);               // u1
    }
    public abstract class Question50 extends Dancer {
        @Override public Leader getPartner(int x) { return null; }
        // public Follower getPartner() { return new Follower(); }  u2: Attempting to use incompatible return type.
        public void run() {
            // new SwingDancer().getPartner();                      u4: 'Question50' is abstract; cannot be instantiated.
        }
    }

    /**
     * QUESTION 55:
     *
     * What is the output of the following application?
     */
    abstract interface CanSwim {
        public void swim(final int distance);
    }
    static class Turtle {
        final int distance = 2;
        public static void main() {
            final int distance = 3;
            // CanSwim seaTurtle = { ... };         ERROR: ANONYMOUS CLASS INIT ERROR.
            CanSwim fixed = new CanSwim() {
                final int distance = 5;
                @Override
                public void swim(final int distance) {
                    System.out.println(distance);
                }
            };
            fixed.swim(7);
        }
    }
    @Test
    public void question55() {
        Turtle.main();
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 58:
     *
     * Which is the first declaration to not compile?
     * <p>
     * R./ The DesertTortoise (Question58) interface does not compile.
     */
    interface CanBurrow { public abstract void burrow(); }
    @FunctionalInterface
    interface HasHardShell extends CanBurrow {}

    abstract class Tortoise implements HasHardShell {
        public abstract int toughness();
    }
    public class Question58 extends Tortoise {
        @Override public int toughness() { return 11; }
        @Override public void burrow() {}                   // "burrow" must be implemented to compile.
    }

    /**
     * QUESTION 67:
     */
    static class Cinema {
        private String name = "Sequel";
        public Cinema(String name) {
            this.name = name;
        }
    }
    static class Movie extends Cinema {
        private String name = "adaptation";
        public Movie(String movie) {
            // ERROR: Cinema class does not have a default constructor. We must declare the overloaded one here.
            super(movie);
            this.name = "Remake";
        }
        public static void main() {
            System.out.print(new Movie("Trilogy").name);
        }
    }

    /**
     * What is the output of the following code?
     *
     * Note: This example deals with method signatures rather than polymorphism. Since "hop()" methods are static,
     * the precise method called depends on the reference type rather than the actual type of the object.
     */
    static interface Rabbit { }
    static class FlemishRabbit implements Rabbit {}
    private void hop(Rabbit r)        { System.out.println("hop");}
    private void hop(FlemishRabbit r) {
        System.out.println("HOP");
    }
    @Test
    public void question69() {
        Rabbit r1 = new FlemishRabbit();
        hop(r1);                                    // PRINT: hop
        FlemishRabbit r2 = new FlemishRabbit();
        hop(r2);                                    // PRINT: HOP
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following application?
     * <p>
     * R./ The code does not compile.
     */
    public void playMusic() {
        System.out.print("Play!");
    }
    // private static void playMusic() { ... }      ERROR: 'playMusic()' is already defined.
    private static void playMusic(String song) {
        System.out.print(song);
    }
    @Test
    public void question72() {
        this.playMusic();
        Assert.assertTrue(true);
    }

    /**
     * How lines of the following code do not compile?
     */
    interface Flavor {
        public default void happy() {
            printFlavor("Rocky road");
        }
        private static void excited() {
            // printFlavor("Peanut butter");        ERROR: Non-static method cannot be referenced from a static context.
        }
        private void printFlavor(String f) {
            System.out.println("Favorite flavor is: " + f);
        }
        public static void sad() {
            // printFlavor("Butter pecan");         ERROR: Non-static method cannot be referenced from a static context.
        }
    }
    public class Question74 implements Flavor {
        @Override
        public void happy() {
            // printFlavor("Cherry chocolate");     ERROR: 'printFlavor()' has private access.
        }
    }

    /**
     * What is the output of the following application?
     *
     * R./ The code does not compile.
     */
    public enum Snow {
        BLIZZARD, SQUALL, FLURRY;   // In this case, this line must ends with a semicolon to compile.
        @Override
        public String toString() {
            return "Sunny";
        }
    }
    @Test
    public void question78() {
        System.out.print(Snow.BLIZZARD.ordinal() + " ");            // PRINT: 0
        System.out.println(Snow.valueOf("flurry".toUpperCase()));   // PRINT: Sunny
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 81:
     *
     * What is the output of the Rocket program?
     */
    static class Ship {
        protected int weight = 3;
        private int height = 5;
        public int getWeight() { return weight; }
        public int getHeight() { return height; }
    }
    static class Rocket extends Ship {
        public int weight = 2;
        public int height = 4;
        public void printDetails() {
            // System.out.print(super.getWeight() + ", " + super.height);    ERROR: 'height' has private access in class Ship.
            System.out.print(super.getWeight() + ", " + super.getHeight());
        }
        public static final void main() {
            new Rocket().printDetails();
        }
    }
    @Test
    public void question81() {
        Rocket.main();
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 83:
     *
     * Fill in the blank with the line of code that allows the program to compile and print 10 at runtime.
     *
     * R./ None of the above.
     *
     * Note: If we want resolve this question, we need to change de implementation of overridden method getVolume()
     * on Debate class to call the Whisper version of the method using Whisper.super.getVolume()
     */
    interface Speak {
        public default int getVolume() { return 20; }
    }
    interface Whisper {
        public default int getVolume() { return 10; }
    }
    static class Debate implements Speak, Whisper {
        // public int getVolume() { return Whisper.super.getVolume(); }
        public int getVolume() { return 30; }
        public static void main() {
            var d = new Debate();
            // System.out.println(Whisper.super.getVolume());       ERROR: Whisper is not an enclosing class.
            d.getVolume();
        }
    }
    @Test
    public void question83() {
        Debate.main();
        Assert.assertTrue(true);
    }

    /**
     * How many lines does the following code output?
     *
     * static, fast, fast.
     */
    static class Bici {
        static {
            System.out.println("static");
        }
        private static void drive() {
            System.out.println("fast");
        }
        { System.out.println("faster"); }
        public static void main() {
            drive();
            drive();
        }
    }
    @Test
    public void question85() {
        Bici.main();
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 92:
     *
     * What is the output of the following application?
     * <p>
     * R./ 5 2 2
     */
    static class Matrix {
        private int level = 1;
        class Deep {
            private int level = 2;
            class Deeper {
                private int level = 5;
                public void printReality(int level) {
                    System.out.print(this.level + " ");                 // PRINT: 5
                    System.out.print(Matrix.Deep.this.level + " ");     // PRINT: 2
                    System.out.print(Deep.this.level);                  // PRINT: 2
                }
            }
        }
        public static void main() {
            Matrix.Deep.Deeper simulation = new Matrix().new Deep().new Deeper();
            simulation.printReality(6);
        }
    }
    @Test
    public void question92() {
        Matrix.main();
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 93:
     *
     * Given that Integer and Long are direct subclasses of Number, what type can be used to fill in the blank in the
     * following class to allow it to compile?
     *
     * R./ Long.
     *
     * Note: The play() method is overridden in Question93 for both: MusicCreator and StringInstrument, so the return
     * type must be covariant with both.
     *
     * Integer is a subclass of Number, meaning the override for MusicCreator is valid, but Integer it is not
     * a subclass of Long used in StringInstrument. Therefore, using Integer would cause the code not compile.
     *
     * Number is compatible with the version of the method in MusicCreator, but not with the version in StringInstrument,
     * because Number is a superclass of Long, not a subclass of it.
     */
    interface MusicCreator {
        public Number play();
    }
    abstract class StringInstrument {
        public Long play() { return 3L; }
    }
    public class Question93 extends StringInstrument implements MusicCreator {
        // @Override public Number play() { return null; }      ERROR: attempting to use incompatible return type.
        // @Override public Integer play() { return null; }     ERROR: attempting to use incompatible return type.

        /**
         * Long is a subclass of Number, and therefore, it is covariant with the version in MusicCreator.
         * Since it matches the type in StringInstrument, it can be used here.
         */
        @Override public Long play() { return 1L; }
    }

    /**
     * QUESTION 94:
     *
     * What is the output of the RightTriangle program?
     * <p>
     * R./ The code does not compile due to line g3.
     */
    abstract class Triangle {
        abstract String getDescription();                   // Package-private abstract method.
    }
    abstract class IsoRightTriangle extends Question94 {    // g1
        public String getDescription() { return "irt"; }    // If getDescription() were declared private here, then an error occur.
    }
    public class Question94 extends Triangle {
        protected String getDescription() { return "rt"; }  // g2: If getDescription() were declared public here, then an error occur.
        public /* static */ void main(String... args) {
            // final var shape = new IsoRightTriangle();    // g3: 'IsoRightTriangle' is abstract; cannot be instantiated.
        }
    }

    /**
     * QUESTION 95:
     *
     * What is the output of the following program?
     */
    interface Dog {
        private void buryBone() { chaseTail(); }
        // private static void wagTail() { chaseTail(); }   ERROR: Non-static method 'chaseTail()' cannot be referenced from a static context.
        public default String chaseTail() { return "So cute!"; }
    }
    public class Puppy implements Dog {
        public String chaseTail() throws IllegalArgumentException { // We can add RuntimeExceptions, but not checked exceptions.
            throw new IllegalArgumentException("Too little!");
        }
    }
    @Test
    public void question95() {
        var p = new Puppy();
        try {
            System.out.print(p.chaseTail());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        Assert.assertTrue(true);
    }

    /**
     * Given the following structure, which snippets of code return true? (Choose three.)
     */
    interface Friendly {}
    abstract class Dolphin implements Friendly {}
    class Animal implements Friendly {}
    class Whale extends Object {}
    public class Fish {}
    class Coral extends Animal {}

    @Test
    public void question101() {
        System.out.println("new Coral() instanceof Friendly = " + (new Coral() instanceof Friendly));   // TRUE
        System.out.println("null        instanceof Object   = " + (null instanceof Object));            // FALSE
        System.out.println("new Coral() instanceof Object   = " + (new Coral() instanceof Object));     // TRUE
        System.out.println("new Fish()  instanceof Friendly = " + (new Fish() instanceof Friendly));    // FALSE
        System.out.println("new Whale() instanceof Object   = " + (new Whale() instanceof Object));     // TRUE
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 102:
     *
     * What is true of the following code?
     * <p>
     * R./ 20 20 75
     */
    enum AnimalEnum {
        CHICKEN(21), PENGUIN(75);         // Sets default values.
        private int numDays;
        private AnimalEnum(int numDays) {
            this.numDays = numDays;
        }
        public int getNumDays() { return numDays; }
        public void setNumDays(int numDays) { this.numDays = numDays; }
    }
    @Test
    public void question102() {
        AnimalEnum chicken = AnimalEnum.CHICKEN;
        chicken.setNumDays(20);                             // OK: Sets a new value to an enum variable.
        System.out.print(chicken.getNumDays());             // PRINT: 20
        System.out.print(" ");
        System.out.print(AnimalEnum.CHICKEN.getNumDays());  // PRINT: 20
        System.out.print(" ");
        System.out.print(AnimalEnum.PENGUIN.getNumDays());  // PRINT: 75
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 104:
     *
     * What is the output of the following application?
     */
    enum Currency {
        DOLLAR, YEN, EURO
    }
    abstract class Provider {
        protected Currency c = Currency.EURO;
    }
    public class Bank extends Provider {
        protected Currency c = Currency.DOLLAR;
    }
    @Test
    public void question104() {
        int value = 0;
        switch(new Bank().c) {      // We use the Currency.DOLLAR value defined Bank class.
            // case 0:              ERROR: Required type: Currency - Provided: int. We can use new Bank().c.ordinal().
            case DOLLAR:
                value--; break;
            // case 1:              ERROR: Required type: Currency - Provided: int. We can use new Bank().c.ordinal().
            case EURO:
                value++; break;
        }
        System.out.print(value);    // PRINT: -1
        Assert.assertTrue(true);
    }

    /**
     * How many lines need to be removed for this code to compile?
     *
     * R./ Three.
     */
    static class Dolls {
        // public int num() { return 3.0; }                 ERROR: Required type: int - Provided: double.
        // public int size() { return 5L; }                 ERROR: Required type: int - Provided: long.
        public int size() { return 5; }
        public int num() { return 3; }

        public void nested() { nested(2, true); }
        public int nested(int w, boolean h) { return 0; }
        public int nested(int level) { return level + 1; }

        public static void main() {
            // System.out.println(new Dolls().nested());    ERROR: Cannot resolve method 'println(void)'.
            System.out.println(new Dolls().nested(5));
        }
    }
    @Test
    public void question105() {
        Dolls.main();
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following application?
     */
    static class Woods {
        static class Tree {}
        public static void main() {
            int heat = 2;
            int water = 10 - heat;
            final class Oak extends Tree {      // p1: Valid operation.
                public int getWater() {
                    // return water;            // p2 ERROR: Variable 'water' needs to be final or effectively final.
                    return 5;
                }
            }
            Oak oak = new Oak();
            System.out.print(oak.getWater());
            water = 0;                          // This line makes variable 'water' does not be effectively final.
        }
    }
    @Test
    public void question116() {
        Woods.main();
        Assert.assertTrue(true);
    }

    /**
     * Which statements about the following program are correct? (Choose two.)
     *
     * The code does not compile because of line m3.
     * The code does not compile because of line m6.
     */
    class Problem extends Exception {}
    static abstract class Danger {
        protected abstract void isDanger() throws Problem;  // m1
    }
    static class SeriousDanger extends Danger {                    // m2
        // protected void isDanger() throws Exception {}       m3 ERROR: overridden method does not throw 'Exception'.
        protected void isDanger() throws Problem {
            throw new RuntimeException("Is Danger...");     // m4 VALID: We can throw RTE despite the method can throw checked ones.
        }
        public static void main() throws Throwable {        // m5 VALID: Throwable is the superclass of all errors and exceptions.
            // var sd = new SeriousDanger().isDanger();        m6 ERROR: variable initializer is 'void'.
            new SeriousDanger().isDanger();
        }
    }
    @Test
    public void question121() {
        try {
            SeriousDanger.main();
        } catch (Throwable e) {
            System.err.println(e.getMessage());
        }
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 125:
     *
     * What is the output of the following application?
     * <p>
     * R./ The code does not compile.
     */
    interface Drive {
        int SPEED = 5;
        default int getSpeed() { return SPEED; }
    }
    interface Hover {
        int MAX_SPEED = 10;
        default int getSpeed() { return MAX_SPEED; }
    }
    // ERROR: Class 'CarTest' inherits unrelated defaults types for "getSpeed()" from Drive and Hover.
    // class CarTest implements Drive, Hover {}

    static class CarTest implements Drive, Hover {
        @Override
        public int getSpeed() { return Drive.super.getSpeed() + Hover.super.getSpeed(); }
        public static void main() {
            class RaceCar extends CarTest {
                @Override public int getSpeed() { return 15; }
            };
            System.out.print(new RaceCar().getSpeed());
        }
    }
    @Test
    public void question125() {
        CarTest.main();
        Assert.assertTrue(true);
    }


    /**
     * What is the output of the following application? (Choose two.)
     *
     * Note: Java attempts promotion of the primitive types first, before trying to wrap them as a Wrapper objects.
     */
    public class ChooseWisely {
        public ChooseWisely() { super(); }
        public int choose(int choice)    { return 5;  }
        public int choose(short choice)  { return 2;  }
        public int choose(long choice)   { return 11; }
        public int choose(double choice) { return 6;  }
        public int choose(Float choice)  { return 8;  }
    }
    @Test
    public void question126() {
        ChooseWisely c = new ChooseWisely();
        System.out.println(c.choose(2f));           // PRINT: 6 >>> promote to a primitive first.
        System.out.println(c.choose((byte) 2 + 1)); // PRINT: 5 >>> casting first, and then, the addition operation.
        Assert.assertTrue(true);
    }

    /**
     * How many lines of the following program do not compile?
     */
    public enum Question128 {
        RED(1,2) { void toSpectrum() {} },
        BLUE(2)  { void toSpectrum() {}
            // void printColor() {}                 ERROR: Cannot override method 'printColor()', its final.
        },
        ORANGE()        { void toSpectrum() {} },
        GREEN(4) { void toSpectrum() {} };
        // GREEN(4);                                ERROR: Must implement abstract method 'printColor()'.

        private Question128(int... color) {}
        // public Question128(int... color) {}      ERROR: Modifier 'public' not allowed.

        abstract void toSpectrum();
        final void printColor() {}
    }

    /**
     * QUESTION 129:
     *
     * What is the output of the Square program?
     */
    static abstract class Trapezoid {
        private int getEqualSides() { return 0; }
    }
    static abstract class Rectangle extends Trapezoid {
        public static int getEqualSides() { return 2; }     // x1
    }
    static final class Square extends Rectangle {
        // ERROR: Instance method cannot override static method 'getEqualSides()'.
        // public int getEqualSides() { return 4; }         x2
        public static void main() {
            final Square myFigure = new Square();           // x3
            System.out.print(myFigure.getEqualSides());
        }
    }
    @Test
    public void question129() {
        Square.main();
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 134:
     *
     * What is a possible output of the following application?
     */
    static class Gift {
        private final Object contents = new Object();
        // private final Object contents;                   ERROR: Variable 'contents' might not have been initialized.
        protected Object getContents() {
            return contents;
        }
        protected void setContents(Object contents) {
            // this.contents = contents;                    ERROR: Cannot assign a value to final variable 'contents'.
        }
        public void showPresent() {
            System.out.print("Your gift: " + contents);
        }
        public static void main() {
            Gift gift = new Gift();
            gift.setContents(gift);
            gift.showPresent();
        }
    }
    @Test
    public void question134() {
        Gift.main();
        Assert.assertTrue(true);
    }


    /**
     * What is the output of the following code?
     */
    static class Rabbits {
        void hop() { System.out.print("hop"); }
    }
    static class FlemishRabbits extends Rabbits {
        void hop() { System.out.print("HOP"); }
    }
    @Test
    public void question138() {
        Rabbits r1 = new FlemishRabbits();
        FlemishRabbits r2 = new FlemishRabbits();
        r1.hop();                                   // PRINT: HOP
        r2.hop();                                   // PRINT: HOP
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 139:
     *
     * Which of the following are valid class declarations?
     *
     * Notes:
     * 1. Class names follow the same requirements as other identifiers.
     * 2. Underscores (_) and dollar signs ($) are allowed, but no other symbols are allowed.
     * 3. Since Java 9, a single underscore is not permitted as an identifier.
     * 4. Numbers are allowed, but not as the first character.
     */
    class river   {}
    class Pond2$  {}
    class $Pond2  {}
    class $Pond2$ {}
    class var_    {}
    class _var    {}
    class _var_   {}
    // class _ {}       ERROR
    // class Str3@m {}  ERROR
    // class 5Ocean {}  ERROR
    // class var  {}    ERROR: 'var' is a restricted identifier.

    /**
     * QUESTION 140:
     *
     * What is the output of the InfiniteMath program?
     * <p>
     * Java allows methods to be overridden, but not variables. Therefore, marking them final does not prevent them
     * from being reimplemented in a subclass. Furthermore, polymorphism does not apply in the same way it would to
     * methods as it does to variables. In particular, the reference type determines the version of the secret variable
     * that is selected, making the output 2.0.
     */
    static class MathQ {
        public final double secret = 2;
    }
    static class ComplexMath extends MathQ {
        public final double secret = 4;
    }
    static class InfiniteMath extends ComplexMath {
        public final double secret = 8;
        public static void main() {
            MathQ math = new InfiniteMath();
            System.out.print(math.secret);
        }
    }
    @Test
    public void question140() {
        InfiniteMath.main();
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 141:
     *
     * Given the following application, which diagram best represents the state of the "mySkier", "mySpeed", and
     * "myName" variables in the main() method after the call to the slalom() method?
     */
    static public class Ski {
        private int age = 18;
        private static void slalom(Ski racer, int[] speed, String name) {
            racer.age = 18;
            name = "Wendy";
            speed = new int[1];
            speed[0] = 11;
            racer = null;
        }
        @Override
        public String toString() { return "Ski{age=" + age + '}'; }
        public static void main() {
            final var mySkier = new Ski();
            mySkier.age = 16;
            final int[] mySpeed = new int[1];
            final String myName = "Rosie";
            slalom(mySkier, mySpeed, myName);
            System.out.println("mySkier = " + mySkier);                     // PRINT: Ski{age=18}
            System.out.println("mySpeed = " + Arrays.toString(mySpeed));    // PRINT: [0]
            System.out.println("myName = " + myName);                       // PRINT: Rosie
        }
    }
    @Test
    public void question141() {
        Ski.main();
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 142:
     *
     * What is the output of the following application?
     */
    private int volume = 1;
    private class Chick142 {
        // private static int volume = 3;       ERROR: Static declarations in inner classes are not supported.
        private static final int volume = 3;    // Member inner classes can be declared static, if they are final too.
        void chick() {
            System.out.print("Honk(" + Chapter3Test.this.volume + ")!");     // PRINT: Honk(1)!
        }
    }
    @Test
    public void question142() {
        final Chapter3Test.Chick142 littleOne = this.new Chick142();
        littleOne.chick();
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 146:
     *
     * What is the output of the ElectricCar program?
     */
    static class Automobile {
        private final String drive() {
            return "Driving vehicle";
        }
    }
    static class Cars extends Automobile {
        protected String drive() {
            return "Driving car";
        }
    }
    static public class ElectricCar extends Cars {
        public final String drive() {
            return "Driving electric car";
        }
        public static void main() {
            final Automobile car = new ElectricCar();
            Cars v = (Cars) car;
            System.out.print(v.drive());                // PRINT: Driving electric car.
        }
    }
    @Test
    public void question146() {
        ElectricCar.main();
        Assert.assertTrue(true);
    }

    /**
     * Given the following class declaration, which options correctly declare a local variable containing an instance of
     * the class?
     */
    public class Question148 {
        private abstract class Sky {
            void fall() {
                // var e1 = new Sky();             ERROR: Class 'Sky' is abstract and cannot be instantiated.
                var e2 = new Sky() {};          // VALID: we are extending the class 'Sky' using an empty braces {}.
                var e3 = new Sky() {            // VALID: we are extending the class 'Sky'.
                    final static int blue = 1;
                };
            }
        }
    }

    /**
     * QUESTION 149
     */
    static abstract class Book {
        protected static String material = "papyrus";
        public Book() {}
        // abstract String read() {}        ERROR: Abstract methods cannot have a body.
        abstract String read();
        public Book(String material) { this.material = material; }
    }
    static public class Encyclopedia extends Book {
        public static String material = "cellulose";
        public Encyclopedia() { super(); }
        public String read() { return "Reading is fun!"; }
        public String getMaterial() { return super.material; }

        public static void main() {
            System.out.println(new Encyclopedia().read());              // PRINT: Reading is fun!
            System.out.println(new Encyclopedia().getMaterial());       // PRINT: papyrus
        }
    }
    @Test
    public void question149() {
       Encyclopedia.main();
       Assert.assertTrue(true);
    }

    /**
     * QUESTION 152:
     *
     * What is the output of the following program?
     */
    public class Dwarf {
        // private final String name;           ERROR: Variable 'name' might not have been initialized. If var 'name' was
                                                //     not final, then it defaults value was 'null'.
        private final String name = null;
        public Dwarf() {
            this("Bashful");
        }
        public Dwarf(String name) {
            name = "Sleepy";                    // We are not assigning a value to the instance variable 'name'.
            // this.name = "Sleepy";            ERROR: Cannot assign a value to the final variable 'name'.
        }
    }
    @Test
    public void question152() {
        var d = new Dwarf("Doc");
        System.out.println(d.name);             // PRINT: null
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 153:
     *
     * What is the output of the following application?
     */
    interface AddNumbers {
        int add(int x, int y);
        static int subtract(int x, int y)  { return x - y; }
        default int multiply(int x, int y) { return x * y; }
    }
    public class Calculator {
        protected void calculate(AddNumbers n, int a, int b) {
            System.out.print(n.add(a, b));                          // PRINT: 8
        }
    }
    @Test
    public void question153() {
        final var ti = new Calculator() {};             // We are extending the class Calculator.
        ti.calculate((k, p) -> p + k + 1, 2, 5);   // j1: Variable 'ti' can call the inhered method 'calculate()'.
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 162:
     *
     * What is the output of the following application?
     * <p>
     * R./ The code does not compile.
     */
    static class Bond {
        private static int price = 5;
        public boolean sell() {
            if (price < 10) {
                price++;
                return true;
            } else // if (price >= 10) { return false; }
                return false;
        }                                                   // ERROR: Missing return statement
        public static void main() {
            new Bond().sell();
            new Bond().sell();
            new Bond().sell();
            System.out.print(price);
        }
    }
    @Test
    public void question162() {
        Bond.main();
        Assert.assertTrue(true);
    }

    /**
     * Question 164:
     *
     * Which statements about static initializers are correct?
     *
     * 1. They cannot be used to create instances of the class they are contained in.
     * 2. They can assign a value to a static final variable.
     * 3. They are executed at most once per program.
     */
    private static final AddNumbers ADD_NUMBERS;
    private Calculator calculator;
    static {
        // calculator = new Calculator();       ERROR: 'this' cannot be referenced from a static context.
        // ADD_NUMBERS = (k, p) -> p + k;       VALID: we are assigned a lambda function.
        ADD_NUMBERS = Integer::sum;
    }

    /**
     * QUESTION 165:
     *
     * What is the output of the BlueCar program?
     *
     * R./ 13245
     */
    static abstract class CarsTwo {
        static { System.out.print("1"); }
        public CarsTwo(String name) {
            // NOTE: Abstract classes cannot be instantiated, but child classes can call public constructors.
            super();
            System.out.print("2");
        }
        { System.out.print("3"); }
    }
    static class BlueCar extends CarsTwo {
        { System.out.print("4"); }
        public BlueCar() {
            super("blue");
            System.out.print("5");
        }
        public static void main() {
            new BlueCar();
        }
    }
    @Test
    public void question165() {
        BlueCar.main();
        Assert.assertTrue(true);
    }

    /**
     * How many lines of the following class contain compilation errors?
     *
     * R./ None.
     */
    class Fly {
        public Fly Fly() { return Fly(); }
        public void Fly(int kite)  {}
        public int  Fly(long kite) { return 1; }
    }
    @Test
    public void question169() {
        var f = new Fly();
        // f.Fly();                                         // WARN: infinite recursion.
        Assert.assertEquals(1, f.Fly(4L));
    }

    /**
     * What is the output of the following application?
     */
    interface Toy { String play(); }
    @Test
    public void question173() {
        abstract class Robot {}
        class Transformer extends Robot implements Toy {
            public String name = "GiantRobot";
            public String play() { return "DinosaurRobot"; }    // y1
        }
        Transformer prime = new Transformer() {
            @Override
            public String play() { return name; }               // y2
        };
        // System.out.print(prime.play() + " " + name);         ERROR: Cannot resolve symbol 'name'.
        System.out.print("prime.play() = " + prime.play());
        Assert.assertEquals("GiantRobot", prime.play());
    }

    /**
     * What is the output of the following application?
     */
    interface Run {
        default CharSequence walk() { return "Walking and running!"; }
    }
    interface Jog {
        default String walk() { return "Walking and jogging!"; }
    }
    public class Sprint implements Run, Jog {
        public String walk() {
            return "Sprinting!";
        }
    }
    @Test
    public void question175() {
        var s = new Sprint();
        System.out.println(s.walk());                           // PRINT: Sprinting!
        Assert.assertEquals("Sprinting!", s.walk());
    }

    /**
     * QUESTION 178:
     */
    static class Dragon {
        boolean scaly;
        static final int gold;
        Dragon protectTreasure(int value, boolean scaly) {
            scaly = true;
            return this;
        }
        static void fly(boolean scaly) {
            scaly = true;
        }
        int saveTheTreasure(boolean scaly) {
            return this.gold;
        }
        static void saveTheDay(boolean scaly) {
            // this.gold = 0;                       ERROR: 'this' cannot be referenced from a static context.
            // gold = 0;                            ERROR: Cannot assign a value to final variable 'gold'.
        }
        static { gold = 100; }
    }

    /**
     * QUESTION 182:
     *
     * Which statement about the following interface is correct?
     *
     * The code does not compile for a different reason.
     */
    interface Planet {
        // NOTE: interface variables are STATIC FINAL by default.
        // int circumference;                       ERROR: Variable 'circumference' might not have been initialized.
        public static final int circumference = 100;
        public abstract void enterAtmosphere();
        public default int getCircumference() {
            enterAtmosphere();
            return circumference;
        }
        private static void leaveOrbit() {
            var earth = new Planet() {
                public void enterAtmosphere() {}    // We must override abstract method 'enterAtmosphere()'.
            };
            earth.getCircumference();
        }
    }
    class PlanetTest implements Planet {
        @Override
        public void enterAtmosphere() {
            System.out.println("Entering to the atmosphere...");
        }
    }
    @Test
    public void question182() {
        PlanetTest planetTest = new PlanetTest();
        Assert.assertEquals(Planet.circumference, planetTest.getCircumference());
    }

    /**
     * Which statement about the following interface is correct?
     *
     * R./ The code does not compile for a different reason.
     */
    static class Husky {
        { this.food = 10; }
        { this.toy = 2; }
        private final int toy;
        private static int food;
        public Husky(int friend) {
            this.food += friend++;
            // this.toy -= friend--;        ERROR: Variable 'toy' might already have been assigned.
        }
        public static void main() {
            var h = new Husky(2);
            System.out.println(h.food + "," + h.toy);
        }
    }
    @Test
    public void question184() {
        Husky.main();
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 185:
     *
     * Suppose you have the following code. Which of the images best represents the state of the references right before
     * the end of the main() method, assuming garbage collection hasn't run?
     */
    static public class Link {
        private final String name;
        private Link next;
        public Link(String name, Link next) {
            this.name = name;
            this.next = next;
        }
        public void setNext(Link next) {
            this.next = next;
        }
        public String toString() {
            return "Link{ name='" + name + '\'' + '}';
        }
        public static void main() {
            var apple = new Link("x", null);
            var orange = new Link("y", apple);
            var banana = new Link("z", orange);
            orange.setNext(banana);
            banana.setNext(orange);
            apple = null;
            banana = null;
            System.out.println("apple = " + apple);                                     // PRINT: null
            System.out.println("banana = " + banana);                                   // PRINT: null
            System.out.println("orange = " + orange);                                   // PRINT: Link{ name='y' }
            System.out.println("orange.next = " + orange.next);                         // PRINT: Link{ name='z'}
            System.out.println("orange.next.next = " + orange.next.next);               // PRINT: Link{ name='y'}
            System.out.println("orange.next.next.next = " + orange.next.next.next);     // PRINT: Link{ name='z'}
            // NOTE: "orange" ends in a loop, linked with initial "banana" object.
        }
    }
    @Test
    public void question185() {
        Link.main();
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 187:
     *
     * Which variable declaration is the first line not to compile?
     */
    class Building {}
    class House extends Building {}
    @Test
    public void question187() {
        Building b1 = new Building();
        House    h1 = new House();
        Building b2 = new House();
        // Building b3 = (House) b1;        VALID: but it causes a ClassCastException at runtime.
        // House h2 = (Building) h1;        ERROR: >>> Required type: House - Provided: Building.
        Building b4 = (Building) b2;
        House    h3 = (House) b2;
        // House h4 = (House) b1;           VALID: but it causes a ClassCastException at runtime.

        Assert.assertTrue(true);
    }

    /**
     * QUESTION 191:
     */
    interface Tool {
        void use(int fun);
    }
    abstract class Childcare {
        abstract void use(int fun);
    }
    final class Stroller extends Childcare implements Tool {
        final public void use(int fun) {                    // Declaring a method 'final' in a 'final' class is redundant.
            int width = 5;
            class ParkVisit {                               // Declaring a local class 'ParkVisit'.
                int getValue() {
                    return width + fun;
                }
            }
            // width = 0;                                      ERROR: 'width' needs to be final or effectively final.
            System.out.print(new ParkVisit().getValue());   // PRINT: 10
        }
    }
    @Test
    public void question191() {
        new Stroller().use(5);
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 194:
     */
    static class RainForest extends Forest {
        // public RainForest(long treeCount) {}      ERROR: There is no default constructor in 'Forest'.
        public RainForest(long treeCount) {
            super(treeCount);                     // FIXED: We need to call a no default constructor on 'Forest' class.
            this.treeCount = treeCount + 1;
        }
        public static void main() {
            System.out.print(new RainForest(5).treeCount);
        }
    }
    static class Forest {
        public long treeCount;
        public Forest(long treeCount) {
            this.treeCount = treeCount + 2;
        }
    }
    @Test
    public void question194() {
        RainForest.main();
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 196:
     *
     * Given that Short and Integer extend Number directly, what type can be used to fill in the blank in the following
     * class to allow it to compile?
     *
     * E. None of the above.
     */
    interface Horn {
        public Integer play();
    }
    abstract class Woodwind {
        public Short play() {
            return 3;
        }
    }
    /**
     * IMPORTANT: Object and Number do not work, because neither is a subclass of Integer nor Short. As stated in the
     * question text, both Integer and Short extend Number directly, so neither can be a subclass of the other.
     * Therefore, nothing can fill in the blank that would allow this code to compile.
     */
    static final class Saxophone /* extends Woodwind implements Horn */ {
        // public _______ play() {}
        // public Short   play() {}     ERROR: clashes with 'Horn';     attempting to use incompatible return type.
        // public Integer play() {}     ERROR: clashes with 'Woodwind'; attempting to use incompatible return type.
        // public Number  play() {}     ERROR: clashes with 'Woodwind'; attempting to use incompatible return type.
        // public Object  play() {}     ERROR: clashes with 'Woodwind'; attempting to use incompatible return type.
    }

    /**
     * QUESTION 198
     */
    enum Proposition {
        // TRUE(1)       { String getNickName() { return "RIGHT"; }},           ERROR: attempting to assign weaker access.
        TRUE(1)    { public String getNickName() { return "RIGHT"; }},
        FALSE(2)   { public String getNickName() { return "WRONG"; }},
        // UNKNOWN(3)    { public String getNickName() { return "LOST"; }}      ERROR: semicolon ';' required.
        UNKNOWN(3) { public String getNickName() { return "LOST"; }};

        public int value;
        Proposition(int value) {
            this.value = value;
        }
        public int getValue() {
            return this.value;
        }
        protected abstract String getNickName();
    }

    /**
     * QUESTION 200
     */
    static class Grasshopper extends Hooper {
        public void move() {
            hop();                              // p1 NOTE: Only 'Grasshopper' can call 'hop()' method.
        }
    }
    static class HopCounter {
        public static void main() {
            var hopper = new Grasshopper();
            hopper.move();                      // p2
            // hopper.hop();                    // p3 ERROR: 'hop()' has protected access.
        }
    }
    @Test
    public void question200() {
        HopCounter.main();
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 202:
     *
     * Given the following class, which method signature could be successfully added to the class as an overloaded
     * version of the findAverage() method?
     *
     * NOTE: 2 methods in the class cannot have the same name and arguments, but a different return type.
     */
    public static class Calculations {
        public Integer findAverage(int sum) { return sum; }
    //  public Long    findAverage(int sum) {}                             ERROR: "findAverage(int)" is already defined.
        public Long findAverage(int sum, int divisor) { return 0L; }    // VALID overloaded version.
    }
    @Test
    public void question202() {
        System.out.println(new Calculations().findAverage(5));
        Assert.assertTrue(true);
    }

    /**
     * Question 203:
     *
     * Which of the following is a valid method name in Java? (Choose two.)
     *
     * NOTE: Method names may include underscore (_) character as well as the dollar ($) symbol.
     * A method name must start with a letter, the dollar ($) symbol, or an underscore (_) character.
     */
    void Go_$Outside$2() {}
    void $sprint() {}
    void _sprint() {}
    // void 9enjoyTheWeather() {}       ERROR: Method names cannot start with a number.
    // void new() {}                    ERROR: new is a reserved word in Java.
    // void walk#() {}
    // void have-Fun() {}

    /**
     * QUESTION 209:
     *
     * What is the output of the following application?
     */
    interface Ready {
        static int first = 2;
        final short DEFAULT_VALUE = 10;
        static final GetSet go = new GetSet();  // n1 ===>>> OK
    }
    static class GetSet implements Ready {
        int first = 5;
        static int second = DEFAULT_VALUE;      // n2 ===>>> OK
    //  static int test  = first;                  ERROR: Non-static field "first" cannot be referenced from a static context.
        static int test  = Ready.first;         // OK
               int test2 = first + 1;

        public static void main() {
            var r = new Ready() {};
            System.out.print(r.first);          // n3 PRINT: 2
            System.out.print(" " + second);     // n4 PRINT: 10
        }
    }
    @Test
    public void question209() {
        GetSet.main();
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 212
     */
    // class Rotorcraft                    ERROR: Class must be declared abstract or implement abstract method 'fly()'.
    static abstract class Rotorcraft {
        protected final int height = 5;
        abstract int fly();              // NOTE: Method 'fly()' has package-private access
    }
    interface CanFly {}
    static class Helicopter extends Rotorcraft implements CanFly {
        private int height = 10;
        protected int fly() {                                // NOTE: It can be protected, but not private.
            return super.height;
        }
        public static void main() {
            // Helicopter h = (Helicopter) new Rotorcraft();    ERROR: 'Rotorcraft' is abstract; cannot be instantiated.
        }
    }

    /**
     * QUESTION 213
     *
     * IMPORTANT: All the interfaces methods without a "private" modifier are implicitly "public"
     */
    interface Alex {
        default void write() { System.out.print("1"); }
        static void publish() {}
        void think();
        private int process() { return 80; }
    }
    interface Michael {
        default void write() { System.out.print("2"); }
        static void publish() {}
        void think();
        private int study() { return 100; }
    }
    static class Twins implements Alex, Michael {
        //     void write() { System.out.print("3"); }          ERROR: attempting to assign weaker access privileges.
        public void write() { System.out.print("3"); }
        //     void think() {                                   ERROR: attempting to assign weaker access privileges.
        public void think() { System.out.print("Thinking"); }
        static void publish() {}
    }
    @Test
    public void question213() {
        Twins twins = new Twins();
        twins.write();
        twins.think();
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 214
     */
    static class Electricity {
        interface Power {}
        public static void main() {
            class Source implements Power {};
            final class Super extends Source {};
            // var start = new Super() {};             ERROR: Cannot inherit from final.
            var end = new Source() {
            //  static       boolean t = true;      // ERROR: Static declarations in local classes are not supported.
                static final boolean t = true;
            };
        }
    }

    /**
     * QUESTION 215
     */
    static class ReadyQuestion {
        protected static int first = 2;
        private final short DEFAULT_VALUE = 10;             // NOTE: Non-static field variable declaration.
        private static class GetSet {
            int first = 5;
            // static int second = DEFAULT_VALUE;           ERROR: 'DEFAULT_VALUE' cannot be referenced from a static context.
            static int second = 20;
        }
        private GetSet go = new GetSet();
        public static void main() {
            ReadyQuestion r = new ReadyQuestion();
            System.out.print(r.go.first);
            System.out.print(", " + r.go.second);
        }
        public void ReadyQuestion() {
            // super();                                     ERROR: Call to 'super()' must be first statement in constructor body.
        }
    }

    /**
     * What is the output of the following application?
     */
    static abstract class Ball {
        protected final int size;
        public Ball(int size) {
            this.size = size;
        }
    }
    interface Equipment {}
    static class SoccerBall extends Ball implements Equipment {
        public SoccerBall() {
            super(5);
        }
        public Ball get() { return this; }
        public static void main() {
            var equipment = (Equipment) (Ball) new SoccerBall().get();
            System.out.println("Var equipment class: " + equipment.getClass().getSimpleName());  // PRINT: SoccerBall.
            System.out.println(((SoccerBall) equipment).size);                                   // PRINT: 5.
        }
    }
    @Test
    public void question219() {
        SoccerBall.main();
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 220
     */
    interface LongQuestion {    // NOTE: This can be named "Long", but then the class will not compile.
        Number length();
    }
    static class Elephant {
        public class Trunk implements LongQuestion {
            public Number length() { return 6; }            // k1
        }
        public class MyTrunk extends Trunk {                // k2:  NOTE THAT THIS IS NOT A STATIC CLASS.
            public Integer length() { return 9; }           // k3
        }
        public static void charge() {
            // System.out.print(new MyTrunk().length());    ERROR: 'this' cannot be referenced from a static context.
            // MyTrunk obj = new MyTrunk();                 NOTE: If 'MyTrunk' was declared static, then this compiles.
        }
        public static void main() {
            new Elephant().charge();                        // k4
        }
    }
}

