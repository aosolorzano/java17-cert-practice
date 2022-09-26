package com.hiperium.java.cert.prep.chapter._20;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * The Files helper class is capable of interacting with real files and directories within the system. Because of this,
 * most of the methods in this part of the chapter take optional parameters and throw an IOException if the path does
 * not exist.
 *
 * Many of the names for the methods in the NIO.2 Files class are a lot more straightforward than what you saw in the
 * java.io.File class. For example, the java.io.File methods "renameTo()" and "mkdir()" have been changed to "move()"
 * and "createDirectory()", respectively, in the Files class.
 *
 * FILES METHODS:
 *
 * **************************************************|*******************************************************|
 * boolean exists(Path, LinkOption...)               | Path move(Path, Path, CopyOption...)                  |
 * --------------------------------------------------|-------------------------------------------------------|
 * boolean isSameFile(Path, Path)                    | void delete(Path)                                     |
 * --------------------------------------------------|-------------------------------------------------------|
 * Path createDirectory(Path, FileAttribute<?>...)   | boolean deleteIfExists(Path)                          |
 * --------------------------------------------------|-------------------------------------------------------|
 * Path createDirectories(Path, FileAttribute<?>...) | BufferedReader newBufferedReader(Path)                |
 * --------------------------------------------------|-------------------------------------------------------|
 * Path copy(Path, Path, CopyOption...)              | BufferedWriter newBufferedWriter(Path, OpenOption...) |
 * --------------------------------------------------|-------------------------------------------------------|
 * long copy(InputStream, Path, CopyOption...)       | List<String> readAllLines(Path)                       |
 * --------------------------------------------------|-------------------------------------------------------|
 * long copy(Path, OutputStream)                     |                                                       |
 * --------------------------------------------------|-------------------------------------------------------|
 *
 */
public class OperatingFilesAndDirectories {

    /**
     * NOTE: Create a file named hello.txt in /tmp directory, with any text inside.
     */
    public static void main(String[] args) {
        checkingExistenceWithExists();
        makingDirectories();
        copyingFiles();
        copyingFilesWithStreams();
        copyFilesIntoDirectory();
        movingOrRenamingPaths();
        deletingFiles();
        readingAndWritingDataWithBuffers();
        readingCompleteFile();
    }

    /**
     * The first example checks whether a file exists, while the second example checks whether a directory exists.
     * This method does not throw an exception if the file does not exist, as doing so would prevent this method from
     * ever returning false at runtime.
     *
     * IMPORTANT: Remember, a file and directory may both have extensions. In the last example, the two paths could
     * refer to two files or two directories. Unless the exam tells you whether the path refers to a file or directory,
     * do not assume either.
     */
    private static void checkingExistenceWithExists() {
        System.out.println("*** Checking existence with exists() ***");
        var b1 = Files.exists(Paths.get("/ostrich/feathers.png"));
        System.out.println("Path " + (b1 ? "Exist" : "Missing"));

        var b2 = Files.exists(Paths.get("/Users/asolorzano"));
        System.out.println("Path " + (b2 ? "Exist" : "Missing"));
    }

    /**
     * createDirectory(): will create a directory and throw an exception if it already exists or the paths leading up to
     * the directory do not exist.
     *
     * createDirectories(): works just like the java.io.File method "mkdirs()", in that it creates the target directory
     * along with any nonexistent parent directories leading up to the path. If all the directories already exist,
     * createDirectories() will simply complete without doing anything. This is useful in situations where you want to
     * ensure a directory exists and create it if it does not.
     */
    private static void makingDirectories() {
        System.out.println("*** Making directories ***");
        try {
            Files.createDirectory(Path.of("/tmp/asolorzano"));      // Throw an exception if directory already exists.
        } catch (IOException e) {
            System.out.println("Error trying to create a directory: " + e.getMessage());
        }
        try {
            Files.createDirectories(Path.of("/tmp/asolorzano/field/pasture/green"));
        } catch (IOException e) {
            System.out.println("Error trying to create directory path: " + e.getMessage());
        }
    }

