import java.io.Console;
import java.util.Locale;
import java.util.Arrays;

public class JavaConsoleTest {

	/**
	 * The "readPassword()" methods are similar to the "readLine()" method with two important differences:
	 * 		- The text the user types is not echoed back and displayed on the screen as they are typing.
	 * 		- The data is returned as a char[] instead of a String.
	 * 	The first feature improves security by not showing the password on the screen if someone happens to be sitting
	 * 	next to you. The second feature involves preventing passwords from entering the String pool.
	 */
	public static void main(String[] args) {
		Console console = System.console();
		if (null == console) {
			System.err.println("Console is not available...");
		} else {
			String userInput = console.readLine("Please enter your name: ");
			console.writer().format("Hi %s%n", userInput);
			console.writer().format(new Locale("es", "EC"), "Welcome to our Zoo!!%n");
			console.format("It has %d animals and employs %d people.", 391, 25);
			console.writer().println();
			console.printf("The zoo spans %5.1f acres.%n", 128.91);
			char[] password = console.readPassword("Enter a password between %d and %d characters: ", 5, 10);
			char[] verify = console.readPassword("Enter the password again: ");
			console.printf("Passwords " + (Arrays.equals(password, verify) ? "match" : "do not match"));
		}
	}
}
