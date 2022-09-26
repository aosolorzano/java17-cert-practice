package com.hiperium.java.cert.prep.chapter._14;

import java.util.ArrayList;
import java.util.List;

class Elephant {}

class Crate<T> {
    private T contents;
    public T emptyCrate() {
        return this.contents;
    }
    public void packCrate(T contents) {
        this.contents = contents;
    }
    public <T> T tricky(T t) {      // SonarLint: Rename "T" which hides a type parameter from the outer scope.
        return t;
    }
}

class SizeLimitedCrate<T, U> {
    private T contents;
    private U sizeLimit;
    public SizeLimitedCrate(T contents, U sizeLimit) {
        this.contents = contents;
        this.sizeLimit = sizeLimit;
    }
}

/**
 * GENERIC INTERFACES
 */
interface Shippable<T> {
    void ship(T t);
}
class ShippableElephantCrate implements Shippable<Elephant> {
    @Override
    public void ship(Elephant elephant) {
        System.out.println("Shipping elephant: " + elephant);
    }
}

/**
 * A type parameter can be named anything you want. The convention is to use single uppercase letters to make it obvious
 * that they aren't real class names. The following are common letters to use:
 *      - E for an element.
 *      - K for a map key.
 *      - V for a map value.
 *      - N for a number.
 *      - T for a generic data type.
 */
public class Generics {

    public static void main(String[] args) {
        Elephant elephant = new Elephant();
        Crate<Elephant> crate = new Crate<>();
        crate.packCrate(elephant);

        // 2 GENERIC PARAMETERS
        Integer numPounds = 15_000;
        SizeLimitedCrate<Elephant, Integer> c1 = new SizeLimitedCrate<>(elephant, numPounds);

        // NOTE: When you have a method that declare a generic parameter type, it is independent of the class parameter
        // type. Take a look when a class like 'Crate', declares a generic T at both levels:
        Crate<Elephant> trickyCrate = new Crate<>();
        String result = trickyCrate.tricky("bot");
        System.out.println("Tricky result: " + result);     // PRINT: bot

        // UNBOUNDED WILDCARDS
        List<String> keywords = new ArrayList<>();
        keywords.add("java");
        printList(keywords);

        // UPPER-BOUNDED WILDCARDS
        List<Integer> numbers = new ArrayList<>();
        numbers.add(5);
        numbers.add(15);
        numbers.add(25);
        System.out.println("The total number upper-bounded wildcard method is: " + total(numbers));

        // UPPER-BOUNDED WILDCARDS WITH INTERFACES
        List<Flyer> flyers = new ArrayList<>();
        flyers.add(new Goose());
        flyers.add(new HangGlider());
        anyFlyer(flyers);
        groupOfFlyers(flyers);

        List<Goose> gooseList = new ArrayList<>();
        gooseList.add(new Goose());
        // anyFlyer(gooseList);                     ERROR: we cannot pass a List<Goose> to a List<Flyer>.
        groupOfFlyers(gooseList);

        List<HangGlider> hangGliders = new ArrayList<>();
        hangGliders.add(new HangGlider());
        // anyFlyer(hangGliders);                   ERROR: we cannot pass a List<HangGlider> to a List<Flyer>.
        groupOfFlyers(hangGliders);

        // LOWER-BOUNDED WILDCARDS
        List<String> strings = new ArrayList<>();
        strings.add("tweet");

        List<Object> objects = new ArrayList<>(strings);
        addSound(strings);
        addSound(objects);
    }

    /**
     * GENERIC METHODS:
     * <p>
     * This is often useful for static methods since they aren't part of an instance that can declare the type. However,
     * it is also allowed on non‐static methods.
     *
     * The method parameter is the generic type T. Before the return type, we declare the formal type parameter of <T>.
     * Unless a method is obtaining the generic formal type parameter from the class/interface, it is specified
     * immediately before the return type of the method.
     */
    public static <T> Crate<T> create() {
        return new Crate<>();
    }

    public static <T> Crate<T> ship(T t) {
        System.out.println("Shipping " + t);
        return new Crate<T>();
    }

    public static <T> void prepare(T t) {
        System.out.println("Preparing " + t);
    }

    public <T> void sink(T t) {
        System.out.println("Sinking " + t);
    }

    public <T> T identity(T t) {
        // return new T();          // ERROR: Type parameter 'T' cannot be instantiated directly.
        return t;
    }

    // public T noGood(T type)         ERROR: The formal parameter type <T> is omitted.
    public <T> T noGood(T t) {
        return t;
    }

    /**
     * BOUNDED GENERIC TYPES:
     *
     * By now, we might have noticed that generics don't seem particularly useful since they are treated as an Object
     * and therefore don't have many methods available. Bounded wildcards solve this by restricting what types can be
     * used in a wildcard. A bounded parameter type is a generic type that specifies a bound for the generic.
     *
     * UNBOUNDED WILDCARDS:
     * An unbounded wildcard represents any data type. We use "?" when we want to specify that any type is okay for us.
     */
    public static void printList(List<?> list) {
        for (Object x : list) {
            System.out.println("Registered keyword: " + x);
        }
    }

    /**
     * UPPER-BOUNDED WILDCARDS:
     *
     * We've established that a generic type can't just use a subclass.
     * ArrayList<Number> list = new ArrayList<Integer>(); ERROR: DOES NOT COMPILE
     *
     * Instead, we need to use a wildcard:
     * List<? extends Number> list = new ArrayList<Integer>();
     *
     * The upper‐bounded wildcard says, like next example, that any class that extends Number or Number itself, can be
     * used as the formal parameter type:
     */
    public static long total(List<? extends Number> list) {
        long count = 0;
        for (Number number : list) {
            count += number.longValue();
        }
        return count;
    }

    /**
     * NOTE: Something interesting happens when we work with upper-bounds or unbounded wildcards. The list becomes
     * logically immutable and therefore cannot be modified.
     */
    static class Bird {}
    static class Sparrow extends Bird { }

    /**
     * The problem stems from the fact that Java doesn't know what type List<? extends Bird> really is. It could be
     * List<Bird> or List<Sparrow> or some other generic type that hasn't even been written yet.
     */
    public static void immutableUpperBoundedList() {
        List<? extends Bird> birds = new ArrayList<Bird>();
        // birds.add(new Sparrow());        ERROR: we cannot add a Sparrow to List<? extends Bird>
        // birds.add(new Bird());           ERROR: we cannot add a Bird to List<Sparrow>

    }

    /**
     * UPPER-BOUNDED WILDCARDS USING INTERFACES
     */
    interface Flyer { void fly(); }
    static class HangGlider implements Flyer {
        @Override public void fly() {}
    }
    static class Goose implements Flyer {
        @Override public void fly() {}
    }

    /**
     * NOTE: a variable of type List<Flyer> can be passed to either method. A variable of type List<Goose> can be
     * passed only to the one with the upper bound.
     */
    private static void anyFlyer(List<Flyer> flyers) {
        System.out.println("Receiving any flyers: " + flyers);
    }
    private static void groupOfFlyers(List<? extends Flyer> flyers) {
        System.out.println("Receiving groups of HangGliders or Goose flyers: " + flyers);
    }

    /**
     * LOWER-BOUNDED WILDCARDS:
     *
     * With a lower bound, we are telling Java that the list, like next example, will be a list of String objects, or a
     * superclass of String. Either way, it is safe to add a String to that list.
     */
    public static void addSound(List<? super String> list) {
        System.out.println("Adding a 'Quack' to list: " + list);
        list.add("Quack");
    }
}
