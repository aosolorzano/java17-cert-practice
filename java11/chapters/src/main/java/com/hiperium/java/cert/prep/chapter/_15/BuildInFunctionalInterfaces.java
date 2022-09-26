package com.hiperium.java.cert.prep.chapter._15;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.function.*;

/**
 *
 * ****************************************************************************|
 * Functional interface  |  Return type  |  Method name  |  Num. of parameters |
 * ****************************************************************************|
 * Supplier<T>           |       T       |     get()     |       0             |
 * Consumer<T>           |     void      |   accept(T)   |       1 (T)         |
 * BiConsumer<T, U>      |     void      |  accept(T,U)  |       2 (T, U)      |
 * Predicate<T>          |    boolean    |    test(T)    |       1 (T)         |
 * BiPredicate<T, U>     |    boolean    |   test(T,U)   |       2 (T, U)      |
 * Function<T, R>        |       R       |    apply(T)   |       1 (T)         |
 * BiFunction<T, U, R>   |       R       |   apply(T,U)  |       2 (T, U)      |
 * UnaryOperator<T>      |       T       |    apply(T)   |       1 (T)         |
 * BinaryOperator<T>     |       T       |   apply(T,T)  |       2 (T, T)      |
 * ****************************************************************************|
 *
 */
public class BuildInFunctionalInterfaces {

    public static final String ANNIE = "Annie";
    public static final String CHICKEN = "chicken";
    public static final String CHICK = "chick";

    public static void main(String[] args) {
        supplier();
        consumerAndBiConsumer();
        predicateAndBiPredicate();
        functionAndBiFunction();
        unaryAndBinaryOperator();
        convenienceMethods();
    }

    public static void supplier() {
        Supplier<LocalDate> s1 = LocalDate::now;
        Supplier<LocalDate> s2 = () -> LocalDate.now();
        LocalDate d1 = s1.get();
        LocalDate d2 = s2.get();
        System.out.println("LocalDate s1: " + d1);
        System.out.println("LocalDate s2: " + d2);

        Supplier<StringBuilder> s3 = StringBuilder::new;
        Supplier<StringBuilder> s4 = () -> new StringBuilder();
        System.out.println("New StringBuilder s3: " + s3.get());
        System.out.println("New StringBuilder s4: " + s4.get());
    }

    public static void consumerAndBiConsumer() {
        Consumer<String> c1 = System.out::println;
        Consumer<String> c2 = (x) -> System.out.println(x);
        c1.accept(ANNIE);         // PRINT: Annie
        c2.accept(ANNIE);         // PRINT: Annie

        var map = new HashMap<String, Integer>();
        BiConsumer<String, Integer> b1 = map::put;
        BiConsumer<String, Integer> b2 = (k, v) -> map.put(k, v);
        b1.accept(CHICKEN, 7);
        b2.accept(CHICK, 1);
        System.out.println("BiConsumer map: " + map);   // PRINT: '{chicken=7, chick=1}'
    }

    public static void predicateAndBiPredicate() {
        Predicate<String> p1 = String::isEmpty;
        Predicate<String> p2 = x -> x.isEmpty();
        System.out.println("Test p1: " + p1.test(""));      // PRINT: true
        System.out.println("Test p2: " + p1.test(" "));     // PRINT: false

        BiPredicate<String, String> b1 = String::startsWith;
        BiPredicate<String, String> b2 = (string, prefix) -> string.startsWith(prefix);
        System.out.println("Test BiPredicate b1: " + b1.test(CHICKEN, CHICK));  // PRINT: true
        System.out.println("Test BiPredicate b2: " + b2.test(CHICKEN, CHICK));  // PRINT: true
    }

    public static void functionAndBiFunction() {
        Function<String, Integer> f1 = String::length;
        Function<String, Integer> f2 = (String x) -> x.length();
        System.out.println("Applying function f1: " + f1.apply("cluck"));   // PRINT: 5
        System.out.println("Applying function f2: " + f2.apply("cluck"));   // PRINT: 5

        BiFunction<String, String, String> b1 = String::concat;
        BiFunction<String, String, String> b2 = (String s1, String s2) -> s1.concat(s2);
        System.out.println("Applying BiFunction b1: " + b1.apply("baby", CHICK));       // PRINT: babychick
        System.out.println("Applying BiFunction b2: " + b2.apply("baby", CHICK));       // PRINT: babychick
    }

    /**
     * - UnaryOperator transforms its value into one of the same type. UnaryOperator extends Function.
     * - BinaryOperator merges two values into one of the same type. BinaryOperator extends BiFunction.
     *
     * The generic declarations on the subclass are what force the type to be the same.
     * We don't need to specify the return type in the generics because UnaryOperator requires it to be the same type as
     * the parameter.
     */
    public static void unaryAndBinaryOperator() {
        UnaryOperator<String> u1 = String::toUpperCase;
        UnaryOperator<String> u2 = (s1) -> s1.toUpperCase();
        System.out.println("UnaryOperator u1: " + u1.apply("chirp"));   // PRINT: CHIRP
        System.out.println("UnaryOperator u2: " + u2.apply("chirp"));   // PRINT: CHIRP

        BinaryOperator<String> b1 = String::concat;
        BinaryOperator<String> b2 = (s1, s2) -> s1.concat(s2);
        System.out.println("BinaryOperator b1: " + b1.apply("baby", CHICK));  // PRINT: babychick
        System.out.println("BinaryOperator b2: " + b2.apply("baby", CHICK));  // PRINT: babychick
    }

    /**
     * --------------------------------------------------------------------------------|
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
     * NOTE: The BiConsumer, BiFunction, and BiPredicate interfaces have similar methods available.
     */
    public static void convenienceMethods() {
        /*
         * PREDICATE
         */
        Predicate<String> egg = s -> s.contains("egg");
        Predicate<String> brown = s -> s.contains("brown");
        // Predicate<String> brownEggs = (s) -> s.contains("egg") && s.contains("brown");       NOT GREAT
        // Predicate<String> otherEggs = (s) -> s.contains("egg") && !s.contains("brown");      NOT GREAT
        Predicate<String> brownEggs = egg.and(brown);
        Predicate<String> otherEggs = egg.and(brown.negate());
        System.out.println("Combined predicate p1: " + brownEggs.test("Test brown egg"));   // PRINT: true
        System.out.println("Combined predicate p2: " + otherEggs.test("Test brown egg"));   // PRINT: false

        /*
         * CONSUMER
         */
        Consumer<String> c1 = x -> System.out.print("1: " + x);
        Consumer<String> c2 = x -> System.out.print(", 2: " + x);
        Consumer<String> combinedConsumer = c1.andThen(c2);
        combinedConsumer.accept(ANNIE);                       // PRINT: 1: Annie, 2: Annie
        System.out.println();

        /*
         * FUNCTION
         *
         * The 'compose()' method on Function chains functional interfaces. However, it passes along the output of one
         * to the input of another. Variable 'before' runs first, turning the 3 into a 4. Then the 'after' runs, doubling
         * the 4 to 8.
         */
        Function<Integer, Integer> before = x -> x + 1;
        Function<Integer, Integer> after = x -> x * 2;
        Function<Integer, Integer> combinedFunction = after.compose(before);
        System.out.println(combinedFunction.apply(3));                      // PRINT: 8
    }
}
