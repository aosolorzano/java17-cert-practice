package com.hiperium.java.cert.prep.chapter._19;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Serializable interface is a marker interface. It means the interface does not have any methods. Any class can
 * implement the Serializable interface since there are no required methods to implement.
 *
 * NOTE: The 'serialVersionUID' helps to inform the JVM that the stored data may not match the new class definition. If
 * an older version of the class is encountered during deserialization, a java.io.InvalidClassException may be thrown.
 * Note that since Serializable is not part of the java.lang package, it must be imported or referenced with the package
 * name.
 *
 * What happens to data marked transient on deserialization? It reverts to its default Java values, such as 0.0 for
 * double, 0 for int, or null for objects like String.
 */
class Gorilla implements Serializable {
    private static final long serialVersionUID = 5675821773091907056L;
    private String name;
    private int age;
    private Boolean friendly;
    private transient String favoriteFood;

    public Gorilla(String name, int age, Boolean friendly, String favoriteFood) {
        this.name = name;
        this.age = age;
        this.friendly = friendly;
        this.favoriteFood = favoriteFood;
    }
    @Override
    public String toString() {
        return "Gorilla{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", friendly=" + friendly +
                ", favoriteFood='" + favoriteFood + '\'' +
                '}';
    }
    // NOTE: IT IS NOT REQUIRED IMPLEMENT GETTER AND SETTERS FOR SERIALIZATION/DESERIALIZATION.
}

/**
 * IMPORTANT: When you deserialize an object, the constructor of the serialized class, along with any instance
 * initializers, is not called when the object is created. Java will call the no‐arg constructor of the first
 * non-serializable parent class it can find in the class hierarchy. In our Gorilla example before, this would just be
 * the no‐arg constructor of Object.
 */
class Chimpanzee implements Serializable {
    private transient String name;
    private transient int age = 10;
    private static char type = 'C';
    { this.age = 14; }

    public Chimpanzee() {
        this.name = "Unknown";
        this.age = 12;
        this.type = 'Q';
    }
    public Chimpanzee(String name, int age, char type) {
        this.name = name;
        this.age = age;
        this.type = type;
    }
    @Override
    public String toString() {
        return "Chimpanzee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", type='" + type + '\'' +
                '}';
    }
}

public class IOStreamClasses {

    public static final String FILE_NAME_SOURCE = "misc/review_chapter_19/test_src.txt";
    public static final String FILE_NAME_DESTINATION = "misc/review_chapter_19/test_dest.txt";
    public static final String FILE_NAME_SERIALIZING = "misc/review_chapter_19/test_serializing.txt";

    public static void main(String[] args) {
        var src = new File(FILE_NAME_SOURCE);
        var dest = new File(FILE_NAME_DESTINATION);
        var data = new File(FILE_NAME_SERIALIZING);
        try {
            readingAndWritingBinaryData(src, dest);
            bufferingBinaryData(src, dest);
            readingAndWritingCharacterData(src, dest);
            bufferingCharacterData(src, dest);
            serializingData(data);
            deserializingData(data);
            serializingAndDeserializingData(data);
            printingData(data);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error Message() = " + e.getMessage());
        }
    }

    /**
     * If we need to append to an existing file, there's a constructor for that. The FileOutputStream class includes
     * overloaded constructors that take a boolean append flag. When set to true, the output stream will append to the
     * end of a file if it already exists. This is useful for writing to the end of log files, for example.
     */
    private static void readingAndWritingBinaryData(File src, File dest) throws IOException {
        System.out.println("*** Reading and Writing Binary Data ***");
        try (var in  = new FileInputStream(src);
             var out = new FileOutputStream(dest)) {
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
        }
    }

    /**
     * While our readingAndWritingBinaryData() method is valid, it tends to perform poorly on large files. As discussed
     * earlier, that's because there is a cost associated with each round‐trip to the file system. We can easily enhance
     * our implementation.
     *
     * Instead of reading the data one byte at a time, we read and write up to 1024 bytes at a time. The return value
     * 'lengthRead' is critical for determining whether we are at the end of the stream and knowing how many bytes we
     * should write into our output stream.
     *
     * We also added a 'flush()' command at the end of the loop to ensure data is written to disk between each iteration.
     *
     * NOTE: BufferedInputStream class is capable of retrieving and storing in memory more data than you might request
     * with a single 'read(byte[])' call. For successive calls to the read(byte[]) method with a small byte array, using
     * the Buffered classes would be faster in a wide variety of situations, since the data can be returned directly
     * from memory without going to the file system.
     */
    private static void bufferingBinaryData(File src, File dest) throws IOException {
        System.out.println("*** Buffering Binary Data ***");
        try (var in  = new BufferedInputStream(new FileInputStream(src));
             var out = new BufferedOutputStream(new FileOutputStream(dest))) {
            // Any buffer size that is a power of 2 from 1,024 to 65,536 is a good choice in practice.
            var buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        }
    }

