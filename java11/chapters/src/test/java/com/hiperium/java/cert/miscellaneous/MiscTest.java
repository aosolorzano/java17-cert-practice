package com.hiperium.java.cert.miscellaneous;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MiscTest {

    /**
     * Which statement about a variable with the type of var are true?
     *
     * B. The variable can be assigned to null only after initial initialization.
     * F. A primitive or an Object can be used with the variable.
     */
    @Test
    public void usingVar() {
        var var = "";
        var = null;
        var num = 3;
        // num = null;                      ERROR >>> Required type: int -- Provided: null
        Assert.assertTrue(true);
    }

    /**
     * Which of the following statements about calling "this()" in a constructor are true?
     *
     * A. If arguments are provided to "this()", then there must be a constructor in the class able to take those
     *    arguments.
     * C. If the no‐argument "this()" is called, then the class must explicitly implement the no‐argument constructor.
     * F. If "this()" is used, it must be the first line of the constructor.
     */
    static class Animal {
        public Animal(String sound) {}
    }
    static class Dog extends Animal {
        public Dog() {
            super("woof");    // ERROR: There is no default constructor available in 'Animal'.
        }
        public Dog(String sound) {
            // this();              ERROR: Call to 'super()' must be first statement in constructor body.
            super(sound);
            // this();              ERROR: Call to 'this()' must be first statement in constructor body.
        }
        public Dog(String name, String sound) {
            // this();              ERROR: We cannot use 2 "this()" methods in the same constructor.
            // super(sound);        ERROR: Call to 'this()' must be first statement in constructor body.
            this(sound);
        }
    }
    @Test
    public void constructorInitialization() {
        new Dog("bark");
        Assert.assertTrue(true);
    }

    static int[][] game;
    @Test
    public void biDimensionalArray() {
    //  game[3][3] = 6;                     THROWS NullPointerException at runtime.
        game = new int[3][3];
    //  game[3][3] = 6;                     THROWS ArrayIndexOutOfBoundsException at runtime.
    //  game[3][3] = "X";                   ERROR >>> Required type: int - Provided: String.
        game[2][2] = 6;
        System.out.println("game.length = " + game.length);     // PRINT: 3
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.printf("%2s", game[i][j]);
            }
            System.out.println("");
        }
        Object[] obj = game;
        System.out.println("obj.length = " + obj.length);       // PRINT: 3
        for (Object o : obj) {
            int[] array = (int[]) o;
            System.out.println(Arrays.toString(array));
        }
    //  obj[2] = "X";                         VALID, but THROWS ArrayStoreException at runtime.
        Assert.assertTrue(true);
    }

    /**
     * offer()   : Inserts the specified element at the end of this deque. This method is equivalent to "offerLast()".
     * peek()    : Retrieves, but does not remove, the head of the queue represented by this deque, or returns null if
     *             this deque is empty. This method is equivalent to "peekFirst()".
     * pool()    : Retrieves and removes the head of the queue represented by this deque (the first element of this
     *             deque), or returns null if this deque is empty. This method is equivalent to "pollFirst()".
     * offerFirst: Inserts the specified element at the front of this deque.
     * offerLast : Inserts the specified element at the end of this deque.
     */
    @Test
    public void arrayDeque() {
        var q = new ArrayDeque<String>();
        q.offer("snowball");    // <<< HEAD
        q.offer("minnie");
        q.offer("sugar");       // <<< TAIL
        System.out.println("q.peek() = " + q.peek());   // PRINT: snowball
        System.out.println("q.peek() = " + q.peek());   // PRINT: snowball
        System.out.println("q.size() = " + q.size());   // PRINT: 3

        q.offerFirst("snowball");
        q.offer("sugar");
        q.offerLast("minnie");
        System.out.println("Deque: " + q);              // PRINT: [snowball, snowball, minnie, sugar, sugar, minnie]
        System.out.println(q.poll());                   // PRINT: snowball
        System.out.println(q.removeFirst());            // PRINT: snowball
        System.out.println("Deque: " + q);              // PRINT: [minnie, sugar, sugar, minnie]
        System.out.println(q.size());                   // PRINT: 4
        Assert.assertTrue(true);
    }

    /**
     * LinkedList is a Deque, or double-ended queue. This let us add elements at both ends.
     *
     * offer(): Adds the specified element as the tail (last element) of this list.
     * push() : Inserts the element at the front of this list. This method is equivalent to "addFirst()".
     * poll() : Retrieves and removes the head (first element) of this list.
     */
    @Test
    public void linkedList() {
        var x = new LinkedList<Integer>();
        x.offer(18);
        x.offer(5);     // <<< TAIL
        x.push(13);     // <<< HEAD
        System.out.println("x.poll() = " + x.poll());       // PRINT: 13
        System.out.println("x.poll() = " + x.poll());       // PRINT: 18
        Assert.assertTrue(true);
    }

    @Test
    public void treeMap() {

    }

    @Test
    public void treeSet() {

    }

    // TODO: Check this code.
    @Test
    public void lambdaWithEffectivelyFinalVariable() {
        var s = "fish";
        Predicate<String> pred = s::contains;               // NOTE: This code is valid, but the following not.
        // Predicate<String> pred2 = x -> s.contains(x);       ERROR: Variable "s" should be final or effectively final.
        s = "turtle";
        System.out.println("pred.test() = " + pred.test("is"));
    }

    @Test
    public void streamPipelineFlow() {
        System.out.println("First stream using string values: \n");
        var list = List.of("Toby", "Anna", "Leroy", "Alex");
        list.stream()
                .filter(n -> n.length() == 4)
                .peek(s -> System.out.println("Peak value after filter = " + s))        // Print all 3 values filtered.
                .sorted()                                                               // Sorted values and pass it 1-1.
                .peek(s -> System.out.println("Peak value after sorted = " + s))
                .limit(2)                                                               // Pass only 2 values to next step.
                .forEach(s -> System.out.println("ForEach value after limit = " + s));  // Print the 2 limited values.

        // ANOTHER STREAM WITH NUMBERS
        System.out.println("\nSecond stream using integer values: \n");
        var infinite = Stream.iterate(1, x -> x + 1);
        infinite.filter(x -> x % 2 == 1)                                                // Filter odd numbers only.
                .peek(s -> System.out.println("Peak value after filter = " + s))        // PRINT: 1 3 5 7 9
                .limit(5)
                .forEach(s -> System.out.println("ForEach value after limit = " + s));  // PRINT: 1 3 5 7 9

        // LIKE LAST ONE BUT USING SKIP() METHOD
        System.out.println("\nThird stream using integer values with skip(): \n");
        var infinite2 = Stream.iterate(1, x -> x + 1);
        infinite2.filter(x -> x % 2 == 1)                                               // Filter odd numbers only.
                .peek(s -> System.out.println("Peak value after filter = " + s))        // PRINT: 1 3 5 7 9
                .limit(5)
                .peek(s -> System.out.println("Peak value after limit = " + s))         // PRINT: 5 7 9
                .skip(2)                                                                // Skip the 2 first elements.
                .forEach(s -> System.out.println("ForEach value after limit = " + s));  // PRINT: 5 7 9
        Assert.assertTrue(true);
    }

    @Test
    public void streamReduce() {
        Stream<String> stream1 = Stream.of("w", "o", "l", "f");
        var result1 = stream1.reduce("",
                (s, c) -> {
                    String accumulator = s + c;
                    System.out.println("s = " + s + ", c = " + c + " -->> " + accumulator);
                    return accumulator;
        });
        System.out.println("result1 = " + result1 + "\n");

        // ANOTHER EXAMPLES WITH INTEGER VALUES
        var result2 = Stream.of(3,5,6).reduce(1,
                (a, b) -> {
                    int accumulator = a * b;
                    System.out.println("a = " + a + ", b = " + b + " -->> " + accumulator);
                    return accumulator;
        });
        System.out.println("result2 = " + result2 + "\n");

        // When we do not provide an identity, an Optional is returned
        BinaryOperator<Integer> op = (a, b) -> {
            int accumulator = a * b;
            System.out.println("a = " + a + ", b = " + b + " -->> " + accumulator);
            return accumulator;
        };
        Stream<Integer> stream3 = Stream.of(3,5,6);
        stream3.reduce(op)
                .ifPresent((result3) -> System.out.println("result3 = " + result3 + "\n"));

        // USING A DIFFERENT RETURN TYPE
        Stream<String> stream4 = Stream.of("w", "o", "l", "f!");
        int result4 = stream4.parallel().reduce(0,
                (i, s) -> {
                    int accumulator = i + s.length();
                    System.out.println("i = " + i + ", s = " + s + ", accumulator -->> " + accumulator);
                    return accumulator;
                },
                (a, b) -> {                 // NOTE: Called if the stream is parallel.
                    int combiner = a + b;
                    System.out.println("a = " + a + ", b = " + b + ", combiner -->> " + combiner);
                    return combiner;
        });
        System.out.println("result4 = " + result4);
        Assert.assertTrue(true);
    }

    @Test
    public void streamCollect() {
        System.out.println("Using StringBuilder");
        Stream<String> stream1 = Stream.of("w", "o", "l", "f");
        StringBuilder result1 = stream1.collect(
                StringBuilder::new,
                (a, b) -> {
                    System.out.println("a = " + a + ", b = " + b);
                    a.append(b);
                    System.out.println("accumulator -->> " + a);
                }, (x, y) -> {                                          // NOTE: Called if the stream is parallel.
                    System.out.println("x = " + x + ", y = " + y);
                    x.append(y);
                    System.out.println("combiner -->> " + x);
                }
        );
        /*
        * SAME AS BEFORE BUT USING A METHOD REFERENCE:
        *
        StringBuilder result1 = stream2.collect(
                StringBuilder::new,
                StringBuilder::append,
                StringBuilder::append
        ); */
        System.out.println("result1 = " + result1 + "\n");

        // USING TREE-SET
        System.out.println("Using TreeSet");
        Stream<String> stream2 = Stream.of("w", "o", "l", "f");
        TreeSet<String> result2 = stream2.parallel().collect(
                TreeSet::new,
                (a, b) -> {
                    System.out.println("a = " + a + ", b = " + b);
                    a.add(b);
                    System.out.println("accumulator -->> " + a);
                }, (x, y) -> {                                          // NOTE: Called if the stream is parallel.
                    System.out.println("x = " + x + ", y = " + y);
                    x.addAll(y);
                    System.out.println("combiner -->> " + x);
                }
        );
        /*
        * SAME AS BEFORE BUT USING A METHOD REFERENCE:
        *
        TreeSet<String> result2 = stream2.collect(
                TreeSet::new,
                TreeSet::add,
                TreeSet::addAll
        ); */
        System.out.println("result2 = " + result2);
        Assert.assertTrue(true);
    }

    /**
     * partitioningBy(): Returns a Collector which partitions the input elements according to a Predicate, and organizes
     * them into a Map<Boolean, List<T>>. The returned Map always contains mappings for both false and true keys.
     */
    @Test
    public void usingByPartitioningCollector() {
        Stream.of("eeny", "meeny", "miny", "moe")
                .collect(Collectors.partitioningBy(x -> x.charAt(0) == 'e'))
                .get(false)
                .stream()
                .peek(x -> System.out.println("Second stream value = " + x))        // meeny miny moe
                .collect(Collectors.partitioningBy(x -> x.length() == 4))
                .get(true)
                .forEach(x -> System.out.println("Third stream value = " + x));     // miny
        Assert.assertTrue(true);
    }

    /**
     * groupingBy(): The collector produces a Map<K, List<T>> whose keys are the values resulting from applying the
     * classification function to the input elements, and whose corresponding values are Lists containing the input
     * elements which map to the associated key under the classification function.
     */
    @Test
    public void usingGroupingByCollector() {
        Stream.of("eeny", "meeny", "miny", "moe")
                .collect(Collectors.groupingBy(x -> x.startsWith("e")))
                .get(false)
                .stream()
                .peek(x -> System.out.println("Second stream value = " + x))        // meeny miny moe
                .collect(Collectors.groupingBy(String::length))
                .get(4)
                .forEach(x -> System.out.println("Third stream value = " + x));     // miny
        Assert.assertTrue(true);
    }

    @Test
    public void primitiveFunctionalInterfaces() {
        IntFunction<Integer> if1 = (v) -> null;
        IntFunction<Integer> if2 = (s) -> s;
        IntFunction<Double>  if3 = (int d) -> (double) d + 1;
        IntFunction<Long>    if4 = (int l) -> (long) l;

        ToIntFunction<Integer> tif1 = (v) -> 3;                         // Cannot return null. Must return an "int".
        ToIntFunction<Integer> tif2 = (Integer s) -> s;
        ToIntFunction<Double>  tif3 = (Double d) -> (int) (d + 1);
        // ToIntFunction<Long> tif4 = (l) -> l.intValue();              Lambda can be replaced with method reference.
        ToIntFunction<Long>    tif4 = Long::intValue;

        Assert.assertTrue(true);
    }
}
