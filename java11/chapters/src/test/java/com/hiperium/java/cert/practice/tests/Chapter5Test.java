package com.hiperium.java.cert.practice.tests;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Working with UsingArrays and Collections:
 *
 *      - Use generics, including wildcards.
 *      - Use a Java array and List, Set, Map and Deque collections, including convenience methods.
 *      - Sort collections and arrays using Comparator and Comparable interfaces.
 */
public class Chapter5Test {

    /**
     * What is the output of the following?
     */
    @Test
    public void question1() {
        List<String> museums = new ArrayList<>(1);
        museums.add("Natural History");
        museums.add("Science");
        museums.add("Arts");
        museums.remove(2);
        System.out.println(museums);        // PRINT: [Natural History, Science]
        Assert.assertTrue(true);
    }

    /**
     * How many of the following are legal declarations?
     */
    @Test
    public void question2() {
        // []String lions  = new String[];                  ERROR: Syntax error.
        // String[] lions  = new String[];                  ERROR: Array initializer expected.
        String[]    lions  = new String[1];

        // String[] tigers = new String[1] {"tiger"};       ERROR: Not a statement.
        String[] tigers    = new String[]  {"tiger"};
        String   bears[]   = new String[]  {};

        // String ohMy[] = new String[0]{};
        String[] ohMy    = new String[0];
        Assert.assertTrue(true);
    }

    /**
     * Which of the following can fill in the blank to make the code compile?
     *
     * public class News<___> {}
     */
    public static class Question3<Object> {}
    public static class QuestionN<N> {}
    public static class News<News> {}

