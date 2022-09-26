package com.hiperium.java.cert.prep.chapter._13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The @FunctionalInterface marker annotation can be applied to any valid functional interface.
 */
@FunctionalInterface
interface Intelligence {
    int cunning();
}

/**
 * Class used to ignore warnings with @SuppressWarnings on callers to these methods.
 */
class SongBird {
    @Deprecated
    static void sing(int volume) {}
    static Object shirp(List<String> data) { return data.size(); }
}

/**
 * The annotations in this class are entirely optional but help improve the quality of the code. By adding these
 * annotations, you help take the guesswork away from someone reading your code. It also enlists the compiler to help
 * you spot errors. For example, applying @Override on a method that is not overriding, triggers a compilation error
 * and could help you spot problems if a class or interface is later changed.
 *
 */
public class CommonAnnotations implements Intelligence {
    /**
     *
     * You should know that it is good practice documenting why a type is being deprecated and be able to suggest
     * possible alternatives.
     *
     * Note: Do not confuse Javadoc annotations with Java annotations. Take a look at the @deprecated and @Deprecated
     * in this example. The first, @deprecated, is a Javadoc annotation used inside a comment, while @Deprecated is a
     * Java annotation applied to a class.
     *
     * Javadoc's annotations are all lowercase, while Java annotations start with an uppercase letter.
     *
     * @param distance length the light needs to travel .
     * @return the result of the light operation.
     * @deprecated use EnhancedCustomAnnotations.lights() instead.
     */
    @Deprecated(since = "1.5")
    public static String perform(int distance) {
        return "Beginning light show";
    }

    /**
     * The @Override is a marker annotation that is used to indicate a method is overriding an inherited method, whether
     * it be inherited from an interface or parent class.
     *
     * NOTE: From Chapter 8, “Class Design,” you should know that the overriding method must have the same signature,
     * the same or broader access modifier, and a covariant return type, and not declare any new or broader checked
     * exceptions.
     */
    @Override
    public int cunning() { return 500; }
    void howl() { System.out.print("Woof!"); }
    private static class Wolf extends CommonAnnotations {
        @Override
        public int cunning() { return 100; }
        @Override
        protected void howl() { System.out.print("Howl!"); }
    }

    /**
     * Applying @SuppressWarnings annotation to a class, method, or type, basically tells the compiler, “I know what
     * I am doing; do not warn me about this.”
     */
    @SuppressWarnings("deprecation")
    public void wakeUp() {
        SongBird.sing(10);               // WARN: 'sing(int)' is deprecated.
    }
    @SuppressWarnings("unchecked")
    public void goToBed(List t) {
        SongBird.shirp(new ArrayList());        // WARN: Raw use of parameterized class 'ArrayList'.
    }

    /**
     * The @SafeVarargs annotation is used to indicate to other developers that your method does not perform any unsafe
     * operations. It tells: “Don't worry about the varargs parameter; I promise this method won't do anything bad with
     * it!”. It also suppresses unchecked compiler warnings for the varargs parameter.
     *
     * NOTE: The annotation can be applied only to methods that are not able to be overridden (marked as private, static
     * o final).
     */
    @SafeVarargs
    final int thisIsUnsafe(List<Integer>... carrot) {
        Object[] stick = carrot;
        stick[0] = Arrays.asList("nope!");
        // ClassCastException at runtime.
        return carrot[0].get(0);                // WARN: Possible heap pollution from parameterized vararg type.
    }

}
