package com.hiperium.java.cert.chapters.tests._19;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class Chapter19Test {

    /**
     * Assuming pom.xml exists and is not empty, what statements about the following method are correct?
     */
    @Test(expected = IOException.class)
    public void question16() throws IOException {
        var o = new FileWriter("target/new-zoo.txt");
        try (var f = new FileReader("pom.xml");         // The program refers to the POM at chapters folder.
             var b = new BufferedReader(f); o) {
            o.write(b.readLine());
        }
        o.write("");                                        // ERROR: Stream closed.
    }

    /**
     * Assume reader is a valid stream that supports mark() and whose next characters are PEACOCKS. What is the expected
     * output of the following code snippet?
     *
     * C. PEOE
     */
    @Test
    public void question17() throws IOException {
        try (var reader = new FileReader("src/test/resources/question17.txt")) {
            var sb = new StringBuilder();
            sb.append((char) reader.read());
            if (reader.markSupported()) {
                reader.mark(10);            // Throws IOException if the stream does not support mark().
                for (int i = 0; i < 2; i++) {
                    sb.append((char) reader.read());
                    reader.skip(2);
                }
                reader.reset();
                reader.skip(0);
                sb.append((char) reader.read());
            }
            System.out.println("sb = " + sb.toString());
            Assert.assertTrue(true);
        }
    }

    /**
     * Given the following method, which statements are correct?
     *
     * C. The code compiles and correctly copies the data between some files.
     * E. If we check file2 in line n1 within the file system after five iterations of the while loop, it may be empty.
     * G. This method contains a resource leak.
     */
    @Test
    public void question19() throws IOException {
        var reader = new InputStreamReader(new FileInputStream("pom.xml"));
        try (var writer = new FileWriter("target/question19-out.txt")) {
            char[] buffer = new char[10];
            while(reader.read(buffer) != -1) {
                writer.write(buffer);
                // n1
            }
        }
        Assert.assertTrue(true);
    }
}
