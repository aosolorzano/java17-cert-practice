package com.hiperium.java.cert.chapters.tests._14;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * QUESTION 6
 */
class Hello<T> {
    T t;
    public Hello(T t) {
        System.out.println("Constructor using generic type parameter <T> as method parameter.");
        this.t = t;
    }
    public <E> Hello(E e, Integer val) {
        System.out.println("Constructor using method type parameter <E> and an Integer value.");
    }
    private <T> void println(T message) {       // WARN: Method type parameter 'T' hides generic type parameter 'T'.
        System.out.println(t + "-" + message);
    }
    @Override
    public String toString() {
        return t.toString();
    }

    public static void main() {
        new Hello<String>("hi").println(1);         // PRINT: hi-1
        // Raw use of parameterized class 'Hello'.
        new Hello("hola").println(true);            // PRINT: hola-true
    }
}

/**
 * QUESTION 8
 */
class Platypus {
    String name;
    int beakLength;
    public Platypus(String name, int beakLength) {
        this.name = name;
        this.beakLength = beakLength;
    }
    public String getName() {
        return name;
    }
    public int getBeakLength() {
        return beakLength;
    }
    public String toString() {
        return "" + beakLength;
    }
    public static void main() {
        Platypus p1 = new Platypus("Paula", 3);
        Platypus p2 = new Platypus("Peter", 5);
        Platypus p3 = new Platypus("Peter", 7);
        List<Platypus> list = Arrays.asList(p1, p2, p3);
        // Collections.sort(list, Comparator.comparing(Platypus::getBeakLength).reversed());    THIS WORKS TOO.
        Collections.sort(list, Comparator.comparing(Platypus::getName).thenComparingInt(Platypus::getBeakLength)
                .reversed());
        System.out.println(list);
    }
}

/**
 * QUESTION 10
 */
class MyComparator implements Comparator<String> {                  // NOTE: We can use the Comparator interface in classes.
    @Override
    public int compare(String a, String b) {
        int result = b.toLowerCase().compareTo(a.toLowerCase());    // Comparing for descending order, and in lowercase.
        System.out.println(b+" compareTo "+a+" ===>>> "+result);
        return result;
    }
    public static void main() {
        String[] values = {"123", "Abb", "aab"};
        Arrays.sort(values, new MyComparator());
        for (var s : values) {
            System.out.print(s + " ");
        }
    }
}

public class Chapter14Test {

    /**
     * Which of the following are true? (Choose all that apply.)
     *
     * C. Exactly two of these lines contain a compiler error.
     * G. If all lines with compiler errors are removed, this code throws an exception.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void question3() {
        List<?> q = List.of("mouse","parrot");
        var v = List.of("mouse","parrot");
        // q.removeIf(String::isEmpty);                  ERROR: Non-static method can't be referenced from static context.
        q.removeIf(s -> ((String) s).isEmpty());      // FIXED: Param 's' is of type Object, it needs to be cast first.
        // q.removeIf(s -> s.length() == 4);             ERROR: Cannot resolve method 'length' in type 'Object'.
        q.removeIf(s -> ((String) s).length() == 4);  // WARN : Immutable object is modified.
        v.removeIf(String::isEmpty);                  //    OK: Object 'v' is of type List<String> not List<Object>.
        v.removeIf(s -> s.length() == 4);             // WARN : Immutable object is modified.
    }

    /**
     * What is the result of the following statements?
     */
    @Test
    public void question4() {
        var greetings = new LinkedList<String>();
        greetings.offer("hello");
        greetings.offer("hi");
        greetings.offer("ola");
        greetings.pop();                            // This method is equivalent to removeFirst().
        greetings.peek();                           // Return the head of this list, or null if this list is empty.
        while (greetings.peek() != null) {
            System.out.println(greetings.pop());
        }
        Assert.assertTrue(true);
    }

    /**
     * Which of these statements compile?
     */
    @Test
    public void question5(){
        // HashSet<Number> hs = new HashSet<Integer>();                 ERROR: Required type: HashSet<Number>, Provided: HashSet<Integer>
        // List<> list = new ArrayList<String>();                       ERROR: Type parameter expected.
        // List<Object> values = new HashSet<Object>();                 ERROR: Required type: List<Object>, Provided: HashSet<Object>
        // List<Object> objects = new ArrayList<? extends Object>();    ERROR: Wildcard types cannot be instantiated directly.
        Map<String, ? extends Number> hm = new HashMap<String, Integer>();
        HashSet<? super ClassCastException> set = new HashSet<Exception>();
        Assert.assertTrue(true);
    }

