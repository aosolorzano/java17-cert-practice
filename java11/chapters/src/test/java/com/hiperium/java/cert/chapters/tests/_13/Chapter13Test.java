package com.hiperium.java.cert.chapters.tests._13;

import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * QUESTION 12
 */
enum UnitOfTemp { C, F }
@interface Snow {
    boolean value();
}

/**
 * QUESTION 23:
 *
 * NOTE: The default retention policy for all annotations is 'RetentionPolicy.CLASS' if no one is specified.
 */
//@Retention(RetentionPolicy.RUNTIME)
@interface Plumber {
    String value() default "Mario";
}
class Team {
    @Plumber("")      private String foreman = "Mario";
    @Plumber          private String worker  = "Kelly";
    @Plumber("Kelly") private String trainee;
}

/**
 * Annotations
 *      - Describe the purpose of annotations and typical usage patterns.
 *      - Apply annotations to classes and methods.
 *      - Describe commonly used annotations in the JDK.
 *      - Declare custom annotations.
 */
public class Chapter13Test {
    /**
     * Which of the following lines of code do not compile?
     */
    @Target(ElementType.METHOD)
    public @interface Question12 {
        // private Question12() {}                  ERROR: Modifier 'private' not allowed here.
        // Question12() {}                          ERROR: Not allowed in interface.
        // int temperature;                         ERROR: Variable 'temperature' might not have been initialized.
        // UnitOfTemp unit default UnitOfTemp.C;    ERROR: Invalid syntax declaration.
        UnitOfTemp unit() default UnitOfTemp.C;
        Snow snow() default @Snow(true);
    }

    /**
     * The main() method in the following program reads the annotation value() of Plumber at runtime on each member of
     * Team. It compiles and runs without any errors. Based on this, how many times is Mario printed at runtime?
     *
     * R./ Zero.
     */
    @Test
    public void question23() {
        var team = new Team();
        var fields = team.getClass().getDeclaredFields();
        for (Field field : fields) {
            System.out.println("\nField: " + field.getName());
            System.out.println("Annotations: " + Arrays.toString(field.getAnnotations()));
            if(field.isAnnotationPresent(Plumber.class)){
                System.out.println("Annotation value: " + field.getAnnotation(Plumber.class).value());
            }
        }
        Assert.assertTrue(true);
    }
}
