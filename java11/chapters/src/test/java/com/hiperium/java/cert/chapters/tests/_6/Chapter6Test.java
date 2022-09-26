package com.hiperium.java.cert.chapters.tests._6;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class Chapter6Test {

    /**
     * How many lines does this code output?
     * <p>
     * R./ 4
     */
    @Test
    public void question10() {
        Set<String> set = Set.of("Minnie", "Mickey");
        List<String> list = new ArrayList<>(set);
        set.forEach(s -> System.out.println(s));
        list.forEach(l -> System.out.println(l));
    }

    /**
     * Which is true of the following code?
     * <p>
     * R./ The code compiles
     */
    @Test
    public void question13() {
        int length = 3;
        for (int i = 0; i < 3; i++) {
            if (i % 2 == 0) {
                Supplier<Integer> supplier = () -> length; // WE CAN REFERENCE length VARIABLE EVEN THOUGHT IT IS NOT FINAL
                System.out.println("i = " + i + ", supplier = " + supplier.get());
            } else {
                int j = i;
                Supplier<Integer> supplier = () -> j; // Each time the else statement is executed, the variable is
                // redeclared and goes out of scope. Therefore, it is not re-assigned. Similarly, length is effectively final.
                System.out.println("j = " + j + ", supplier = " + supplier.get());
            }
        }
    }

    /**
     * Which variables are effectively final?
     * <p>
     * R./ local1 and param2. Method parameters and local variables are effectively final if they aren’t changed
     * after initialization.
     */
    public void question16() {
        isIt("param1", "param2");
    }

    private void isIt(String param1, String param2) {
        String local1 = param1 + param2;
        String local2 = param1 + param2;
        param1 = null;
        local2 = null;
    }
}