    /**
     * When directories are copied, the copy is shallow. A shallow copy means that the files and subdirectories within
     * the directory are not copied. A deep copy means that the entire tree is copied, including all of its content and
     * subdirectories.
     */
    private static void copyingFiles() {
        System.out.println("*** Copying Files ***");
        try {
            // Throw an exception if directory already exists.
            Files.copy(Paths.get("/tmp/asolorzano"), Paths.get("/tmp/asolorzano-bak"));
        } catch (IOException e) {
            System.out.println("Error trying to copying a directory: " + e.getMessage());
        }

        var newFilePath = "/tmp/asolorzano/field/book.txt";
        Path newFile = Paths.get(newFilePath);
        try {
            Files.createFile(newFile);
        } catch (IOException e) {
            System.out.println("Error trying to create a new file: " + e.getMessage());
        }

        try {
            // Throw an exception if archive already exists.
            Files.copy(Paths.get(newFilePath), Paths.get("/tmp/book.txt"));
        } catch (IOException e) {
            System.out.println("Error trying to copying files: " + e.getMessage());
        }

        // By default, if the target already exists, the "copy()" method will throw an exception. You can change this
        // behavior by providing the StandardCopyOption enum value REPLACE_EXISTING to the method.
        try {
            Files.copy(Paths.get(newFilePath), Paths.get("/tmp/book.txt"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Error trying to replace existing file: " + e.getMessage());
        }

    }

    /**
     * While we used FileInputStream in the first example, the streams could have been any valid I/O stream including
     * website connections, in‐memory stream resources, and so forth. The second example prints the contents of a file
     * directly to the System.out stream.
     */
    private static void copyingFilesWithStreams() {
        System.out.println("*** Copying Files with Streams ***");
        try (var is = new FileInputStream("/tmp/hello.txt")){
            Files.copy(is, Paths.get("/tmp/hello-bak.txt"));
        } catch (IOException e) {
            System.out.println("Error trying to copying files: " + e.getMessage());
        }
        try {
            Files.copy(Paths.get("/tmp/hello-bak.txt"), System.out);        // PRINT: Hello world
        } catch (IOException e) {
            System.out.println("Error trying to print file to System.out: " + e.getMessage());
        }
    }

    private static void copyFilesIntoDirectory() {
        System.out.println("*** Copying Files into a Directory ***");
        var file = Paths.get("/tmp/hello.txt");
        var directory = Paths.get("/tmp/hello-directory");
        try {
            // IMPORTANT: if the directory did not exist, then it would create a new file with the contents of hello.txt,
            // but it would be called /hello-dir. Remember, we said files may not need to have extensions, and in this
            // example, it matters.
            Files.copy(file, directory, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Error copying a file into another: " + e.getMessage());
        }

        // The correct way to copy the file into the directory would be to do the following:
        // var dir = Paths.get("/tmp/hello-dir/hello.txt");     But, we can use the "resolve()" method too.
        var dir = Paths.get("/tmp/asolorzano-bak").resolve(file.getFileName());
        try {
            Files.copy(file, dir);
        } catch (IOException e) {
            System.out.println("Error copying a file into a directory: " + e.getMessage());
        }
    }

    /**
     * Like copy(), move() requires REPLACE_EXISTING to overwrite the target if it exists, else it will throw an
     * exception. Also like copy(), move() will not put a file in a directory if the source is a file and the target is
     * a directory. Instead, it will create a new file with the name of the directory.
     */
    private static void movingOrRenamingPaths() {
        System.out.println("*** Moving or renaming Paths ***");
        try {
            Files.move(Path.of("/tmp/asolorzano-bak"), Path.of("/tmp/asolorzano-new"));
        } catch (IOException e) {
            System.out.println("Error moving a directory: " + e.getMessage());
        }
        try {
            Files.move(Paths.get("/tmp/hello-bak.txt"), Paths.get("/tmp/hello-new.txt"));
        } catch (IOException e) {
            System.out.println("Error moving a file: " + e.getMessage());
        }
        try {
            // An atomic move is one in which a file is moved within the file system as a single indivisible operation.
            // Putting in another way, any process monitoring the file system never sees an incomplete or partially
            // written file. If the file system does not support this feature, an AtomicMoveNotSupportedException will
            // be thrown.
            Files.move(Path.of("/tmp/book-bak.txt"), Path.of("/tmp/book-new.txt"),
                    StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            System.out.println("Error moving a file using ATOMIC_MOVE: " + e.getMessage());
        }
    }

    /**
     * To delete a directory, it must be empty. Both of these methods throw an exception if operated on a nonempty
     * directory. In addition, if the path is a symbolic link, then the symbolic link will be deleted, not the path that
     * the symbolic link points to.
     *
     * NOTE: Similar to "createDirectories()", "deleteIfExists()" is useful in situations where you want to ensure a
     * path does not exist, and delete it if it does.
     */
    private static void deletingFiles() {
        System.out.println("*** Deleting Files ***");
        try {
            Files.delete(Paths.get("/tmp/asolorzano-bak"));
        } catch (IOException e) {
            System.out.println("Error deleting directory: " + e.getMessage());
        }
        try {
            var deleted = Files.deleteIfExists(Path.of("/tmp/hello-directory"));
            System.out.println("File deleted?: " + deleted);
        } catch (IOException e) {
            System.out.println("Error deleting a file if exists: " + e.getMessage());
        }
    }

    /**
     * Did you notice that both of these methods use buffered streams rather than low‐level file streams? As we mentioned
     * before, the buffered stream classes are more performant, especially when working with files.
     */
    private static void readingAndWritingDataWithBuffers() {
        System.out.println("*** Reading and Writing data using Buffers ***");
        var path = Path.of("/tmp/hello.txt");
        try (var reader = Files.newBufferedReader(path)) {
            String currentLine = null;
            while ((currentLine = reader.readLine()) != null) {
                System.out.println("Reading current line: " + currentLine);
            }
        } catch (IOException e) {
            System.out.println("Error reading file content: " + e.getMessage());
        }

        var list = new ArrayList<String>();
        list.add("Smoke");
        list.add("Yogi");
        var file = Path.of("/tmp/bears.txt");
        try (var writer = Files.newBufferedWriter(file)) {
            for (String text : list) {
                writer.write(text);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to a file: " + e.getMessage());
        }
    }

    /**
     * The entire file is read when "readAllLines()" method is called, with the resulting List<String> storing all the
     * contents of the file in memory at once. If the file is significantly large, then we may trigger an
     * OutOfMemoryError trying to load all of it into memory.
     */
    private static void readingCompleteFile() {
        System.out.println("*** Reading an entire File ***");
        var path = Path.of("/tmp/bears.txt");
        try {
            List<String> lines = Files.readAllLines(path);
            lines.forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Error reading a file: " + e.getMessage());
        }
    }
}
