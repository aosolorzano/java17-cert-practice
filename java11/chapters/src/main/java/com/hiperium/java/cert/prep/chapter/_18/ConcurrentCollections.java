package com.hiperium.java.cert.prep.chapter._18;

import java.util.*;
import java.util.concurrent.*;

/**
 * CONCURRENT COLLECTION CLASSES:
 *
 * *************************|****************************************|*******************|***********|***********|
 * Class name               | Java Collections Framework interfaces  | Elements ordered? | Sorted?   | Blocking? |
 * *************************|****************************************|*******************|***********|***********|
 * ConcurrentHashMap        | ConcurrentMap                          | No                | No        | No        |
 * -------------------------|----------------------------------------|-------------------|-----------|-----------|
 * ConcurrentLinkedQueue    | Queue                                  | Yes               | No        | No        |
 * -------------------------|----------------------------------------|-------------------|-----------|-----------|
 * ConcurrentSkipListMap    | ConcurrentMap, SortedMap, NavigableMap | Yes               | Yes       | No        |
 * -------------------------|----------------------------------------|-------------------|-----------|-----------|
 * ConcurrentSkipListSet    | SortedSet, NavigableSet                | Yes               | Yes       | No        |
 * -------------------------|----------------------------------------|-------------------|-----------|-----------|
 * CopyOnWriteArrayList     | List                                   | Yes               | No        | No        |
 * -------------------------|----------------------------------------|-------------------|-----------|-----------|
 * CopyOnWriteArraySet      | Set                                    | No                | No        | No        |
 * -------------------------|----------------------------------------|-------------------|-----------|-----------|
 * LinkedBlockingQueue      | BlockingQueue                          | Yes               | No        | Yes       |
 * -------------------------|----------------------------------------|-------------------|-----------|-----------|
 *
 */
public class ConcurrentCollections {

    public static void main(String[] args) {
        mapAndQueueCollections();
        skipListCollections();
        copyOnWriteListCollections();
        copyOnWriteSetCollections();
        blockingQueueCollections();
        obtainingSyncCollections();
    }

    /**
     * Based on our knowledge of collections, classes like ConcurrentHashMap and ConcurrentLinkedQueue should be quite
     * easy to learn.
     */
    private static void mapAndQueueCollections() {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        map.put("zebra", 52);
        map.put("elephant", 510);
        System.out.println(map.get("elephant"));                // PRINT: 10

        Queue<Integer> queue = new ConcurrentLinkedQueue<>();
        queue.offer(31);
        System.out.println(queue.peek());                       // PRINT: 31
        System.out.println(queue.poll());                       // PRINT: 31
    }

    /**
     * The SkipList classes: ConcurrentSkipListSet and ConcurrentSkipListMap, are concurrent versions of their sorted
     * counterparts: TreeSet and TreeMap, respectively. They maintain their elements or keys in the natural ordering of
     * their elements.
     */
    private static void skipListCollections() {
        Set<String> gardenAnimals = new ConcurrentSkipListSet<>();
        gardenAnimals.add("rabbit");
        gardenAnimals.add("gopher");
        // System.out.println(gardenAnimals.stream().collect(Collectors.joining(", ")));    We can use String.join().
        System.out.println(String.join(", ", gardenAnimals));

        Map<String, String> rainForestAnimalDiet = new ConcurrentSkipListMap<>();
        rainForestAnimalDiet.put("koala", "bamboo");
        rainForestAnimalDiet.put("lion", "meat");
        rainForestAnimalDiet.entrySet()
                .stream()
                .forEach((e) -> System.out.println(e.getKey() + " - " + e.getValue()));
    }