    /**
     * The FileReader and FileWriter classes, along with their associated buffer classes, are among the most convenient
     * I/O classes because of their built‐in support for text data. The FileReader class doesn't contain any new methods
     * we haven't seen before. The FileWriter inherits a method from the Writer class that allows it to write String
     * values like show next.
     */
    private static void readingAndWritingCharacterData(File src, File dest) throws IOException {
        System.out.println("*** Reading and Writing Character Data ***");
        try (var reader = new FileReader(src);
             var writer = new FileWriter(dest)) {
            writer.write("Hello world from FileWriter ;)");
            int b;
            while ((b = reader.read()) != -1) {
                writer.write(b);
                // writer.newLine();        ERROR: Cannot resolve method 'newLine' in 'FileWriter'.
            }
        }
    }

    /**
     * We add two new methods, 'readLine()' and 'newLine()', that are particularly useful when working with String
     * values.
     *
     * Instead of using a buffer array, we are using a String to store the data read during each loop iteration. By
     * storing the data temporarily as a String, we can manipulate it as we would any String value. For example, we can
     * call replaceAll() or toUpperCase() to create new values.
     *
     * Finally, we are inserting a 'newLine()' on every iteration of the loop. This is because 'readLine()' strips out
     * the line break character. Without the call to newLine(), the copied file would have all of its line breaks
     * removed.
     */
    private static void bufferingCharacterData(File src, File dest) throws IOException {
        System.out.println("*** Buffering Character Data ***");
        try (var reader = new BufferedReader(new FileReader(src));
             var writer = new BufferedWriter(new FileWriter(dest))) {
            writer.write("Hello world from BufferedWriter ;)");
            writer.newLine();
            String s;
            while ((s = reader.readLine()) != null) {
                writer.write(s);
                writer.newLine();
            }
        }
    }

