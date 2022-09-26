package com.hiperium.java.cert.practice.tests;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * WORKING WITH STREAMS AND LAMBDA EXPRESSIONS:
 *
 *      - Implement functional interfaces using lambda expressions, including interfaces from the java.util.function.
 *      - Use Java Streams to filter, transform and process data.
 *      - Perform decomposition and reduction, including grouping and partitioning on sequential and parallel streams.
 *
 *
 * STATEFUL LAMBDA EXPRESSION should be avoided with both serial and parallel streams because they can lead to
 * unintended side effects. A common way top remove a stateful lambda expression the modifies a List, is to have a
 * stream operation that outputs a new List.
 *
 * LAZY EVALUATION delays the execution of a stream pipeline until the terminal operation is executed.
 *
 *
 * COMMON FUNCTIONAL INTERFACES:
 *
 * ***********************************************************************|
 * Functional interface | Return type   | Method name   | # of parameters |
 * ---------------------|---------------|---------------|-----------------|
 * Supplier<T>          | T             | get()         | 0               |
 * Consumer<T>          | void          | accept(T)     | 1 (T)           |
 * BiConsumer<T, U>     | void          | accept(T, U)  | 2 (T, U)        |
 * Predicate<T>         | boolean       | test(T)       | 1 (T)           |
 * BiPredicate<T, U>    | boolean       | test(T, U)    | 2 (T, U)        |
 * Function<T, R>       | R             | apply(T)      | 1 (T)           |
 * BiFunction<T, U, R>  | R             | apply(T, U)   | 2 (T, U)        |
 * UnaryOperator<T>     | T             | apply(T)      | 1 (T)           |
 * BinaryOperator<T>    | T             | apply(T, T)   | 2 (T, T)        |
 * ---------------------|---------------|---------------|-----------------|
 *
 * PREDICATE AND BI-PREDICATE:
 *      Predicate<String> p1 = String::isEmpty;
 *      BiPredicate<String, String> p2 = (s1, s2) -> s1.startsWith(s2);
 *      BiPredicate<String, String> p2 = String::startsWith;
 *
 * FUNCTION AND BI-FUNCTION:
 *      Function<String, Integer> f1 = x -> x.length();
 *      Function<String, Integer> f1 = String::length;
 *      BiFunction<String, String, String> f2 = (s1, s2) -> s1.concat(s2);
 *      BiFunction<String, String, String> f2 = String::concat;
 *
 * UNARY-OPERATOR AND BINARY-OPERATOR:
 *      public interface UnaryOperator<T>  extends Function<T, T> {}
 *      public interface BinaryOperator<T> extends BiFunction<T, T, T> {}
 *
 *      UnaryOperator<String>  u1 = x -> x.toUpperCase();
 *      UnaryOperator<String>  u1 = String::toUpperCase;
 *      BinaryOperator<String> b1 = (s1, s2) -> s1.concat(s2);
 *      BinaryOperator<String> b1 = String::concat;
 *
 *
 * CONVENIENCE METHODS:
 *
 * ********************************************************************************|
 * Interface instance  |  Method return type  |  Method name |  Method parameters  |
 * --------------------|----------------------|--------------|---------------------|
 * Consumer            |  Consumer            |  andThen()   |  Consumer           |
 * Function            |  Function            |  andThen()   |  Function           |
 * Function            |  Function            |  compose()   |  Function           |
 * Predicate           |  Predicate           |  and()       |  Predicate          |
 * Predicate           |  Predicate           |  negate()    |      —              |
 * Predicate           |  Predicate           |  or()        |  Predicate          |
 * --------------------------------------------------------------------------------|
 *
 *
 * OPTIONAL INSTANCE METHODS:
 *
 * **************************************************************************************************|
 * Method                  |  When Optional is empty             |  When Optional contains a value   |
 * ------------------------|-------------------------------------|-----------------------------------|
 * get()                   |  Throws an exception                |  Returns value                    |
 * ifPresent(Consumer c)   |  Does nothing                       |  Calls Consumer with value        |
 * isPresent()             |  Returns false                      |  Returns true                     |
 * orElse(T other)         |  Returns other parameter            |  Returns value                    |
 * orElseGet(Supplier s)   |  Returns result of calling Supplier |  Returns value                    |
 * orElseThrow()           |  Throws NoSuchElementException      |  Returns value                    |
 * orElseThrow(Supplier s) |  Throws exception created by        |  Returns value                    |
 *                         |  calling a Supplier                 |                                   |
 * --------------------------------------------------------------------------------------------------|
 *
 *
 * COMMON PRIMITIVE STREAM METHODS:
 *
 * ********************************************|******************|**************************************************|
 * METHOD                                      | PRIMITIVE STREAM | DESCRIPTION                                      |
 * ********************************************|******************|**************************************************|
 *                                             |                  |                                                  |
 * OptionalDouble average()                    | IntStream        | The arithmetic mean of the elements.             |
 *                                             | LongStream       |                                                  |
 *                                             | DoubleStream     |                                                  |
 * --------------------------------------------|------------------|--------------------------------------------------|
 * Stream<T> boxed()                           | IntStream        | A Stream<T> where T is the wrapper class         |
 *                                             | LongStream       | associated with the primitive value.             |
 *                                             | DoubleStream     |                                                  |
 * --------------------------------------------|------------------|--------------------------------------------------|
 * OptionalInt max()                           | IntStream        | The maximum element of the stream.               |
 * OptionalLong max()                          | LongStream       |                                                  |
 * OptionalDouble max()                        | DoubleStream     |                                                  |
 * --------------------------------------------|------------------|--------------------------------------------------|
 * OptionalInt min()                           | IntStream        | The minimum element of the stream.               |
 * OptionalLong min()                          | LongStream       |                                                  |
 * OptionalDouble min()                        | DoubleStream     |                                                  |
 * --------------------------------------------|------------------|--------------------------------------------------|
 * IntStream range(int a, int b)               | IntStream        | Returns a primitive stream from a (inclusive) to |
 * LongStream range(long a, long b)            | LongStream       | b (exclusive).                                   |
 * --------------------------------------------|------------------|--------------------------------------------------|
 * IntStream rangeClosed(int a, int b)         | IntStream        | Returns a primitive stream from a (inclusive) to |
 * LongStream rangeClosed(long a, long b)      | LongStream       | b (inclusive).                                   |
 * --------------------------------------------|------------------|--------------------------------------------------|
 * int sum()                                   | IntStream        | Returns the sum of the elements in the stream.   |
 * long sum()                                  | LongStream       |                                                  |
 * double sum()                                | DoubleStream     |                                                  |
 * --------------------------------------------|------------------|--------------------------------------------------|
 * IntSummaryStatistics summaryStatistics()    | IntStream        | Returns an object containing numerous stream     |
 * LongSummaryStatistics summaryStatistics()   | LongStream       | statistics such as the average, min, max, etc.   |
 * DoubleSummaryStatistics summaryStatistics() | DoubleStream     |                                                  |
 * --------------------------------------------|------------------|--------------------------------------------------|
 *
 *
 * OPTIONAL TYPES FOR PRIMITIVES:
 *
 * ************************************************************************************|
 *              ---                | OptionalDouble  | OptionalInt    | OptionalLong   |
 * ********************************|*****************|****************|****************|
 * Getting as a primitive          | getAsDouble()   | getAsInt()     | getAsLong()    |
 * --------------------------------|-----------------|----------------|----------------|
 * orElseGet() parameter type      | DoubleSupplier  | IntSupplier    | LongSupplier   |
 * --------------------------------|-----------------|----------------|----------------|
 * Return type of max() and min()  | OptionalDouble  | OptionalInt    | OptionalLong   |
 * --------------------------------|-----------------|----------------|----------------|
 * Return type of sum()            | double          | int            | long           |
 * --------------------------------|-----------------|----------------|----------------|
 * Return type of average()        | OptionalDouble  | OptionalDouble | OptionalDouble |
 * --------------------------------|-----------------|----------------|----------------|
 *
 *
 * COMMON FUNCTIONAL INTERFACES FOR PRIMITIVES:
 *
 * **********************|*********************|*************|************************|
 * Functional interfaces | # parameters        | Return type | Single abstract method |
 * **********************|*********************|*************|************************|
 * DoubleSupplier        | 0                   | double      | getAsDouble            |
 * IntSupplier           |                     | int         | getAsInt               |
 * LongSupplier          |                     | long        | getAsLong              |
 * ----------------------|---------------------|-------------|------------------------|
 * DoubleConsumer        | 1 ( double)         | void        | accept                 |
 * IntConsumer           | 1 ( int)            |             |                        |
 * LongConsumer          | 1 ( long)           |             |                        |
 * ----------------------|---------------------|-------------|------------------------|
 * DoublePredicate       | 1 ( double)         | boolean     | test                   |
 * IntPredicate          | 1 ( int)            |             |                        |
 * LongPredicate         | 1 ( long)           |             |                        |
 * ----------------------|---------------------|-------------|------------------------|
 * DoubleFunction<R>     | 1 ( double)         | R           | apply                  |
 * IntFunction<R>        | 1 ( int)            |             |                        |
 * LongFunction<R>       | 1 ( long)           |             |                        |
 * ----------------------|---------------------|-------------|------------------------|
 * DoubleUnaryOperator   | 1 ( double)         | double      | applyAsDouble          |
 * IntUnaryOperator      | 1 ( int)            | int         | applyAsInt             |
 * LongUnaryOperator     | 1 ( long)           | long        | applyAsLong            |
 * ----------------------|---------------------|-------------|------------------------|
 * DoubleBinaryOperator  | 2 ( double, double) | double      | applyAsDouble          |
 * IntBinaryOperator     | 2 ( int, int)       | int         | applyAsInt             |
 * LongBinaryOperator    | 2 ( long, long)     | long        | applyAsLong            |
 * ----------------------|---------------------|-------------|------------------------|
 *
 *
 * PRIMITIVE SPECIFIC FUNCTIONAL INTERFACES:
 *
 * **************************|***************|*************|************************|
 * Functional interfaces     | # parameters  | Return type | Single abstract method |
 * **************************|***************|*************|************************|
 * ToDoubleFunction<T>       | 1 ( T)        | double      | applyAsDouble          |
 * ToIntFunction<T>          |               | int         | applyAsInt             |
 * ToLongFunction<T>         |               | long        | applyAsLong            |
 * --------------------------|---------------|-------------|------------------------|
 * ToDoubleBiFunction<T, U>  | 2 ( T, U)     | double      | applyAsDouble          |
 * ToIntBiFunction<T, U>     |               | int         | applyAsInt             |
 * ToLongBiFunction<T, U>    |               | long        | applyAsLong            |
 * --------------------------|---------------|-------------|------------------------|
 * DoubleToIntFunction       | 1 ( double)   | int         | applyAsInt             |
 * DoubleToLongFunction      | 1 ( double)   | long        | applyAsLong            |
 * IntToDoubleFunction       | 1 ( int)      | double      | applyAsDouble          |
 * IntToLongFunction         | 1 ( int)      | long        | applyAsLong            |
 * LongToDoubleFunction      | 1 ( long)     | double      | applyAsDouble          |
 * LongToIntFunction         | 1 ( long)     | int         | applyAsInt             |
 * --------------------------|---------------|-------------|------------------------|
 * ObjDoubleConsumer<T>      | 2 (T, double) | void        | accept                 |
 * ObjIntConsumer<T>         | 2 (T, int)    |             |                        |
 * ObjLongConsumer<T>        | 2 (T, long)   |             |                        |
 * --------------------------|---------------|-------------|------------------------|
 *
 */
