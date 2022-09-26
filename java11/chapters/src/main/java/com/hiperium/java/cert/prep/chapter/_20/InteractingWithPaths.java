package com.hiperium.java.cert.prep.chapter._20;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * IMPORTANT: Most methods defined in the Path interface do not require the path to exist. In addition, only
 * "toRealPath()" declares an IOException.
 *
 * PATH METHODS:
 * *****************************|***********************************|
 * Path of(String, String...)   | Path getParent()                  |
 * -----------------------------|-----------------------------------|
 * URI toURI()                  | Path getRoot()                    |
 * -----------------------------|-----------------------------------|
 * File toFile()                | boolean isAbsolute()              |
 * -----------------------------|-----------------------------------|
 * String toString()            | Path toAbsolutePath()             |
 * -----------------------------|-----------------------------------|
 * int getNameCount()           | Path relativize()                 |
 * -----------------------------|-----------------------------------|
 * Path getName(int)            | Path resolve(Path)                |
 * -----------------------------|-----------------------------------|
 * Path subpath(int, int)       | Path normalize()                  |
 * -----------------------------|-----------------------------------|
 * Path getFileName()           | Path toRealPath(LinkOption...)    |
 * -----------------------------|-----------------------------------|
 *
 */
public class InteractingWithPaths {

    public static final String ANIMATRONIC_JAVA_PATH = "/ext/zoo/dinos/Animatronic.java";

    public static void main(String[] args) {
        viewingPathInfo();
        usingSubPath();
        accessingPathElements();
        checkingPathType();
        usingNormalize();
        try {
            gettingFileSystemPath();
        } catch (IOException e) {
            System.out.println("Error getting file system path: " + e.getMessage());
        }
    }

    /**
     * The "getNameCount()" and "getName()" methods are often used in conjunction to retrieve the number of elements in
     * the path and a reference to each element, respectively. These two methods do not include the root directory as
     * part of the path.
     */
    private static void viewingPathInfo() {
        System.out.println("*** Viewing Path Info ***");
        Path path = Paths.get(ANIMATRONIC_JAVA_PATH);
        System.out.println("The path name is: " + path);
        for (int i = 0; i < path.getNameCount(); i++) {
            System.out.println("    Element " + i + " is: " + path.getName(i));
        }
    }

    /**
     * subpath(int beginIndex, int endIndex): The returned Path object has the name elements that begin at beginIndex
     * and extend to the element at index endIndex - 1.
     * This method throws an IllegalArgumentException if beginIndex is negative, or greater than or equal to the number
     * of elements. If endIndex is less than or equal to beginIndex, or larger than the number of elements.
     */
    private static void usingSubPath() {
        System.out.println("*** Using SubPath ***");
        var path = Paths.get(ANIMATRONIC_JAVA_PATH);
        System.out.println("subpath(0, 3) = " + path.subpath(0, 3));        // PRINT: ext/zoo/dinos
        System.out.println("subpath(1, 2) = " + path.subpath(1, 2));        // PRINT: zoo
        System.out.println("subpath(1, 3) = " + path.subpath(1, 3));        // PRINT: zoo/dinos
    }

    /**
     * The getFileName() returns the Path element of the current file or directory, while getParent() returns the full
     * path of the containing directory. The getParent() returns null if operated on the root path or at the top of a
     * relative path. The getRoot() method returns the root element of the file within the file system, or null if the
     * path is a relative path.
     */
    private static void accessingPathElements() {
        System.out.println("*** Accessing Path Elements ***");
        Path path1 = Paths.get(ANIMATRONIC_JAVA_PATH);
        printPathElements(path1);
        System.out.println("Another example using the path: ./armadillo/../shells.txt");
        Path path2 = Paths.get("./armadillo/../shells.txt");
        printPathElements(path2);
    }

    private static void printPathElements(Path path) {
        System.out.println("Filename is: " + path.getFileName());           // PRINT: Animatronic.java
        System.out.println("    Root is: " + path.getRoot());               // PRINT: /
        Path currentParent = path;
        while ((currentParent = currentParent.getParent()) != null) {
            System.out.println("    Current parent is: " + currentParent);
            // PRINT: /ext/zoo/dinos, /ext/zoo, /ext, /
        }
    }

    /**
     * toAbsolutePath(): converts a relative Path object to an absolute Path object by joining it to the current working
     * directory. If the Path object is already absolute, then the method just returns the Path object.
     *
     * NOTE: The current working directory can be selected from System.getProperty("user.dir"). This is the value that
     * 'toAbsolutePath()' will use when applied to a relative path.
     */
    private static void checkingPathType() {
        System.out.println("*** Checking Path Types ***");
        var path = Paths.get("misc/chapter_19");
        System.out.println("Path is absolute?: " + path.isAbsolute());      // PRINT: false
        System.out.println("Absolute path: " + path.toAbsolutePath());
        // WORKDIR: /Users/asolorzano/Java/workspace/java11-cert-practice
        // PRINT  : /Users/asolorzano/Java/workspace/java11-cert-practice/misc/chapter_19
    }

    /**
     * The first two examples apply the path symbols to remove the redundancies, but what about the last one? That is as
     * simplified as it can be. The "normalize()" method does not remove all the path symbols; only the ones that can be
     * reduced.
     */
    private static void usingNormalize() {
        var p1 = Path.of("./armadillo/../shells.txt");
        System.out.println("p1.normalize() = " + p1.normalize());       // PRINT: shells.txt

        var p2 = Path.of("/cats/../panther/foot");
        System.out.println("p2.normalize() = " + p2.normalize());       // PRINT: /panther/foot

        var p3 = Path.of("../../fish.txt");
        System.out.println("p3.normalize() = " + p3.normalize());       // PRINT: ../../fish.txt

        // The "normalize()" method also allows us to compare equivalent paths:
        var p4 = Paths.get("/pony/../weather.txt");
        var p5 = Paths.get("/weather.txt");
        System.out.println("p4.equals(p5) = " + p4.equals(p5));                             // PRINT: false
        System.out.println("p4.normalize().equals(p5) = " + p4.normalize().equals(p5));     // PRINT: true
    }

    /**
     * toRealPath(): This method is similar to "normalize()", in that it eliminates any redundant path symbols. It is
     * also similar to "toAbsolutePath()", in that it will join the path with the current working directory if the path
     * is relative. Unlike those two methods, though, "toRealPath()" will throw an exception if the path does not exist.
     * In addition, it will follow symbolic links, with an optional varargs parameter to ignore them.
     */
    private static void gettingFileSystemPath() throws IOException {
        System.out.println("Working dir: " + Paths.get(".").toRealPath());
        System.out.println("Zoom dir" + Paths.get("../../../Documents/Zoom").toRealPath());
        // Cannot access hidden files nor env variables
        // System.out.println(Paths.get("../../../.sdkman/candidates/current").toRealPath());   IOException
    }
}
