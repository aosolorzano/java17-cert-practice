package com.hiperium.java.cert.prep.chapter._16;

/**
 * An assertion is a boolean expression that you place at a point in your code where you expect something to be true.
 * An assert statement contains this statement along with an optional message.
 *
 * An assertion allows for detecting defects in the code. You can turn on assertions for testing and debugging while
 * leaving them off when your program is in production.
 *
 * Why assert something when you know it is true? It is true only when everything is working properly. If the program
 * has a defect, it might not actually be true. Detecting this earlier in the process lets you know something is wrong.
 */
public class DeclaringAssertions {

    /**
     * By default, assert statements are ignored by the JVM at runtime. To enable assertions, use the ‐enableassertions
     * flag on the command line.
     *
     *      java -enableassertions Rectangle
     *
     * You can also use the shortcut ‐ea flag.
     *
     *      java -ea Rectangle
     *
     * Using the ‐enableassertions or ‐ea flag without any arguments enables assertions in all classes (except system
     * classes). You can also enable assertions for a specific class or package. For example, the following command
     * enables assertions only for classes in the com.demos package and any subpackages:
     *
     *      java -ea:com.demos... my.programs.Main
     *
     * The ellipsis (...) means any class in the specified package or subpackages. You can also enable assertions for a
     * specific class. We do not use ellipsis here:
     *
     *      java -ea:com.demos.TestColors my.programs.Main
     *
     * Sometimes you want to enable assertions for the entire application but disable it for select packages or classes.
     * Java offers the ‐disableassertions or ‐da flag for just such an occasion. The following command enables assertions
     * for the com.demos package but disables assertions for the TestColors class:
     *
     *      java -ea:com.demos... -da:com.demos.TestColors my.programs.Main
     *
     * For the exam, make sure we understand how to use the ‐ea and ‐da flags in conjunction with each other.
     */
    public static void main(String[] args) {
        String name = "Annie";
        int age = 10, height = 4;
        double length = 45.0;

        assert 1 == age;
        assert (2 == height);
        assert 45.0 == length : "Problem with length";
        assert ("Cecelia".equals(name)): "Failed to verify user data";

        // SYNTAX ERRORS:
        // assert (1);                          ERROR: Required type: boolean - Provided: int
        // assert x -> true;                    ERROR: Lambda expression not expected here.
        // assert 1 == 2? "Accept" : "Error";   ERROR: Required type: boolean - Provided: String
        // assert.test(5 > age);                ERROR: Cannot resolve method 'test' in 'DeclaringAssertions'
    }
}