public class Chapter6Test {

    /**
     * What is the result of executing the following application multiple times?
     *
     * F. None of the above. The code does not compile.
     */
    @Test
    public void question4() {
        List.of(1,2,3,4).stream()
                .forEach(System.out::println);          // PRINT: 1 2 3 4
        List.of(1,2,3,4).stream().parallel()
                .forEach(System.out::println);          // PRINT: 3 4 2 1
        List.of(1,2,3,4).stream().parallel()
                .forEachOrdered(System.out::println);   // PRINT: 1 2 3 4
        Assert.assertTrue(true);
    }

    /**
     * What does the following output?
     */
    @Test
    public void question6() {
        var list = new ArrayList<String>();
        list.add("Austin");
        list.add("Boston");
        list.add("San Francisco");
        var c = list.stream()
                .filter(a -> a.length() > 10)
                .count();
        System.out.println(c + " -- " + list.size());   // PRINT: 1 -- 3
        Assert.assertTrue(true);
    }

    /**
     * Which are true of the following?
     *
     * B. One of the lines fails to compile or throw an exception.
     * D. None of these returns true when calling opt.isPresent().
     */
    @Test(expected = NullPointerException.class)
    public void question12() {
        var empty = Optional.empty();
        var param = Optional.of(null);              // It throws NPE if value is null.
        var method = Optional.ofNullable(null);     // It returns an empty optional if value is null.
    }