    /**
     * These classes copy all of their elements to a new underlying structure anytime an element is added, modified, or
     * removed from the collection. By a modified element, we mean that the reference in the collection is changed.
     * Modifying the actual contents of objects within the collection will not cause a new structure to be allocated.
     *
     * Although the data is copied to a new underlying structure, our reference to the Collection object does not change.
     * This is particularly useful in multi-threaded environments that need to iterate the collection. Any iterator
     * established prior to a modification will not see the changes, but instead it will iterate over the original
     * elements prior to the modification.
     */
    private static void copyOnWriteListCollections() {
        List<Integer> favNumbers = new CopyOnWriteArrayList<>(List.of(4, 3, 42));
        for(var n: favNumbers) {
            System.out.print(n + " ");                      // PRINT: 4, 3, 42
            favNumbers.add(9);                              // We can add elements while we are iterating over it.
        }
        System.out.println();
        System.out.println("Size: " + favNumbers.size());   // PRINT: 6
    }

    /**
     * NOTE: The CopyOnWrite classes can use a lot of memory, since a new collection structure needs to be allocated
     * anytime the collection is modified. They are commonly used in multi-threaded environment situations where reads
     * are far more common than writes.
     */
    private static void copyOnWriteSetCollections() {
        Set<Character> favLetters = new CopyOnWriteArraySet<>(List.of('a', 't'));
        for (char c : favLetters) {
            System.out.print(c + " ");                      // PRINT: 'a', 't'
            favLetters.add('s');
        }
        System.out.println();
        System.out.println("Size: " + favLetters.size());   // PRINT: 3
    }

    /**
     * The BlockingQueue is just like a regular Queue, except that it includes methods that will wait a specific amount
     * of time to complete an operation.
     *
     * ****************************************|***************************************************************************|
     * Method name                             | Description                                                               |
     * ----------------------------------------|---------------------------------------------------------------------------|
     * offer(E e, long timeout, TimeUnit unit) | Adds an item to the queue, waiting the specified time and returning false |
     *                                         | if the time elapses before space is available.                            |
     * ----------------------------------------|---------------------------------------------------------------------------|
     * poll(long timeout, TimeUnit unit)       | Retrieves and removes an item from the queue, waiting the specified time  |
     *                                         | and returning null if the time elapses before the item is available.      |
     * ----------------------------------------|---------------------------------------------------------------------------|
     *
     * The following sample is using a LinkedBlockingQueue to wait for the results of some operations.
     */
    private static void blockingQueueCollections() {
        try {
            var blockingQueue = new LinkedBlockingQueue<Integer>();
            blockingQueue.offer(39);
            blockingQueue.offer(3, 2, TimeUnit.SECONDS);

            System.out.println(blockingQueue.poll());                                   // PRINT: 39
            System.out.println(blockingQueue.poll(10, TimeUnit.MILLISECONDS));  // PRINT: 3
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Besides the concurrent collection classes, the Concurrency API also includes methods for obtaining synchronized
     * versions of existing non-concurrent collection objects. These synchronized methods are defined in the Collections
     * class.
     *
     * synchronizedCollection(Collection<T> c)
     * synchronizedList(List<T> list)
     * synchronizedMap(Map<K,V> m)
     * synchronizedNavigableMap(NavigableMap<K,V> m)
     * synchronizedNavigableSet(NavigableSet<T> s)
     * synchronizedSet(Set<T> s)
     * synchronizedSortedMap(SortedMap<K,V> m)
     * synchronizedSortedSet(SortedSet<T> s)
     *
     * IMPORTANT: When should we use these methods? If we know at the time of creation that our object requires sync,
     * then we should use one of the concurrent collection classes used in previous methods. On the other hand, if we
     * are given an existing collection that is not a concurrent class and need to access it among multiple threads, we
     * can wrap it using the methods listed before.
     *
     * Unlike the concurrent collections, the synchronized collections also throw an exception if they are modified
     * within an iterator by a single thread.
     */
    private static void obtainingSyncCollections() {
        var foodData = new HashMap<String, Integer>();
        foodData.put("penguin", 1);
        foodData.put("flamingo", 2);
        var synFoodData = Collections.synchronizedMap(foodData);    // <<<<<<<<---
        for(String key: synFoodData.keySet()) {
            // synFoodData.remove(key);                     // ERROR: Throw ConcurrentModificationException.
            System.out.println("Sync map key: " + key);
        }
    }
}
