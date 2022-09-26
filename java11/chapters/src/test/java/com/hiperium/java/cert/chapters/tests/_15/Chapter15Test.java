package com.hiperium.java.cert.chapters.tests._15;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.*;

public class Chapter15Test {

    /**
     * What could be the output of the following?
     */
    @Test
    public void question1() {
        var stream = Stream.iterate("", s -> s + "1");  // '' 1 11 111 1111 1111...
        // System.out.println(stream.limit(2).map(x -> x + "2"));           PRINT: ReferencePipeline$3@aecb35a
        stream.limit(5).map(x -> x + "2").forEach(System.out::println);     // 2 12 112 1112 11112
        Assert.assertTrue(true);
    }

    @Test(expected = IllegalStateException.class)
    public void question3() {
        Predicate<String> predicate = s -> s.length() > 3;
        var stream = Stream.iterate("-", s -> !s.isEmpty(), s -> s + s);
        var b1 = stream.noneMatch(predicate);
        System.out.println("b1: " + b1);                 // PRINT: false
        var b2 = stream.anyMatch(predicate);    // Stream has already been operated upon or closed.
    }

    /**
     * Which of the following sets result to 8.0?
     */
    @Test
    public void question5() {
        double result1 = LongStream.of(6L, 8L, 10L)
                .mapToInt(x -> (int) x)
                .boxed()
                .collect(Collectors.groupingBy(x -> x))
                .keySet()
                .stream()
                .collect(Collectors.averagingInt(x -> x));
        System.out.println(result1);                            // PRINT: 8.0

        double result2 = LongStream.of(6L, 8L, 10L)
                .mapToInt(x -> (int) x)
                .boxed()
                .collect(Collectors.groupingBy(x -> x, Collectors.toSet()))
                .keySet()
                .stream()
                .collect(Collectors.averagingInt(x -> x));
        System.out.println(result2);                            // PRINT: 8.0
        Assert.assertTrue(true);
    }

    /**
     * Which of the following can fill in the blank so that the code prints out false?
     *      var s = Stream.generate(() -> "meow");
     *      var match = s._________________(String::isEmpty);
     *      System.out.println(match);
     */
    @Test
    public void question6() {
        var s = Stream.generate(() -> "meow");
        // var match = s.anyMatch(String::isEmpty);             It runs indefinitely
        // var match = s.noneMatch(String::isEmpty);            It runs indefinitely
        var match = s.allMatch(String::isEmpty);    // The processed ones match.
        System.out.println(match);
        Assert.assertTrue(true);
    }

    /**
     * We have a method that returns a sorted list without changing the original. Which of the following can replace
     * the method implementation to do the same with streams?
     *
     * private static List<String> sort(List<String> list) {
     *      var copy = new ArrayList<String>(list);
     *      Collections.sort(copy, (a, b) -> b.compareTo(a));
     *      return copy;
     * }
     *
     */
    @Test
    public void question7() {
        var list = new ArrayList<String>(List.of("lions", "tigers", "bears"));
        list.stream()
                .sorted((a, b) -> b.compareTo(a))
                .collect(Collectors.toList())
                .forEach(System.out::println);      // PRINT: tigers lions bears
        Assert.assertTrue(true);
    }

    /**
     * Which of the following are true given this declaration?
     */
    @Test
    public void question8() {
        var stream1 = IntStream.empty();
        OptionalDouble avg  = stream1.average();
        System.out.println("Average: " + avg);          // PRINT: OptionalDouble.empty

        var stream2 = IntStream.empty();
        OptionalInt findAny = stream2.findAny();
        System.out.println("FindAny: " + findAny);      // PRINT: OptionalInt.empty

        var stream3 = IntStream.empty();
        int sum = stream3.sum();
        System.out.println("Sum: " + sum);              // PRINT: 0
        Assert.assertTrue(true);
    }

    /**
     * Which of the following can we add after line 6 for the code to run without error and not produce any output?
     *      4: var stream = LongStream.of(1, 2, 3);
     *      5: var opt = stream.map(n -> n * 10)
     *      6:    .filter(n -> n < 5).findFirst();
     */
    @Test
    public void question9() {
        var stream = LongStream.of(1, 2, 3);
        var opt = stream.map(n -> n * 10)
                .filter(n -> n < 5).findFirst();
        if (opt.isPresent())
            // System.out.println(opt.get());       ERROR: Cannot resolve method 'get' in 'OptionalLong'
            System.out.println(opt.getAsLong());
        opt.ifPresent(System.out::println);
        Assert.assertTrue(true);
    }

    /**
     * Given the four statements (L, M, N, O), select and order the ones that would complete the expression and cause
     * the code to output 10 lines.
     *
     *    Stream.generate(() -> "1")
     *        L: .filter(x -> x.length()> 1)
     *        M: .forEach(System.out::println)
     *        N: .limit(10)
     *        O: .peek(System.out::println);
     */
    @Test
    public void question10() {
        Stream.generate(() -> "1")
                .limit(10)
                .peek(System.out::println);     // PRINT: Nothing. ==>> Result of 'Stream.peek()' is ignored.
        Stream.generate(() -> "1")
                .limit(10)
                .forEach(System.out::println);  // PRINT: 1 1 1 1 1 1 1 1 1 1 (Terminal Operation).
        Assert.assertTrue(true);
    }