    /**
     * Fill in the blank with the functional interface that allows the code to compile and print 3 at runtime.
     *
     * C. ToIntFunction<Integer>
     */
    @Test
    public void question15() {
        // ___________________ transformer = x -> x;
        ToIntFunction<Integer> transformer = x -> x;
        var prime = List.of(3,1,4,1,5,9)
                .stream()
                .limit(1)
                .peek(s -> {})
                .mapToInt(transformer)
                .peek(s -> {})
                .sum();
        System.out.println("prime = " + prime);
        Assert.assertTrue(true);
    }

    /**
     * What is the result of the following?
     *
     * D. The code does not compile.
     */
    @Test
    public void question17() {
        var list = new ArrayList<>();
        list.add("Monday");
        // list.add(String::new);       ERROR: Object is not a functional interface.
        list.add("Tuesday");
        list.remove(0);
        System.out.println("list(0) = " + list.get(0));
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 20:
     *
     * Which statements about the following application are correct?
     *
     * C. The class compiles.
     * D. The first number printed is consistently 100;
     */
    private static class TicketTaker {
        long ticketsSold;
        final AtomicInteger ticketsTaken;
        public TicketTaker() {
            this.ticketsSold = 0;
            this.ticketsTaken = new AtomicInteger(0);
        }
        public void performJob() {
            IntStream.iterate(1, p -> p + 1)
                    .parallel()
                    .limit(100)
                    .forEach(i -> ticketsTaken.getAndIncrement());
            IntStream.iterate(1, q -> q + 1)
                    .parallel()
                    .limit(500)
                    .forEach(i -> ++ticketsSold);
            System.out.println("ticketsTaken = " + ticketsTaken);
            System.out.println("ticketsSold = " + ticketsSold);
        }
    }
    @Test
    public void question20() {
        new TicketTaker().performJob();
        Assert.assertTrue(true);
    }

    /**
     * What does the following output?
     *
     * F. the code does not compile.
     */
    @Test
    public void question30() {
        Set<String> set = new HashSet<>();
        set.add("tire-");
        List<String> list = new ArrayList<>();
        Deque<String> queue = new ArrayDeque<>();
        queue.push("wheel-");
        Stream.of(set, list, queue)
        //      .flatMap(x -> x)                ERROR: flatMap() works with Streams rather than Collections.
        //      .flatMap(x -> x.stream())       Fixing the problem.
                .flatMap(Collection::stream)
                .forEach(System.out::println);
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 32:
     *
     * What is the output of the following application?
     *
     * D. The code does not compile for a different reason.
     */
    private static class TicketTaker2 {
        private static int AT_CAPACITY = 100;
        // public int takeTicket(int currentCount, IntUnaryOperator<Integer> counter)       ERROR: Fixed bellow.
        public int takeTicket(int currentCount, IntUnaryOperator counter) {
            return counter.applyAsInt(currentCount);
        }
    }
    @Test
    public void question32() {
        final TicketTaker2 bob = new TicketTaker2();
        final int oldCount = 50;
        final int newCount = bob.takeTicket(oldCount,
                t -> {
                   if (t > TicketTaker2.AT_CAPACITY)
                       throw new RuntimeException("Sorry, max has been reached.");
                   return t + 1;
                }
        );
        System.out.println("newCount = " + newCount);       // PRINT: newCount = 51
        Assert.assertTrue(true);
    }

    /**
     * What is true about the following code?
     *
     * C. 2 lines do not compile.
     * E. If any lines that do not compile are fixed, the output includes "pink".
     */
    private static class Question34 {
        public static void run(String... s) {
            // Predicate dash = c -> c.startsWith("-");             ERROR: Cannot resolve method 'startsWith' in 'Object'.
            Predicate<String> dash = c -> c.startsWith("-");
            System.out.println(dash.test("-"));

            Consumer clear = System.out::println;
            clear.accept("pink");

            // Comparator<String> c = (String s, String t) -> 0;    ERROR: Variable 's' is already defined in the scope.
            Comparator<String> c = (String u, String t) -> 0;
            System.out.println(c.compare("s", "t"));
        }
    }
    @Test
    public void question34() {
        Question34.run("");
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 37:
     *
     * Fill in the blanks so that both methods produce the same output for all inputs.
     *
     * D. isPresent(), ifPresent()
     *
     */
    private static void longer(Optional<Boolean> opt) {
        // if (opt.______())
        if (opt.isPresent())                            // If a value is present, returns true, otherwise false.
            System.out.println("run: " + opt.get());
    }
    private static void shorter(Optional<Boolean> opt) {
        opt.map(x -> "run: " + x)
        //      ._________();
                .ifPresent(System.out::println);        // If a value is present, performs the given action.
    }

    /**
     * What is the output of the following application?
     *
     * B. aCataHat HatCat
     */
    public String concat1(List<String> values) {
        return values.parallelStream()
                .reduce("a",
                //      (x, y) -> x + y,
                        (x, y) -> {
                            System.out.println("x = " + x + ", y = " + y);
                            return x + y;
                        },
                //      String::concat);
                        (a, b) -> {
                            System.out.println("a = " + a + ", b = " + b);
                            return a.concat(b);
                        }
                );
    }
    /**
     * get(): If a value is present, returns the value, otherwise throws NoSuchElementException.
     */
    public String concat2(List<String> values) {
        return values.parallelStream()
                .reduce((w, z) -> z + w)    // NOTE: We are using a reduction without an identity parameter.
                .get();                     // WARN: 'Optional.get()' without 'isPresent()' check.
    }
    @Test
    public void question43() {
        var c = this;
        var list = List.of("Cat", "Hat");
        String x = c.concat1(list);
        String y = c.concat2(list);
        System.out.println("x = " + x + ", y = " + y);
        Assert.assertTrue(true);
    }

    /**
     * Fill in the blanks so this code prints *8.0-8.0*?
     */
    @Test
    public void question51() {
        var ints = IntStream.of(6, 10);
        var longs = ints.mapToLong(i -> i);
        // var first = longs._______________________;           COMPLETE THE BLANK
        var first = longs.average().getAsDouble();

        var moreLongs = LongStream.of(6, 10);
        var stats = moreLongs
                .summaryStatistics();
        // var second = ________________;                       COMPLETE THE BLANK
        var second = stats.getAverage();

        System.out.println("*" + first + "-" + second + "*");
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 54:
     *
     * What is the possible output of the following application?
     *
     * A. {1975=[Escort], 1967=[Mustang, Thunderbird]}
     */
    private static class Car {
        private String model;
        private int year;
        public Car(String model, int year) {
            this.model = model;
            this.year = year;
        }
        public int getYear() { return year; }
        public String toString() { return model; }

        public static void main(String[] args) {
            var cars = new ArrayList<Car>();                    // NOTE: We need to specify the array type.
            cars.add(new Car("Mustang", 1967));
            cars.add(new Car("Thunderbird", 1967));
            cars.add(new Car("Escort", 1975));
            var map = cars.stream()
                    .collect(Collectors.groupingByConcurrent(Car::getYear));
            System.out.println("map = " + map);
        }
    }
    @Test
    public void question54() {
        Car.main(null);
        Assert.assertTrue(true);
    }

    /**
     * What is true o the following?
     *
     * C. The output is: 4 bark
     * F. If "meow" was replaced by null reference, the output would change.
     *
     * NOTE: For the last answer, the code could throw a NullPointerException, since we need to check the length of the
     * String to determine witch part of the map it goes in.
     */
    @Test
    public void question57() {
        var s = Stream.of("speak", "bark", "meow", "growl");
        BinaryOperator<String> merge = (a, b) -> a;
        var map = s.collect(Collectors.toMap(String::length, k -> k, merge));
        System.out.println("map.size() = " + map.size() + ", map.get(4) = " + map.get(4));
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 62:
     *
     * What is the output of the following application?
     *
     * C. The code does not compile for a different reason.
     *
     * NOTE: If the code was corrected, at runtime, it would then produce an infinite loop. On each iteration of the
     * loop, a new Doll instance would be created with 5, since the post-decrement operator returns the original value
     * of the variable.
     */
    private static class Doll {
        private int layer;
        public Doll(int layer) {
            super();
            this.layer = layer;
        }
        public static void open(UnaryOperator<Doll> task, Doll doll) {
            // while((doll = task.accept(doll)) != null) {    ERROR: Cannot resolve method 'accept' in 'UnaryOperator'.
            // while((doll = task.apply(doll))  != null) {    This code produces an infinity loop at runtime.
            while((doll = task.apply(doll)) == null) {
                System.out.print("X");
            }
        }
        public static void main(String[] wood) {
            open(s -> {
                if (s.layer <= 0) return null;
                else return new Doll(s.layer--);
            }, new Doll(5));
        }
    }
    @Test
    public void question62() {
        Doll.main(null);
        Assert.assertTrue(true);
    }

    /**
     * What is the expected output of the following code?
     *
     * D. It prints 4 numbers twice each.
     *
     * skip(): Returns a stream consisting of the remaining elements of this stream after discarding the first "n"
     * elements of the stream. If this stream contains fewer than n elements then an empty stream will be returned.
     *
     * limit(): Returns a stream consisting of the elements of this stream, truncated to be no longer than maxSize in
     * length.
     *
     * NOTE: As an element goes through the pipeline, it is printed twice, once by the peek() method, and once by the
     * forEach() method.
     *
     */
    @Test
    public void question63() {
        Random r = new Random();
        Stream.generate(r::nextDouble)
                .skip(2)
                .limit(4)
                .sorted()
                .peek(System.out::println)          // 0.26, 0.82, 0.84, 0.85
                .forEach(System.out::println);      // 0.26, 0.82, 0.84, 0.85
        Assert.assertTrue(true);
    }

    /**
     * Given the following independent stream operations, which statements are correct?
     *
     * B. The second stream operation compiles.
     * C. The third stream operation compiles.
     * D. None of the stream operations that compile produce an exception at runtime.
     */
    @Test
    public void question67() {
        List.of(2, 4, 6, 8)
        //      .parallel()                                 ERROR: Cannot resolve method 'parallel' in 'List'.
                .parallelStream()
                .forEach(System.out::println);

        System.out.println("\nSecond stream example: ");
        List.of(2, 4, 6, 8)
                .parallelStream()
                .parallel()
                .forEach(System.out::println);

        System.out.println("\nThird stream example: ");
        List.of(2, 4, 6, 8)
                .parallelStream()
                .parallel().parallel().parallel()           // Allowed but unnecessary.
                .forEach(System.out::println);
        Assert.assertTrue(true);
    }

    /**
     * What is the result of the following?
     *
     * C. 19
     */
    class Ballot {
        private String name;
        private int judgeNumber;
        private int score;
        public Ballot(String name, int judgeNumber, int score) {
            this.name = name;
            this.judgeNumber = judgeNumber;
            this.score = score;
        }
        public String getName() { return name; }
        public int getScore() { return score; }
    }
    /**
     * Collectors.summingInt(): Returns a Collector that produces the sum of an integer-valued function applied to the
     * input elements. If no elements are present, the result is 0.
     */
    @Test
    public void question69() {
        Stream<Ballot> ballots = Stream.of(
          new Ballot("Mario", 1, 10),
          new Ballot("Christina", 1, 8),
          new Ballot("Mario", 2, 9),
          new Ballot("Christina", 2, 8)
        );
        var scores = ballots.collect(
          Collectors.groupingBy(Ballot::getName, Collectors.summingInt(Ballot::getScore))
        );
        System.out.println("scores.get(\"Mario\") = " + scores.get("Mario"));
        Assert.assertTrue(true);
    }

    /**
     * Which of the following can fill in the blank to have the code print 44?
     *
     * D. Both, map and mapToInt.
     *
     * NOTE: There is not a "mapToObject()" method in the Stream API. There is a similar method named "mapToObj()"
     * method on IntStream.
     */
    @Test
    public void question70() {
        var stream = Stream.of("base", "ball");
        //stream._(s -> s.length()).forEach(System.out::println);       To complete
        stream.map(String::length).forEach(System.out::println);
        Assert.assertTrue(true);
    }

    /**
     * What does the following do?
     *
     * C. It compiles without issue.
     */
    private static class Shoot {
        interface Target {
            boolean needToAim(double angle);
        }
        static void prepare(double angle, Target t) {
            boolean ready = t.needToAim(angle);             // k1
            System.out.println("ready = " + ready);
        }
    }
    @Test
    public void question71() {
        Shoot.prepare(45, d -> d > 5 || d < -5);        // k2
        Assert.assertTrue(true);
    }

    /**
     * Which statements about the following code are correct?
     *
     * C. It consistently prints another value.
     *
     * NOTE: The identity is 1, which is applied to every element meaning the operation sums the values (1+1), (1+2) and
     * (1+3). For this reason, 9 is consistently printed at runtime.
     */
    @Test
    public void question72() {
        var data = List.of(1,2,3);
        int f = data.parallelStream()
                .reduce(1,
                        (a, b) -> {
                            System.out.println("accumulator >>> a = " + a + ", b = " + b);
                            return a + b;
                        },
                        (a, b) -> {
                            System.out.println("combiner >>> a = " + a + ", b = " + b);
                            return a + b;
                        });
        System.out.println("f = " + f);
        Assert.assertTrue(true);
    }

    /**
     * What is the result of the following?
     *
     * E. The code compiles but throws an exception at runtime.
     *
     * NOTE: The average() method returns an OptionalDouble, and because the "s1" stream is empty, then the optional is
     * also empty. When we are trying to get the value of the optional, the code throws a NoSuchElementException at
     * runtime.
     */
    @Test(expected = NoSuchElementException.class)
    public void question73() {
        var s2 = IntStream.of(-1, 0, 1);
        System.out.println("s2.average().getAsDouble() = " + s2.average().getAsDouble());   // PRINT: 0.0

        var s1 = IntStream.empty();
        System.out.println("s1.average().getAsDouble() = " + s1.average().getAsDouble());   // ERROR at Runtime.
    }

    /**
     * QUESTION 77:
     *
     * What is the output of the following application?
     *
     */
    static class Tourist {
        public double distance;
        public Tourist(double distance) {
            this.distance = distance;
        }
    }
    static class Lifeguard {
        private void saveLife(Predicate<Tourist> canSave, Tourist tourist) {
            System.out.println(canSave.test(tourist)? "Saved" : "Too far");         // PRINT: Saved
        }
        public static void main(String[] args) {
            new Lifeguard().saveLife(s -> s.distance < 4, new Tourist(2));
        }
    }
    @Test
    public void question77() {
        Lifeguard.main(null);
        Assert.assertTrue(true);
    }

    /**
     * How many of these lines have compiler errors?
     */
    @Test
    public void question81() {
        // Consumer<Object> c1 = ArrayList::new;    ERROR: ArrayList have no constructor that take an Object.
        // Consumer<Object> c1 = String::new;       ERROR: String have no constructor that take an Object.
        Consumer<Object> c3 = System.out::println;
        // var c4 = ArrayList::new;                 Cannot infer type: method reference requires an explicit target type.
        // var c5 = String::new;                    Cannot infer type: method reference requires an explicit target type.
        // var c5 = System.out::println;            Cannot infer type: method reference requires an explicit target type.
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following program?
     */
    @Test
    public void question82() {
        var p = List.of(new StringBuilder("hello"), new StringBuilder("goodbye"));
        var q = p.parallelStream()
                .reduce(0,
        //              (w, x) -> w.length() + x.length(),    ERROR: Cannot resolve method 'length' in 'Integer'.
                        (w, x) -> w + x.length(),
        //              (y, z) -> y.length() + z.length());   ERROR: Cannot resolve method 'length' in 'Integer'.
                        Integer::sum);
        System.out.println("q = " + q);

        // TESTING
        System.out.println("Testing 1 details...");
        var r = Stream.of(new StringBuilder("hello"), new StringBuilder("goodbye")).parallel()
                        .reduce(0,
                                (w, x) -> {
                                    System.out.println("Accumulator >>> w = " + w + ", x = " + x);
                                    return w + x.length();
                                },
                                (y, z) -> {
                                    System.out.println("Combiner >>> y = " + y + ", z = " + z);
                                    return y + z;
                                });
        System.out.println("r = " + r);

        System.out.println("Testing 2 alternative...");
        var s = Stream.of(new StringBuilder("hello"), new StringBuilder("goodbye"))
        //      .mapToInt(v -> v.length())      We cannot use mapToInt before to apply a Collect that uses Object values.
                .map(v -> v.length())
                .collect(Collectors.summingInt(i -> i));
        System.out.println("s = " + s);

        System.out.println("Testing 3 alternative...");
        var t = Stream.of(new StringBuilder("hello"), new StringBuilder("goodbye"))
                .mapToInt(v -> v.length())
                .sum();
        System.out.println("t = " + t);
        Assert.assertTrue(true);
    }

    /**
     * What is true of this code?
     *
     * C. It outputs {false=[], true=[true]}.
     * F. The output is different after line k is removed.
     *
     * IMPORTANT: If line K is removed, a NullPointerException will be thrown at runtime in the collect method, since
     * null is neither true nor false.
     */
    @Test
    public void question83() {
        var bools = Stream.of(Boolean.TRUE, null);
        var map = bools
                .limit(1)                                           // line k
                .collect(Collectors.partitioningBy(b -> b));
        System.out.println("map = " + map);
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following code?
     *
     * flatMapToInt(): Returns an IntStream consisting of the results of replacing each element of this stream with the
     * contents of a mapped stream produced by applying the provided mapping function to each element.
     */
    @Test
    public void question84() {
        var apples = List.of(1, 2);
        var oranges = List.of(1, 2);
        final var count = Stream.of(apples, oranges)
        //      .flatMapToInt(List::stream)                         ERROR: cannot convert Stream<Integer> to IntStream.
                .flatMapToInt(x -> x.stream().mapToInt(i -> i))
                .peek(System.out::print)                            // PRINT: 1212
                .count();
        System.out.println("\ncount = " + count);                   // PRINT: 4
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following code?
     */
    @Test
    public void question87() {
        // IntegerSummaryStatistics stats = Stream.of(20, 40)   ERROR: Cannot resolve symbol 'IntegerSummaryStatistics'.
        IntSummaryStatistics stats = Stream.of(20, 40)
                .mapToInt(i -> i)
                .summaryStatistics();
        long total = stats.getSum();
        long count = stats.getCount();
        long max = stats.getMax();
        System.out.println("total = " + total);
        System.out.println("count = " + count);
        System.out.println("max = " + max);
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 90:
     *
     * What is true of the following?
     *
     * B. If filling in the blank with method "x", the code prints a stack trace.
     * C. If filling in the blank with method "y", the code outputs "Caught it".
     * C. If filling in the blank with method "z", the code outputs "Caught it".
     *
     */
    static class Catch {
        public static void main(String[] args) {
            Optional opt = Optional.empty();
            var message = "";
            try {
                message = z(opt);                           // Unchecked assignment: 'Optional' to 'Optional<String>'.
            } catch (IllegalArgumentException e) {
                System.out.println("Caught it");
            }
            System.out.println("message = " + message);
        }
        private static String x (Optional<String> opt) {
            return opt.orElseThrow();                       // throw NoSuchElementException if no value is present.
        }
        private static String y (Optional<String> opt) {
            return opt.orElseThrow(IllegalArgumentException::new);
        }
        private static String z (Optional<String> opt) {
            return opt.orElse("Caught it");
        }
    }

    /**
     * How many changes need to be made to the following stream operation to execute a parallel reduction?
     *
     * C. Two
     *
     * limit() API NOTE: While limit() is generally a cheap operation on sequential stream pipelines, it can be quite
     * expensive on ordered parallel pipelines, especially for large values of maxSize, since limit(n) is constrained to
     * return not just any n elements, but the first n elements in the encounter order. Using an unordered stream source
     * (such as generate(Supplier)) or removing the ordering constraint with unordered(), may result in significant
     * speedups of limit() in parallel pipelines, if the semantics of your situation permit. If consistency with
     * encounter order is required, and you are experiencing poor performance or memory utilization with limit() in
     * parallel pipelines, switching to sequential execution with sequential() may improve performance.
     */
    @Test
    public void question93() {
        var r = new Random();
        var data = Stream.generate(() -> String.valueOf(r.nextInt()))
        //      .limit(50_000_000)
                .limit(50)
                .collect(Collectors.toSet());
        // var map = data.stream()                                  // 1. We should use a parallel stream.
        data.parallelStream()
        //      .collect(Collectors.groupingBy(String::length));    // 2. We should use a grouping by concurrent.
                .collect(Collectors
                        .groupingByConcurrent(String::length))
                        .forEach((k, v) -> System.out.println("key = " + k + ", value = " + v));
        Assert.assertTrue(true);
    }

    /**
     * What is the output of this code?
     */
    @Test
    public void question94() {
        Stream.of("one", "two", "bloat")
                .limit(1)
                .map(String::toUpperCase)       // line x
                .sorted()
                .forEach(System.out::println);  // PRINT: ONE
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following?
     *
     * NOTE: the reverseOrder() take zero parameters instead of the required two, and does not implement the Comparator
     * interface. For these reasons, it cannot be used as method reference.
     */
    @Test
    public void question98() {
        var s = Stream.of("over the river",
                "through the woods",
                "to grandmother's house we go");
        s.filter(n -> n.startsWith("t"))
        //      .sorted(Comparator::reverseOrder)       ERROR: reverseOrder() take 0 parameters instead of 2.
                .sorted((s1, s2) -> s2.compareTo(s1))
                .findFirst()
                .ifPresent(System.out::println);        // PRINT: to grandmother's house we go.
        Assert.assertTrue(true);
    }

    /**
     * Given the following code, what statement about the values printed on lines q1 and q2 is correct?
     *
     * forEachOrdered(): This operation processes the elements one at a time, in encounter order if one exists.
     * Performing the action for one element happens-before performing the action for subsequent elements, but for any
     * given element, the action may be performed in whatever thread the library chooses.
     */
    @Test
    public void question106() {
        var mitchWorkout = new CopyOnWriteArrayList<Integer>();
        List.of(1,5,7,9).stream().parallel()
                .forEach(mitchWorkout::add);
        // mitchWorkout.forEachOrdered(System.out::println);        q1 ERROR: Cannot resolve method 'forEachOrdered'.
        System.out.println("ForEach in CopyOnWriteArrayList: ");
        mitchWorkout.forEach(System.out::println);                  // PRINT: 7 9 5 1

        System.out.println("Using forEachOrdered: ");
        List.of(1,5,7,9).stream().parallel()
                .filter(x -> x % 2 == 1)                            // For testing purposes.
                .forEachOrdered(System.out::println);               // q2: 1 5 7 9
        Assert.assertTrue(true);
    }

    /**
     * Which of the following can fill in the blank to have the code print out "*"?
     *
     * distinct(): Returns a stream consisting of the distinct elements (according to Object.equals(Object)) of this
     * stream. For ordered streams, the selection of distinct elements is stable (for duplicated elements, the element
     * appearing first in the encounter order is preserved.) For unordered streams, no stability guarantees are made.
     *
     * This is a stateful intermediate operation.
     */
    @Test
    public void question107() {
        Stream.generate(() -> "*")
                .limit(3)
        //      .sorted(_______)
                .sorted((s, t) -> s.length() - t.length())
                .distinct()
                .forEach(System.out::println);
        Assert.assertTrue(true);
    }

    /**
     * What is true of the following code?
     */
    @Test(expected = IllegalStateException.class)
    public void question113() {
        Stream<Integer> s1 = Stream.of(8, 2);
        Stream<Integer> s2 = Stream.of(10, 20);
        s2 = s1.filter(n -> n > 4);
        s1 = s2.filter(n -> n < 1);
        System.out.println("s1.count() = " + s1.count());   // PRINT: 0
        System.out.println("s2.count() = " + s2.count());   // ERROR: stream has already been operated upon or closed.
    }

    /**
     * What this code output?
     *
     * asList(): Returns a fixed-size list backed by the specified array..
     */
    @Test
    public void question115() {
        var babies = Arrays.asList("chick", "cygnet", "duckling");
        babies.replaceAll(x -> { var newValue = "baby"; return newValue; });
        // System.out.println(newValue);            ERROR: Cannot resolve symbol 'newValue'.
        // babies.add("baby");                      ERROR: UnsupportedOperationException at runtime.
        System.out.println("babies = " + babies);   // PRINT: [baby, baby, baby]
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following code?
     */
    @Test
    public void question130() {
        var pears = List.of(1,2,3,4,5,6);
        final var sum = pears.stream()
                .skip(1)
                .limit(3)
        //      .flatMapToInt(s -> IntStream.of(s))             Lambda can be replaced with method reference.
                .flatMapToInt(IntStream::of)
                .skip(1)
                .boxed()
                .mapToDouble(s -> s)
                .sum();
        System.out.println("sum = " + sum);                     // PRINT: 7.0
        Assert.assertTrue(true);
    }

    /**
     * Which method reference can replace the lambda on the second line, so the output is the same?
     */
    @Test
    public void question135() {
        var s = "fish";
        // Predicate<String> pred = (a) -> s.contains(a);   Lambda can be replaced with method reference.
        Predicate<String> pred = s::contains;
        s = "turtle";
        System.out.println("pred.test(\"is\") = " + pred.test("is"));   // PRINT: true
    }

    /**
     * What is the output of the following application?
     *
     * removeIf(): Removes all the elements of this collection that satisfy the given predicate. Return true if any
     * elements were removed.
     */
    static class DogSearch {
        void reduceList(List<String> names, Predicate<String> tester) {
            names.removeIf(tester);
        }
        public static void main(String[] treats) {
            int MAX_LENGTH = 2;                                         // Effectively final variable
            DogSearch search = new DogSearch();
            List<String> names = new ArrayList<>();
            names.add("Lassie");
            names.add("Benji");
            names.add("Brian");
            search.reduceList(names, d -> d.length() > MAX_LENGTH);
            System.out.print(names.size());                             // PRINT: 0
        }
    }
    @Test
    public void question137() {
        DogSearch.main(null);
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following program?
     */
    @Test
    public void question138() {
        var p = List.of(1,3,5);
        var q = p.parallelStream().reduce(0f,
                (w, x) -> w.floatValue() + x.floatValue(),      // NOTE: Unnecessary unboxing 'w.floatValue()'.
                (y, z) -> y.floatValue() + z.floatValue());     // NOTE: Unnecessary unboxing for y and z.
        System.out.println("q = " + q);                         // PRINT: 9.0
        Assert.assertTrue(true);
    }

    /**
     * What does the following output?
     */
    @Test
    public void question139() {
        Set<String> set = new HashSet<>();
        set.add("tire-");
        List<String> list = new LinkedList<>();
        Deque<String> queue = new ArrayDeque<>();
        queue.push("wheel-");
        Stream.of(set, list, queue)
        //      .flatMap(x -> x.stream())           Lambda can be replaced with method reference.
                .flatMap(Collection::stream)
                .forEach(System.out::print);        // PRINT: tire-wheel-
        Assert.assertTrue(true);
    }

    /**
     * Which is a possible output of the following code?
     *
     * A. {false=[Stonehenge, Statue of Liberty], true=[Eiffel Tower, Mount Fuji]}
     */
    @Test
    public void question143() {
        var landmarks = Set.of("Eiffel Tower", "Statue of Liberty", "Stonehenge", "Mount Fuji");
        var result = landmarks.stream()
                .collect(Collectors.partitioningBy(s -> s.contains(" ")))
                .entrySet()
                .stream()
                .flatMap(s -> s.getValue().stream())
                .collect(Collectors.groupingBy(s -> !s.startsWith("S")));
        System.out.println("result = " + result);
        Assert.assertTrue(true);
    }

    /**
     * Which is one of the lines output by this code?
     */
    @Test
    public void question146() {
       var list = new ArrayList<Integer>();
       list.add(10);
       list.add(9);
       list.add(8);

       var num = 9;
       list.removeIf(x -> {
           int keep = num;                       // Variable "num" should be final or effectively final.
           return x == keep;
       });
        // num = 8;                              // ERROR: as commented before.
        System.out.println("list = " + list);    // PRINT: [10, 8]
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following application?
     */
    static class Warehouse {
        private int quantity = 40;
        private final BooleanSupplier stock;
        {
            stock = () -> quantity > 0;
        }
        public void checkInventory() {
            // if(stock.get())                          ERROR: Cannot resolve method 'get' in 'BooleanSupplier'.
            if(stock.getAsBoolean())
                System.out.print("Plenty!");
            else {
                System.out.print("On Backorder!");
            }
        }
        public static void main(String[] widget) {
            final Warehouse w13 = new Warehouse();
            w13.checkInventory();                       // PRINT: Plenty!
        }
    }
    @Test
    public void question149() {
        Warehouse.main(null);
        Assert.assertTrue(true);
    }

    /**
     * What is possible output of the following application?
     */
    static class Thermometer {
        private String feelsLike;
        private double temp;
        public Thermometer(String feelsLike, double temp) {
            this.feelsLike = feelsLike;
            this.temp = temp;
        }
        public double getTemp() { return temp; }
        @Override
        public String toString() { return feelsLike; }

        public static void main(String[] args) {
            var readings = List.of(new Thermometer("HOT!", 72),
                    new Thermometer("Too Cold!", 0),
                    new Thermometer("Just right", 72));
            readings
                    .parallelStream()                                                   // k1
                    .collect(Collectors.groupingByConcurrent(Thermometer::getTemp))     // k2
            //      .forEach(System.out::println);                                      k3 ERROR: 'println' is ambiguous.
                    .forEach((k, v) -> System.out.println("key = " + k
                            + ", value = " + v));
            // PRINT: key = 0.0, value = [Too Cold!]
            //        key = 72.0, value = [HOT!, Just right]
        }
    }
    @Test
    public void question150() {
        Thermometer.main(null);
        Assert.assertTrue(true);
    }
}
