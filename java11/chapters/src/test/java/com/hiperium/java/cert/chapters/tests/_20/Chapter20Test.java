package com.hiperium.java.cert.chapters.tests._20;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Chapter20Test {

    private static final Path question3File = Path.of("/tmp/sloth.schedule");
    private static final Path question17File = Path.of("/tmp/food-schedule.csv");
    private static final Path question19File = Path.of("/tmp/walking.txt");
    private static final Path question19Dir = Path.of("/tmp/actions");
    private static final Path question21File = Path.of("/Users/asolorzano/Public/sounds.txt");

    @BeforeClass
    public static void createFiles() {
        try {
            if (!Files.exists(question3File)) {
                Files.createFile(question3File);
            }
            if (!Files.exists(question17File)) {
                Files.createFile(question17File);
            }
            if (!Files.exists(question19File)) {
                Files.createFile(question19File);
            }
            if (!Files.exists(question19Dir)) {
                Files.createDirectory(question19Dir);
            }
            if (!Files.exists(question21File)) {
                Files.createFile(question21File);
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }
    }

    @AfterClass
    public static void deleteCreatedFiles() {
        try {
            Files.deleteIfExists(question3File);
            Files.deleteIfExists(Path.of("/tmp/joe"));
            Files.deleteIfExists(Path.of("/tmp/mule.png"));
            Files.deleteIfExists(question17File);
            Files.deleteIfExists(question19File);
            Files.deleteIfExists(question19Dir);
            Files.deleteIfExists(question21File);
        } catch (IOException e) {
            System.out.println("Error deleting file: " + e.getMessage());
        }
    }

    /**
     * What is the output of the following code?
     */
    @Test
    public void question1() {
        var path = Path.of("/user/./root", "../kodiacbear.txt");
        System.out.println("Initial path: " + path);                // PRINT: /user/./root/../kodiacbear.txt
        // path.normalize().relativize("/lion");                    ERROR: Required type: Path, Provided: String.

        path.normalize().relativize(Path.of("/lion"));          // WARN: Result of 'relativize()' is ignored.
        System.out.println("Without normalized path = " + path);

        path = path.normalize();
        System.out.println("With normalized path = " + path);       // PRINT: /user/kodiacbear.txt

        path = path.normalize().relativize(Path.of("/lion"));
        System.out.println("With normalized and relativize path = " + path);    // PRINT: ../../lion
        Assert.assertTrue(true);
    }

    /**
     * What is the result of executing the following code?
     */
    @Test
    public void question3() {
        var p = Paths.get("/tmp/sloth.schedule");
        try {
            var a = Files.readAttributes(p, BasicFileAttributes.class);
            // Files.mkdir(p.resolve(".backup"));           ERROR: Cannot resolve method 'mkdir' in 'Files'.
            Files.createDirectory(p.resolve(".backup"));
            if (a.size() > 0 && a.isDirectory()) {
                // a.setTimes(null, null, null);            ERROR: Cannot resolve method 'setTimes' in 'BasicFileAttributes'.
            }
        } catch (IOException e) {
            System.out.println("Error operating file: " + e.getMessage());
        }
        Assert.assertTrue(true);
    }

    /**
     * If the current working directory is /user/home, then what is the output of the following code?
     *
     * C. /user/home/bear
     */
    @Test
    public void question4() {
        var p = Paths.get("/zoo/animals/bear/koala/food.txt");
        System.out.println(
                "p.subpath(1, 3).getName(1).toAbsolutePath() = " +
                p.subpath(1, 3).getName(1).toAbsolutePath());
        // PRINT: /Users/asolorzano/Java/workspace/java11-cert-practice/chapters/bear
        System.out.println("p.subpath(1, 3) = " + p.subpath(1, 3));                             // PRINT: animals/bear
        System.out.println("p.subpath(1, 3).getName(1) = " + p.subpath(1, 3).getName(1));       // PRINT: bear
        Assert.assertTrue(true);
    }

    /**
     * Assume /kang exists as a symbolic link to the directory "/mammal/kangaroo" within the file system. Which of the
     * following statements are correct about this code snippet?
     *
     * B. A new directory may be created.
     * C. If the code creates a directory, it will be reachable at /kang/joey.
     */
    @Test
    public void question5() {
        var path = Paths.get("/tmp");
        System.out.println("Files.isDirectory(path) = " + Files.isDirectory(path));
        System.out.println("Files.isSymbolicLink(path) = " + Files.isSymbolicLink(path));
        if (Files.isDirectory(path) && Files.isSymbolicLink(path)) {
            try {
                Files.createDirectory(path.resolve("joe"));
            } catch (IOException e) {
                System.out.println("Error creating directory: " + e.getMessage());
            }
        }
        Assert.assertTrue(true);
    }

    /**
     * Assume that the directory "/opt" exists and is empty. What is the result of executing the following code?
     */
    @Test
    public void question6() {
        Path path = Path.of("/opt");
        try (var z = Files.walk(path)) {
            boolean b = z
            //      .filter((p, a) -> a.isDirectory() && !path.equals(p))   X: Cannot resolve method 'isDirectory()'.
                    .filter(p -> !path.equals(p))                           // Lambda expression expects 1 parameter.
                    .findFirst().isPresent();                               // Y
            System.out.println(b? "No Sub" : "Has Sub");
        } catch (IOException e) {
            System.out.println("Error trying to walk into directory: " + e.getMessage());
        }
        Assert.assertTrue(true);
    }

    /**
     * If the current working directory is /zoo and the path /zoo/libs does not exist, then what is the result of
     * executing the following code?
     *
     * F. It compiles but throws an exception at runtime.
     *
     * NOTE: The method Files.isSameFile() first checks to see whether the Path values are the same in terms of equals().
     * Since the first path is relative and the second path is absolute, this comparison will return false, forcing
     * "isSameFile()" to check for the existence of both paths in the file system. Since we know /zoo/libs does not
     * exist, a NoSuchFileException is thrown.
     */
    @Test
    public void question7() {
        Path path = Paths.get("libs");
        try {
            if (Files.isSameFile(path, Paths.get("/Users/asolorzano/Java/workspace/java11-cert-practice/chapters/libs")))
                Files.createDirectories(path.resolve("info"));
        } catch (IOException e) {
            System.out.println("Error trying to verify if is the same file: " + e.getMessage());
        }
        Assert.assertTrue(true);
    }


    /**
     * What is the output of the following code?
     *
     * NOTE: Calling "resolve()" with absolute path as parameter, returns the absolute path.
     */
    @Test
    public void question9() {
        var path1 = Path.of("/pets/../cat.txt");
        var path2 = Paths.get("./dog.txt");
        System.out.println("path1.resolve(path2) = " + path1.resolve(path2));   // PRINT: /pets/../cat.txt/./dog.txt
        System.out.println("path2.resolve(path1) = " + path2.resolve(path1));   // PRINT: /pets/../cat.txt
        Assert.assertTrue(true);
    }

    /**
     * For the "copy()" method shown here, assume that the source exists as a regular file and that the target does not.
     * What is the result of the following code?
     *
     * A. It will output false.
     *
     * NOTE: "isSameFile()" first checks to see whether the Path values are the same in terms of "equals()", and returns
     * true only if the files pointed to are the same in the file system.
     */
    @Test
    public void question13() {
        var p1 = Path.of(".","/","pom.xml");
        System.out.println("p1.toString() = " + p1.toString());                             // PRINT: ./pom.xml
        p1 = p1.normalize();
        System.out.println("normalized p1 = " + p1);                                        // PRINT: pom.xml
        var p2 = Path.of("/tmp/mule.png");
        try {
            Files.copy(p1, p2, StandardCopyOption.COPY_ATTRIBUTES);
            System.out.println("Files.isSameFile(p1, p2) = " + Files.isSameFile(p1, p2));   // PRINT: false
        } catch (IOException e) {
            System.out.println("Error trying to copy files: " + e.getMessage());
        }
        Assert.assertTrue(true);
    }

    /**
     * Assume /monkeys exists as a directory containing multiple files, symbolic links, and subdirectories. Which
     * statement about the following code is correct?
     *
     * C. It prints nothing.
     *
     * NOTE: The "maxDepth" parameter specified as a second argument of "find()" method is 0, meaning the only record
     * that will be searched is the top level directory. Since we know that the top directory is a directory and not a
     * symbolic link, no other paths will be visited, and nothing wil be printed.
     */
    @Test
    public void question14() {
        var f = Path.of(".");
        try (var m = Files.find(f, 0, (path, attributes) -> attributes.isSymbolicLink())) {
        //  m.map(path -> path.toString())
            m.map(Path::toString)
                    .collect(Collectors.toList())
                    .stream()
                    .filter(s -> s.toString().endsWith(".xml"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Error trying to find files: " + e.getMessage());
        }
        Assert.assertTrue(true);
    }

    /**
     * Assuming the /tmp/food‐schedule.csv file exists with the specified contents, what is the expected output of
     * calling question17() on it?
     *
     * /tmp/food-schedule.csv
     *      6am,Breakfast
     *      9am,SecondBreakfast
     *      12pm,Lunch
     *      6pm,Dinner
     */
    @Test
    public void question17() {
        if (Files.exists(question17File)) {
            try (var writer = Files.newBufferedWriter(question17File)) {
                var list = new ArrayList<String>();
                list.add("6am,Breakfast");
                list.add("9am,SecondBreakfast");
                list.add("12pm,Lunch");
                list.add("6pm,Dinner");
                for (String text : list) {
                    writer.write(text);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error trying to write to a file: " + e.getMessage());
            }
        }
        try {
            Files.readAllLines(question17File)                              // r1
            //      .flatMap(p -> Stream.of(p.split(",")))                  // r2 ERROR: Cannot resolve method 'flatMap' in 'List'.
                    .stream()
                    .peek(s -> System.out.println("stream: " + s))
                    .flatMap(p -> Stream.of(p.split(",")))
                    .peek(s -> System.out.println("flatMap: " + s))
                    //      .map(q -> q.toUpperCase())                      // r3
                    .map(String::toUpperCase)
                    .forEach(s -> System.out.println("forEach: " + s));
        } catch (IOException e) {
            System.out.println("Error trying to read all lines of a file: " + e.getMessage());
        }
        Assert.assertTrue(true);
    }

    /**
     * What are some possible results of executing the following code?
     *
     * A. It prints some files in the root directory.
     * D. Another exception is thrown at runtime.
     *
     * NOTE: Files.walk() does not follow symbolic links by default. Only if the FOLLOW_LINKS option is provided, and a
     * cycle is encountered, an exception will be thrown.
     */
    @Test
    public void question18() {
        var x = Path.of("./src/main/..");
        try {
            Files.walk(x.toRealPath().getParent())
                    .peek(path -> System.out.println("path = " + path))
                    .map(path -> path.toAbsolutePath().toString())
                    .peek(s -> System.out.println("toAbsolutePath = " + s))
                    .filter(s -> s.endsWith(".java"))
                    .peek(s -> System.out.println("filtered = " + s))
                    .collect(Collectors.toList())
                    .forEach(s -> System.out.println("forEach = " + s));

        } catch (IOException e) {
            System.out.println("Error trying to get Real Path: " + e.getMessage());
        }
        Assert.assertTrue(true);
    }

    /**
     * Assuming the directories and files referenced to exist and are not symbolic links, what is the result of
     * executing the following code?
     *
     * NOTE: The second check returns false, because "equals()" checks only if path values are the same, without
     * reducing the path symbols.
     */
    @Test
    public void question19() {
        var p1 = Path.of("/tmp", ".").resolve(Path.of("walking.txt"));
        var p2 = new File("/tmp/././actions/../walking.txt").toPath();
        System.out.println("path1 = " + p1);        // PRINT: /tmp/./walking.txt
        System.out.println("path2 = " + p2);        // PRINT: /tmp/././actions/../walking.txt
        try {
            System.out.println("Files.isSameFile(p1, p2) = "
                    + Files.isSameFile(p1, p2));                            // PRINT: true
            System.out.println("p1.equals(p2) = " + p1.equals(p2));         // PRINT: false
            System.out.println("p1.normalize() = " + p1.normalize());       // PRINT: /tmp/walking.txt
            System.out.println("p2.normalize() = " + p2.normalize());       // PRINT: /tmp/walking.txt
            System.out.println(p1.normalize().equals(p2.normalize()));      // PRINT: true
        } catch (IOException e) {
            System.out.println("Error trying to verify if is the same path: " + e.getMessage());
        }
        Assert.assertTrue(true);
    }

    /**
     * Assuming the current directory is /seals/harp/food, what is the result of executing the following code?
     *
     * NOTE: The "normalize()" method does not convert a relative path into an absolute path, therefore the path after
     * normalize is just the current directory. The "for()" loop iterates the name values, but since there is only one
     * entry, the loop terminates after a single iteration.
     */
    @Test
    public void question20() {
        Path path = Paths.get(".");
        System.out.println("path = " + path);                                   // PRINT: .
        System.out.println("path.normalize() = " + path.normalize());           // PRINT: Empty string
        System.out.println("path.getNameCount() = " + path.getNameCount());     // PRINT: 1
        int count = 0;
        for (int i = 0; i < path.getNameCount(); i++) {
            count++;
        }
        System.out.println("count = " + count);     // PRINT: 1
        Assert.assertTrue(true);
    }

    /**
     * Assume the source instance passed to the following method represents a file that exists. Also, assume
     * /flip/sounds.txt exists as a file prior to executing this method. When this method is executed, which statement
     * correctly copies the file to the path specified by /flip/sounds.txt?
     *
     * B. Files.copy(source, dolphinDir.resolve(n), StandardCopyOption.REPLACE_EXISTING);
     */
    @Test
    public void question21() {
        Path source = Path.of("./pom.xml");
        copyIntoFlipDirectory(source);
        Assert.assertTrue(true);
    }

    private void copyIntoFlipDirectory(Path source) {
        var dolphinDir = Path.of("/Users/asolorzano/Public");
        try {
            // NOTE: Does not throw an exception if directory exists. But, with "/tmp" directory an exception occurs.
            dolphinDir = Files.createDirectories(dolphinDir);
            System.out.printf("Directory %s created successfully%n", dolphinDir);
        } catch (IOException e) {
            System.out.println("Error trying to create directory: " + e.getMessage());
        }
        var n = Paths.get("sounds.txt");
        try {
            Files.copy(source, dolphinDir.resolve(n), StandardCopyOption.REPLACE_EXISTING);
            System.out.printf("File %s copied successfully%n", source.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error trying to copy file: " + e.getMessage());
        }
    }
}
