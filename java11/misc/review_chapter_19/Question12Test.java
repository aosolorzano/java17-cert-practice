import java.io.Console;
import java.io.Writer;
import java.io.IOException;

public class Question12Test {

    /**
     * What are possible results of executing the following code?
     *
     * E. The code does not compile. We must add throws IOException to method declaration.
     */
    public static void main(String[] args) throws IOException {
        String line;
        var c = System.console();
        Writer w = c.writer();
        try (w) {                                                   // ERROR: unreported exception IOException
            if ((line = c.readLine("Enter your name: ")) != null)
                w.append(line);                                     // ERROR: unreported exception IOException
            w.flush();                                              // ERROR: unreported exception IOException
        }
    }
}