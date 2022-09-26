package com.hiperium.java.cert.prep.chapter._15;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class PrimitiveStreams {

    public static void main(String[] args) {
        // Calculate the numbers of a finite stream
        Stream<Integer> stream1 = Stream.of(1, 2, 3);
        System.out.println(stream1.reduce(0, Integer::sum));

        // Same example but this time using an IntStream for working with numeric data
        Stream<Integer> stream2 = Stream.of(1, 2, 3);
        System.out.println(stream2.mapToInt(x -> x).sum());

        // Average
        IntStream intStream1 = IntStream.of(1, 2, 3);
        OptionalDouble average = intStream1.average();
        System.out.println(average.getAsDouble());

        // Generate a range of numbers from 1 (inclusive) to 6 (exclusive)
        IntStream range1 = IntStream.range(1, 6);
        range1.forEach(System.out::println);            // PRINT: 1 2 3 4 5 6

        // Generate a range of numbers from 1 (inclusive) to 5 (inclusive)
        IntStream range2 = IntStream.rangeClosed(1, 5);
        range2.forEach(System.out::println);            // PRINT: 1 2 3 4 5

        mappingStream();
        usingOptionals();
        gettingStatistics();
    }

    /**
     * Mapping methods between types of streams
     *
     * *************************************************************************************************************|
     * Source stream class | To create Stream | To create DoubleStream | To create IntStream | To create LongStream |
     * ********************|******************|************************|*********************|**********************|
     * Stream<T>           | map()            | mapToDouble()          | mapToInt()          | mapToLong()          |
     * DoubleStream        | mapToObj()       | map()                  | mapToInt()          | mapToLong()          |
     * IntStream           | mapToObj()       | mapToDouble()          | map()               | mapToLong()          |
     * LongStream          | mapToObj()       | mapToDouble()          | mapToInt()          | map()                |
     * ********************|******************|************************|*********************|**********************|
     */
    public static void mappingStream() {
        // MAPPING A STRING STREAM TO A PRIMITIVE INT
        Stream<String> stream = Stream.of("penguin", "fish");
        // IntStream stream = stream.mapToInt(s -> s.length());    Replace with a Method Reference (MR).
        IntStream intStream = stream.mapToInt(String::length);
        intStream.forEach(System.out::println);                     // PRINT: 7 4

        // MAPPING TO PRIMITIVE USING A FLAT MAP
        var integerList = new ArrayList<Integer>(List.of(1, 3, 5, 7, 9));
        // IntStream        stream = integerList.stream().   flatMapToInt(x -> IntStream.of(x));    Replace with a MR.
        IntStream       intStream1 = integerList.stream().   flatMapToInt(IntStream::of);
        DoubleStream doubleStream1 = integerList.stream().flatMapToDouble(DoubleStream::of);
        LongStream     longStream1 = integerList.stream().  flatMapToLong(LongStream::of);
        intStream1.   forEach(System.out::println);
        doubleStream1.forEach(System.out::println);
        longStream1.  forEach(System.out::println);

        // MAPPING BACK TO STREAM OF OBJECTS
        IntStream       intStream2 = integerList.stream().   flatMapToInt(IntStream::of);
        DoubleStream doubleStream2 = integerList.stream().flatMapToDouble(DoubleStream::of);
        LongStream     longStream2 = integerList.stream().  flatMapToLong(LongStream::of);
        Stream<Integer> stream1 = intStream2.   boxed();
        Stream<Double>  stream2 = doubleStream2.boxed();
        Stream<Long>    stream3 = longStream2.  boxed();
        // Stream<Integer>    s = longStream2.  mapToObj(x -> x);   Using the 'mapToObj' version.
        stream1.forEach(System.out::println);
        stream2.forEach(System.out::println);
        stream3.forEach(System.out::println);
    }

    /**
     * Optional types for primitives:
     *
     * **********************************************************************************|
     *              ***               | OptionalDouble | OptionalInt    | OptionalLong   |
     * *******************************|****************|****************|****************|
     * Getting as a primitive         | getAsDouble()  | getAsInt()     | getAsLong()    |
     * orElseGet() parameter type     | DoubleSupplier | IntSupplier    | LongSupplier   |
     * Return type of max() and min() | OptionalDouble | OptionalInt    | OptionalLong   |
     * Return type of sum()           | double         | int            | long           |
     * Return type of average()       | OptionalDouble | OptionalDouble | OptionalDouble |
     * *******************************|****************|****************|****************|
     */
    public static void usingOptionals() {
        var stream = IntStream.rangeClosed(1, 10);
        /*
         * The return type is not the Optional we have accustomed to. It is a new type called OptionalDouble. Why do we
         * have a separate type? Why not just use Optional<Double>? The difference is that OptionalDouble is for a
         * primitive and Optional<Double> is for the Double wrapper class.
         */
        OptionalDouble optionalDouble = stream.average();
        optionalDouble.ifPresent(System.out::println);                      // PRINT: 5.5
        System.out.println(optionalDouble.getAsDouble());                   // PRINT: 5.5
        System.out.println(optionalDouble.orElseGet(() -> Double.NaN));     // PRINT: 5.5

        // Sum example
        LongStream longs = LongStream.of(5, 10);
        long sum = longs.sum();
        System.out.println("Sum LongStream: " + sum);
    }

    public static void gettingStatistics() {
        IntStream intStream = IntStream.rangeClosed(10, 50);
        IntSummaryStatistics statistics = intStream.summaryStatistics();
        if (statistics.getCount() == 0) throw new RuntimeException();
        System.out.println("Stat count: " + statistics.getCount());
        System.out.println("Stat min: " + statistics.getMin());
        System.out.println("Stat max: " + statistics.getMax());
        System.out.println("Stat avg: " + statistics.getAverage());
        System.out.println("Stat sum: " + statistics.getSum());
    }
}
