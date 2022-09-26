package com.hiperium.java.cert.prep.chapter._15;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * --------------------------------------------------------------------------------------------------|
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
 */
public class ReturningOptional {

    public static void main(String[] args) {
        System.out.println("Average: " + average(90, 100));     // PRINT: Optional[95.0]
        System.out.println("Average: " + average());                    // PRINT: Optional.empty

        Optional<Double> optionalDouble = average(90, 100);
        if (optionalDouble.isPresent())
            System.out.println("Optional with value: " + optionalDouble.get());

        // We can specify a Consumer to be run when there is a value inside the Optional.
        optionalDouble.ifPresent(System.out::println);

        // Working with nullable values
        Integer initialValue = null;
        nullables(initialValue);
        initialValue = 10;
        nullables(initialValue);

        // Working with empty values
        emptyOptional();
    }

    public static Optional<Double> average(int... scores) {
        if (scores.length == 0)
            return Optional.empty();
        int sum = 0;
        for (int score : scores)
            sum += score;
        return Optional.of((double) sum / scores.length);
    }

    public static void nullables(Integer value) {
        Optional<Integer> optionalInteger = Optional.ofNullable(value);
        optionalInteger.ifPresent(val -> System.out.println("Optional Integer value: " + val));
    }

    public static void emptyOptional() {
        Optional<Double> opt = average();
        System.out.println(opt.orElse(Double.NaN));
        System.out.println(opt.orElseGet(() -> Math.random()));

        // We can have the code throw an exception if the Optional is empty.
        try {
            Optional<Double> doubleOptional = average();
            System.out.println("The average value is: " + doubleOptional.orElseThrow());
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }

        // Alternatively, we can throw a custom exception if the Optional is empty.
        try {
            Optional<Double> doubleOptional = average();
            System.out.println("The average value is: " + doubleOptional.orElseThrow(() ->
                    new IllegalArgumentException("No average value found.")));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // Next code prints out 95.0 three times. Since the value does exist, there is no need to use the “or else” logic.
        Optional<Double> optionalDouble = average(90, 100);
        System.out.println(optionalDouble.orElse(Double.NaN));
        System.out.println(optionalDouble.orElseGet(() -> Math.random()));
        System.out.println(optionalDouble.orElseThrow());
    }
}
