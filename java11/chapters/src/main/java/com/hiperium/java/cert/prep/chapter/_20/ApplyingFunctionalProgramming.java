package com.hiperium.java.cert.prep.chapter._20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * In the next table, "file" refers to an INSTANCE of the "java.io.File" class, while "path" and "otherPath" refer to
 * INSTANCES of the NIO.2 Path interface.
 *
 * COMPARISON OF "java.io.File" AND "NIO.2" METHODS:
 *
 * **************************|*********************************|
 * LEGACY I/O FILE METHOD    | NIO.2 METHOD                    |
 * **************************|*********************************|
 * file.delete()             | Files.delete(path)              |
 * --------------------------|---------------------------------|
 * file.exists()             | Files.exists(path)              |
 * --------------------------|---------------------------------|
 * file.getAbsolutePath()    | path.toAbsolutePath()           |
 * --------------------------|---------------------------------|
 * file.getName()            | path.getFileName()              |
 * --------------------------|---------------------------------|
 * file.getParent()          | path.getParent()                |
 * --------------------------|---------------------------------|
 * file.isDirectory()        | Files.isDirectory(path)         |
 * --------------------------|---------------------------------|
 * file.isFile()             | Files.isRegularFile(path)       |
 * --------------------------|---------------------------------|
 * file.lastModified()       | Files.getLastModifiedTime(path) |
 * --------------------------|---------------------------------|
 * file.length()             | Files.size(path)                |
 * --------------------------|---------------------------------|
 * file.listFiles()          | Files.list(path)                |
 * --------------------------|---------------------------------|
 * file.mkdir()              | Files.createDirectory(path)     |
 * --------------------------|---------------------------------|
 * file.mkdirs()             | Files.createDirectories(path)   |
 * --------------------------|---------------------------------|
 * file.renameTo(otherFile)  | Files.move(path,otherPath)      |
 * --------------------------|---------------------------------|
 */
public class ApplyingFunctionalProgramming {

    public static void main(String[] args) {
        listingDirectoryContent();
        deepCopyDirectory();
        walkingIntoDirectory();
        searchingDirectory();
        readingFile();
    }

    /**
     * The "Files.list()" is similar to the "java.io.File.listFiles()" method, except that it returns a Stream<Path>
     * rather than a File[]. Since streams use lazy evaluation, this means the method will load each path element as
     * needed, rather than the entire directory at once.
     */
    private static void listingDirectoryContent() {
        System.out.println("*** Listing Directory Content ***");
        try (Stream<Path> s = Files.list(Path.of("."))) {
            s.forEach(System.out::println);                     // PRINT: Getting project directory content.
        } catch (IOException e) {
            System.out.println("Error getting directory content: " + e.getMessage());
        }
    }

    /**
     * On a previous example, we presented the "Files.copy()" method and showed that it only performed a shallow copy of
     * a directory. This time, we can use the "Files.list()" to perform a deep copy.
     */
    private static void deepCopyDirectory() {
        System.out.println("*** Making a Deep Copy Directory ***");
        Path source = Path.of("/tmp/asolorzano");
        Path target = Path.of("/tmp/asolorzano-new");
        copyPath(source, target);
    }

    private static void copyPath(Path source, Path target) {
        System.out.println("COPYING " + source + " TO " + target);
        try {
            Files.copy(source, target);
            if (Files.isDirectory(source)) {
                try (Stream<Path> s = Files.list(source)) {
                    s.forEach(path -> {
                        System.out.println("Internal path to copy: " + path);
                        copyPath(path, target.resolve(path.getFileName()));
                    });
                }
            }
        } catch (IOException e) {
            System.out.println("Error trying to make a deep copy directory: " + e.getMessage());
        }
    }

    private static void walkingIntoDirectory() {
        System.out.println("*** Walking into a Directory ***");
        try {
            var size = getPathSize(Path.of("."));
            System.out.format("Total size: %.2f megabytes %n", size / 1_000_000.0);
        } catch (IOException e) {
            System.out.println("Error trying to get the path size: " + e.getMessage());
        }
    }

    /**
     * Many of our earlier NIO.2 methods traverse symbolic links by default, with a NOFOLLOW_LINKS used to disable this
     * behavior. The "walk()" method does not follow symbolic links by default and requires the FOLLOW_LINKS option to
     * be enabled.
     */
    private static long getPathSize(Path source) throws IOException {
        try (var s = Files.walk(source)) {
            return s.parallel()
                    .filter(path -> !Files.isDirectory(path))
                    .mapToLong(ApplyingFunctionalProgramming::getSize)
                    .sum();
        }
    }

    /**
     * IMPORTANT: The "getSize()" helper method is needed because "Files.size()" declares IOException, and we'd rather
     * not put a try/catch block inside a lambda expression.
     */
    private static long getSize(Path p) {
        try {
            return Files.size(p);
        } catch (IOException e) {
            System.out.println("Error trying to get file size: " + e.getMessage());
        }
        return 0L;
    }
    /**
     * The "find()" method behaves in a similar manner as the walk() method, except that it takes a BiPredicate to filter
     * the data. It also requires a depth limit be set. Like walk(), find() also supports the FOLLOW_LINK option.
     *
     * The two parameters of the BiPredicate are a Path object and a "BasicFileAttributes" object, which you saw earlier
     * in the chapter. In this manner, NIO.2 automatically retrieves the basic file information for you, allowing you to
     * write complex lambda expressions that have direct access to this object.
     *
     * NOTE: While we could have accomplished this using the "walk()" method along with a call to "readAttributes()",
     * this implementation is a lot shorter and more convenient than those would have been. We also don't have to worry
     * about any methods within the lambda expression declaring a checked exception, as we saw in the "getPathSize()"
     * example.
     */
    private static void searchingDirectory() {
        System.out.println("*** Searching a Directory ***");
        Path path = Paths.get(".");
        long minSize = 1_000;
        try (var s = Files.find(path, 15,
                (p, a) -> a.isRegularFile() && p.toString().endsWith(".java") && a.size() > minSize)) {
            s.forEach(path1 -> System.out.printf("Found java file with more than %d bytes: %s%n",
                    minSize, path1));
        } catch (IOException e) {
            System.out.println("Error searching java files into directory: " + e.getMessage());
        }
    }

    private static void readingFile() {
        System.out.println("*** Reading a File ***");
        Path path = Paths.get("package-modules.sh");
        try (var s = Files.lines(path)) {
            s.forEach(s1 -> System.out.println("Reading line: " + s1));
        } catch (IOException e) {
            System.out.println("Error reading file line: " + e.getMessage());
        }

        // We can leverage other stream methods for a more powerful example.
        try (var s = Files.lines(path)){
            s.filter(s1 -> !s1.startsWith("jar"))
                    .map(s1 -> s1.length() > 50 ? s1.substring(0, 50) : s1)
                    .forEach(s1 -> System.out.println("Reading filtered line: = " + s1));
        } catch (IOException e) {
            System.out.println("Error reading filtered file line: " + e.getMessage());
        }
    }
}
