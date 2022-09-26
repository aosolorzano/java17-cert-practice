package com.hiperium.java.cert.practice.tests;

import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.*;
import java.util.List;

/**
 * QUESTION 14
 *
 * NOTE: The default retention policy for all annotations is 'RetentionPolicy.CLASS' if no one is specified.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@interface Fast {
    int topSpeed() default 10;
}
@Fast class BigCat {}
class Cheetah extends BigCat {}

/**
 * Annotations
 *      - Describe the purpose of annotations and typical usage patterns.
 *      - Apply annotations to classes and methods.
 *      - Describe commonly used annotations in the JDK.
 *      - Declare custom annotations.
 */
public class Chapter13Test {

    /**
     * Assume the following code compiles. Which annotation inserted in the line allows the code to print a non‐null
     * value at runtime?
     *
     * NOTE: To print a non‐null value, both @Retention(RetentionPolicy.RUNTIME) and @Inherited would be required.
     */
    @Test
    public void question14() {
        var a = Cheetah.class.getAnnotation(Fast.class);    // IMPORTANT: With @Inherited, @Fast annotation is
                                                                 //  applied to Cheetah class too.
        System.out.print(a);
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 16:
     *
     * Which statement about the following declarations is correct?
     *
     * R./ Only the declaration of Friend contains a compiler error.
     *
     */
    @Target(ElementType.TYPE_USE)
    public @interface Friend {
        String value();
        // String lastName() default null;          ERROR: Attribute value must be constant
        String lastName() default "";
        int age = 10;
    }
    class MyFriends {
        void makeFriends() {
            var friends = List.of(new @Friend("Olivia") Object(),
                    new @Friend("Adeline") String(),
                    new @Friend("Henry") MyFriends());
        }
    }

}
