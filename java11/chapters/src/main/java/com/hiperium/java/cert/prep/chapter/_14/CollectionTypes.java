package com.hiperium.java.cert.prep.chapter._14;

import java.util.*;
import java.util.function.BiFunction;

/**
 * A collection is a group of objects contained in a single object. The Java Collections Framework is a set of classes
 * in the java.util for storing collections. There are four main interfaces in the Java Collections Framework:
 *
 *      - LIST: A list is an ordered collection of elements that allows duplicate entries. Elements in a list can be
 *        accessed by an int index.
 *      - SET: A set is a collection that does not allow duplicate entries.
 *      - QUEUE: A queue is a collection that orders its elements in a specific order for processing. A typical queue
 *        processes its elements in a first‐in, first‐out order, but other orderings are possible.
 *      - MAP: A map is a collection that maps a key to a value, with no duplicate keys allowed. The elements in a map
 *        are key/value pairs.
 *
 * The Map doesn't implement the Collection interface. It is considered part of the Java Collections Framework, even
 * though it isn't technically a Collection.
 */
public class CollectionTypes {

    public static final String SPARROW = "Sparrow";
    public static final String HAWK = "hawk";

    /**
     * NOTE: Pay special attention to which names are classes and which are interfaces. The exam may ask you which is
     * the best class or which is the best interface for a given scenario.
     */
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();        // USING THE DIAMOND '<>' OPERATOR.
        numbers.add(1);
        numbers.add(Integer.valueOf(3));
        numbers.add(Integer.valueOf(5));
        numbers.remove(1);                          // IT REMOVES THE INTEGER AT INDEX 1: remove(int index).
        numbers.remove(Integer.valueOf(5));               // IT REMOVES THE NUMBER 5 FROM THE LIST: remove(Integer value).
        System.out.println("Number list: " + numbers);    // PRINT: [1]

        /*
         * COLLECTION INTERFACE :
         * The Collection interface contains useful methods for working with lists, sets, and queues.
         */
        addingToCollection();
        removingFromCollection();
        otherCollectionMethods();
        /*
         * LIST INTERFACE:
         *
         * ArrayList: The main benefit is that you can look up any element in constant time. Adding or removing an
         * element is slower than accessing an element. This makes an ArrayList a good choice when you are reading more
         * often than (or the same amount as) writing to the ArrayList.
         *
         * LinkedList: it is special case because it implements both List and Queue. The main benefits of a LinkedList
         * are that you can access, add, and remove from the beginning and end of the list in constant time.
         * The trade‐off is that dealing with an arbitrary index takes linear time. This makes a LinkedList a good
         * choice when you'll be using it as Queue.
         */
        listsFromFactory();
        listMethods();
        /*
         * SET INTERFACE:
         *
         * HashSet: stores its elements in a hash table, which means the keys are a hash and the values are an Object.
         * This means that it uses the hashCode() method of the objects to retrieve them more efficiently.
         * The main benefit is that adding elements and checking whether an element is in the set both have constant time.
         * The trade‐off is that you lose the order in which you inserted the elements. Most of the time, you are not
         * concerned with this in a set anyway, making HashSet the most common set.
         *
         * TreeSet: stores its elements in a sorted tree structure. The main benefit is that the set is always in sorted
         * order. The trade‐off is that adding and checking whether an element exists take longer than with a HashSet,
         * especially as the tree grows larger.
         */
        setMethods();

        /*
         * QUEUE INTERFACE:
         * You use a queue when elements are added and removed in a specific order. Queues are typically used for sorting
         * elements prior to processing them. Unless stated otherwise, a queue is assumed to be FIFO (first‐in, first‐out).
         * The other format is LIFO (last‐in, first‐out), which is commonly referred to as a stack.
         *
         * Example of a Queue:      FRONT -->> Rover - Spot - Bella <<-- BACK
         *
         * Since this is a FIFO queue, Rover is first, which means he was the first one to arrive. Bella is last, which
         * means she was last to arrive and has the longest wait remaining. All queues have specific requirements for
         * adding and removing the next element. Beyond that, they each offer different functionality.
         *
         * LinkedList: In addition to being a list, it is a double‐ended queue. A double‐ended queue is different from a
         * regular queue in that you can insert and remove elements from both the front and back of the queue.
         * The main benefit of a LinkedList is that it implements both the List and Queue interfaces. The trade‐off is
         * that it isn't as efficient as a “pure” queue. You can use the ArrayDeque class (short for double‐ended queue)
         * if you need a more efficient queue.
         */
        queueMethods();

