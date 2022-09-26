package com.hiperium.java.cert.prep.chapter._19;

import java.io.*;

/**
 * Common I/O stream methods:
 *
 * *******************|**********************************|**********************************************************|
 * STREAM CLASS       | METHOD NAME                      | DESCRIPTION                                              |
 * *******************|**********************************|**********************************************************|
 * All streams        | void close()                     | Closes stream and releases resources.                    |
 * -------------------|----------------------------------|----------------------------------------------------------|
 * All input streams  | int read()                       | Reads 1 byte or returns ‐1 if no bytes were available.   |
 * -------------------|----------------------------------|----------------------------------------------------------|
 * InputStream        | int read(byte[] b)               |                                                          |
 * -------------------|----------------------------------| Reads values into a buffer. Returns number of bytes read.|
 * Reader             | int read(char[] c)               |                                                          |
 * -------------------|----------------------------------|----------------------------------------------------------|
 * InputStream        | int read(byte[] b, int offset,   |                                                          |
 *                    |     int length)                  | Reads up to length values into a buffer starting from    |
 * -------------------|----------------------------------| position offset. Returns number of bytes read.           |
 * Reader             | int read(char[] c, int offset,   |                                                          |
 *                    |     int length)                  |                                                          |
 * -------------------|----------------------------------|----------------------------------------------------------|
 * All output streams | void write(int)                  | Writes a single byte.                                    |
 * -------------------|----------------------------------|----------------------------------------------------------|
 * OutputStream       | void write(byte[] b)             |                                                          |
 * -------------------|----------------------------------| Writes an array of values into the stream.               |
 * Writer             | void write(char[] c)             |                                                          |
 * -------------------|----------------------------------|----------------------------------------------------------|
 * OutputStream       | void write(byte[] b, int offset, |                                                          |
 *                    |     int length)                  | Writes length values from an array into the stream,      |
 * -------------------|----------------------------------| starting with an offset index.                           |
 * Writer             | void write(char[] c, int offset, |                                                          |
 *                    |     int length)                  |                                                          |
 * -------------------|----------------------------------|----------------------------------------------------------|
 * All input streams  | boolean markSupported()          | Returns true if the stream class supports mark().        |
 * -------------------|----------------------------------|----------------------------------------------------------|
 * All input streams  | mark(int readLimit)              | Marks the current position in the stream.                |
 * -------------------|----------------------------------|----------------------------------------------------------|
 * All input streams  | void reset()                     | Attempts to reset the stream to the mark() position.     |
 * -------------------|----------------------------------|----------------------------------------------------------|
 * All input streams  | long skip(long n)                | Reads and discards a specified number of characters.     |
 * -------------------|----------------------------------|----------------------------------------------------------|
 * All output streams | void flush()                     | Flushes buffered data through the stream.                |
 * -------------------|----------------------------------|----------------------------------------------------------|
 */
public class CommonIOStreamOperations {

    public static final String FILE_NAME = "misc/chapter_19/test_stream.txt";
    public static final String IS_READ_LABEL = "is.read() = ";

    public static void main(String[] args) {
        try (var fr = new FileReader(FILE_NAME); var is = new FileInputStream(FILE_NAME)) {
            accessReaderCharacters(fr);
            accessStreamCharacters(is);
            markAndReset(is);
            skipValues(is);
            flushData();
        } catch (IOException e) {
            System.out.println("Error processing file: " + e.getMessage());
        }
    }

    private static void accessReaderCharacters(Reader in) throws IOException {
        int b;
        while ((b = in.read()) != -1) {
            System.out.println("Reader data (int) = " + b);
        }
    }

    private static void accessStreamCharacters(InputStream in) throws IOException {
        int b;
        while ((b = in.read()) != -1) {
            System.out.println("InputStream data (int) = " + b);
        }
    }

    /**
     * Assume that we have an InputStream instance whose next values are LION. The code will output LIOION if mark() is
     * supported, and LION otherwise. It's a good practice to organize your read() operations so that the stream ends up
     * at the same position regardless of whether mark() is supported.
     *
     * What about the value of 100 we passed to the mark() method? This value is called the readLimit. It instructs the
     * stream that we expect to call reset() after at most 100 bytes. If our program calls reset() after reading more
     * than 100 bytes from calling mark(100), then it may throw an exception, depending on the stream class.
     *
     * NOTE: In actuality, mark() and reset() are not really putting the data back into the stream but storing the data
     * in a temporary buffer in memory to be read again. Therefore, you should not call the mark() operation with too
     * large a value, as this could take up a lot of memory.
     */
    private static void markAndReset(InputStream is) throws IOException {
        System.out.println("*** Mark and Reset ***");
        System.out.println(IS_READ_LABEL + (char) is.read());      // PRINT: L
        if (is.markSupported()) {
            is.mark(100);
            System.out.println(IS_READ_LABEL + (char) is.read());  // PRINT: I
            System.out.println(IS_READ_LABEL + (char) is.read());  // PRINT: O
            is.reset(); // Reset stream position to before 'I'.
        }
        System.out.println(IS_READ_LABEL + (char) is.read());  // PRINT: I
        System.out.println(IS_READ_LABEL + (char) is.read());  // PRINT: O
        System.out.println(IS_READ_LABEL + (char) is.read());  // PRINT: N
    }

    /**
     * Assume that we have an InputStream instance whose next values are LION. This code prints LN at runtime. We
     * skipped two characters, I and O. The return parameter of skip() tells us how many values were actually skipped.
     * For example, if we are near the end of the stream and call skip(100), the return value might be 20, indicating
     * the end of the stream was reached after 20 values were skipped. Using the return value of skip() is important if
     * you need to keep track of where you are in a stream and how many bytes have been processed.
     */
    private static void skipValues(InputStream is) throws IOException {
        System.out.println("*** Skip ***");
        System.out.println(IS_READ_LABEL + (char) is.read());      // PRINT: L
        is.skip(2);                                             // Skips 'I' and 'O'
        System.out.println(IS_READ_LABEL + (char) is.read());      // PRINT: N
    }

    /**
     * When data is written to an output stream, the underlying operating system does not guarantee that the data will
     * make it to the file system immediately. In many operating systems, the data may be cached in memory, with write
     * occurring only after a temporary cache is filled or after some amount of time has passed.
     *
     * If the data is cached in memory and the application terminates unexpectedly, the data would be lost, because it
     * was never written to the file system. To address this, all output stream classes provide a flush() method, which
     * requests that all accumulated data be written immediately to disk.
     *
     * But, each time it is used, it may cause a noticeable delay in the application, especially for large files. Unless
     * the data that you are writing is extremely critical, the flush() method should be used only intermittently. For
     * example, it should not necessarily be called after every write.
     *
     * NOTE: You also do not need to call the flush() method when you have finished writing data, since the close()
     * method will automatically do this.
     */
    private static void flushData() throws IOException {
        System.out.println("*** Flush ***");
        try (var fos = new FileOutputStream(FILE_NAME, true)) {
            for (int i = 0; i < 25; i++) {
                fos.write('a');
                if (i % 20 == 0)
                    fos.flush();
            }
        }
    }
}
