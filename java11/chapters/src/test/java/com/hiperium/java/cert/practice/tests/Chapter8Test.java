package com.hiperium.java.cert.practice.tests;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * QUESTION 30
 */
class SearchList<T> {
    private List<T> data;
    private boolean foundMatch = false;

    public SearchList(List<T> data) {
        this.data = data;
    }
    public void exists(T v, int start, int end) {
        if (end - start == 0) {}
        else if (end - start == 1) {
            foundMatch = foundMatch || v.equals(data.get(start));
        } else {
            final int middle = start + (end - start) / 2;
            System.out.println("middle = " + middle);
            new Thread(() -> exists(v, start, middle)).run();
            new Thread(() -> exists(v, middle, end)).run();
        }
    }
    public static void main() {
        List<Integer> data = List.of(1,2,3,4,5,6);
        SearchList<Integer> t = new SearchList<Integer>(data);
        t.exists(5, 0, data.size());
        System.out.println("t.foundMatch = " + t.foundMatch);
    }
}

/**
 * CONCURRENCY:
 *      - Create worker threads using Runnable and Callable, and manage concurrency using an ExecutorService and
 *        java.util.concurrent API.
 *      - Develop thread‐safe code, using different locking mechanisms and java.util.concurrent API.
 *
 * scheduleAtFixedRate(): creates a new task for the associated action at a set time interval, even if previous tasks
 * for the same action are still active. In this manner, it is possible multiple threads working on the same action
 * could be executing at the same time.
 *
 * scheduleWithFixedDelay(): waits until each task is completed before scheduling the next task, guaranteeing at most
 * one thread working on the action is active in the thread pool.
 *
 * Future.get(): Waits if necessary for the computation to complete, and then retrieves its result.
 *
 * shutdown(): Initiates an orderly shutdown in which previously submitted tasks are executed, but no new tasks will be
 * accepted. This method does not wait for previously submitted tasks to complete execution.
 * The shutdown() method prevents new tasks from being added but allows existing tasks to finish. In addition to
 * preventing new tasks from being added, the "shutdownNow()" method also attempts to stop all running tasks.
 *
 * Neither of these methods guarantees any task will be stopped.
 *
 * -> Resource starvation is when a single active thread is perpetually unable to gain access to a shared resource.
 * -> Livelock is a special case of resource starvation, in which 2 or more active threads are unable to gain access to
 *    shared resources, repeating the process over and over again.
 * -> Race condition is an undesirable result when 2 tasks that should be completed sequentially, are completed at the
 *    same time.
 */
public class Chapter8Test {

    /**
     * What is the output of the following code snippet?
     */
    @Test
    public void question1() throws ExecutionException, InterruptedException {
        Callable c = new Callable() {       // ERROR: Inner class must implement abstract method 'call()' in 'Callable'.
            public Object run() {
                System.out.print("X");
                return 10;
            }
            @Override
            public Object call() throws Exception {
                System.out.print("X ");
                return 10;
            }
        };
        var s = Executors.newScheduledThreadPool(1);
        for(int i=0; i<10; i++) {
            Future f = s.submit(c);
            f.get();
        }
        s.shutdown();
        System.out.print("Done!");
        Assert.assertTrue(true);
    }

    /**
     * The following program simulates flipping a coin an even number of times. Assuming five seconds is enough time for
     * all the tasks to finish, what is the output of the following application?
     *
     * F. The output cannot be determined ahead of time.
     *
     * NOTE: Even though the thread‐safe AtomicBoolean is used, it is not used in a thread‐safe manner.
     *
     * The flip() method first retrieves the value and then sets a new value. These two calls are not executed together
     * in an atomic or synchronized manner. For this reason, the output could be true or false, with one or more of the
     * flips possibly being lost.
     */
    private AtomicBoolean coin = new AtomicBoolean(false);
    void flip() {
        coin.getAndSet(!coin.get());
    }
    @Test
    public void question3() throws Exception {
        var luck = new Chapter8Test();
        ExecutorService s = Executors.newCachedThreadPool();
        for (int i = 0; i < 1000; i++) {
            // s.execute(() -> luck.flip());    Lambda can be replaced with method reference
            s.execute(luck::flip);
        }
        s.shutdown();
        Thread.sleep(2000);
        System.out.println("Coin value: " + luck.coin.get());
        Assert.assertTrue(true);
    }

