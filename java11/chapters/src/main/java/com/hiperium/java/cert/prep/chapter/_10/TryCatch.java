package com.hiperium.java.cert.prep.chapter._10;

class MyCloseableFileClass implements AutoCloseable {
    private final int num;

    public MyCloseableFileClass(int num) {
        this.num = num;
    }

    @Override // WE EXCLUDE THE EXCEPTION
    public void close() { // We can override method without throwing an Exception
        System.out.println("Closing without exception: " + this.num);
    }
}

class MyFileClass implements AutoCloseable {
    private final int num;

    public MyFileClass(int num) {
        this.num = num;
    }

    @Override
    public void close() throws Exception {
        System.out.println("Closing: " + this.num);
    }
}

public class TryCatch {

    public static void main(String[] args) {
        try {
            System.out.println("Real work");
        }
        // catch (Exception e) {} ERROR: if we add this catch, RuntimeException catch will not be reached.
        // catch (IOException e) {} ERROR: IOException is never thrown in the corresponding try block
        catch (IllegalArgumentException e) {
        } catch (StackOverflowError e) {
        } catch (RuntimeException e) {
            System.out.println("Not good...");
        }
        tryWithResources();
        anotherTryWithResources();
    }

    /**
     * The resources are closed in the reverse order from which they are declared, and the implicit
     * finally is executed before the programmer-defined finally.
     */
    private static void tryWithResources() {
        try (var resource1 = new MyFileClass(1); var resource2 = new MyFileClass(2)) {
            throw new RuntimeException();
        } catch (Exception e) {
            System.out.println("Exception block...");
        } finally {
            System.out.println("Finally block...");
        }
    }

    private static void anotherTryWithResources() {
        try (var resource1 = new MyCloseableFileClass(1)) {
            System.out.println("Try-with-resource without implementing catch...");
        }
        try (var resource1 = new MyCloseableFileClass(2)) {
            throw new Error("We can throw java.lang.Error...");
        } catch (Error e) {  // We can add catch clauses.
            System.out.println(e);
            // throw e; ===>>> We can re-throw an Error, and it acts like a RuntimeException.
        }
    }
}
