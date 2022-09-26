package com.hiperium.java.cert.prep.chapter._18;

/**
 * Defining the task that a Thread instance will execute can be done two ways in Java:
 *
 * 1. Provide a Runnable object or lambda expression to the Thread constructor. This is the most common way.
 */
class PrintData implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 3; i++)
            System.out.println("Printing record: " + i);
    }
}

/**
 * 2. Create a class that extends Thread and overrides the run() method. We should extend the Thread class only under
 * specific circumstances, such as when we are creating our own priority‐based thread.
 */
class ReadInventoryThread extends Thread {
    @Override
    public void run() {
        System.out.println("Printing zoo inventory...");
    }
}

/**
 * NOTE: While previous versions of the exam were quite focused on understanding the difference between extending Thread
 * and implementing Runnable, the exam now strongly encourages developers to use the Concurrency API.
 *
 * We also do not need to know about other thread‐related methods, such as Object.wait(), Object.notify(), Thread.join(),
 * etc. In fact, we should avoid them in general, and use the Concurrency API as much as possible.
 */
public class ThreadBasics {

    private static int counter = 0;
    /**
     * This sample uses a total of 4 threads: the main() user thread, and 3 additional created threads. Each additional
     * thread is executed as an asynchronous task. It means that the thread executing the main() method does not wait
     * for the results of each newly created thread before continuing.
     */
    public static void main(String[] args) throws InterruptedException {
        System.out.println("begin");
        (new ReadInventoryThread())  .start();
        (new Thread(new PrintData())).start();
        (new ReadInventoryThread())  .start();
        System.out.println("end");
        pollingWithSleep();
    }

    /**
     * The Thread.sleep() method requests the current thread of execution rest for a specified number of milliseconds.
     * When used inside the body of the main() method, the thread associated with the main() method will pause, while
     * the separate thread will continue to run.
     */
    public static void pollingWithSleep() throws InterruptedException {
        new Thread(() -> {
            for (int i = 0; i < 500; i++)
                counter++;
        }).start();
        while (counter < 100) {
            System.out.println("Not reached yet...");
            Thread.sleep(1000);
        }
        System.out.println("Reached!!");
    }
}
