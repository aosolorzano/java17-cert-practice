import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Question33Test {

    /**
     * Given the following application, in which the user enters "bone" twice, what is the expected result?
     *
     * A. The program completes after printing a message once.
     *
     * NOTE: The IO Stream "System.in" is closed at the end of the try-with-resources block. That means calling
     * "readLine()" again results in an operation on a closed stream, which would print an exception at runtime, but
     * "System.err" is closed too due the try-with-resources block. Therefore, only 1 message is printed.
     */
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        var retriever = new BufferedReader(new InputStreamReader(System.in));
        try (retriever; var husky = System.err) {
            var fetch = retriever.readLine();
            System.out.printf("%s fetched in %5.1f seconds", fetch, (System.currentTimeMillis() - start) / 1000.0);
        }
        var fetchAgain = retriever.readLine();              // Throws an exception at runtime due a closed resource.
        System.out.println(fetchAgain + " fetched again!");
    }
}