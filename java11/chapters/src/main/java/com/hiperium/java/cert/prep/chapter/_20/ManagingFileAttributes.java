package com.hiperium.java.cert.prep.chapter._20;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class ManagingFileAttributes {

    public static void main(String[] args) {
        readingCommonAttributes();
        checkingFileAccessibility();
        readingFileSize();
        checkingFileChanges();
        retrievingAttributes();
    }

    private static void readingCommonAttributes() {
        System.out.println("*** Reading Common Attributes ***");
        System.out.println(Files.isDirectory(Path.of("/tmp")));                 // PRINT: true
        System.out.println(Files.isDirectory(Path.of("/tmp/hello.txt")));       // PRINT: false
        System.out.println(Files.isSymbolicLink(Path.of("/tmp")));              // PRINT: true
        System.out.println(Files.isRegularFile(Path.of("/tmp")));               // PRINT: false
        System.out.println(Files.isRegularFile(Path.of("/tmp/hello.txt")));     // PRINT: true
    }

    /**
     * With exception to "isHidden()", these methods do not declare any checked exceptions and return false if the file
     * does not exist.
     */
    private static void checkingFileAccessibility() {
        System.out.println("*** Checking File Accessibility ***");
        try {
            System.out.println(Files.isHidden(Paths.get("/tmp")));              // PRINT: false
            System.out.println(Files.isHidden(Paths.get("/opt")));              // PRINT: false
            System.out.println(Files.isHidden(Paths.get(
                    "/Users/asolorzano/.gitconfig")));                          // PRINT: true
            System.out.println(Files.isHidden(Paths.get(
                    "/Users/asolorzano/.ssh")));                                // PRINT: true
        } catch (IOException e) {
            System.out.println("Error trying to verify if file is hidden: " + e.getMessage());
        }
        System.out.println(Files.isReadable(Paths.get("/tmp/book.txt")));           // PRINT: true
        System.out.println(Files.isWritable(Paths.get("/tmp/book.txt")));           // PRINT: true
        System.out.println(Files.isExecutable(Paths.get("package-modules.sh")));    // PRINT: true
    }

    /**
     * The Files.size() method is defined only on files. Calling Files.size() on a directory is undefined, and the result
     * depends on the file system. If you need to determine the size of a directory and its contents, you'll need to walk
     * the directory tree. We'll show you how to do this later in the chapter.
     */
    private static void readingFileSize() {
        System.out.println("*** Reading File Size ***");
        try {
            System.out.println(Files.size(Paths.get("package-modules.sh")));        // PRINT: 1349 (bytes)
        } catch (IOException e) {
            System.out.println("Error trying to get file size: " + e.getMessage());
        }
    }

    private static void checkingFileChanges() {
        System.out.println("*** Checking File Changes ***");
        Path path = Paths.get("package-modules.sh");
        try {
            System.out.println(Files.getLastModifiedTime(path));                // PRINT: 2021-10-25T06:21:10.297867Z
            System.out.println(Files.getLastModifiedTime(path).toMillis());     // PRINT: 1635142870297
        } catch (IOException e) {
            System.out.println("Error trying to get file last modified time: " + e.getMessage());
        }
    }

    private static void retrievingAttributes() {
        System.out.println("*** Retrieving Basic Attributes ***");
        var path = Paths.get("package-modules.sh");
        try {
            BasicFileAttributes data = Files.readAttributes(path, BasicFileAttributes.class);
            System.out.println("data.isDirectory() = " + data.isDirectory());
            System.out.println("data.isRegularFile() = " + data.isRegularFile());
            System.out.println("data.isSymbolicLink() = " + data.isSymbolicLink());
            System.out.println("data.size() in bytes = " + data.size());
            System.out.println("data.lastModifiedTime() = " + data.lastModifiedTime());
        } catch (IOException e) {
            System.out.println("Error getting file data basic attributes: " + e.getMessage());
        }
    }
}
