package com.hiperium.java.cert.practice.tests;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Chapter9Test {

    private static final Path question3File = Path.of("/tmp/icecream.txt");
    private static final Path question8File = Path.of("/tmp/baseball.txt");
    private static final Path question9File = Path.of("/tmp/breakfast.menu");
    private static final Path question34Dir = Path.of("/tmp/question34/delete/directory/tree");
    private static final Path question34File = Path.of("/tmp/question34/delete/directory/tree/test.txt");
    private static final Path question40File = Path.of("/tmp/question40/test.txt");
    private static final Path question40Dir = Path.of("/tmp/question40/");

    @BeforeClass
    public static void init() {
        try {
            if (!Files.exists(question3File)) {
                Files.createFile(question3File);
            }
            if (!Files.exists(question8File)) {
                Files.createFile(question8File);
            }
            if (!Files.exists(question9File)) {
                Files.createFile(question9File);
            }
            if (!Files.exists(question34Dir)) {
                Files.createDirectories(question34Dir);
            }
            if (!Files.exists(question34File)) {
                Files.createFile(question34File);
            }
            if (!Files.exists(question40Dir)) {
                Files.createDirectories(question40Dir);
            }
            if (!Files.exists(question40File)) {
                Files.createFile(question40File);
            }
        } catch (IOException e) {
            System.out.println("Error creating a file: " + e.getMessage());
        }
    }

    @AfterClass
    public static void destroy() {
        try {
            Files.deleteIfExists(question3File);
            Files.deleteIfExists(Path.of("/tmp/draft.txt"));
            Files.deleteIfExists(question8File);
            Files.deleteIfExists(question9File);
            Files.deleteIfExists(Path.of("/tmp/healthy.txt"));
            Files.deleteIfExists(Path.of("/tmp/scan.txt"));
            Files.deleteIfExists(question34File);
            Files.deleteIfExists(question34Dir);
            Files.deleteIfExists(question40File);
            Files.deleteIfExists(question40Dir);
        } catch (IOException e) {
            System.out.println("Error deleting file: " + e.getMessage());
        }
    }

    /**
     * Assuming the file path referenced in the following class is accessible and writable, what is the output of the
     * following program?
     *
     * NOTE: If FileWriter was used instead of FileOutputStream, then the code compile and print 1.
     * The try-with-resources statement closes "System.out" before the "catch" or "finally" blocks are called. When the
     * "finally" block is executed, the output has nowhere to go, which means the last value of 3 is not printed.
     */
    @Test
    public void question3() {
        String fn = "/tmp/icecream.txt";
        // try (var w = new BufferedWriter(new FileOutputStream(fn));       ERROR: Required type: Writer
        //      var s = System.out) {
        try (var w = new BufferedWriter(new FileWriter(fn))) {
            w.write("ALERT!");
            w.flush();
            w.write('!');           // <<<< NOTE
            System.out.print("1");
        } catch (IOException e) {
            System.out.print("2");
            System.out.println("Question 3 ERROR: " + e.getMessage());
        } finally {
            System.out.print("3");
        }
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 4:
     *
     * What is the expected output of the following code? Assume the directories referenced to, do not exist, prior to
     * the execution, and that the file system is available and able to be written.
     */
    public static class Resume {
        public void writeResume() throws Exception {
            var f1 = Path.of("/tmp/proofs");
            // f1.createDirectories();                       ERROR: Cannot resolve method 'createDirectories' in 'Path'.
            Files.createDirectories(f1);
            var f2 = Path.of("/tmp");
            // f2.createDirectory();                         k1 ERROR: Cannot resolve method 'createDirectory' in 'Path'
            try(var w = Files.newBufferedWriter(Path.of(f2.toString(), "draft.txt"))) {
                w.append("My dream job");
                w.flush();
            }
            // f1.delete(f1);                               ERROR: Cannot resolve method 'delete' in 'Path'.
            // f2.delete(f2);                               k2 ERROR: Cannot resolve method 'delete' in 'Path'.
            Files.deleteIfExists(f1);
        }
    }
    @Test
    public void question4() {
        try {
            new Resume().writeResume();
        } catch (Exception e) {
            System.out.println("Question 4 ERROR: " + e.getMessage());
        }
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following code snipped?
     */
    @Test
    public void question6() {
        Path p1 = Path.of("./found/../keys");
        Path p2 = Path.of("/lost/blue.txt");
        System.out.println("p1.resolve(p2) = " + p1.resolve(p2));   // PRINT: /lost/blue.txt
        System.out.println("p2.resolve(p1) = " + p2.resolve(p1));   // PRINT: /lost/blue.txt/./found/../keys
        Assert.assertTrue(true);
    }

    /**
     * Assuming "/tmp/baseball.txt" exists and is accessible, what is the expected result of executing the following
     * code snipped?
     *
     * D. The code compiles, but an exception is printed at runtime.
     *
     * NOTE: The 2nd argument of copy() command should be the location of the new file, not the folder of the new file
     * is placed in. Therefore, the program attempts to write the file to the path "/home", and since there is a dir at
     * that location, a FileAlreadyExistsException is thrown at runtime.
     */
    @Test
    public void question8() {
        var p1 = Path.of("baseball.txt");
        var p2 = Path.of("/home");
        var p3 = Path.of("/tmp");
        try {
            Files.createDirectories(p2);
        } catch (IOException e) {
            System.out.println("Question 8 ERROR to create directories: " + e.getMessage());
        }
        try {
            Files.copy(p3.resolve(p1), p2);
        } catch (IOException e) {
            System.out.println("Question 8 ERROR to copy files: " + e.getMessage());
        }
        Assert.assertTrue(true);
    }

    /**
     * Assuming the file referenced in the following snipped exists and contains five lines with the word "eggs" in
     * them, what is the expected output?
     */
    @Test
    public void question9() {
        var p = Path.of("/tmp/breakfast.menu");
        try {
            Files.readAllLines(p)
            //      .filter(s -> s.contains("eggs"))            ERROR: Cannot resolve method 'filter' in 'List'.
                    .stream()
                    .filter(s -> s.contains("eggs"))
                    .collect(Collectors.toList())
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Question 9 ERROR: " + e.getMessage());
        }
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 10:
     *
     * What is the output of the following program? Assume the file paths referenced in the class exists and are able
     * to be written to and read from.
     */
    public static class Vegetable implements Serializable {
        private Integer size = 1;
        private transient String name = "Red";
        { size= 3; name = "Purple"; }
        public Vegetable() {
            this.size = 2;
            this.name = "Green";
        }
    }
    @Test
    public void question10() {
        // NOTE: If the file "/tmp/healthy.txt" does not exist, then it is created at runtime.
        try (var o = new ObjectOutputStream(new FileOutputStream("/tmp/healthy.txt"))) {
            final var v = new Vegetable();
            v.size = 4;
            o.writeObject(v);
        } catch (IOException e) {
            System.out.println("Question 10 ERROR: " + e.getMessage());
        }
        try (var o = new ObjectInputStream(new FileInputStream("/tmp/healthy.txt"))) {
            var v = (Vegetable) o.readObject();
            System.out.println("v.name = " + v.name);                       // PRINT: null
            System.out.println("v.size = " + v.size);                       // PRINT: 4
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Question 10 ERROR: " + e.getMessage());
        }
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 14:
     *
     * What is the output of the following application?
     */
    private static class TaffyFactory {
        public int getPrize(byte[] luck) throws IOException {
            try (InputStream is = new ByteArrayInputStream(luck)) {
                System.out.println("is.read(new byte[2]) = " + is.read(new byte[2]));   // PRINT: 2
                System.out.println("is.markSupported() = " + is.markSupported());       // PRINT: true
                if (!is.markSupported()) return -1;
                is.mark(5);
                System.out.println("is.read() = " + is.read());                         // PRINT: 3
                System.out.println("is.read() = " + is.read());                         // PRINT: 4
                System.out.println("is.skip(3) = " + is.skip(3));                     // PRINT: 3
                System.out.println("is.read() = " + is.read());                         // PRINT: 8
                is.reset();
                return is.read();
            }
        }
    }
    @Test
    public void question14() throws IOException {
        final TaffyFactory p = new TaffyFactory();
        final var luck = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println("p.getPrize(luck) = " + p.getPrize(luck));   // PRINT: 3
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 15:
     *
     * What is the output of the following application?
     */
    public static class Valve implements Serializable {
        private int chambers = -1;
        private transient Double size = null;
        private static String color;
        public Valve() {
            System.out.println("Valve constructor");
            this.chambers = 3;
            color = "BLUE";
        }
        public static void main() throws Throwable {
            try (var o = new ObjectOutputStream(new FileOutputStream("/tmp/scan.txt"))) {
                final Valve v = new Valve();
                v.chambers = 2;
                v.size = 10.0;
                v.color = "RED";
                o.writeObject(v);
            }
            new Valve();    // NOTE: This set the static variable "color" to "BLUE".
            try (var o = new ObjectInputStream(new FileInputStream("/tmp/scan.txt"))) {
                Valve v = (Valve)o.readObject();
                System.out.println("v.chambers = " + v.chambers);   // PRINT: 2
                System.out.println("v.size = " + v.size);           // PRINT: null
                System.out.println("v.color = " + v.color);         // PRINT: BLUE
            }
        }
        { System.out.println("Valve init block"); chambers = 4; }
    }
    @Test
    public void question15() {
        try {
            Valve.main();
        } catch (Throwable e) {
            System.out.println("Question 15 ERROR: " + e.getMessage());
        }
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 19:
     *
     * The Rose application is run with an input argument of /tmp directory that contains 5 subdirectories, each of
     * which contains 5 files. What is the output of the following program?
     *
     * NOTE: "toRealPath()" method interacts with file system and therefore throws a checked exception IOException.
     * Since this checked exception is not handled inside the lambda expression, the code does not compile.
     *
     * If the lambda expression was fixed to handle the exception, then the expected number of Path values to be
     * printed, would be 6.
     */
    public static class Rose {
        public void tendGarden(Path p) throws Exception {
            // Files.walk(p, 1)
            //      .map(path -> path.toRealPath())             ERROR: Unhandled exception: java.io.IOException.
            Files.walk(p,1)
                    .map(path -> {
                        Path result = null;
                        try {
                            result = path.toRealPath();
                        } catch (IOException e) {
                            result = Path.of("invalidPathValue");
                        }
                        return path;
                    }).forEach(System.out::println);

        }
    }

    /**
     * Which statements about the following method is correct?
     *
     * NOTE: If maxDepth parameter was added, then the method would compile but, not print anything at runtime since
     * the stream does not include a terminal operation.
     */
    @Test
    public void question21() {
        var s = Path.of("/tmp");
        try {
            // Files.find(s, (p, a) -> a.isDirectory());            ERROR: Method declaration is incorrect.
            Files.find(s, 1, (p, a) -> a.isDirectory());
        } catch (IOException e) {
            System.out.println("Question 21 ERROR: " + e.getMessage());
        }
        Assert.assertTrue(true);
    }

    /**
     * Which statement about then following code snipped is correct?
     *
     * D. Both statements compile and produce the same results at runtime
     */
    @Test
    public void question24() throws IOException {
        Path p = Path.of("./src/main/java");
        Files.walk(p)
                .map(z -> z.toAbsolutePath().toString())
                .filter(s -> s.endsWith(".java"))
                .collect(Collectors.toList())
                .forEach(System.out::println);
        Files.find(p, Integer.MAX_VALUE, (w, a) -> w.toAbsolutePath().toString().endsWith(".java"))
                .collect(Collectors.toList())
                .forEach(System.out::println);
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 31:
     *
     * Assuming the current directory is "/home", then what is the output of the following program?
     *
     * E. The code compiles but prints an exception at runtime.
     *
     * subpath(): Returns a relative Path that is a subsequence of the name elements of this path. The name that is
     * closest to the root in the directory hierarchy has an index 0. The returned Path object has the name elements
     * that begin at beginIndex and extend to the element at index endIndex - 1.
     *
     * NOTE: If "getName(0)" has been used instead "getName(1)", then the program would run without issue and print
     * /Users/asolorzano/Java/workspace/java11-cert-practice/chapters/tricks
     */
    public static class Magician {
        public String doTrick(Path path) {
            Path subPath = path.subpath(2, 3);
            System.out.println("subPath = " + subPath);     // PRINT: tricks
            Path name1 = subPath.getName(1);                // getName() is zero indexed and may throw an IAE at runtime.
            System.out.println("name1 = " + name1);
            return name1
                    .toAbsolutePath()
                    .toString();
        }
        public static void main() {
            final Magician m = new Magician();
            System.out.print(m.doTrick(Paths.get("/bag/of/tricks/.././disappear.txt")));
        }
    }
    @Test(expected = IllegalArgumentException.class)
    public void question31() {
        Magician.main();
    }

    /**
     * What is the expected result of calling "deleteTree()" on a directory?
     *
     * D. The code does not compile.
     *
     * NOTE: The Files.delete() and Files.list() declares a checked IOException that must be handled or declared.
     */
    @Test
    public void question34() {
        Path q = Path.of("/tmp/question34");
        deleteTree(q);
        Assert.assertTrue(true);
    }
    private void deleteTree(Path q) {
        if (Files.isDirectory(q)) {
            try { Files.list(q).forEach(this::deleteTree); }    // list(q) declares a checked java.io.IOException.
            catch (IOException e) { System.out.println("Error trying to list directory: " + e.getMessage()); }
            try { Files.delete(q); }                            // delete(q) declares a checked java.io.IOException.
            catch (IOException e) { System.out.println("Error trying to delete directory: " + e.getMessage()); }
        } else {
            try { Files.delete(q); }                            // delete(q) declares a checked java.io.IOException.
            catch (IOException e) { System.out.println("Error trying to delete file: " + e.getMessage()); }
        }
    }

    /**
     * What is the output of the following code snipped?
     *
     * B. Different!
     */
    @Test
    public void question37() {
        var halleyComet = Path.of("starts/./rocks/../m1.meteor")
                .subpath(1, 5).normalize();
        System.out.println("halleyComet = " + halleyComet);             // PRINT: m1.meteor
        var lexellsComet = Paths.get("./starts/../solar/");
        lexellsComet.subpath(1, 3).resolve("m1.meteor").normalize();    // WARN: Result of 'normalize()' is ignored.
        System.out.println("lexellsComet = " + lexellsComet);           // PRINT: ./starts/../solar
        System.out.println(halleyComet.equals(lexellsComet) ?
                "Same!" : "Different!");                                // PRINT: Different!

        // TESTING
        var lexellsSubpath = lexellsComet.subpath(1, 3);
        System.out.println("lexellsSubpath = " + lexellsSubpath);           // PRINT: starts/..

        var lexellsResolved = lexellsSubpath.resolve("m1.meteor");
        System.out.println("lexellsResolved = " + lexellsResolved);         // PRINT: starts/../m1.meteor

        var lexellsNormalized = lexellsResolved.normalize();
        System.out.println("lexellsNormalized = " + lexellsNormalized);     // PRINT: m1.meteor
        Assert.assertTrue(true);
    }

    /**
     * QUESTION 40:
     *
     * What is the output of the following application?
     *
     * C. The number of lines in all files in a single directory.
     */
    public static class TheCount {
        public static Stream<String> readLines(Path p) {
            try {
                return Files.lines(p);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        public static long count (Path p) throws Exception {
            return Files.list(p)
                    .filter(Files::isRegularFile)
                    .peek(path -> System.out.println("File to be counted = " + path))
                    .flatMap(TheCount::readLines)
                    .peek(s -> System.out.println("Flatted map = " + s))
                    .count();
        }
    }
    @Test
    public void question40() throws Exception {
        System.out.println("count = " + TheCount.count(Paths.get("/tmp/question40")));
        Assert.assertTrue(true);
    }
}