    /**
     * What is the result of the following code?
     */
    @Test
    public void question6(){
        Hello.main();
        Assert.assertTrue(true);
    }

    /**
     * Which of the following statements are true?
     *
     * A. The code compiles successfully.
     * D. The output is indeterminate.
     */
    @Test
    public void question7() {
        var numbers = new HashSet<Number>();
        numbers.add(Integer.valueOf(86));
        numbers.add(75);
        numbers.add(Integer.valueOf(86));       // FALSE
        numbers.add(null);
        numbers.add(309L);
        Iterator iter = numbers.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        Assert.assertTrue(true);
    }

    /**
     * What is the result of the following code?
     */
    @Test
    public void question8(){
        Platypus.main();
        Assert.assertTrue(true);
    }

    @Test
    public void question9() {
        Map<String, Double> map = new HashMap<>();
        map.put("pi", 3.14159);
        map.put("log(1)", new Double(0.0));
        map.put("x", Double.valueOf(123.4));
        Assert.assertTrue(true);
    }

    /**
     * What is the result of the following program?
     *
     * R./ Abb aab 123
     */
    @Test
    public void question10(){
        MyComparator.main();
        Assert.assertTrue(true);
    }

    /**
     * What is the result of the following code?
     */
    @Test
    public void question11(){
        var map = new HashMap<Integer,Integer>(10);
        for (int i = 1; i <= 10; i++) {
            map.put(i, i * i);
        }
        System.out.println(map.get(4));     // PRINT: 16
        Assert.assertTrue(true);
    }

    /**
     * Which of these statements can fill in the blank so that the Helper class compiles successfully?
     */
    static class Question12 {
        public static <U extends Exception> void printException(U u) {
            System.out.println(u.getMessage());
        }
    }
    @Test
    public void question12() {
        Question12.printException(new FileNotFoundException("A"));
        Question12.printException(new Exception("B"));
        Question12.<NullPointerException>printException(new NullPointerException("D"));
        // Question12.<Throwable>printException(new Exception("C"));  ERROR: Type parameter 'java.lang.Throwable' is not
        //                                                                   within its bound.
        // Question12.printException(new Throwable("E"));             ERROR: no instance of type variable exist, so that
        //                                                                   Throwable conforms to Exception.
        Assert.assertTrue(true);
    }

    /**
     * Which of these statements can fill in the blank so that the Wildcard class compiles successfully?
     */
    class Question13 {
        public void showSize(List<?> list) {
            System.out.println(list.size());
        }
    }
    @Test
    public void question13() {
        Question13 card = new Question13();
        // List<?> list = new ArrayList<?>();                    ERROR: Wildcard type '?' cannot be instantiated directly.
        // List<?> list = new HashSet<String>();                 ERROR: Required type: List<?> Provided: HashSet<String>.
        // List<Exception> list = new LinkedList<IOException>(); ERROR: Required type: List<Exception> Provided: LinkedList<IOException>.
        // List<Exception> list = new LinkedList<>();            VALID
        // ArrayList<? super Date> list = new ArrayList<Date>(); VALID
        ArrayList<? extends Number> list = new ArrayList<Integer>();
        card.showSize(list);
        Assert.assertTrue(true);
    }

    /**
     * What is the result of the following program?
     */
    static class Question14 implements Comparable<Question14>, Comparator<Question14> {
        private int num;
        private String text;
        public Question14(int num, String text) {
            this.num = num;
            this.text = text;
        }
        @Override
        public int compareTo(Question14 s) {
            return this.text.compareTo(s.text);
        }
        @Override
        public int compare(Question14 s1, Question14 s2) {
            return s1.num - s2.num;
        }
        public String toString() {
            return "" + this.num;
        }
    }
    @Test
    public void question14() {
        var s1 = new Question14(88, "a");
        var s2 = new Question14(55, "b");
        var t1 = new TreeSet<Question14>();             // 't1' does not use a specific Comparator, but the Comparable one.
        t1.add(s1);
        t1.add(s2);
        var t2 = new TreeSet<Question14>(s1);           // 't2' use Comparator specified by 's1'(using integer var 'num').
        t2.add(s1);
        t2.add(s2);
        System.out.println("t1: "+ t1 +", t2: " + t2);  // RESULT: [88, 55] [55, 88]
        Assert.assertTrue(true);
    }