    /**
     * Given the following program, how many times is Locked! expected to be printed? Assume 100 milliseconds is enough
     * time for each task created by the program to complete.
     *
     * A. One time.
     *
     * NOTE: The lockUp() method actually contains two calls to lock the object and only one call to unlock it. For this
     * reason, the first thread to reach tryLock() obtains a lock that is never released. For this reason, Locked! is
     * printed only once.
     */
    private Lock lock = new ReentrantLock();
    public void lockUp() {
        if (lock.tryLock()) {
            // lock.lock();                 Fixing the problem.
            System.out.println("Locked!");
            lock.unlock();
        }
    }
    @Test
    public void question5() throws Exception {
        // var gate = new Chapter8Test();
        for(int i=0; i<5; i++) {
            // new Thread(() -> gate.lockUp()).start();
            new Thread(this::lockUp).start();
            Thread.sleep(100);
        }
        Assert.assertTrue(true);
    }

    /**
     * Given the original array, how many of the following for statements result in an exception at runtime, assuming
     * each is executed independently?
     */
    @Test
    public void question6() {
        var original = List.of(1,2,3,4,5);

        var copy1 = new CopyOnWriteArrayList<Integer>(original);
        for(Integer w : copy1)
            copy1.remove(w);
        /*
         * This code generates ConcurrentModificationException at runtime:
         *
         * var copy2 = Collections.synchronizedList(original);
         * for(Integer w : copy2)
         *   copy2.remove(w);
         *
         * var copy3 = new ArrayList<Integer>(original);
         * for(Integer w : copy3)
         *   copy3.remove(w);
         */
        var copy4 = new ConcurrentLinkedQueue<Integer>(original);
        for(Integer w : copy4)
            copy4.remove(w);
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following application?
     *
     * B. "1 null"
     */
    public void submitReports() throws ExecutionException, InterruptedException {
        var s = Executors.newCachedThreadPool();
        Future bosses = s.submit(() -> System.out.println("1"));
        s.shutdown();
        // System.out.println(bosses.get());    ERROR: Unhandled exceptions: InterruptedException and ExecutionException.
        System.out.println("bosses.get() = " + bosses.get());
    }
    @Test
    public void question8() throws ExecutionException, InterruptedException {
        this.submitReports();
        Assert.assertTrue(true);
    }

    /**
     * How many times does the following application print Ready at runtime?
     *
     * A. Zero.
     *
     * NOTE: The application defines a thread executor with a single thread and 12 submitted tasks. Because only 1
     * thread is available to work at a time, the first thread will wait endlessly on the call to await(). Since the
     * CyclicBarrier requires 4 threads to release it, the application waits endlessly in a frozen condition. Since the
     * barrier is never reached and the code hangs, the application will never output Ready. If "newCachedThreadPool()"
     * had been used instead of "newSingleThreadExecutor()", then the barrier would be reached three times.
     */
    private void await(CyclicBarrier c) {
        try {
            c.await();
        } catch (Exception ignored) {}
    }
    public void march(CyclicBarrier c) {
        // var s = Executors.newSingleThreadExecutor();             This executor causes the code to hang.
        var s = Executors.newCachedThreadPool();
        for (int i = 0; i < 12; i++) {
            s.execute(() -> await(c));
        }
        s.shutdown();
    }
    @Test
    public void question10() {
        this.march(new CyclicBarrier(4, () -> System.out.println("Ready")));
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following application?
     *
     * F. The output cannot be determined ahead of time.
     *
     * NOTE: Both ExecutorService methods operate synchronously, waiting for a result from one or more tasks, but each
     * method call has been submitted to the thread executor as an asynchronous task. For this reason, both methods will
     * take about one second to complete, and since either can finish first, the output will vary at runtime,
     */
    boolean finished = false;
    ExecutorService service = Executors.newFixedThreadPool(8);
    public static int sleep() {
        try { Thread.sleep(1000);
        } catch (Exception ignored) {}
        return 1;
    }
    public void hare() {
        try {
            Callable<Integer> c = () -> sleep();
            final var r = List.of(c, c, c);
            var results = service.invokeAll(r);
            System.out.println("Hare won the race!!");
            this.finished = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void tortoise() {
        try {
            Callable<Integer> c = () -> sleep();
            final var r = List.of(c, c, c);
            Integer result = service.invokeAny(r);
            System.out.println("Tortoise won the race!!");
            this.finished = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void question13() throws InterruptedException {
        this.service.execute(this::hare);
        this.service.execute(this::tortoise);
        while (!this.finished) {                // Added for testing purposes.
            Thread.sleep(500);
        }
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following application?
     *
     * C.[Filing]null 3.14159
     *
     * NOTE: The call "f1.get()" waits until the task is finished and always return null, since Runnable expressions
     * have a void return type.
     */
    public static void completePaperwork() {
        System.out.print("[Filing]");
    }
    public static double getPi() {
        return 3.14159;
    }
    @Test
    public void question15() throws ExecutionException, InterruptedException {
        ExecutorService x = Executors.newSingleThreadExecutor();
        Future<?> f1 = x.submit(Chapter8Test::completePaperwork);
        Future<Object> f2 = x.submit(Chapter8Test::getPi);
        System.out.println(f1.get() +  " " + f2.get());
        x.shutdown();
        Assert.assertTrue(true);
    }

    /**
     * Assuming 10 sec is enough time for all tasks to finish, what statements about the following program are correct?
     *
     * C. The incrementBy10() method is not thread-safe
     * D. The output is 1000 on every execution.
     *
     * NOTE: While an AtomicLong is used, there are two calls on this variable, the first to retrieve the value and the
     * second to set the new value. These two calls are not executed together in an atomic or synchronized manner. For
     * this reason, the incrementBy10() method is not thread‐safe.
     *
     * That said, the code performs in single‐threaded manner at runtime because the call to "get()" method waits for
     * each thread to finish. For this reason, the output is consistently 1000.
     *
     */
    private AtomicLong bigHand = new AtomicLong(0);
    void incrementBy10() {
        bigHand.getAndSet(bigHand.get() + 10);
    }
    @Test
    public void question16() throws ExecutionException, InterruptedException {
        var smartWatch = new Chapter8Test();
        ExecutorService s = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            s.submit(smartWatch::incrementBy10).get();
        }
        s.shutdown();
        s.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("bigHand = " + smartWatch.bigHand.get());
        Assert.assertTrue(true);
    }

    /**
     * What is the output of the following application?
     *
     * F. None of the above. The result is unknown until runtime.
     *
     * NOTE: The issue here is that the "question19()" method, reads the value of "getStroke()" while tasks may still be
     * executing within the ExecutorService. The "shutdown()" method stops new tasks from being submitted but does not
     * wait for previously submitted tasks to complete. Therefore, the result may output 0, 1000, or anything in between.
     *
     * If the ExecutorService method "awaitTermination()" is called before the value of stroke is printed and enough
     * time elapses, then the result would be 1000 every time.
     */
    int stroke = 0;
    public synchronized void swimming() {
        stroke++;
    }
    private int getStroke() {
        synchronized (this) { return stroke; }
    }
    @Test
    public void question19() throws InterruptedException {
        ExecutorService s = Executors.newFixedThreadPool(10);
        Chapter8Test a = new Chapter8Test();
        for (int i = 0; i < 1000; i++) {
            s.execute(a::swimming);
        }
        s.shutdown();
        s.awaitTermination(10, TimeUnit.SECONDS);   // Adding for testing purposes.
        System.out.println("stroke = " + a.getStroke());
        Assert.assertTrue(true);
    }

    /**
     * How many lines of the following method contain compilation errors?
     *
     * C. Two
     */
    @Test(expected = NullPointerException.class)
    public void question23() throws InterruptedException {
        ScheduledExecutorService t = Executors.newSingleThreadScheduledExecutor();
        // Future result = t.execute(System.out::println);      ERROR >>> Required type: Future, Provided: void.
        t.execute(System.out::println);
        t.invokeAll(null);                                 // Throw NPE if tasks or any of its elements are null.
        // t.scheduleAtFixedRate(() -> { return; },             ERROR >>> 1 parameter missing.
        //        5, TimeUnit.MINUTES);
        t.scheduleAtFixedRate(() -> { return; },
                5, 5, TimeUnit.MINUTES);
    }

    /**
     * How many times does the following application print "W" at runtime?
     *
     * B. 10
     *
     * NOTE: The application defines a CyclicBarrier with a barrier limit of 5 threads. The application then submits 12
     * tasks to a cached executor service. In this scenario, a cached thread executor will use between 5 and 12 threads,
     * reusing existing threads as they become available. In this manner, there is no worry about running out of available
     * threads. The barrier will then trigger twice, printing five values for each of the sets of threads, for a total
     * of 10 "W" characters.
     *
     * The application does not terminate successfully nor produce an exception at runtime. It hangs at runtime because
     * the CyclicBarrier limit is 5, while the number of tasks submitted and awaiting activation is 12. This means that
     * two of the tasks will be left over, stuck in a deadlocked state, waiting for the barrier limit to be reached but
     * with no more tasks available to trigger it.
     *
     * If the number of tasks was a multiple of the barrier limit, such as 15 instead of 12, then the application will
     * still hang because the ExecutorService is never shut down. The "isShutdown()" call in the application finally
     * block does not trigger a shutdown.
     */
    private void waitTillFinished(int task, CyclicBarrier c) {
        System.out.println("BEGIN - task: " + task);
        try {
            c.await();
            Thread.sleep(1000);         // For testing purposes.
            System.out.println("W >>> finalized task: " + task);
        } catch (Exception ignored) {}
    }
    public void row(ExecutorService s) {
        var cb = new CyclicBarrier(5);
        IntStream.iterate(1, i -> i+1)
                .limit(12)
                .parallel()             // Test: causes all tasks submitted at once, before thread pull invoke any task.
                .forEach(i -> {
                    System.out.println("Submitting task = " + i);
                    s.submit(() -> waitTillFinished(i, cb));
                });
    }
    @Test
    public void question24() throws InterruptedException {
        System.out.println("BEGIN");
        ExecutorService service = null;
        try {
            service = Executors.newCachedThreadPool();
            this.row(service);
        } finally {
            // service.isShutdown();    For testing purposes.
            System.out.println("Waiting for all tasks finished...");
            service.awaitTermination(1, TimeUnit.SECONDS);
            System.out.println("Shutting down the service...");
            if (!service.isShutdown()) {
                service.shutdown();
            }
        }
        Assert.assertTrue(true);
    }

    /**
     * Given the following program, how many times is "TV Time" expected to be printed? Assume 10 seconds is enough time
     * for each task created by the program to complete.
     *
     * C. The code does not compile.
     */
    private static Lock myTurn = new ReentrantLock();
    public void watch(int task) {
        System.out.println("BEGIN - task: " + task);
        try {
            // if(myTurn.lock(5, TimeUnit.SECONDS))             ERROR: lock()' cannot be applied to '(int, TimeUnit)'.
            if(myTurn.tryLock(1, TimeUnit.SECONDS)) {
                System.out.println("TV Time!!");
                myTurn.unlock();
            }
        } catch (InterruptedException ignored) {}
    }
    @Test
    public void question26() throws InterruptedException {
        var newTv = this;
        for (int i = 0; i < 3; i++) {
            final int task = i;
            System.out.println("Submitting task = " + task);
            new Thread(() -> newTv.watch(task)).start();
            Thread.sleep(500);
        }
        Assert.assertTrue(true);
    }

    /**
     * Given the original array, how many of the following for statements enter an infinite loop at runtime, assuming
     * each is executed independently?
     */
    @Test
    public void question27() {
        var original = new ArrayList<Integer>(List.of(1,2,3));
        /*
         * This code generates ConcurrentModificationException at runtime:
         *
         * var copy1 = new ArrayList<Integer>(original);
         * for(Integer q : copy1)
         *   copy1.add(1);
         *
         * var copy4 = Collections.synchronizedList(original);
         * for(Integer q : copy4)
         *   copy4.add(4);
         */
        var copy2 = new CopyOnWriteArrayList<Integer>(original);
        for(Integer q : copy2)
            copy2.add(2);
        /*
         * This code generates the program to hang. <<< NOTE >>>: Each time a new value is inserted, the iterator is
         * updated, and the process repeat, producing an infinity loop.
         *
         * var copy3 = new LinkedBlockingQueue<Integer>(original);
         * for(Integer q : copy3)
         *   copy3.offer(3);
         */
        Assert.assertTrue(true);
    }

    /**
     * Assuming 10 sec is enough time for all tasks to finish, what statements about the following application?
     *
     * E. The result is unknown until runtime.
     *
     * NOTE: The class attempts to add and remove values from a single cookie variable in a thread‐safe manner but fails
     * to do so because the methods "deposit()" and "withdrawal()" synchronize on DIFFERENT OBJECTS. The instance method
     * "deposit()" synchronizes on the bank object, while the static method withdrawal() synchronizes on the static
     * "Bank.class" object. Since the compound assignment operators (+=) and (‐=) are not thread‐safe, it is possible
     * for one call to modify the value of cookies while the other is already operating on it, resulting in a loss of
     * information. For this reason, the output cannot be predicted.
     *
     * If the two methods were synchronized on the same object, then the cookies variable would be protected from
     * concurrent modifications, printing 0 at runtime.
     *
     */
    static int cookies = 0;
    public synchronized void deposit(int amount) {
        cookies += cookies;
    }
    public static synchronized void withdrawal(int amount) {
        cookies -= cookies;
    }
    @Test
    public void question29() throws InterruptedException {
        var teller = Executors.newScheduledThreadPool(50);
        var bank = this;
        for (int i = 0; i < 25; i++) {
            teller.submit(() -> bank.deposit(5));
            teller.submit(() -> bank.withdrawal(5));
        }
        teller.shutdown();
        teller.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("cookies = " + bank.cookies);
        Assert.assertTrue(true);
    }

    /**
     * What is the result of the following application?
     *
     * A. true.
     */
    @Test
    public void question30() {
        SearchList.main();
        Assert.assertTrue(true);
    }
}