    /**
     * What is true of this code?
     */
    @Test
    public void question4() {
        // List<String> strings = new ArrayList<?>();       ERROR: Wildcard type '?' cannot be instantiated directly.
        var ints = new HashSet<Integer>();
        Double dbl = 5.0;
        ints.add(2);
        ints.add(null);
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following?
     *
     * offer(): Inserts the specified element at the end of this deque.
     * peek() : Retrieves, but does not remove, the head of the queue represented by this deque, or returns null if this
     *          deque is empty.
     */
    @Test
    public void question6() {
        var q = new ArrayDeque<String>();
        q.offer("snowball");
        q.offer("minnie");
        q.offer("sugar");
        System.out.println(q.peek() + " - " + q.peek() + " - " + q.size());     // PRINT: snowball - snowball - 3
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 12:
     *
     * What is true of the following code?
     */
    private void sortAndSearch(String... args) {
        var one = args[0];
        Arrays.sort(args);
        // String result = UsingArrays.binarySearch(args, one);      ERROR: Required type: String - Provided: int
        int result = Arrays.binarySearch(args, one);
        System.out.println(result);                          // PRINT: 1
    }
    @Test
    public void question12() {
        this.sortAndSearch("seed", "flower");
        Assert.assertTrue(true);
    }

    /**
     * How many of the following are legal declarations?
     */
    @Test
    public void question13() {
        // [][]String alpha;        ERROR: Unexpected token.
        // []String beta;           ERROR: Unexpected token.
        // var[][] zeta;            ERROR: Unexpected token.
        String[][] gamma;
        String[] delta[];
        String epsilon[][];
        Assert.assertTrue(true);
    }

    /**
     * How many of the following are legal declarations?
     */
    @Test
    public void question14() {
        var list = new ArrayList<Integer>();
        list.add(56);
        list.add(56);
        list.add(3);
        var set = new TreeSet<Integer>(list);
        System.out.print(set.size());               // PRINT: 2
        System.out.print(" " );
        System.out.print(set.iterator().next());    // PRINT: 3
        Assert.assertTrue(true);
    }

    /**
     * What is true of the code when run as java Copier.java? (Choose two.)
     *
     * D. Four lines contain a compiler error.
     * E. If the compiler errors were fixed, the code would throw an exception.
     */
    private void copier(String... original) {
        // String...copy = original;                ERROR: Unexpected token.
        // UsingArrays.linearSort(original);             ERROR: Cannot resolve method 'linearSort' in 'UsingArrays'.
        // UsingArrays.search(original, "");             ERROR: Cannot resolve method 'search' in 'UsingArrays'.
        // System.out.println(original.size()       ERROR: Cannot resolve method 'size' in 'String'.
        //        + " " + original[0]);
        System.out.println(original[0]);            // Throws java.lang.ArrayIndexOutOfBoundsException
    }
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void question15() {
        this.copier();
    }

    /**
     * What is the output of the following? (Choose three.)
     *      20: var chars = new ___<Character>();
     *      21: chars.add('a');
     *      22: chars.add(Character.valueOf('b'));
     *      23: chars.set(1, 'c');
     *      24: chars.remove(0);
     *      25: System.out.print(chars.size() + " " + chars.contains('b'));
     */
    @Test
    public void question16() {
        var chars = new ArrayList<Character>();
        chars.add('a');
        chars.add(Character.valueOf('b'));
        chars.set(1, 'c');                      // Replaces the element at the specified position with the new one.
        chars.remove(0);
        System.out.println("chars.size() = " + chars.size());                   // PRINT: 1
        System.out.println("chars.contains('b') = " + chars.contains('b'));     // PRINT: FALSE

        // USING A HASHSET
        var chars2 = new HashSet<Character>();
        chars2.add('a');
        chars2.add(Character.valueOf('b'));
    //  chars2.set(1, 'c');                   ERROR: Cannot resolve method 'set' in 'HashSet'.
        chars2.remove(0);                  // WARN: 'HashSet<Character>' may not contain objects of type 'Integer'.
        System.out.println("chars2.size() = " + chars2.size());                 // PRINT: 2
        System.out.println("chars2.contains('b') = " + chars2.contains('b'));   // PRINT: TRUE
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 17:
     *
     * What is the output of the following?
     *
     * E. The code compiles but throws an exception at runtime.
     *
     * NOTE: Magazine cannot be cast to class java.lang.Comparable, because it does not implement it.
     */
    class Magazine {
        private String name;
        public Magazine(String name) {
            this.name = name;
        }
        public int compareTo(Magazine m) {
            return name.compareTo(m.name);
        }
        public String toString() {
            return name;
        }
    }
    @Test(expected = ClassCastException.class)
    public void question17() {
        var set = new TreeSet<Magazine>();      // WARN: Construction of sorted collection with non-comparable elements.
        set.add(new Magazine("highlights"));
        set.add(new Magazine("Newsweek"));
        set.add(new Magazine("highlights"));
        System.out.println(set.iterator().next());
    }

    /**
     * Which is the first line to prevent this code from compiling and running without error?
     *
     * R./ Line r2
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void question18() {
        char[][] ticTacToe = new char[3][3];                    // r1
        ticTacToe[1][3] = 'X';                                  // r2
        ticTacToe[2][2] = 'X';
        ticTacToe[3][1] = 'X';                                  // WARN: Array index is out of bounds.
        System.out.println(ticTacToe.length + " in a row!");    // r3
    }

    /**
     * What is a possible result of this code?
     */
    @Test
    public void question20() {
        var nums = new HashSet<Long>();
        nums.add((long) Math.min(5, 3));    // Returns the smaller of two INT values.
        nums.add(Math.round(3.14));         // Returns the closest LONG to the argument.
        nums.add((long) Math.pow(4, 2));    // Returns a DOUBLE value of the 1st argument raised to the power of 2nd.
        System.out.println(nums);           // PRINT: [16, 3]
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following?
     *
     * offer(): Adds the specified element as the tail (last element) of this list.
     * poll() : Retrieves and removes the head (first element) of this list.
     * push() : Inserts the element at the front of this list. This method is equivalent to addFirst.
     */
    @Test
    public void question21() {
        var x = new LinkedList<Integer>();
        x.offer(18);
        x.offer(5);
        x.push(13);
        System.out.println(x.poll() + " " + x.poll()); // PRINT: 13 18
        Assert.assertTrue(true);
    }

    /**
     * What does the following output?
     *
     * mismatch(): Finds and returns the index of the first mismatch between two Object arrays, otherwise return -1
     * if no mismatch is found or the arrays are the same.
     */
    @Test
    public void question26() {
        var linux = new String[]{"Linux", "Mac", "Windows"};
        var mac = new String[]{"Mac", "Linux", "Windows"};
        var search = Arrays.binarySearch(linux, "Linux");

        var mismatch1 = Arrays.mismatch(linux, mac);
        var mismatch2 = Arrays.mismatch(mac, mac);

        System.out.println(search + " " + mismatch1 + " " + mismatch2);     // PRINT: 0 0 -1
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 27:
     *
     * Which line in the main() method doesn't compile or points to a class that doesn't compile?
     */
    interface Comic<C> {
        void draw(C c);
    }
    static class ComicClass<C> implements Comic<C> {
        public void draw(C c) {
            System.out.println("From ComicClass: " + c);
        }
    }
    static class SnoopyClass implements Comic<Snoopy> {     // We are using 'Snoopy' class as generic parameter type.
        public void draw(Snoopy c) {
            System.out.println("From SnoopyClass: " + c);
        }
    }
    static class SnoopyComic implements Comic<Snoopy> {     // We are using 'Snoopy' class as generic parameter type.
    //  public void draw(C c) {}                            ERROR: Cannot resolve symbol 'C'.
        public void draw(Snoopy c) {
            System.out.println("From SnoopyComic: " + c);
        }
    }
    static class AnotherSnoopy<GenericTypeID> implements Comic<GenericTypeID> {
        public void draw(GenericTypeID c) {
            System.out.println("From AnotherSnoopy: " + c);
        }
    }
    static class Snoopy {
        public static void main(Snoopy snoopy) {
            Comic<Snoopy> c1 = c -> System.out.println(c);  c1.draw(snoopy);    // PRINT: Snoopy{}
            Comic<Snoopy> c2 = new ComicClass<>();          c2.draw(snoopy);    // PRINT: From ComicClass: Snoopy{}
            Comic<Snoopy> c3 = new SnoopyClass();           c3.draw(snoopy);    // PRINT: From SnoopyClass: Snoopy{}
            Comic<Snoopy> c4 = new SnoopyComic();           c4.draw(snoopy);    // PRINT: From SnoopyComic: Snoopy{}
        }
        @Override
        public String toString() {
            return "Snoopy{}";
        }
    }
    @Test
    public void question27() {
        Snoopy snoopy = new Snoopy();
        Snoopy.main(snoopy);
        Assert.assertTrue(true);
    }

    /**
     * Which of the following fill in the blank to print out true? (Choose two.)
     *
     *      String[] array = {"Natural History", "Science"};
     *      var museums = _______________________;
     *      museums.set(0, "Art");
     *      System.out.println(museums.contains("Art"));
     */
    @Test
    public void question30() {
        String[] array = {"Natural History", "Science"};
        // var museums = List.of(array);                            Throws RTE: Returns an unmodifiable list.
        // var museums = List.of("Natural History", "Science");     Throws RTE: Returns an unmodifiable list.
        // var museums = UsingArrays.asList(array);                      OK: Returns a fixed-size list backed by the array.
        var museums = Arrays.asList("Natural History", "Science");
        museums.set(0, "Art");
        System.out.println(museums.contains("Art"));                // PRINT: true
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 31:
     *
     * Which option cannot fill in the blank to print Clean socks?
     */
    static class Wash<T> {
        T item;
        public void clean(T item) {
            System.out.println("Clean " + item);
        }
    }
    static class LaundryTime {
        public static void main() {
        //  var wash          = new Wash<String>();      OK
        //  var wash          = new Wash<>();            OK
        //  Wash wash         = new Wash();              OK
        //  Wash<String> wash = new Wash<>();            OK
            Wash wash         = new Wash<String>();
            wash.clean("socks");
        }
    }
    @Test
    public void question31() {
        LaundryTime.main();
        Assert.assertTrue(true);
    }

    /**
     * Fill in the blank so the code prints gamma.
     *
     *      var list = UsingArrays.asList("alpha", "beta", "gamma");
     *      Collections.sort(list, __________);
     *      System.out.println(list.get(0));
     */
    @Test
    public void question33() {
        var list = Arrays.asList("alpha", "beta", "gamma");
    //  Collections.sort(list, (s, t) -> t.compareTo(s));
        Collections.sort(list, Comparator.comparing((String s) -> s.charAt(0)).reversed());
        System.out.println(list.get(0));
        Assert.assertTrue(true);
    }

    /**
     * How many of the following are legal declarations?
     *
     * NOTE: When creating an array object, a set of elements or size is required.
     */
    @Test
    public void question34() {
        // float[] lion  = new float[];      ERROR: Array initializer expected.
           float[] tiger = new float[1];
        // float[] bear  = new[] float;      ERROR: Syntax error.
        // float[] ohMy  = new[1] float;     ERROR: Syntax error.
        Assert.assertTrue(true);
    }

    /**
     * Suppose we have list of type List<Integer>. Which method allows you to pass a List and returns an immutable Set
     * containing the same elements?
     */
    @Test
    public void question36() {
        List<Integer> list = List.of(1, 1, 2);
    //  Set<Integer> set = Set.of(list);        ERROR: Required type: Set<Integer> - Provided: Set<List<Integer>>.
        Set<Integer> set = Set.copyOf(list);
        System.out.println(set);                // PRINT: [1, 2]
        Assert.assertTrue(true);
    }

    /**
     * What does the following output?
     */
    @Test
    public void question37() {
        var os = new String[] {"Mac", "Linux", "Windows"};
        Arrays.sort(os);
        System.out.println(Arrays.binarySearch(os, "RedHat"));      // PRINT: -3
        System.out.println(Arrays.binarySearch(os, "Mac"));         // PRINT: 1
        Assert.assertTrue(true);
    }

    /**
     * What does the following output?
     */
    @Test
    public void question38() {
        var names = new HashMap<String, String>();
        names.put("peter", "pan");
        names.put("wendy", "darling");
        var first = names.entrySet();  // line x1
        // System.out.println(first.getKey());                          line x2: Cannot resolve method 'getKey' in 'Set'.
        for (Map.Entry<String, String> entry : first) {
            System.out.println(entry);                      // PRINT: peter=pan wendy=darling
            System.out.println(entry.getKey());             // PRINT: peter wendy
            System.out.println(entry.getValue());           // PRINT: pan   darling
        }
        Assert.assertTrue(true);
    }

    /**
     * Which of these elements are in the output of the following?
     *
     * pool(): Retrieves and removes the head of the queue represented by this deque (the first element of this
     * deque), or returns null if this deque is empty.
     */
    @Test
    public void question39() {
        var q = new ArrayDeque<String>();
        q.offerFirst("snowball");
        q.offer("sugar");
        q.offerLast("minnie");
        System.out.println("Queue: " + q);      // PRINT: [snowball, sugar, minnie]

        System.out.println(q.poll());           // PRINT: snowball
        System.out.println(q.removeFirst());    // PRINT: sugar
        System.out.println(q.size());           // PRINT: 1
        Assert.assertTrue(true);
    }

    /**
     * What is true about the output of the following code?
     *
     * compare(): the value 0 if the first and second array are equal and contain the same elements in the same order;
     * a value less than 0 if the first array is lexicographically less than the second array; and a value greater than
     * 0 if the first array is lexicographically greater than the second array.
     */
    @Test
    public void question42() {
        var ints = new int[]{3, 1, 4};
        var others = new int[]{2, 7, 1, 8};
        System.out.println(Arrays.compare(ints, others));   // PRINT: 1
        Assert.assertTrue(true);
    }

    /**
     * How many of these lines have a compiler error?
     */
    @Test
    public void question44() {
        var list = List.of('a', 'c', 'e');
        // Char letter1 = list.get(0);      ERROR: Cannot resolve symbol 'Char'.
        char letter2 = list.get(0);
        int letter3 = list.get(0);          // letter3 = 97
        // Integer letter4 = list.get(0);   ERROR: Required type: Integer -- Provided: Character
        Object letter5 = list.get(0);
        Assert.assertTrue(true);
    }

    /**
     * What is the result of the following?
     *
     * E. The result is not defined.
     *
     * binarySearch(): The list must be sorted into ascending order according to the natural ordering of its elements
     * prior to making this call. If it is not sorted, the results are undefined. If the list contains multiple elements
     * equal to the specified object, there is no guarantee which one will be found.
     */
    @Test
    public void question46() {
        Comparator<Integer> c = (x, y) -> y - x;
        var ints = Arrays.asList(3, 1, 4);
        Collections.sort(ints, c);
        System.out.println("Ordered array: " + ints);               // PRINT: [4, 3, 1]
        System.out.println(Collections.binarySearch(ints, 4));  // PRINT: -4
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 49:
     *
     * What is the result of the following when called as java Binary.java?
     *
     * B. []
     * D. The code throws an ArrayIndexOutOfBoundsException
     */
    public static class Binary {
        public static void main(String... args) {
            System.out.println("args = " + Arrays.toString(args));              // PRINT: []
            Arrays.sort(args);
            System.out.println("ordered args = " + Arrays.toString(args));      // PRINT: []
            System.out.println(args[0]);
        }
    }
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void question49() {
        Binary.main();
    }

    /**
     * What is the result of running the following program?
     */
    @Test(expected = ArrayStoreException.class)
    public void question51() {
        int[][] game = new int[6][6];
        game[3][3] = 6;
        Object[] obj = game;                // Assigns the same 6x6 array of 'game' to array ob Objects 'obj'.
        obj[3] = "X";                       // WARN: Storing a bat type of value to the original int array.
        System.out.println(game[3][3]);
    }

    /**
     * What is the output of the following?
     */
    @Test
    public void question53() {
        var threes = Arrays.asList("3", "three", "THREE");
        Collections.sort(threes);
        System.out.println(threes);         // PRINT: [3, THREE, three]
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following?
     *
     * D. The code does not compile.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void question55() {
        List<Character> chars = new ArrayList<>();
        chars.add('a');
        chars.add('b');
        chars.clear();
        chars.remove(0);                                         // WARN: Can throw an index of out bounds ar RT.
        // System.out.print(chars.isEmpty() + " " + chars.length());     ERROR: Cannot resolve method 'length' in 'List'.
        System.out.print(chars.isEmpty() + " " + chars.size());
    }

    /**
     * QUESTION 56:
     *
     * Which fills in the blank in the method signature to allow this code to compile?
     *
     * C. T extends Collection<U>
     */
    static class ExtendingGenerics {
        private static <T extends Collection<U>, U> U add(T list, U element) {
            list.add(element);
            return element;
        }
        public static void main() {
            var values = new ArrayList<String>();
            add(values, "duck");
            add(values, "duck");
            add(values, "goose");
            System.out.println(values);
        }
    }
    @Test
    public void question56() {
        ExtendingGenerics.main();
        Assert.assertTrue(true);
    }

    /**
     * Which is the first line to prevent this code from compiling and running without error?
     *
     * A. Line r1.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void question58() {
    //  char[][] ticTacToe = new char[3,3];                     // r1: Does not compile.
        char[][] ticTacToe = new char[3][3];                    // r1
        ticTacToe[1][3] = 'X';                                  // r2
        ticTacToe[2][2] = 'X';
        ticTacToe[3][1] = 'X';
        System.out.println(ticTacToe.length + " in a row!");    // r3
    }

    /**
     * What is the result of the following?
     */
    @Test
    public void question59() {
        var list = new ArrayList<String>();
        list.add("Austin");
        list.add("Boston");
        list.add("San Francisco");
        list.removeIf(a -> a.length() > 10);
        System.out.println("list.size() = " + list.size());     // PRINT: 2
        System.out.println("list = " + list);                   // PRINT: [Austin, Boston]
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 60:
     *
     * What happens when calling the following method with a non‐null and non‐empty array?
     */
    public static void addStationName(String[] names) {
    //  names[names.length]     = "Times Square";                   // Throws ArrayIndexOutOfBoundsException
        names[names.length - 1] = "Fixed";
        System.out.println("names = " + Arrays.toString(names));    // PRINT: [Fixed]
    }
    @Test
    public void question60() {
        addStationName(new String[]{"test"});
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 63:
     *
     * Which of the following fills in the blank so this code compiles?
     *
     *      getExceptions(Collection<___> coll) {}
     *
     * F. None of the above.
     *
     * NOTE: Review question 80 for a more perspective.
     */
    public static void option1(Collection<? extends RuntimeException> coll) {
    //  coll.add(new RuntimeException());   ERROR >>> Required type: <? extends RuntimeException> Provided: RTE.
    //  coll.add(new Exception());          ERROR >>> Required type: <? extends RuntimeException> Provided: Exception.
    }

    // NOTE: Broader types than the generic type are not allowed to be added to the list.
    public static void option2(Collection<? super RuntimeException> coll) {
        coll.add(new RuntimeException());
    //  coll.add(new Exception());              ERROR >>> Required type: <? super RuntimeException> Provided: Exception.
    }
    @Test
    public void question63() {
        List<IllegalArgumentException> list1 = new ArrayList<>();
        list1.add(new IllegalArgumentException("Exception 1"));
        List<RuntimeException> list2 = new ArrayList<>();
        list2.add(new RuntimeException("Exception 1"));
        List<Exception> list3 = new ArrayList<>();
        list3.add(new Exception("Exception 1"));

        option1(list1);     // OK >>> List<IllegalArgumentException>
        option1(list2);     // OK >>> List<RuntimeException>
    //  option1(list3);     ERROR >>> Required type: <? super RuntimeException> Provided: List<Exception>.
    //  option2(list1);     ERROR >>> Required type: <? super RuntimeException> Provided: List<IllegalArgumentException>.
        option2(list2);     // OK >>> List<RuntimeException>
        option2(list3);     // OK >>> List<Exception>
        Assert.assertTrue(true);
    }

    /**
     *  QUESTION 66:
     *
     * What is the output of the following?
     */
    static class MagazineQ implements Comparable<MagazineQ> {
        private String name;
        public MagazineQ(String name) {
            this.name = name;
        }
        @Override
        public int compareTo(MagazineQ m) {
            return name.compareTo(m.name);
        }
        @Override
        public String toString() {
            return name;
        }
    }
    @Test
    public void question66() {
        var set = new TreeSet<MagazineQ>();
        set.add(new MagazineQ("highlights"));
        set.add(new MagazineQ("Newsweek"));
        set.add(new MagazineQ("highlights"));
        System.out.println(set);                        // PRINT: [Newsweek, highlights]
        System.out.println(set.iterator().next());      // PRINT: Newsweek
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 67:
     *
     * Which options can fill in the blanks to print Cleaned 2 items?
     *
     * B. extends, List
     */
    static class WashQ<T extends Collection> {
        T item;
        public void clean(T items) {
            System.out.println("Cleaned " + items.size() + " items");
        }
    }
    // NOTE: ArrayList implements List, but not extend it.
    // However, List extends Collection.
    @Test
    public void question67() {
        WashQ<List> wash = new WashQ<>();
    //  WashQ<List> wash = new WashQ<ArrayList>();   ERROR >>> Required type: WashQ<List>, Provided: WashQ<ArrayList>.
        wash.clean(List.of("sock", "tie"));
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following?
     */
    @Test
    public void question69() {
        var listing = new String[][]{{"Book"}, {"Game", "29.99"}};
        System.out.println(listing.length + " " + listing[0].length);       // PRINT: 2 1
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following?
     *
     * E. The code does not compile.
     *
     * NOTE: "addFirst()" and "addLast()" are methods of the Deque interface. On the other hand ArrayDeque implements
     * Queue and Deque, but in this example, var "q" it is of type Queue, and that interface does not have "addFirst()"
     * and "addLast()" methods.
     *
     * add()  : Inserts the specified element into this queue.
     * offer(): Inserts the specified element into this queue.
     * peek() : Retrieves, but does not remove, the head of this queue, or returns null if this queue is empty.
     */
    @Test
    public void question70() {
        Queue<String> q = new ArrayDeque<>();
        q.add("snowball");
        // q.addLast("sugar");                              ERROR: Cannot resolve method 'addLast' in 'Queue'.
        // q.addFirst("minnie");                            ERROR: Cannot resolve method 'addFirst' in 'Queue'.
        q.offer("sugar");
        q.add("minnie");
        System.out.println("Queue: " + q);                  // PRINT: [snowball, sugar, minnie]
        System.out.println("q.peek() = " + q.peek());       // PRINT: snowball
        System.out.println("q.peek() = " + q.peek());       // PRINT: snowball
        System.out.println("q.size() = " + q.size());       // PRINT: 3
        Assert.assertTrue(true);
    }

    /**
     * Fill in the blank so the code prints gamma.
     *
     *      var list = UsingArrays.asList("alpha", "beta", "gamma");
     *      Collections.sort(list, ___________________________);
     *      System.out.println(list.get(0));
     */
    @Test
    public void question72() {
        var list = Arrays.asList("alpha", "beta", "gamma");
        Collections.sort(list, Comparator.comparing(String::length).thenComparing(s -> s.charAt(0)).reversed());
        System.out.println(list.get(0));

    //  COMPARATOR ERROR:
    //  Comparator.comparing(String::length).andCompare(s -> s.charAt(0));      Cannot resolve method 'andCompare'.
    //  Comparator.comparing(String::length).thenCompare(s -> s.charAt(0));     Cannot resolve method 'thenCompare'.

        Collections.sort(list, Comparator.comparing(String::length));
        System.out.println("Ordered by length: " + list);               // PRINT: [beta, alpha, gamma]

        Collections.sort(list, Comparator.comparing(s -> s.charAt(0)));
        System.out.println("Ordered by charAt(0): " + list);            // PRINT: [alpha, beta, gamma]

        Collections.sort(list, Comparator.comparing(s -> s.charAt(1)));
        System.out.println("Ordered by charAt(1): " + list);            // PRINT: [gamma, beta, alpha]
        Assert.assertTrue(true);
    }

    /**
     * What does the following output?
     */
    @Test
    public void question74() {
        var pennies = new ArrayList<>();
        pennies.add(1);
        pennies.add(2);
        pennies.add(Integer.valueOf(3));
        pennies.add(Integer.valueOf(4));
        pennies.remove(2);
        System.out.println(pennies);            // PRINT: [1, 2, 4]
        pennies.remove(Integer.valueOf(1));
        System.out.println(pennies);            // PRINT: [2, 4]
        Assert.assertTrue(true);
    }

    /**
     * What is true of the following code?
     *
     * C. If the blank contains x.compareTo(y), then the code outputs 0.
     * D. If the blank contains ‐y.compareTo(x), then the code outputs 0.
     *
     * NOTE: The code will output 0 when the array is sorted in ascending order since 'flower' will be first.
     * Reversing the order of the variables or adding a negative sign sorts in descending order makes both a
     * complicated way of sorting in ascending order.
     */
    private void sortAndSearch75(String... args) {
        var one = args[1];
        Comparator<String> comp = (x, y) -> -y.compareTo(x);        // Is the same as: (x, y) -> x.compareTo(y);
        Arrays.sort(args, comp);
        System.out.println("Ordered: " + Arrays.toString(args));    // [flower, seed]
        var result = Arrays.binarySearch(args, one, comp);      // PRINT: 0
        System.out.println(result);
    }
    @Test
    public void question75() {
        this.sortAndSearch75("seed", "flower");
        Assert.assertTrue(true);
    }

    /**
     * What does this code output?
     */
    @Test
    public void question76() {
        String[] nums = new String[] { "1", "9", "10" };
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));      // PRINT: [1, 10, 9]
        Assert.assertTrue(true);
    }

    /**
     * What is true of the following code?
     */
    @Test
    public void question78() {
        var names = new HashMap<String, String>();
        names.put("peter", "pan");
        names.put("wendy", "darling");
        // String w = names.getOrDefault("peter");                      ERROR: 2nd parameter is missing.
        String x = names.getOrDefault("peter", "x");    // x = pan
        String y = names.getOrDefault("john", "x");     // y = x
        Assert.assertTrue(true);
    }

    /**
     * What is true of the following code?
     */
    @Test(expected = ConcurrentModificationException.class)
    public void question79() {
        List<String> list = List.of("Mary", "had", "a", "little", "lamb");
        Set<String> set = new HashSet<>(list);
        set.addAll(list);               // PRINT: [a, lamb, had, Mary, little]. The Set does not allow duplicated values.
        System.out.println(set);
        for (String sheep : set)
            if (sheep.length() > 1)
                set.remove(sheep);      // We should not modify an array while we are iterating it.
        System.out.println(set);
    }


    /**
     * Which of the following fills in the blank so this code compiles?
     *
     *      getExceptions(Collection<___> coll)
     *
     * C. ? super Exception
     */
    public void question80Option1(Collection<? super Exception> coll) {
        coll.add(new RuntimeException());
        coll.add(new Exception());
    }
    public void question80Option2(Collection<? super RuntimeException> coll) {
        coll.add(new RuntimeException());
    //  coll.add(new Exception());          ERROR >>> Required type: <? super RuntimeException> Provided: Exception.
    }
    // NOTE: Broader types than the generic type are not allowed to be added to the list.
    @Test
    public void question80() {
        List<Exception> list1 = new ArrayList<>();
        list1.add(new Exception("Exception 1"));
        List<RuntimeException> list2 = new ArrayList<>();
        list2.add(new RuntimeException("Exception 2"));

        question80Option1(list1);
    //  question80Option1(list2);  ERROR >>> Required type: Collection<? super Exception> -- Provided: List<RuntimeException>.
        question80Option2(list1);
        question80Option2(list2);

        Assert.assertTrue(true);
    }
}
