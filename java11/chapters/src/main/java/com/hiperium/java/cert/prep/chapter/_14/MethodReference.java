package com.hiperium.java.cert.prep.chapter._14;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MethodReference {

    private static String[] names = {"Tom", "Dick", "Harry"};

    public static void main(String[] args) {
        callingStaticMethod();
        callingAnInstanceMethod();
        callingConstructor();
    }

    public static void callingStaticMethod() {
        // List<Integer> list = List.of(9,6,3,1);           UnsupportedOperationException: Sorting an immutable list.
        List<Integer> list = Arrays.asList(9, 6, 3, 1);     // Fixed-size list. We should not add or remove elements.
        // list.add(12);                                    UnsupportedOperationException: Changing a fixed-size list.

        // CREATING A CONSUMER TO ORDER A FIXED-SIZE INT LIST
        Consumer<List<Integer>> methodReference = Collections::sort;    // list -> Collections.sort(list)
        methodReference.accept(list);
        System.out.println("Ordered Integer list: " + list);

        // CREATING A CONSUMER TO ORDER A FIXED-SIZE STRING LIST
        List<String> list2 = Arrays.asList(names);                      // 1. Create a fixed-size of String list.
        Consumer<List<String>> methodReference2 = Collections::sort;    // 2. list -> Collections.sort(list)
        System.out.println("Ordered String list: " + list2);
    }

    public static void callingAnInstanceMethod() {
        var random = new Random();                          // 1. Create the needed instance.
        Supplier<Integer> supplier = random::nextInt;       // 2. Call the instance from inside the function.
        Integer randomValue = supplier.get();
        System.out.println("Random value: " + randomValue);

        var str = "abc";                                    // 1. Create the needed instance.
        Predicate<String> predicate = str::startsWith;      // 2. (s) -> str.startsWith(s)
        System.out.println("Does the 'abc' string contains letter 'a'?: " + predicate.test("a"));
        System.out.println("Does the 'abc' string contains letter 'x'?: " + predicate.test("x"));
    }

    public static void callingConstructor() {
        Supplier<List<String>> supplier = ArrayList::new;               // 1. Create a new ArrayList from a Supplier.
        List<String> list = supplier.get();                             // 2. Return the newly created ArrayList.
        System.out.println("New array list from supplier: " + list);

        Function<Integer, List<String>> function = ArrayList::new;
        System.out.println("New array list with initial capacity (3) from function: " + function.apply(3));
    }
}
