package com.hiperium.java.cert.prep.chapter._5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UsingArrays {

    /**
     * NOTE: ARRAYS DOES NOT HAVE "SIZE()" METHOD.
     */
    public static void main(String[] args) {
        int[] numbers = new int[3];
        System.out.println("Printing array values: " + Arrays.toString(numbers));
        System.out.println("Printing array length: " + numbers.length);

        // Initialized array
        int[] numbers2 = new int[]{42, 55, 99};
        System.out.println("Initialized array numbers2 = " + Arrays.toString(numbers2));

        // Anonymous array
        int[] numbers3 = {42, 55, 99};
        System.out.println("Anonymous array numbers3 = " + Arrays.toString(numbers3));

        /* ***************** CORRECT JAVA ARRAYS DECLARATIONS *****************
        int[] numAnimals;                   OK
        int [] numAnimals2;                 OK
        int []numAnimals3;                  OK
        int numAnimals4[];                  OK
        int numAnimals5 [];                 OK
        int[][] scores = new int[5][];      NOTE: INITIALIZED AT LEAST THE FIRST ARRAY
        Date[] dates[] = Date[2][];         SAME AS BEFORE
        ******************************************************************** */

        /* ***************** INVALID JAVA ARRAYS DECLARATIONS *****************
        int[][] java = new int[][];     ARRAY INITIALIZER EXPECTED
        int[][] types = new int[];      ARRAY INITIALIZER EXPECTED
        int[][] types = new int[2];     REQUIRED [][]
        ******************************************************************** */

        String[] bugs = {"cricket", "beetle", "ladybug"};
        String[] alias = {"cricket", "beetle", "ladybug"};
        System.out.println("bugs.equals(alias) = " + bugs.equals(alias));       // PRINT: FALSE
        System.out.println("bugs == alias) = " + (bugs == alias));              // PRINT: FALSE
        System.out.println("Arrays.equals(bugs, alias) = "                      // PRINT: TRUE
                + Arrays.equals(bugs, alias));
        alias = bugs;
        System.out.println("bugs.equals(alias) = " + bugs.equals(alias));       // PRINT: TRUE
        System.out.println("bugs == alias) = " + (bugs == alias));              // PRINT: TRUE

        binarySearch();
        compareArrays();
        fromListToArray();
        fromArrayToLists();
        immutableList();
    }

    /**
     * NOTE: Before search an array, it must be ordered first.
     *       Collections.sort(numbers); IS USED TO SORT LIST OF WRAPPER CLASSES LIKE INTEGER.
     */
    private static void binarySearch() {
        System.out.println("**** BINARY SEARCH ****");
        int[] numbers = {8, 6, 2, 1};
        System.out.println(Arrays.binarySearch(numbers, 2));    // PRINT: -1 (NOT ORDERED ARRAY)

        Arrays.sort(numbers);
        System.out.println(Arrays.binarySearch(numbers, 2));    // PRINT: NOW FOUND AT POSITION 1
        System.out.println(Arrays.binarySearch(numbers, 6));    // PRINT: FOUND AT POSITION 2
        System.out.println(Arrays.binarySearch(numbers, 9));    // PRINT: -5
        // NOTE: Although 9 isn’t in the list, it would need to be inserted at element 4 to preserve the sorted order.
        // We negate and subtract 1 for consistency, getting –4 –1, also known as –5.

    }

    /**
     * NOTE: The method returns the value 0 if the first and second array are equal and contain the same elements in
     * the same order; a value less than 0 if the first array is lexicographically less than the second array; and a
     * value greater than 0 if the first array is lexicographically greater than the second array
     */
    private static void compareArrays() {
        System.out.println("**** COMPARE ARRAYS ****");
        System.out.println(Arrays.compare(new String[]{"a"},  new String[]{"A"}));           // 32
        System.out.println(Arrays.compare(new String[]{"a"},  new String[]{"b"}));           // -1
        System.out.println(Arrays.compare(new String[]{"a"},  new String[]{"x"}));           // -23
        System.out.println(Arrays.compare(new String[]{"a"},  new String[]{"1"}));           // 48
        System.out.println(Arrays.compare(new String[]{"a"},  new String[]{"a"}));           // 0
        System.out.println(Arrays.compare(new String[]{"a"},  new String[]{"aa"}));          // -1
        System.out.println(Arrays.compare(new String[]{"a"},  new String[]{"ab"}));          // -1
        System.out.println(Arrays.compare(new String[]{"a"},  new String[]{"ab1"}));         // -2
        System.out.println(Arrays.compare(new String[]{"a"},  new String[]{"ab1c"}));        // -3
        System.out.println(Arrays.compare(new int[]{1, 3},    new int[]{1}));                // 1
        System.out.println(Arrays.compare(new int[]{1, 3, 9}, new int[]{1}));                // 2
        System.out.println(Arrays.compare(new String[]{"a"},  new String[]{"a", "a"}));      // -1
        System.out.println(Arrays.compare(new String[]{"a"},  new String[]{"a", "b"}));      // -1
        System.out.println(Arrays.compare(new String[]{"a"},  new String[]{"a", "a", "a"})); // -2
        System.out.println(Arrays.compare(new String[]{null}, new String[]{"ab", "yz"}));    // -1
    }

    private static void fromListToArray() {
        System.out.println("**** FROM ARRAY LIST TO ARRAY ****");
        List<String> list = new ArrayList<>();
        list.add("hawk");
        list.add("robin");
        Object[] objectArray = list.toArray();
        System.out.println("objectArray = " + Arrays.toString(objectArray));    // PRINT:

        // IMPORTANT: LIKE BEFORE, BUT 'new String[0]' PARAMETER IS NEEDED.
        String[] stringArray = list.toArray(new String[0]);
        System.out.println("stringArray = " + Arrays.toString(stringArray));    // PRINT:
        list.clear();
        System.out.println("Object Array size = " + objectArray.length);        // PRINT: 2
        System.out.println("String Array size = " + stringArray.length);        // PRINT: 2
    }

    /**
     * After 'UsingArrays.asList' both variables makes references to the same values on the heap. Thus, the list variable
     * is a backed version of the original array.
     */
    private static void fromArrayToLists() {
        System.out.println("**** FROM ARRAY TO ARRAY LIST ****");
        String[] array = {"hawk", "robin"};
        System.out.println("Original String Array values: " + Arrays.toString(array));

        // FIXED-SIZE. BACKED VERSION OF "ARRAY"
        List<String> list = Arrays.asList(array);
        System.out.println("String List values from array: " + list.toString());            // [hawk, robin]

        list.set(1, "test");
        System.out.println("String List after set 'test': " + list.toString());             // [hawk, test]
        System.out.println("String array after set 'test': " + Arrays.toString(array));     // [hawk, test]

        array[0] = "new";
        System.out.println("String Array after set 'new': " + Arrays.toString(array));      // [new, test]
        System.out.println("String List after set 'new': " + list.toString());              // [new, test]
    }

    private static void immutableList() {
        System.out.println("**** IMMUTABLE LIST ****");
        String[] array = {"hawk", "robin"};
        System.out.println("Original String Array values: " + Arrays.toString(array));

        // IMMUTABLE LIST OF "ARRAY"
        List<String> list = List.of(array);
        System.out.println("List values: " + list.toString());                              // [hawk, robin]
        array[0] = "new";
        System.out.println("Array after set 'new' value: " + Arrays.toString(array));       // [new, robin]
        System.out.println("List after set 'new' value: " + list.toString());               // [hawk, robin]
        // list.set(1, "test");     THROWS UnsupportedOperationException
    }
}
