package com.hiperium.java.cert.prep.chapter._22;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A denial of service (DoS) attack is when a hacker makes one or more requests with the intent of disrupting legitimate
 * requests. Most denial of service attacks require multiple requests to bring down their targets. Some attacks send a
 * very large request that can even bring down the application in one shot.
 *
 * A denial of service attack comes from one machine. It may make many requests, but they have the same origin. By
 * contrast, a distributed denial of service (DDoS) attack is a denial of service attack that comes from many sources at
 * once.
 */
public class PreventingDoSAttack {

    public static final String ANIMATRONIC_JAVA_PATH = "ext/zoo/dinos/Animatronic.java";

    public static void main(String[] args) {
        leakingResources();
        readingVeryLargeResources();
        overflowNumbers();
    }

    /**
     * One way a hacker can mount a denial of service attack is to take advantage of poorly written code. This simple
     * method counts the number of lines in a file using NIO.2 methods.
     */
    private static void leakingResources() {
        System.out.println("*** Leaking Resources *** ");
        Path path = Path.of(ANIMATRONIC_JAVA_PATH);
        try {
            System.out.println("countLinesPoor(path) = " + countLinesPoor(path));
            System.out.println("countLinesBetter(path) = " + countLinesBetter(path));
        } catch (IOException e) {
            System.out.println("Error trying to count the lines of the file: " + e.getMessage());
        }
    }

    /**
     * A hacker likes this method. He can call it in a loop. Since the method opens a file system resource and never
     * closes it, there is a resource leak. After a hacker calls the method enough times, the program crashes because
     * there are no more file handles available.
     */
    public static long countLinesPoor(Path path) throws IOException {
        return Files.lines(path).count();
    }
    public static long countLinesBetter(Path path) throws IOException {
        try (var stream = Files.lines(path)){
            return stream.count();
        }
    }
    /**
     * Another source of a denial of service attacks is very large resources. Suppose we have a simple method that reads
     * a file into memory, does some transformations on it, and writes it to a new file.
     */
    private static void readingVeryLargeResources() {
        System.out.println("*** Reading Large Resources *** ");
        Path in = Path.of(ANIMATRONIC_JAVA_PATH);
        try {
            transformPoor(in, Path.of("/tmp/AnimatronicPoor.java"));
            transformBetter(in, Path.of("/tmp/AnimatronicBetter.java"));
        } catch (IOException e) {
            System.out.println("Error trying read and write from/to a file: " + e.getMessage());
        }
    }
    /**
     * On a small file, this works just fine. However, on an extremely large file, our program could run out of memory
     * and crash. A hacker strikes again.
     */
    public static void transformPoor(Path in, Path out) throws IOException  {
        var list = Files.readAllLines(in);
        list.removeIf(s -> s.trim().isBlank());
        Files.write(out, list);
    }
    /**
     * To prevent this problem, we can check the size of the file before reading it.
     */
    public static void transformBetter(Path in, Path out) throws IOException  {
        long fileSize = Files.size(in);
        System.out.println("transformBetter() -> fileSize = " + fileSize + " (bytes)");
        if (fileSize > 10_000) {
            try (var stream = Files.lines(in);
                 var writer = new BufferedWriter(new FileWriter(out.toFile()))){
                stream.filter(s -> !s.trim().isBlank())
                        .forEach(s -> writeToFile(writer, s));
            }
        } else {
            var list = Files.readAllLines(in);
            list.removeIf(s -> s.trim().isBlank());
            Files.write(out, list);
        }
    }
    private static void writeToFile(BufferedWriter writer, String s) {
        try {
            writer.write(s);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error trying write to a file: " + e.getMessage());
        }
    }

    /**
     * When checking file size, be careful with an int type and loops. Since an int has a maximum size, exceeding that
     * size results in integer overflow. Incrementing an int at the maximum value results in a negative number, so
     * validation might not work as expected.
     *
     * In the next example, the 3rd output is true when it must be false. We start with a giant number that is over a
     * million. Adding a small number to it exceeds the capacity of an int. Java overflows the number into a very
     * negative number. Since all negative numbers are under a million, the validation doesn't do what we want it to.
     */
    private static void overflowNumbers() {
        System.out.println("*** Overflow Numbers *** ");
        System.out.println("enoughRoomToAddLinePoor(100) = " + enoughRoomToAddLinePoor(100));
        System.out.println("enoughRoomToAddLinePoor(2_000_000) = " + enoughRoomToAddLinePoor(2_000_000));
        System.out.println("enoughRoomToAddLinePoor(Integer.MAX_VALUE) = " + enoughRoomToAddLinePoor(Integer.MAX_VALUE));

        System.out.println("enoughRoomToAddLineBetter(100) = " + enoughRoomToAddLineBetter(100));
        System.out.println("enoughRoomToAddLineBetter(2_000_000) = " + enoughRoomToAddLineBetter(2_000_000));
        System.out.println("enoughRoomToAddLineBetter(Integer.MAX_VALUE) = " + enoughRoomToAddLineBetter(Integer.MAX_VALUE));

    }
    /**
     * In this example, we have a requirement to make sure that we can add a line to a file and have the size stay under
     * a million.
     */
    public static boolean enoughRoomToAddLinePoor(int requestedSize) {
        int maxLength = 1_000_000;
        String newLine = "END OF FILE";
        int newLineSize = newLine.length();
        return requestedSize + newLineSize < maxLength;
    }
    /**
     * When accepting numeric input, you need to verify it isn't too large or too small. In this example, the input
     * value "requestedSize" should have been checked before adding it to newLineSize.
     */
    public static boolean enoughRoomToAddLineBetter(int requestedSize) {
        int maxLength = 1_000_000;
        if (requestedSize < 0 || requestedSize >= maxLength) {
            return false;
        }
        String newLine = "END OF FILE";
        int newLineSize = newLine.length();
        return requestedSize + newLineSize < maxLength;
    }

}