    /**
     * Serialization is the process of converting an in‐memory object to a byte stream. Likewise, deserialization is the
     * process of converting from a byte stream into an object. Serialization often involves writing an object to a
     * stored or transmittable format, while deserialization is the reciprocal process.
     *
     * NOTE: Any process attempting to serialize an object will throw a 'NotSerializableException' if the class does not
     * implement the Serializable interface properly.
     */
    private static void serializingData(File datafile) throws IOException {
        System.out.println("*** Serializing Data ***");
        List<Gorilla> list = new ArrayList<>();
        list.add(new Gorilla("Monica", 5, false, "food A"));
        list.add(new Gorilla("Marcos", 3, true, "food B"));

        try (var out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(datafile)))) {
            for (Gorilla gorilla : list) {
                out.writeObject(gorilla);
            }
        }
    }
    /**
     * NOTE: Unlike our earlier techniques for reading methods from an input stream, we need to use an infinite loop to
     * process the data, which throws an EOFException when the end of the stream is reached. If your program happens to
     * know the number of objects in the stream, then you can call readObject() a fixed number of times, rather than
     * using an infinite loop.
     */
    private static void deserializingData(File datafile) throws IOException, ClassNotFoundException {
        System.out.println("*** Deserializing Data ***");
        var gorillas = new ArrayList<Gorilla>();
        try (var in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(datafile)))) {
            while (true) {
                var object = in.readObject();
                if (object instanceof Gorilla)
                    gorillas.add((Gorilla) object);
            }
        // IMPORTANT: EXCEPTION USED WHEN FILE END REACHED.
        } catch (EOFException e) {
            System.out.println("EOFException: " + e.getMessage());
        }
        // PRINT: [Gorilla{name='Monica', age=5, friendly=false, favoriteFood='null'},
        //         Gorilla{name='Marcos', age=3, friendly=true, favoriteFood='null'}]
        System.out.println("gorillas = " + gorillas);
    }
    /**
     * Upon deserialization, none of the constructors in Chimpanzee is called. Even the no‐arg constructor that sets the
     * values [name=Unknown, age=12, type=Q] is ignored. The instance initializer that sets age to 14 is also not
     * executed.
     *
     * What about the type variable? Since it's static, it will actually display whatever value was set last. If the
     * data is serialized and deserialized within the same execution, then it will display B, since that was the last
     * Chimpanzee we created. On the other hand, if the program performs the deserialization and print on startup, then
     * it will print C, since that is the value the class is initialized with.
     */
    private static void serializingAndDeserializingData(File data) throws IOException, ClassNotFoundException {
        System.out.println("*** Serializing Chimpanzee Data ***");
        var chimpanzees = new ArrayList<Chimpanzee>();
        chimpanzees.add(new Chimpanzee("Ham",2,'A'));
        chimpanzees.add(new Chimpanzee("Enos",4,'B'));

        try (var out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(data)))) {
            for (Chimpanzee chimpanzee : chimpanzees) {
                out.writeObject(chimpanzee);
            }
            out.flush();
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("*** Deserializing Chimpanzee Data ***");
        chimpanzees.clear();
        try (var in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(data)))) {
            while (true) {
                var object = in.readObject();
                if (object instanceof Chimpanzee)
                    chimpanzees.add((Chimpanzee) object);
            }
        } catch (EOFException e) {
            System.out.println("EOFException: " + e.getMessage());
        }
        // PRINT: [Chimpanzee{name='null', age=0, type=B}, Chimpanzee{name='null', age=0, type=B}]
        System.out.println("chimpanzees = " + chimpanzees);
    }

    /**
     * The print stream classes have the distinction of being the only I/O stream classes we cover that do not have
     * corresponding input stream classes. And unlike other OutputStream classes, PrintStream does not have Output in
     * its name.
     *
     * Furthermore, the PrintWriter class even has a constructor that takes an OutputStream as input. This is one of the
     * few exceptions in which we can mix a byte and character stream.
     *
     *      public PrintWriter(OutputStream out)
     *
     * COMMON PRINT STREAM FORMAT() SYMBOLS:
     *
     * *******|*****************************************************************|
     * Symbol | Description                                                     |
     * -------|-----------------------------------------------------------------|
     * %s     | Applies to any type, commonly String values.                    |
     * -------|-----------------------------------------------------------------|
     * %d     | Applies to integer values like int and long.                    |
     * -------|-----------------------------------------------------------------|
     * %f     | Applies to floating‐point values like float and double.         |
     * -------|-----------------------------------------------------------------|
     * %n     | Inserts a line break using the system‐dependent line separator. |
     * -------|-----------------------------------------------------------------|
     */
    private static void printingData(File data) throws IOException {
        try (PrintWriter out = new PrintWriter("zoo.log")) {
            out.write(String.valueOf(5));
            out.println(5);
            var chimpanzee = new Chimpanzee();
            out.write(chimpanzee == null? "null" : chimpanzee.toString());
            out.print(chimpanzee);

            System.out.println("*** Printing Data using 'format()' ***");
            String name = "Lindsey";
            int orderId = 5;
            System.out.format("Hello " + name + ", order " + orderId + " is ready");
            System.out.printf("Hello %s, order %d is ready %n", name, orderId);            // We can use 'format()' instead.

            String username = "james";
            double score = 90.25;
            int total = 100;
            System.out.format("%s:%n    Score: %f out of %d%n", username, score, total);

            // By default, %f displays exactly six digits past the decimal. If you want to display only one digit after the
            // decimal, you could use %.1f instead of %f. The format() method relies on rounding, rather than truncating
            // when shortening numbers. For example, 90.250000 will be displayed as 90.3 (not 90.2) when passed to format()
            // with %.1f.
            out.format("%s:%n    Score: %.1f out of %d%n", username, score, total);

            // The format() method also supports two additional features. You can specify the total length of output by
            // using a number before the decimal symbol. By default, the method will fill the empty space with blank spaces.
            // You can also fill the empty space with zeros, by placing a single zero before the decimal symbol. The
            // following examples use brackets, [], to show the start/end of the formatted value:
            var pi = 3.14159265359;
            System.out.printf("[%f]", pi);          // PRINT: [3.141593]
            System.out.printf("[%12.8f]", pi);      // PRINT: [  3.14159265]
            System.out.printf("[%012.8f]", pi);     // PRINT: [003.14159265]
            System.out.format("[%012f]", pi);       // PRINT: [00003.141593]
            System.out.format("[%12.2f]", pi);      // PRINT: [        3.14]
            System.out.format("[%.3f]", pi);        // PRINT: [3.142]
        }

        // MORE EXAMPLES
        File source = new File("zoo.log");
        try (var out = new PrintWriter(new BufferedWriter(new FileWriter(source)))) {
            out.print("Today's weather is: ");
            out.println("Sunny");
            out.print("Today's temperature at the zoo is: ");
            out.print(1 / 3.0);
            out.println('C');
            out.format("It has rained %5.2f inches this year %d", 10.2, 2021);
            out.println();
            out.printf("It may rain %s more inches this year", 1.2);
        }
    }
}
