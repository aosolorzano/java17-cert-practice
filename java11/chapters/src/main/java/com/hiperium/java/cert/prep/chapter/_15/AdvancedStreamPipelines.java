package com.hiperium.java.cert.prep.chapter._15;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AdvancedStreamPipelines {

    public static final String DELIMITER = ", ";
    public static final String[] ANIMALS = new String[]{"lions", "tigers", "bears"};

    public static void main(String[] args) {
        // We are given an Optional<Integer> and we asked to print the value, but only if it is a three‐digit number.
        Optional<Integer> optional = Optional.of(56);
        optional.map(n -> "" + n)                       // part 1
                .filter(s -> s.length() == 3)           // part 2
                .ifPresent(System.out::println);        // part 3

        basicCollectors();
        collectingIntoMaps();
        grouping();
        partitioning();
        mapping();
    }

    public static void basicCollectors() {
        // JOINING STRING VALUES OF STREAM
        var stream1 = Stream.of(ANIMALS);
        String result1 = stream1.collect(Collectors.joining(DELIMITER));
        System.out.println(result1);                // PRINT: lions, tigers, bears

        var stream2 = Stream.of(ANIMALS);
        stream2.reduce((s1, s2) -> s1.concat(DELIMITER).concat(s2))
                .ifPresent(System.out::println);    // PRINT: lions, tigers, bears

        var stream3 = Stream.of(ANIMALS);
        StringBuilder result3 = stream3.collect(
                StringBuilder::new,
                (s1, s2) -> {
                    if (s1.length() == 0)
                        s1.append(s2);
                    else
                        s1.append(DELIMITER).append(s2);
                },
                StringBuilder::append
        );
        System.out.println(result3);                // PRINT: lions, tigers, bears

        // THE AVERAGE OF THE ANIMALS NAMES LENGTH
        var stream5 = Stream.of(ANIMALS);
        Double result5 = stream5.collect(Collectors.averagingInt(String::length));
        System.out.println(result5);                // PRINT: 5.333333333333333

        var stream6 = Stream.of(ANIMALS);
        Double result6 = stream6.mapToInt(s -> s.length()).average().getAsDouble();
        System.out.println(result6);                // PRINT: 5.333333333333333

        // WE CAN USE A STREAM AND THEN CONVERT TO A COLLECTION AT THE END
        var stream7 = Stream.of(ANIMALS);
        TreeSet<String> result7 = stream7
                .filter(s -> s.startsWith("t"))
                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println(result7);                // PRINT: [tigers]
    }

    public static void collectingIntoMaps() {
        var stream1 = Stream.of(ANIMALS);
        Map<String, Integer> map1 = stream1.collect(
                // We specify 2 functions to provide a key and value for the map.
                Collectors.toMap(s -> s, String::length)
        );
        System.out.println(map1);               // PRINT: '{lions=5, bears=5, tigers=6}'

        // MAP THE LENGTH OF THE ANIMAL NAME TO THE NAME ITSELF SEPARATED BY A COMA
        var stream2 = Stream.of(ANIMALS);
        Map<Integer, String> map2 = stream2.collect(
                Collectors.toMap(
                        String::length,
                        k -> k,
                        (s1, s2) -> s1.concat(DELIMITER).concat(s2))
        );
        System.out.println(map2);               // PRINT: '{5=lions, bears, 6=tigers}'
        System.out.println(map2.getClass());    // PRINT: java.util.HashMap

        // THIS TIME SPECIFY THE TYPE OF THE MAP
        var stream3 = Stream.of(ANIMALS);
        Map<Integer, String> map3 = stream3.collect(
                Collectors.toMap(
                        String::length,
                        k -> k,
                        (s1, s2) -> s1.concat(DELIMITER).concat(s2),
                        TreeMap::new)
        );
        System.out.println(map3);               // PRINT: '{5=lions, bears, 6=tigers}'
        System.out.println(map3.getClass());    // PRINT: java.util.TreeMap
    }

    public static void grouping() {
        // GROUPING THE ANIMAL NAMES BY THEIR LENGTH
        var stream1 = Stream.of(ANIMALS);
        Map<Integer, List<String>> map1 = stream1.collect(
                Collectors.groupingBy(String::length)
        );
        System.out.println(map1);               // PRINT: '{5=[lions, bears], 6=[tigers]}'

        // GROUPING THE ANIMAL NAMES BY THEIR LENGTH USING A SET
        var stream2 = Stream.of(ANIMALS);
        Map<Integer, Set<String>> map2 = stream2.collect(Collectors.groupingBy(
                String::length,
                Collectors.toSet()
        ));
        System.out.println(map2);               // PRINT: '{5=[lions, bears], 6=[tigers]}'

        // GROUPING THE ANIMAL NAMES BY THEIR LENGTH USING A SET FOR VALUES INTO A TREEMAP
        var stream3 = Stream.of(ANIMALS);
        Map<Integer, Set<String>> map3 = stream3.collect(Collectors.groupingBy(
                String::length,
                TreeMap::new,
                Collectors.toSet()
        ));
        System.out.println(map3);               // PRINT: '{5=[lions, bears], 6=[tigers]}'

        // WE CAN GROUP BY THE LENGTH OF THE ANIMAL NAME TO SEE HOW MANY OF EACH LENGTH WE HAVE
        var stream4 = Stream.of(ANIMALS);
        Map<Integer, Long> map4 = stream4.collect(Collectors.groupingBy(
                String::length,
                Collectors.counting()
        ));
        System.out.println(map4);               // PRINT: '{5=2, 6=1}'
    }

    /**
     * NOTE: Unlike groupingBy(), we cannot change the type of Map that gets returned. However, there are only two keys
     * in the map, so does it really matter which Map type we use?
     */
    public static void partitioning() {
        // PARTITIONING THE ANIMAL NAMES BY THEIR CHARACTERS LENGTH CONDITION
        var stream1 = Stream.of(ANIMALS);
        Map<Boolean, List<String>> map1 = stream1.collect(Collectors.partitioningBy(
                s -> s.length() <= 5
        ));
        System.out.println(map1);       // PRINT: '{false=[tigers], true=[lions, bears]}'

        // PARTITIONING THE ANIMAL NAMES BY THEIR CHARACTERS LENGTH CONDITION USING A SET FOR VALUES
        var stream2 = Stream.of(ANIMALS);
        Map<Boolean, Set<String>> map2 = stream2.collect(Collectors.partitioningBy(
                s -> s.length() <= 7,
                Collectors.toSet()
        ));
        System.out.println(map2);       // PRINT: '{false=[], true=[lions, bears, tigers]}'
    }

    public static void mapping() {
        // We want to get the first letter of the first animal alphabetically of each length.
        var stream1 = Stream.of(ANIMALS);
        Map<Integer, Optional<Character>> map1 = stream1.collect(
                Collectors.groupingBy(
                        String::length,
                        Collectors.mapping(
                                s -> s.charAt(0),
                                Collectors.minBy((a, b) -> a - b)
                        )
                )
        );
        System.out.println(map1);       // PRINT: '{5=Optional[b], 6=Optional[t]}'
    }

}
