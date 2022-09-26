package com.hiperium.java.cert.prep.chapter._15;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UsingStreams {

    public static final String CHIMP = "chimp";

    public static void main(String[] args) {
        creatingStreams();
        commonTerminalOperations();
        commonIntermediateOperations();
    }

    /**
     * ************************************************************************************************|
     * Method                    |  Finite or infinite? | Notes                                        |
     * ************************* | ******************** | *********************************************|
     * Stream.empty()            |  Finite              | Creates Stream with zero elements.           |
     * Stream.of(varargs)        |  Finite              | Creates Stream with elements listed.         |
     * coll.stream()             |  Finite              | Creates Stream from a Collection.            |
     * coll.parallelStream()     |  Finite              | Creates Stream from a Collection where the   |
     *                           |                      | stream can run in parallel.                  |
     * Stream.generate(supplier) | Infinite             | Creates Stream by calling the Supplier for   |
     *                           |                      | each element upon request.                   |
     * Stream.iterate(seed,      | Infinite             | Creates Stream by using the seed for the 1st |
     * unaryOperator)            |                      | element and then calling the UnaryOperator   |
     *                           |                      | for each subsequent element upon request.    |
     * Stream.iterate(seed,      | Finite or infinite   | Creates Stream by using the seed for the     |
     * predicate, unaryOperator) |                      | first element and then calling the           |
     *                           |                      | UnaryOperator for each subsequent element    |
     *                           |                      | upon request. Stops if the Predicate returns |
     *                           |                      | false.                                       |
     * ************************************************************************************************|
     */
    public static void creatingStreams() {
        creatingFiniteStreams();
        creatingInfinityStreams();
    }

    public static void creatingFiniteStreams() {
        Stream<String> empty = Stream.empty();
        Stream<Integer> singleElement = Stream.of(1);
        Stream<Integer> fromArray = Stream.of(1, 2, 3);

        // Java also provides a convenient way of converting a Collection to a stream.
        var list = List.of("a", "b", "c");
        Stream<String> fromList = list.stream();

        // Creating a Parallel Stream
        Stream<String> fromListParallel = list.parallelStream();
    }

    /**
     * Stream that generate random numbers:
     *
     * 'Stream<Double> randoms = Stream.generate(Math::random);'
     *
     * The 'iterate()' method takes a seed or starting value as the first parameter. This is the first element that
     * will be part of the stream. The other parameter is a lambda expression that gets passed the previous value
     * and generates the next value.
     *
     * 'Stream<Integer> oddNumbers = Stream.iterate(1, n -> n + 2);'
     *
     * What if we wanted just odd numbers less than 100? Java 9 introduced an overloaded version of iterate() that
     * helps with just that.
     */
    public static void creatingInfinityStreams() {
        Stream<Integer> oddNumbersUnder100 = Stream.iterate(1, n -> n < 100, n -> n + 2);
        oddNumbersUnder100.forEach(System.out::println);
    }

    /**
     * We can perform a TERMINAL OPERATION without any intermediate operations but not the other way around.
     * Reductions are a special type of terminal operation where all the contents of the stream are combined into a
     * single primitive or Object.
     *
     * ******************************************************************************************|
     * Method         | What happens          | Parameter Type        | Return value | Reduction |
     *                | for infinite streams  |                       |              |           |
     * ***************|***********************|***********************|**************|***********|
     * count()        | Does not terminate.   |           -           | long         | Yes       |
     * ---------------|-----------------------|-----------------------|--------------|-----------|
     * min()          | Does not terminate.   | Comparator<? super T> | Optional<T>  | Yes       |
     * max()          |                       |                       |              |           |
     * ---------------|-----------------------|-----------------------|--------------|-----------|
     * findAny()      | Terminates.           |           -           | Optional<T>  | No        |
     * findFirst()    |                       |                       |              |           |
     * ---------------|-----------------------|-----------------------|--------------|-----------|
     * allMatch()     | Sometimes terminates. | Predicate<? super T>  | boolean      | No        |
     * anyMatch()     |                       |                       |              |           |
     * noneMatch()    |                       |                       |              |           |
     * ---------------|-----------------------|-----------------------|--------------|-----------|
     * forEach()      | Does not terminate.   | Consumer<? super T>   | void         | No        |
     * ---------------|-----------------------|-----------------------|--------------|-----------|
     * reduce()       | Does not terminate.   |       Varies          | Varies       | Yes       |
     * ---------------|-----------------------|-----------------------|--------------|-----------|
     * collect()      | Does not terminate.   |       Varies          | Varies       | Yes       |
     * ---------------|-----------------------|-----------------------|--------------|-----------|
     */
    public static void commonTerminalOperations() {
        count();
        minAndMax();
        findAnyAndFinFirst();
        matchMethods();
        forEach();
        reduce();
        collect();
    }

    /**
     * The count() method determines the number of elements in a finite stream. For an infinite stream, it never
     * terminates. Why? Count from 1 to infinity and let us know when you are finished.
     *
     * The count() method is a reduction because it looks at each element in the stream and returns a single value.
     */
    public static void count() {
        Stream<String> s = Stream.of("monkey", "gorilla", "bonobo");
        System.out.println("Counting 's' stream: " +  s.count());

        // IllegalStateException: stream has already been operated upon or closed
        // s.forEach(s1 -> System.out.println("Element for stream: " + s1)); ===>> RUNTIME EXCEPTION
    }

    /**
     * The min() and max() methods allow us to pass a custom comparator and find the smallest or largest value in a
     * finite stream according to that sort order. Like the count() method, min() and max() hang on an infinite stream
     * because they cannot be sure that a smaller or larger value isn't coming later in the stream.
     *
     * Both methods are reductions because they return a single value after looking at the entire stream.
     *
     * NOTE: What if we need both the min() and max() values of the same stream? For now, we can't have both, at least
     * not using these methods. Remember, a stream can have only one terminal operation. Once a terminal operation has
     * been run, the stream cannot be used again.
     */
    public static void minAndMax() {
        Stream<String> s = Stream.of("monkey", "ape", "bonobo");
        Optional<String> min = s.min((s1, s2) -> s1.length() - s2.length());
        min.ifPresent(System.out::println);         // PRINT: ape

        // As an example of where there isn't a minimum, let's look at an empty stream.
        Optional<?> minEmpty = Stream.empty().min((s1, s2) -> ((String) s1).length() - ((String) s2).length());
        System.out.println(minEmpty.isPresent());   // PRINT: false
    }

    /**
     * These methods are terminal operations but not reductions. The reason is that they sometimes return without
     * processing all elements. This means that they return a value based on the stream but do not reduce the entire
     * stream into one value.
     */
    public static void findAnyAndFinFirst() {
        Stream<String> s = Stream.of("monkey", "gorilla", "bonobo");
        Stream<String> infinite = Stream.generate(() -> CHIMP);
        /*
         * The findAny() method can return any element of the stream. When called on the streams, it commonly returns
         * the first element, although this behavior is not guaranteed.
         */
        s.findAny().ifPresent(System.out::println);             // PRINT: monkey (usually)
        infinite.findAny().ifPresent(System.out::println);      // PRINT: chimp
    }

    /**
     * The allMatch(), anyMatch(), and noneMatch() methods search a stream and return information about how the stream
     * pertains to the predicate. These may or may not terminate for infinite streams. It depends on the data. Like the
     * find methods, they are not reductions because they do not necessarily look at all elements.
     *
     * NOTE: Remember that allMatch(), anyMatch(), and noneMatch() returns a boolean. By contrast, the find methods
     * return an Optional because they return an element of the stream.
     */
    public static void matchMethods() {
        var list = List.of("monkey", "2", CHIMP);
        Stream<String> infinite = Stream.generate(() -> CHIMP);
        Predicate<String> predicate = x -> Character.isLetter(x.charAt(0));

        // NOTE: This show us that we can reuse the same predicate, but we need a different stream each time.
        System.out.println(list.stream().anyMatch(predicate));      // PRINT: TRUE
        System.out.println(list.stream().allMatch(predicate));      // PRINT: FALSE
        System.out.println(list.stream().noneMatch(predicate));     // PRINT: FALSE
        System.out.println(infinite.anyMatch(predicate));           // PRINT: TRUE
    }

    /**
     * NOTE: Remember that we can call forEach() directly on a Collection or on a Stream. Don't get confused on the
     * exam when we see both approaches. While forEach() sounds like a loop, it is really a terminal operator for
     * streams. Streams cannot be used as the source in a for‐each loop to run, because they don't implement the
     * Iterable interface.
     */
    public static void forEach() {
        var list = Stream.of("monkey", "gorilla", "bonobo");
        list.forEach(System.out::println);
    }

    /**
     * The 'reduce()' method combines a stream into a single object. It is a reduction, which means it processes all
     * elements. The three method signatures are described in the next examples:
     */
    public static void reduce() {
        /*
         * 1. T reduce(T identity, BinaryOperator<T> accumulator)
         */
        Stream<String> stream = Stream.of("w", "o", "l", "f");
        String word = stream.reduce("", String::concat);
        System.out.println("Stream reduction result: " + word);

        // Write a reduction to multiply all the Integer elements in a stream
        Stream<Integer> numbers = Stream.of(3, 5, 6);
        System.out.println(numbers.reduce(1, (a, b) -> a * b));     // PRINT: 90

        /*
         * 2. Optional<T> reduce(BinaryOperator<T> accumulator)
         *
         * When you don't specify an identity, an Optional is returned because there might not be any data. There are
         * three choices for what is in the Optional:
         *
         * a. If the stream is empty, an empty Optional is returned.
         * b. If the stream has one element, it is returned.
         * c. If the stream has multiple elements, the accumulator is applied to combine them.
         */
        BinaryOperator<Integer> multiplyOperator = (a, b) -> a * b;
        Stream<Integer> emptyNumbers = Stream.empty();
        Stream<Integer> oneNumber    = Stream.of(3);
        Stream<Integer> threeNumbers = Stream.of(3, 5, 6);
        emptyNumbers.reduce(multiplyOperator).ifPresent(System.out::println);    // PRINT: NO OUTPUT
        oneNumber   .reduce(multiplyOperator).ifPresent(System.out::println);    // PRINT: 3
        threeNumbers.reduce(multiplyOperator).ifPresent(System.out::println);    // PRINT: 90

        /*
         * 3. <U> U reduce(U identity, BiFunction<U, '?' super T, U> accumulator,
         *                 BinaryOperator<U> combiner)
         *
         * The third method is used when we are dealing with different types. It allows Java to create intermediate
         * reductions and then combine them at the end.
         *
         * The next example counts the number of characters in each String:
         */
        Stream<String> stringStream = Stream.of("w", "o", "l", "f!");
        int length = stringStream.reduce(0, (i, s) -> i + s.length(), (a, b) -> a + b);
        System.out.println("Reduce stream length: " + length);      // PRINT: 5
    }

    /**
     * The collect() method is a special type of reduction called a mutable reduction. It is more efficient than a
     * regular reduction because we use the same mutable object while accumulating. Common mutable objects include
     * StringBuilder and ArrayList. This is a really useful method, because it lets us get data out of streams and
     * into another form.
     */
    public static void collect() {
        /*
         * <R> R collect(Supplier<R> supplier,
         *               BiConsumer<R, ? super T> accumulator,
         *               BiConsumer<R, R> combiner)
         */
        Stream<String> stringStream1 = Stream.of("w", "o", "l", "f");
        StringBuilder collect1 = stringStream1.collect(
          StringBuilder::new,
          StringBuilder::append,
          StringBuilder::append
        );
        System.out.println("Collect to a StringBuilder: " + collect1);              // PRINT: wolf

        // Another example where the logic is different in the accumulator and combiner.
        Stream<String> stringStream2 = Stream.of("w", "o", "l", "f");
        TreeSet<String> collect2 = stringStream2.collect(
          TreeSet::new,
          TreeSet::add,
          TreeSet::addAll
        );
        System.out.println("Collect to a TreeSet: " + collect2);                    // PRINT: [f, l, o, w]

        // Java provides a utility class named Collectors.
        Stream<String> stringStream3 = Stream.of("w", "o", "l", "f");
        TreeSet<String> collect3 = stringStream3.collect(Collectors.toCollection(TreeSet::new));
        System.out.println("Collect to a TreeSet with Collectors: " + collect3);    // PRINT: [f, l, o, w]

        // If we didn't need the set to be sorted:
        Stream<String> stringStream4 = Stream.of("w", "o", "l", "f");
        Set<String> collect4 = stringStream4.collect(Collectors.toSet());
        System.out.println("Collect to a Set with Collectors: " + collect4);        // PRINT: [f, w, l, o]
    }

    /**
     * Unlike a terminal operation, an intermediate operation produces a stream as its result. An intermediate operation
     * can also deal with an infinite stream simply by returning another infinite stream. The assembly line worker
     * doesn't need to worry about how many more elements are coming through and instead can focus on the current element.
     */
    public static void commonIntermediateOperations() {
        filter();
        distinct();
        skipAndLimit();
        map();
        flatMap();
        sorted();
        peak();
    }

    /**
     * The filter() method returns a Stream with elements that match a given expression.
     */
    public static void filter() {
        Stream<String> stream = Stream.of("monkey", "gorilla", "bonobo");
        stream.filter(x -> x.startsWith("m")).forEach(System.out::println);     // PRINT: monkey
    }

    /**
     * The distinct() method returns a stream with duplicate values removed. As we might imagine, Java calls equals()
     * to determine whether the objects are the same.
     */
    public static void distinct() {
        Stream<String> stream = Stream.of("duck", "duck", "goose");
        stream.distinct().forEach(System.out::println);                         // PRINT: duck, goose
    }

    /**
     * The limit() and skip() methods can make a Stream smaller, or they could make a finite stream out of an infinite
     * stream.
     *
     * Stream<T> limit(long maxSize)
     * Stream<T> skip(long n)
     *
     * The following example creates an infinite stream of numbers counting from 1. The skip() operation returns
     * an infinite stream starting with the numbers counting from 6, since it skips the first five elements.
     * The limit() call takes the first two of those. Now we have a finite stream with two elements, which we can then
     * print with the forEach() method.
     */
    public static void skipAndLimit() {
        Stream<Integer> s = Stream.iterate(1, n -> n + 1);
        s.skip(5).limit(2).forEach(System.out::println);            // PRINT: 6, 7
    }

    /**
     * The map() method creates an one‐to‐one mapping from the elements in the stream to the elements of the next step
     * in the stream.
     *
     * <R> Stream<R> map(Function<? super T, ? extends R> mapper)
     *
     * NOTE: The map() method on streams is for transforming data. Don't confuse it with the Map interface, which maps
     * keys to values.
     *
     * As an example, this code converts a list of String objects to a list of Integer objects representing their lengths.
     */
    public static void map() {
        Stream<String> stream = Stream.of("monkey", "gorilla", "bonobo");
        stream.map(String::length).forEach(System.out::println);            // PRINT: 6, 7, 6
    }

    /**
     * The flatMap() method takes each element in the stream and makes any elements it contains top‐level elements in a
     * single stream. This is helpful when you want to remove empty elements from a stream, or you want to combine a
     * stream of lists.
     *
     * <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper)
     */
    public static void flatMap() {
        List<String> zero = List.of();
        var one = List.of("Bonobo");
        var two = List.of("Mama Gorilla", "Baby Gorilla");
        Stream<List<String>> animals = Stream.of(zero, one, two);
        animals.flatMap(m -> m.stream()).forEach(System.out::println);      // PRINT: Bonobo, Mama Gorilla, Baby Gorilla
    }

    /**
     * The sorted() method returns a stream with the elements sorted. Just like sorting arrays, Java uses natural
     * ordering unless we specify a comparator.
     */
    public static void sorted() {
        Stream<String> s = Stream.of("brown-", "bear-");
        s.sorted().forEach(System.out::println);                                    // PRINT: brear-brown-

        // We can use a Comparator implementation via a method or lambda
        Stream<String> stream = Stream.of("brown bear-", "grizzly-");
        stream.sorted(Comparator.reverseOrder()).forEach(System.out::println);      // PRINT: grizzly-brown bear-

        /*
         * Comparator is a functional interface. This means that we can use method references or lambdas to implement it.
         * The Comparator interface implements one method that takes two String parameters and returns an int. However,
         * Comparator::reverseOrder doesn't do that. It is a reference to a function that takes zero parameters and
         * returns a Comparator. This is not compatible with the interface. This means that we have to use a method and
         * not a method reference.
         *
         * stream.sorted(Comparator::reverseOrder);     ERROR: Cannot resolve method 'reverseOrder'.
         */
    }

    /**
     * The most common use for peek() is to output the contents of the stream as it goes by.
     *
     * Stream<T> peek(Consumer<? super T> action)
     *
     * You might notice the intermediate peek() operation takes the same argument as the terminal forEach() operation.
     * Think of peek() as an intermediate version of forEach() that returns the original stream back to you.
     */
    public static void peak() {
        var stream = Stream.of("black bear", "brown bear", "grizzly");
        long count = stream.filter(s -> s.startsWith("g")).peek(System.out::println).count();
        System.out.println("Peek count: " + count);
    }
}
