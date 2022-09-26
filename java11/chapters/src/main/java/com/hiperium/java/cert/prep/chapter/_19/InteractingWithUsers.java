package com.hiperium.java.cert.prep.chapter._19;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

public class InteractingWithUsers {

    public static void main(String[] args) throws IOException {
        // readingInputAsStream();      This block the compilation process
        usingConsole();
    }

    private static void readingInputAsStream() throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        String userInput = reader.readLine();
        System.out.println("userInput = " + userInput);
    }

    /**
     * The Console class is a singleton because it is accessible only from a factory method and only one instance of it
     * is created by the JVM. We cannot instantiate a Console class, the code will not compile.
     *
     * NOTE: The Console object may not be available, depending on where the code is being called. If it is not
     * available, then System.console() returns null. It is imperative that you check for a null value before attempting
     * to use a Console object.
     */
    private static void usingConsole() {
        Console console = System.console();
        if (null == console) {
            System.err.println("Console not available...");
            // NOTE: You can find an example of Console in "misc/chapter_19" folder.
        } else {
            String userInput = console.readLine();
            console.writer().println("You entered: " + userInput);
        }
    }
}