    /**
     * What changes need to be made together for this code to print the string 12345?
     */
    @Test
    public void question11() {
        System.out.println(
                Stream.iterate(1, x -> ++x)             // PRINT: 12345
        //      Stream.iterate(1, x -> x++)                     PRINT: 11111
                        .limit(5)
                        .map(x -> "" + x)
                        .collect(Collectors.joining())
        //              .forEach(System.out::println);          ERROR: Cannot resolve method 'forEach' in 'String'.
        );
        Assert.assertTrue(true);
    }

    /**
     * Which of the following is true?
     */
    @Test
    public void question13() {
        List<Integer>x1 = List.of(1,2,3);
        List<Integer>x2 = List.of(4,5,6);
        List<Integer>x3 = List.of();
        // Stream.of(x1, x2, x3).map(x -> x + 1)    ERROR: Operator '+' cannot be applied to 'List<java.lang.Integer>'.
        Stream.of(x1, x2, x3)
                .flatMap(x -> x.stream())
                .map(x -> x + 1)
                .forEach(System.out::println);      // PRINT: 2 3 4 5 6 7
        Assert.assertTrue(true);
    }

    /**
     * Which of the following is true?
     *
     *      4: Stream<Integer> s = Stream.of(1);
     *      5: IntStream is = s.boxed();
     *      6: DoubleStream ds = s.mapToDouble(x -> x);
     *      7: Stream<Integer> s2 = ds.mapToInt(x -> x);
     *      8: s2.forEach(System.out::print);
     */
    @Test(expected = IllegalStateException.class)
    public void question14() {
        Stream<Integer> s = Stream.of(1);
        // IntStream is = s.boxed();                        ERROR: Cannot resolve method 'boxed' in 'Stream'.
        IntStream is = s.mapToInt(x -> x);
        DoubleStream ds = s.mapToDouble(x -> x);         // RTE: stream has already been operated upon or closed
        // Stream<Integer> s2 = ds.mapToInt(x -> x);        ERROR: double cannot be converted to int.
        // Stream<Integer> s2 = ds.mapToInt(x -> (int) x);  ERROR: Required type: Stream<Integer> - Provided: IntStream.
        Stream<Integer> s2 = ds.boxed()
                               .map(Double::intValue);
        s2.forEach(System.out::print);
    }

    /**
     * Which of the following statements are true about this code? (Choose all that apply.)
     *     20: Predicate<String> empty = String::isEmpty;
     *     21: Predicate<String> notEmpty = empty.negate();
     *     22:
     *     23: var result = Stream.generate(() -> "")
     *     24:    .limit(10)
     *     25:    .filter(notEmpty)
     *     26:    .collect(Collectors.groupingBy(k -> k))
     *     27:    .entrySet()
     *     28:    .stream()
     *     29:    .map(Entry::getValue)
     *     30:    .flatMap(Collection::stream)
     *     31:    .collect(Collectors.partitioningBy(notEmpty));
     *     32: System.out.println(result);
     */
    @Test
    public void question16() {
        Predicate<String> empty = String::isEmpty;
        Predicate<String> notEmpty = empty.negate();
        var result = Stream.generate(() -> "")
                .limit(10)
                .filter(notEmpty)
                .collect(Collectors.groupingBy(k -> k))
                .entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .collect(Collectors.partitioningBy(notEmpty));
        System.out.println(result);                             // PRINT: {false=[], true=[]}

        // FIRST PART
        Stream<String> stream = Stream.iterate("", s -> s + "1");
        Map<String, Set<String>> map = stream
                .limit(5)
                .filter(notEmpty)
                .collect(Collectors.groupingBy(
                        k -> k,
                        TreeMap::new,
                        Collectors.toSet()));
        System.out.println("First Map: " + map);            // PRINT: {1=[1], 11=[11], 111=[111], 1111=[1111]}

        // SECOND PART
        System.out.println("EntrySet: " + map.entrySet());  // PRINT: [1=[1], 11=[11], 111=[111], 1111=[1111]]
        var resultMap = map.entrySet()
                .stream()
                .map(Map.Entry::getValue)                       // Stream<Set<String>>
        //      .forEach(System.out::println)                      [1] [11] [111] [1111]
                .flatMap(Collection::stream)                    // Stream<String>
        //      .forEach(System.out::println)                      1 11 111 1111
                .collect(Collectors.partitioningBy(notEmpty));
        System.out.println("Result Map: " + resultMap);         // PRINT: {false=[], true=[1, 11, 111, 1111]}

        // C. If we changed line 31 from partitioningBy(notEmpty) to groupingBy(n ‐> n), it would output: {}
        var result2 = Stream.generate(() -> "")
                .limit(10)
                .filter(notEmpty)
                .collect(Collectors.groupingBy(k -> k))
                .entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(n -> n));
        System.out.println("Result 2: " + result2);             // PRINT: {}

        Assert.assertTrue(true);
    }

    /**
     * What is the result of the following?
     *
     *      var s = DoubleStream.of(1.2, 2.4);
     *      s.peek(System.out::println).filter(x -> x> 2).count();
     *
     * R./ 1.2 and 2.4
     * If we print the returned value of count() method, it will be 1.
     */
    @Test
    public void question18() {
        var s = DoubleStream.of(1.2, 2.4);
        System.out.println("Count: " + s.peek(System.out::println).filter(x -> x > 2).count());
        Assert.assertTrue(true);
    }
}