    /**
     * What is the result of the following code?
     */
    @Test
    public void question15() {
        Comparator<Integer> c1 = (o1, o2) -> o2 - o1;       // Reverse order.
        Comparator<Integer> c2 = Comparator.naturalOrder();
        Comparator<Integer> c3 = Comparator.reverseOrder();
        var list = Arrays.asList(5,4,7,2);     // Fixed-size list backed by the specified array.
        Collections.sort(list, c2);                         // NOTE: For binary search, we need a list in ascending order.
        System.out.println("Ordered list: " + list);
        System.out.println("Index of number 2 in the list: " + Collections.binarySearch(list, 2));
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 18:
     * Which of the following lines can be inserted to make the code compile?
     *
     */
    class W {}
    class X extends W {}
    class Y extends X {}
    class Z<Y> {                // WARN: Type parameter 'Y' hides visible type (class) 'Y'.
        // INSERT CODE HERE
        W w1 = new W();
        W w2 = new X();
        // W w3 = new Y();      ERROR: Type parameter 'Y' cannot be instantiated directly.
        // Y y1 = new W();      ERROR: Required type: Y --- Provided: W.
        // Y y2 = new X();      ERROR: Required type: Y --- Provided: X.
        // Y y1 = new Y();      ERROR: ERROR: Type parameter 'Y' cannot be instantiated directly.
    }

    /**
     * Which options are true of the following code?
     *
     * A. If we fill in the blank with List, the output is [10].
     * D. If we fill in the blank with Queue, the output is [10, 12].
     */
    @Test
    public void question19() {
        // List<Integer> q = new LinkedList<>();
        Queue<Integer> q = new LinkedList<>();
        q.add(10);
        q.add(12);
        q.remove(1);                // NOTE: For a List, we remove the index. For a Queue, we remove the element.
        System.out.println(q);
        Assert.assertTrue(true);
    }

    /**
     * What is the result of the following code?
     */
    @Test
    public void question20() {
        Map m = new HashMap();
        m.put(123, "456");
        m.put("abc", "def");
        // System.out.println(m.contains("123"));       ERROR: Cannot resolve method 'contains' in 'Map'
        System.out.println(m.containsKey("123"));       // PRINT: false
        Assert.assertTrue(true);
    }

    /**
     * What is the result of the following code?
     */
    @Test(expected = UnsupportedOperationException.class)
    public void question21() {
        var map = Map.of(1,2, 3, 6);                 // Returns an unmodifiable map.
        var list = List.copyOf(map.entrySet());     // Returns an unmodifiable List.
        System.out.println("List from map EntrySet: " + list);                         // PRINT: [3=6, 1=2].

        List<Integer> one = List.of(8, 16, 2);              // Returns an unmodifiable List.
        var copy = List.copyOf(one);           // Returns an unmodifiable List.
        var copyOfCopy = List.copyOf(copy);    // Returns an unmodifiable List.
        var thirdCopy = new ArrayList<>(copyOfCopy);

        // list.replaceAll(x -> x * 2);         ERROR: Operator '*' cannot be applied to 'Entry<Integer, Integer>', 'int'.
        one.replaceAll(x -> x * 2);             // WARN: Immutable object is modified.
        thirdCopy.replaceAll(x -> x * 2);
        System.out.println("Third list: " + thirdCopy);             // PRINT: [16, 32, 4]
    }

    /**
     * What code change is needed to make the method compile assuming there is no class named T?
     *
     * B. Add <T> after the static keyword.
     */
    public static <T> T question22(T t) {
        return t;
    }

    /**
     * What is the result of the following?
     *
     * NOTE: Method map.merge() may be of use when combining multiple mapped values for a key. For example, to either
     * create or append a String msg to a value mapping:
     *
     * map.merge(key, msg, String::concat)
     */
    @Test
    public void question25() {
        var map = new HashMap<Integer, Integer>();
        map.put(1, 10);
        map.put(2, 20);
        map.put(3, null);
        map.merge(1, 3, (a,b) -> a + b);
        // map.merge(2, 20, Integer::sum);              Note: Lambda can be replaced with method reference
        map.merge(3, 3, (a,b) -> a + b);
        System.out.println(map);                        // PRINT: {1=13, 2=20, 3=3}
        Assert.assertTrue(true);
    }
}
