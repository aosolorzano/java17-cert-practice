package com.hiperium.java.cert.prep.chapter._18;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * A parallel stream is a stream that is capable of processing results concurrently, using multiple threads. For example,
 * you can use a parallel stream and the map() operation to operate concurrently on the elements in the stream, vastly
 * improving performance over processing a single element at a time.
 *
 * The number of threads available in a parallel stream is proportional to the number of available CPUs in your
 * environment.
 */
public class ParallelStreams {

    public static void main(String[] args) {
        serialStream();
        parallelDecomposition();
        combiningResultsWithReduce();
        combiningResultsWithCollect();
        parallelReductionOnCollectors();
        avoidStatefulOperations();
    }

    /**
     * A parallel decomposition is the process of taking a task, breaking it up into smaller pieces that can be performed
     * concurrently, and then reassembling the results. The more concurrent a decomposition, the greater the performance
     * improvement of using parallel streams.
     *
     * We can pretend that in a real application the next method, might be calling a database or reading a file.
     */
    private static int doWork(int input) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return input;
    }

    private static void serialStream() {
        long start = System.currentTimeMillis();
        List.of(1,2,3)
                .stream()
                .map(w -> doWork(w))
                .forEach(s -> System.out.print(s + " "));                   // PRINT: 1 2 3
        System.out.println();
        var timeTaken = (System.currentTimeMillis() - start) / 1000;
        System.out.println("Serial stream time taken: " + timeTaken);       // PRINT: 6
    }

    /**
     * With a parallel stream, the map() and forEach() operations are applied concurrently. The results are no longer
     * ordered or predictable. The map() and forEach() operations on a parallel stream are equivalent to submitting
     * multiple Runnable lambda expressions to a pooled thread executor and then waiting for the results.
     *
     * What about the time required? In this case, our system had enough CPUs for all the tasks to be run concurrently.
     * The key is that we've written our code to take advantage of parallel processing when available, so our job is done.
     */
    private static void parallelDecomposition() {
        long start = System.currentTimeMillis();
        List.of(1,2,3,4,5)
                .parallelStream()
                .map(ParallelStreams::doWork)
                .forEach(s -> System.out.print(s + " "));                   // PRINT: 1 2 5 4 3
        System.out.println();
        var timeTaken = (System.currentTimeMillis() - start) / 1000;
        System.out.println("Parallel stream time taken: " + timeTaken);     // PRINT: 2
    }

    /**
     * The stream operation "reduce()" combines a stream into a single object. Recall the signature of the method:
     *
     *     <U> U reduce(U identity,
     *          BiFunction<U,? super T,U> accumulator,
     *          BinaryOperator<U> combiner)
     *
     * In the next example, in a serial stream, "wolf" word is built one character at a time. In a parallel stream, the
     * intermediate values "wo" and "lf" are created and then combined.
     */
    private static void combiningResultsWithReduce() {
        // SERIAL STREAM: using the "identity" and "accumulator" parameters only.
        String word = List.of('w','o','l','f')
                .stream()
                .reduce("",
                        // We used 'c' for char, whereas s1, s2, and s3 for String values.
                        (s1, c) ->  { System.out.println("s1: " + s1 + ", c: " + c);   return s1 + c; },
                        (s2, s3) -> { System.out.println("s2: " + s2 + ", s3: " + s3); return s2 + s3; }
                );
        System.out.println("Serial reduction: " + word);

        // USING PARALLEL STREAM: using the "identity", "accumulator" and "combinator" parameters.
        word = List.of('w','o','l','f')
                .parallelStream()
                .reduce("",
                        (s1, c) ->  { System.out.println("s1: " + s1 + ", c: " + c);   return s1 + c; },
                        (s2, s3) -> { System.out.println("s2: " + s2 + ", s3: " + s3); return s2 + s3; }
                );
        System.out.println("Parallel reduction: " + word);
        problematicAccumulator();
    }

    /**
     * Let's take a look at an example using a problematic accumulator. In particular, order matters when subtracting
     * numbers; therefore, the following code can output different values depending on whether you use a serial or
     * parallel stream.
     */
    private static void problematicAccumulator() {
        System.out.println(List.of(1,2,3,4,5,6)
                .parallelStream()
                .reduce(0, (a,b) -> (a - b)));

        // You can see other problems if we use an identity parameter that is not truly an identity value.
        System.out.println(List.of("w","o","l","f")
                .parallelStream()
                .reduce("X", String::concat));
        // On a serial stream, it prints "Xwolf", but on a parallel stream the result is "XwXoXlXf". As part of the
        // parallel process, the identity is applied to multiple elements in the stream, resulting in unexpected data.
    }

    /**
     * Like reduce(), the Stream API includes a three‐argument version of collect() that takes accumulator and combiner
     * operators, along with a supplier operator instead of an identity.
     *
     *      <R> R collect(Supplier<R> supplier,
     *          BiConsumer<R, ? super T> accumulator,
     *          BiConsumer<R, R> combiner)
     *
     * Also, like reduce(), the accumulator and combiner operations must be able to process results in any order.
     *
     * Recall that elements in a ConcurrentSkipListSet are sorted according to their natural ordering. You should use a
     * concurrent collection to combine the results, ensuring that the results of concurrent threads do not cause a
     * ConcurrentModificationException.
     */
    private static void combiningResultsWithCollect() {
        Stream<String> stream = Stream.of("w","o","l","f").parallel();
        SortedSet<String> set = stream.collect(
                ConcurrentSkipListSet::new,
                Set::add,
                Set::addAll);
        System.out.println("Stream collect operation: " + set);     // PRINT: [f, l, o, w]
    }

    /**
     * When using a Collector to perform a parallel reduction, a number of properties must be true. Otherwise, the
     * collect() operation will execute in a single‐threaded fashion:
     *
     *      1. The stream is parallel.
     *      2. The parameter of the collect() operation has the Characteristics.CONCURRENT characteristic.
     *      3. Either, the stream is unordered or the collector has the characteristic Characteristics.UNORDERED.
     *
     * The Collectors class includes two sets of static methods for retrieving collectors: "toConcurrentMap()" and
     * "groupingByConcurrent()", that are both UNORDERED and CONCURRENT. These methods produce Collector instances
     * capable of performing parallel reductions efficiently.
     */
    private static void parallelReductionOnCollectors() {
        // USING CONCURRENT MAP
        Stream<String> stream1 = Stream.of("lions", "tigers", "bears").parallel();
        ConcurrentMap<Integer, String> map1 = stream1.collect(
                Collectors.toConcurrentMap(
                        String::length,
                        k -> k,
                        (s1, s2) -> s1 + ", " + s2));
        System.out.println(map1);                            // PRINT: '{5=lions, bears, 6=tigers}'.
        System.out.println(map1.getClass());                 // PRINT: java.util.concurrent.ConcurrentHashMap.

        // USING GROUPING BY CONCURRENT
        var stream2 = Stream.of("lions", "tigers", "bears").parallel();
        ConcurrentMap<Integer, List<String>> map2 = stream2.collect(
                Collectors.groupingByConcurrent(String::length)
        );
        System.out.println(map2);                            // PRINT: '{5=[lions, bears], 6=[tigers]}'.
        System.out.println(map2.getClass());                 // PRINT: java.util.concurrent.ConcurrentHashMap.
    }

    /**
     * AVOIDING STATEFUL OPERATIONS:
     *
     * Side effects can appear in parallel streams if your lambda expressions are stateful. A stateful lambda expression
     * is one whose result depends on any state that might change during the execution of a pipeline.
     *
     * In the following method, with a serial stream send in the parameter, the output will be:
     *
     *      [2, 4, 6, 8, 10]
     *
     * But with a parallel stream, the output could be:
     *
     *      [6, 8, 10, 2, 4]
     *
     * The problem is that our lambda expression is stateful and modifies a list that is outside our stream. We could
     * use "forEachOrdered()" to add elements to the list, but that forces the parallel stream to be serial, potentially
     * losing concurrency enhancements.
     */
    private static List<Integer> addValues(IntStream source) {
        var data = Collections.synchronizedList(new ArrayList<Integer>());
        source.filter(s -> s % 2 == 0)
                .forEach(i -> { data.add(i); });  // STATEFUL: DON'T DO THIS!
        return data;
    }

    /**
     * We can fix this solution by rewriting our stream operation to no longer have a stateful lambda expression. This
     * method processes the stream and then collects all the results into a new list. It produces the same result on
     * both serial and parallel streams:
     *
     *      [2, 4, 6, 8, 10]
     *
     * This implementation removes the stateful operation and relies on the collector to assemble the elements. We could
     * also use a concurrent collector to parallelize the building of the list. The goal is to write our code to allow
     * for parallel processing and let the JVM handle the rest.
     */
    private static List<Integer> addValuesFixed(IntStream source) {
        return source.filter(s -> s % 2 == 0)
                .boxed()
                .collect(Collectors.toList());
    }

    /**
     * NOTE: It is strongly recommended that you avoid stateful operations when using parallel streams, to remove any
     * potential data side effects. In fact, they should be avoided in serial streams since doing so limits the code's
     * ability to someday take advantage of parallelization.
     */
    private static void avoidStatefulOperations() {
        var list1 = addValues(IntStream.range(1, 11));
        System.out.println("First method with serial stream: " + list1);            // PRINT: [2, 4, 6, 8, 10]

        var list2 = addValues(IntStream.range(1, 11).parallel());
        System.out.println("First method with parallel stream: " + list2);          // PRINT: [6, 4, 2, 8, 10]

        var list3 = addValuesFixed(IntStream.range(1, 11));
        System.out.println("Second method with serial stream: " + list3);           // PRINT: [2, 4, 6, 8, 10]

        var list4 = addValuesFixed(IntStream.range(1, 11).parallel());
        System.out.println("Second method with parallel stream: " + list4);         // PRINT: [2, 4, 6, 8, 10]
    }
}