        /*
         * MAP INTERFACE:
         * You use a map when you want to identify values by a key. You don't need to know the names of the specific
         * interfaces that the different maps implement, but you do need to know that TreeMap is sorted. The main thing
         * that all Map classes have in common is that they all have keys and values.
         *
         * HashMap: stores the keys in a hash table. This means that it uses the hashCode() method of the keys to retrieve
         * their values more efficiently. The main benefit is that adding elements and retrieving the element by key both
         * have constant time. The trade‐off is that you lose the order in which you inserted the elements. Most of the
         * time, you aren't concerned with this in a map anyway. If you were, you could use LinkedHashMap.
         *
         * TreeMap: A TreeMap stores the keys in a sorted tree structure. The main benefit is that the keys are always
         * in sorted order. Like a TreeSet, the trade‐off is that adding and checking whether a key is present takes
         * longer as the tree grows larger.
         */
        mapsFromFactory();
        mapsMethods();
    }

    public static void addingToCollection() {
        Collection<String> list = new ArrayList<>();
        System.out.println(list.add(SPARROW));          // PRINT: TRUE
        System.out.println(list.add(SPARROW));          // PRINT: TRUE

        Collection<String> set = new HashSet<>();
        System.out.println(set.add(SPARROW));           // PRINT: TRUE
        System.out.println(set.add(SPARROW));           // PRINT: FALSE
    }

    public static void removingFromCollection() {
        Collection<String> list = new ArrayList<>();
        list.add(HAWK);
        list.add(HAWK);
        System.out.println(list.remove("cardinal"));      // PRINTS: FALSE
        System.out.println(list.remove(HAWK));               // PRINTS: TRUE
        System.out.println("List after removing: " + list);  // PRINTS: [hawk]
        // list.remove(100);                                 Throws IndexOutOfBoundsException => index 100 does not exist.
        list.add(HAWK);
        list.add(HAWK);
        for (String bird : list) {
            // list.remove(bird);                            Throws ConcurrentModificationException at runtime.
            System.out.println(bird);
        }
    }

    public static void otherCollectionMethods() {
        // ISEMPTY() AND SIZE()
        Collection<String> list = new ArrayList<>();
        System.out.println(list.isEmpty());                     // PRINT: TRUE
        System.out.println(list.size());                        // PRINT: 0
        list.add(HAWK);
        list.add(HAWK);
        System.out.println(list.isEmpty());                     // PRINT: FALSE
        System.out.println(list.size());                        // PRINT: 2

        // CLEAR()
        list.clear();
        System.out.println(list.isEmpty());                     // PRINT: TRUE
        System.out.println(list.size());                        // PRINT: 0

        // CONTAINS
        list.add(HAWK);
        System.out.println(list.contains(HAWK));                // PRINT: TRUE
        System.out.println(list.contains("robin"));             // PRINT: FALSE

        // removeIf(Predicate<? super E> filter)
        list.add("Magician");
        list.add("Assistant");
        list.removeIf((var s) -> s.startsWith("A"));            // startsWith("A") cannot be used with Method Reference.
        //                                                         To do that, we need a previous String var (str::startsWith).
        System.out.println("List after removeIf(): " + list);   // PRINT: [Magician]

        // removeIf() with method reference
        list.add("Wand");
        list.add("");
        list.removeIf(String::isEmpty);                         // s -> s.isEmpty()

        // forEach(Consumer<? super T> action)
        list.add("Annie");
        list.add("Ripley");
        list.forEach(System.out::println);                      // s -> System.out.println(s)
    }

    /**
     * Some of these methods return an immutable object, it cannot be changed or modified.
     */
    public static void listsFromFactory() {
        String originalArray[] = new String[]{"a", "b", "c"};
        List<String> asList = Arrays.asList(originalArray);     // Fixed-size list BACKED by the 'originalArray'.
        List<String> listOf = List.of(originalArray);           // Immutable list from the 'originalArray'.
        List<String> listCopyOf = List.copyOf(asList);          // Immutable list with copy of original 'asList' values.
        System.out.println("Backed list: " + asList);
        System.out.println("Immutable listOf: " + listOf);
        System.out.println("Immutable listCopyOf: " + listCopyOf);

        // TESTING BACKED LIST IN BI-DIRECTIONAL WAY
        originalArray[2] = "z";
        System.out.println("Original array: " + Arrays.toString(originalArray));
        System.out.println("Backed list: " + asList);
        asList.set(0, "x");
        System.out.println("Original array: " + Arrays.toString(originalArray));
        System.out.println("Backed list: " + asList);
    }

    /**
     * The methods in the List interface are for working with indexes in addition to the Collection inherited ones.
     *
     * LinkedList: The output would be the same if you tried these examples with LinkedList. Although the code would be
     * less efficient, it wouldn't be noticeable until you have very large lists.
     */
    public static void listMethods() {
        List<String> list = new ArrayList<>();
        list.add("SD");                             // [SD]
        list.add(0, "NY");            // [NY, SD] Adds element at index and moves the rest toward the end.
        list.set(1, "FL");                          // [NY, FL] Replaces element at index and returns original.
        System.out.println(list.get(0));            // NY
        list.remove("NY");                       // [FL]
        list.remove(0);                       // []
        // list.set(0, "?");                        // IndexOutOfBoundException ==>> there are no elements to replace.

        // replaceAll(UnaryOperator<E> operator)
        List<Integer> numbers = Arrays.asList(1,2,3);
        numbers.replaceAll(x -> (x * 2));
        System.out.println(numbers);                // [2,4,6]

        // LINKED LIST WITH SAME METHODS
        List<String> linkedList = new LinkedList<>();
        linkedList.add("SD");
        linkedList.add(0, "NY");
        linkedList.set(1, "FL");
        System.out.println(linkedList.get(0));
        linkedList.remove("NY");
        linkedList.remove(0);
        List<Integer> linkedNumbers = Arrays.asList(1,2,3);
        linkedNumbers.replaceAll(x -> (x * 2));
        System.out.println(linkedNumbers);
    }

    /**
     * Remember that the equals() method is used to determine equality. The hashCode() method is used to know which
     * bucket to look in so that Java doesn't have to look through the whole set to find out whether an object is there.
     */
    public static void setMethods() {
        // Set<Character> letters = Set.of('z','o','o');        IllegalArgumentException: duplicate element: o
        Set<Character> letters = Set.of('z','o','O','1');
        System.out.println("Letters hashset: " + letters);      // PRINT: [z, 1, O, o] => print values in arbitrary order.
        Set<Character> lettersCopy = Set.copyOf(letters);
        System.out.println("Letters copy: " + lettersCopy);     // PRINT: [z, 1, O, o]

        Set<Integer> set = new HashSet<>();
        boolean b1 = set.add(66);                   // TRUE
        boolean b2 = set.add(10);                   // TRUE
        boolean b3 = set.add(66);                   // FALSE
        boolean b4 = set.add(8);                    // TRUE
        set.forEach(System.out::println);           // PRINTS 66 8 10 => it prints the values in an arbitrary order.

        Set<Integer> treeSet = new TreeSet<>();
        boolean b5 = treeSet.add(66);               // TRUE
        boolean b6 = treeSet.add(10);               // TRUE
        boolean b7 = treeSet.add(66);               // FALSE
        boolean b8 = treeSet.add(8);                // TRUE
        treeSet.forEach(System.out::println);       // PRINTS: 8 10 66 => it prints the values in a natural order.
    }

    /**
     * There are basically two sets of methods. One set throws an exception when something goes wrong. The other uses a
     * different return value when something goes wrong. The offer()/ poll()/ peek() methods are the more common.
     *
     * boolean add(E elm): Adds an element to the back of the queue and returns true, or throws an exception.
     * E remove()        : Removes and returns next element, or throws an exception if the queue is empty.
     * E element()       : Returns the next element, or throws an exception if the queue is empty.
     *
     * boolean offer(E elm): Adds an element to the back of the queue and return whether was successful.
     * E pool()            : Removes and returns next element or return null if the queue is empty.
     * E peek()            : Return the next element, or return null if the queue is empty.
     */
    public static void queueMethods() {
        Queue<Integer> queue = new LinkedList<>();
        System.out.println(queue.offer(10));     // TRUE:    FRONT -->> 10 <<-- BACK
        System.out.println(queue.offer(4));      // TRUE:    FRONT -->> 10 - 4 <<-- BACK
        System.out.println(queue.peek());           // 10:      FRONT -->> 10 - 4 <<-- BACK
        System.out.println(queue.poll());           // 10:      FRONT -->> 4 <<-- BACK
        System.out.println(queue.poll());           // 4:       FRONT -->><<-- BACK
        System.out.println(queue.peek());           // null     FRONT -->><<-- BACK
    }

    /**
     * void clear()                              : Removes all keys and values from the map.
     * boolean containsKey(Object key)           : Returns whether key is in map.
     * boolean containsValue(Object value)       : Returns whether value is in map.
     * Set<Map.Entry<K,V>> entrySet()            : Returns a Set of key/value pairs.
     * void forEach(BiConsumer(K key, V value))  : Loop through each key/value pair.
     * V get(Object key)                         : Returns the value mapped by key or null if none is mapped.
     * boolean isEmpty()                         : Returns whether the map is empty.
     * Set<K> keySet()                           : Returns set of all keys.
     *
     * V put(K key, V value)                     : Adds or replaces key/value pair. Returns previous value or null.
     * V putIfAbsent(K key, V value)             : Adds value if key not present and returns null. Otherwise, returns
     *                                             existing value.
     * V remove(Object key)                      : Removes and returns the value mapped to key. Returns null if none.
     * V replace(K key, V value)                 : Replaces the value for a given key if the key is set. Returns the
     *                                             original value replaced, or null if none.
     * int size()                                : Returns the number of entries (key/value pairs) in the map.
     * Collection<V> values()                    : Returns Collection of all values.
     * void replaceAll(BiFunction<K, V, V> func) : Replaces each value with the results of the function.
     * V getOrDefault(Object key, V defaultValue): Returns the value mapped by the key or the default value if
     *                                             none is mapped.
     * V merge(K key, V value, Function(<V, V, V> func)): Sets value if key not set. Runs the function if the key is set.
     *                                                    to determine the new value. Removes if null.
     */
    public static void mapsFromFactory() {
        // CREATING A HASHMAP FROM AN IMMUTABLE MAP
        Map<String, String> mapOf = Map.of("koala", "bamboo", "lion", "meat", "giraffe", "leaf");
        Map<String, String> hashMap = new HashMap<>(mapOf);         // MAP WITH NOT SORTED ORDER KEYS
        for (String key : hashMap.keySet()) {
            System.out.print(key + ", ");                           // PRINT: koala, lion, giraffe
        }
        System.out.println();

        // CREATING A TREEMAP FROM AN IMMUTABLE MAP
        Map<String, String> mapOfEntries = Map.ofEntries(
                Map.entry("koala", "bamboo"),
                Map.entry("lion", "meat"),
                Map.entry("giraffe", "leaf")
        );
        Map<String, String> treeMap = new TreeMap<>(mapOfEntries);  // MAP WITH SORTED THE KEYS
        for (String key : treeMap.keySet()) {
            System.out.print(key + ", ");                           // PRINT: giraffe, koala, lion
        }
        System.out.println();
        for (String value : treeMap.values()) {
            System.out.print(value + ", ");         // PRINT: leaf, bamboo, meat. Corresponding to the sorted key map.
        }

        // MAKING SOME BOOLEAN CHECKS
        // Note: The contains() method is on the Collection interface, but not in the Map interface.
        System.out.println(treeMap.containsKey("lion"));            // PRINT: TRUE
        System.out.println(treeMap.containsValue("lion"));          // PRINT: FALSE
        System.out.println(treeMap.size());                         // PRINT: 3
        treeMap.clear();
        System.out.println(treeMap.size());                         // PRINT: 0
        System.out.println(treeMap.isEmpty());                      // PRINT: TRUE
    }

    public static void mapsMethods() {
        // FOREACH AND ENTRY-SET:
        Map<Integer, Character> map = new HashMap<>();
        map.put(1, 'a');
        map.put(2, 'b');
        map.put(3, 'c');
        map.forEach((k,v) -> System.out.println(v));    // The lambda used by the forEach() method has two parameters:
        //                                                 the key and the value.

        // If we don't care about the key, this particular code could have been written with the values() method.
        map.values().forEach(System.out::println);
        // Another way of going through all the data in a map is to get the key/value pairs in a Set.
        map.entrySet().forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue()));

        // GET OR DEFAULT:
        Map<Character, String> map2 = Map.ofEntries(
          Map.entry('x', "spot")
        );
        System.out.println("X marks the " + map2.get('x'));
        System.out.println("X marks the " + map2.getOrDefault('x', ""));
        System.out.println("Y marks the " + map2.get('y'));
        System.out.println("Y marks the " + map2.getOrDefault('y', ""));

        // REPLACE AND REPLACE-ALL
        Map<Integer, Integer> map3 = new HashMap<>();
        map3.put(1, 2);
        map3.put(2, 4);
        Integer ordinal = map3.replace(2, 10);      // 4: the previous value associated with the specified key.
        System.out.println(map3);                   // PRINTS: { 1=2, 2=10 } after the replacement.
        map3.replaceAll((k, v) -> k + v);           // Sets the value of each element with the result of the function.
        // map3.replaceAll(Integer::sum);              We can use Method Reference.
        System.out.println(map3);                   // PRINTS: { 1=3, 2=12 } after the replacement.

        // PUT-IF-ABSENT:
        // Sets a value in the map but skips it if the value is set to a NON-NULL value.
        Map<String, String> map4 = new HashMap<>();
        map4.put("Jenny", "Bus Tour");
        map4.put("Tom", null);
        map4.putIfAbsent("Jenny", "Tram");
        map4.putIfAbsent("Sam", "Tram");
        map4.putIfAbsent("Tom", "Tram");
        System.out.println(map4);           // PRINTS: {Tom=Tram, Jenny=Bus Tour, Sam=Tram} after the replacement.

        // MERGE:
        // The merge() method adds logic of what to choose. Suppose we want to choose the ride with the longest name.
        // We can write code to express this by passing a mapping function to the merge() method.
        BiFunction<String, String, String> mapper = (originalValue, proposedValue) ->
                originalValue.length() > proposedValue.length()? originalValue : proposedValue;
        Map<String, String> map5 = new HashMap<>();
        map5.putIfAbsent("Jenny", "Bus Tour");
        map5.putIfAbsent("Tom", "Tram");
        String jenny = map5.merge("Jenny", "SkyRide", mapper);
        System.out.println("Map5, new value for key Jenny: " + jenny);      // PRINT: Bus Tour
        String tom   = map5.merge("Tom", "SkyRide", mapper);      // PRINT: SkyRide
        System.out.println("Map5, new value for key Tom: " + tom);
        System.out.println("Final map5: " + map5);                          // PRINT: {Tom=SkyRide, Jenny=Bus Tour} finally.

        // The merge() method also has logic for what happens if null values or missing keys are involved. In this case,
        // it doesn't call the BiFunction at all, and it simply uses the new value.
        // NOTE: the mapping function isn't called. If it were, we'd have a NullPointerException. The mapping function
        // is used only when there are two actual values to decide between.
        Map<String, String> map6 = new HashMap<>();
        map6.put("Sam", null);
        map6.merge("Tom", "SkyRide", mapper);
        map6.merge("Sam", "SkyRide", mapper);
        System.out.println("Final map6: " + map6);          // PRINT: {Tom=SkyRide, Sam=SkyRide} finally.

        // What happens when the mapping function is called and returns null??. In this case, the key is removed from
        // the map when this happens:
        BiFunction<String, String, String> mapper2 = (originalValue, proposedValue) -> null;
        Map<String, String> map7 = new HashMap<>();
        map7.put("Jenny", "Bus Tour");
        map7.put("Tom", "Bus Tour");
        map7.merge("Jenny", "SkyRide", mapper2);    // This key is removed from the map.
        map7.merge("Sam", "SkyRide", mapper2);      // Adding this non-existing entry to the map.
        System.out.println("Final map7: " + map7);            // PRINT: {Tom=Bus Tour, Sam=SkyRide} finally.
    }
}
