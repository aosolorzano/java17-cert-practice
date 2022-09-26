import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.AccessControlException;

/**
 * To run this program, we must do the following:
 *      1. Copy the file "java.policy" in your home directory as ".java.policy"
 *      2. Compile this class using "javac". Do not run this class directly with "java" command.
 *      2. Use flag: -Djava.security.manager to enable the security manager at runtime.
 *
 * QUESTION 21:
 * Which statements about executing the following program are correct?
 *
 * C. The code is not susceptible to tainted inputs from the user.
 * E. The code is not susceptible to an injection attack.
 */
public class Question21Test {

    public static void main(String[] args) {
        SecurityManager sm = System.getSecurityManager();
        System.out.println("Security Manager is " + (sm == null? "disabled." : "enabled."));
        try {
            String property = System.getProperty("12345");
            System.out.println("property = " + property);
        } catch (AccessControlException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("Trying to get property with Provileged Action...");
            String score = PrintScores.getScores("12345");
            System.out.println("Property value = " + score);
        }
    }
    /**
     * NOTE: Since a constant SCORES is used to read the system properties on line "m2", rather than user provided input,
     * the code is safe from tainted inputs from the user. Furthermore, the code validates its inputs enough that an
     * injection attack is not possible.
     */
    private static class PrintScores {
        private static final String CODE = "12345";
        private static final String SCORES = "test.scores";

        public static String getScores(String accessCode) {
            return AccessController.doPrivileged(new PrivilegedAction<String>() {
                public String run() {
                    if(accessCode.equals(CODE))                                     // m1
                        return System.getProperty(SCORES);                          // m2
                    throw new SecurityException("Incorrect code.");
                }
            });
        }
    }
}