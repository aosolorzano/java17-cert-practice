package com.hiperium.java.cert.prep.chapter._15;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class StreamPipelines {

    public static void main(String[] args) {
        var list = List.of("Toby", "Anna", "Leroy", "Alex");

        // WITHOUT USING STREAMS
        List<String> filtered = new ArrayList<>();
        for (String name : list)
            if (name.length() == 4)
                filtered.add(name);
        Collections.sort(filtered);
        var iter = filtered.iterator();
        if (iter.hasNext()) System.out.println(iter.next());    // Note that this does not use while iterator.
        if (iter.hasNext()) System.out.println(iter.next());    // It prints twice the values of the collection.

        // WITH STREAMS
        Stream.of(list)
                .flatMap(m -> m.stream())
                .filter(x -> x.length() == 4)
                .sorted()
                .limit(2)
                .forEach(System.out::println);

        // PEEKING BEGIN THE SCENES
        var infinite1 = Stream.iterate(1, x -> x + 1);
        infinite1.limit(5)
                .filter(x -> x % 2 == 1)
                .forEach(System.out::println);      // PRINT: 1 3 5 (odd numbers from 1 to 5)

        var infinite2 = Stream.iterate(1, x -> x + 1);
        infinite2.limit(5)
                .peek(System.out::println)
                .filter(x -> x % 2 == 1)
                .forEach(System.out::println);      // PRINT: 1 1 2 3 3 4 5 5 (print all processed numbers and odds too)

        // Reversing the order of PEEKING
        var infinite3 =  Stream.iterate(1, x -> x + 1);
        infinite3.filter(x -> x % 2 == 1)
                .limit(5)
                .forEach(System.out::println);      // PRINT: 1 3 5 7 9 (limit to 5 odd numbers)

        var infinite4 =  Stream.iterate(1, x -> x + 1);
        infinite4.filter(x -> x % 2 == 1)
                .peek(System.out::println)
                .limit(5)
                .forEach(System.out::println);      // PRINT: 11 33 55 77 99
    }
}
