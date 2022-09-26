package com.hiperium.java.cert.prep.chapter._19;

import java.io.*;
import java.util.Objects;

/**
 * Commonly used java.io.File methods
 *
 * *****************************|*************************************************************************************|
 * METHOD NAME                  | DESCRIPTION                                                                         |
 * *****************************|*************************************************************************************|
 * boolean delete()             | Deletes the file or directory and returns true only if successful. If this instance |
 *                              | denotes a directory, then the directory must be empty in order to be deleted.       |
 * -----------------------------|-------------------------------------------------------------------------------------|
 * boolean exists()             | Checks if a file exists.                                                            |
 * -----------------------------|-------------------------------------------------------------------------------------|
 * String getAbsolutePath()     | Retrieves the absolute name of the file or directory within the file system.        |
 * -----------------------------|-------------------------------------------------------------------------------------|
 * String getName()             | Retrieves the name of the file or directory.                                        |
 * -----------------------------|-------------------------------------------------------------------------------------|
 * String getParent()           | Retrieves the parent directory that the path is contained in or null if there is    |
 *                              | none.                                                                               |
 * -----------------------------|-------------------------------------------------------------------------------------|
 * boolean isDirectory()        | Checks if a File reference is a directory within the file system.                   |
 * -----------------------------|-------------------------------------------------------------------------------------|
 * boolean isFile()             | Checks if a File reference is a file within the file system.                        |
 * -----------------------------|-------------------------------------------------------------------------------------|
 * long lastModified()          | Returns the number of milliseconds since the epoch (number of milliseconds since 12 |
 *                              | a.m. UTC on January 1, 1970) when the file was last modified.                       |
 * -----------------------------|-------------------------------------------------------------------------------------|
 * long length()                | Retrieves the number of bytes in the file.                                          |
 * -----------------------------|-------------------------------------------------------------------------------------|
 * File[] listFiles()           | Retrieves a list of files within a directory.                                       |
 * -----------------------------|-------------------------------------------------------------------------------------|
 * boolean mkdir()              | Creates the directory named by this path.                                           |
 * -----------------------------|-------------------------------------------------------------------------------------|
 * boolean mkdirs()             | Creates the directory named by this path including any nonexistent parent dirs.     |
 * -----------------------------|-------------------------------------------------------------------------------------|
 * boolean renameTo(File dest)  | Renames the file or directory denoted by this path to dest and returns true only if |
 *                              | successful.                                                                         |
 * -----------------------------|-------------------------------------------------------------------------------------|
 *
 */
public class FilesAndDirectories {

    public static final String DATA_STRIPES_TXT = "/home/asolorzano/data/stripes.txt";

    public static void main(String[] args) {
        getLocalFileSeparatorCharacter();
        validateIfFileExists();
        pathInformation();
        highLevelStream();
    }

    private static void getLocalFileSeparatorCharacter() {
        System.out.println("File separator from properties: " + System.getProperty("file.separator"));  // PRINT: '/'
        System.out.println("File separator from File class: " + File.separator);                        // PRINT: '/'
    }

    private static void validateIfFileExists() {
        var zooFile = new File(DATA_STRIPES_TXT);
        var c = System.console();
        Writer w = c.writer();
        System.out.println("zooFile.exists() = " + zooFile.exists());                                   // PRINT: FALSE
    }

    private static void pathInformation() {
        var file = new File("README.md");
        System.out.println("file.exists() = " + file.exists());
        if (file.exists()) {
            System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());   // PRINT: /Users/java/tests/...

            System.out.println("file.getParent() = " + file.getParent());               // PRINT: null
            if (file.isFile()) {
                System.out.println("file.length() = " + file.length());                 // PRINT: 8215 (bytes)
                System.out.println("file.lastModified() = " + file.lastModified());     // PRINT: 1635270499829
            } else {
                for (File subFile : Objects.requireNonNull(file.listFiles())) {
                    System.out.println("    " + subFile.getName());
                }
            }
        }
    }

    /**
     * One of the reasons that Buffered streams tend to perform so well in practice is that many file systems are
     * optimized for sequential disk access. The more sequential bytes you read at a time, the fewer round‐trips between
     * the Java process and the file system, improving the access of your application. For example, accessing 1,600
     * sequential bytes is a lot faster than accessing 1,600 bytes spread across the hard drive.
     */
    private static void highLevelStream() {
        try (var br = new BufferedReader(new FileReader("package-modules.sh"))) {
            System.out.println("br.readLine() = " + br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // The entire buffered object is wrapped by a high‐level ObjectInputStream, which allows us to interpret the
        // data as a Java object.
        try (var ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("package-modules.sh")))) {
            System.out.println("ois.readObject() = " + ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR reading object from stream: " + e.getMessage());
        }
    }
}
