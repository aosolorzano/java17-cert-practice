package com.hiperium.java.cert.chapters.tests._18;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.*;

/**
 * QUESTION 2
 */
class Bank {
    private Lock vault = new ReentrantLock();
    private int total = 0;

    public void deposit(int value) {
        try {
            vault.tryLock();
            total += value;
        } finally {
            vault.unlock();
        }
    }
    public void depositWithLock(int value) {
        try {
            vault.lock();
            System.out.println("Lock obtained for value: " + value);
            total += value;
        } finally {
            vault.unlock();
        }
    }
    public void depositWithLock2(int value) {
        if (vault.tryLock()) {
            try {
                System.out.println("Lock obtained for value: " + value);
                total += value;
            } finally {
                vault.unlock();
            }
        } else {
            System.out.println("Unable to acquire lock for value: " + value + ". Doing something else...");
        }
    }
    public static void main() {
        var bank = new Bank();
        IntStream.range(1, 10).parallel()
        //      .forEach(bank::deposit);
                .forEach(bank::depositWithLock);
        //      .forEach(bank::depositWithLock2);
        System.out.println("Bank total: " + bank.total);
    }
}

/**
 * QUESTION 24:
 *
 * What statements about the following class definition are true?
 *
 * A. It compiles without issue.
 * F. At most one instance of TicketManager will be created in an application that uses this class.
 *
 * Since "getInstance()" is a static method and "sellTickets()" is an instance method, lines k1 and k4 synchronize on
 * different objects.
 *
 * The class is not thread-safe because the "addTickets()" method is not synchronized. For example, one thread could
 * call sellTickets() while another thread calls addTickets(). These methods are not synchronized with each other and
 * could cause an invalid number of tickets due to a race condition.
 *
 * Finally, option F is correct because the "getInstance()" method is synchronized. Since the constructor is private,
 * this method is the only way to create an instance of TicketManager outside the class. The first thread to enter the
 * method will set the instance variable, and all other threads will use the existing value.
 *
 */
class TicketManager {
    private int tickets;
    private static TicketManager instance;
    private TicketManager() {}
    static synchronized TicketManager getInstance() {           // k1
        if (instance==null) instance = new TicketManager();     // k2
        return instance;
    }
    public int getTicketCount() { return tickets; }
    public void addTickets(int value) { tickets += value; }     // k3
    public void sellTickets(int value) {
        synchronized (this) {                                   // k4
            tickets -= value;
        }
    }
}

/**
 * QUESTION 26
 *
 * NOTE: The "performCount()" method can also throw a runtime exception, which will then be thrown by the "get()" call
 * as an ExecutionException. Finally, it is also possible for "performCount()" to hang indefinitely, such as with a
 * deadlock or infinite loop. Luckily, the call to "get()" includes a timeout value. While each call to Future.get()
 * can wait up to a day for a result, it will eventually finish.
 *
 */
class CountZooAnimals {
    public static void performCount(int animal) {
        // IMPLEMENTATION OMITTED
    }
    public static void printResults(Future<?> f) {
        try { System.out.println(f.get(1, TimeUnit.DAYS));          // o1
        } catch (Exception e) { System.out.println("Exception!"); }
    }
    public static void main() throws Exception {
        ExecutorService s = null;
        final var r = new ArrayList<Future<?>>();
        try {
            s = Executors.newSingleThreadExecutor();
            for(int i = 0; i < 10; i++) {
                final int animal = i;
                r.add(s.submit(() -> performCount(animal)));            // o2
            }
            // r.forEach(f -> printResults(f));                         Lambda can be replaced with method reference.
            r.forEach(CountZooAnimals::printResults);
        } finally {
            if(s != null) s.shutdown();
        }
    }
}


public class Chapter18Test {

    /**
     * Given that the sum of the numbers from 1 (inclusive) to 10 (exclusive) is 45, what are the possible results of
     * executing the following program?
     *
     * R./ This code fails to check the return value of tryLock() method, resulting in the protected code being entered
     * regardless of whether the lock is obtained or not. In some executions (when tryLock() returns true on every call),
     * the code will complete successfully and print 45 at runtime. On other executions (when tryLock() returns false at
     * least once), the "unlock()" method will throw an IllegalMonitorStateException at runtime.
     */
    @Test
    public void question2() {
        Bank.main();
        Assert.assertTrue(true);
    }

    /**
     * Which lines need to be changed to make the code compile?
     */
    @Test
    public void question4() {
        // ExecutorService service = Executors.newSingleThreadScheduledExecutor();          w1: Change 'service' type.
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(() -> {
            System.out.println("Open Zoo.");
            // return null;                                                                 w2: Unexpected return value.
        }, 5, 10, TimeUnit.SECONDS);
        var result = service.submit(() -> {                                       // w3
            try {
                Thread.sleep(2000);
                System.out.println("Wake Staff.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // System.out.println(result.get());                                                w4: Unhandled exceptions
        try {
            System.out.println(result.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(true);
    }

    /**
     * What statement about the following code is true?
     *
     * C. The output cannot be determined ahead of time.
     *
     * The key here is that the increment operator ++ is not atomic. While the first part of the output will always be
     * 100, the second part is nondeterministic. It could output any value from 1 to 100, because the threads can
     * overwrite each other's work.
     */
    @Test
    public void question5() {
        var value1 = new AtomicLong(0);
        final long[] value2 = {0};
        IntStream.iterate(1, i -> 1).limit(100).parallel()
                .forEach(i -> value1.incrementAndGet());
        IntStream.iterate(1, i -> 1).limit(100).parallel()
                .forEach(i -> ++value2[0]);
        System.out.println(value1 + " -- " + value2[0]);
        Assert.assertTrue(true);
    }

    /**
     * Which statements about the following code are correct?
     *
     * C. The peek() method will print the entries in an order that cannot be determined ahead of time.
     * E. The forEachOrdered() method will print the entries in the order: 2 5 1 9 8.
     */
    @Test
    public void question6() {
        var data = List.of(2,5,1,9,8);
        data.stream().parallel()
                .mapToInt(s -> s)
        //      .peek(System.out::println)
                .peek(i -> System.out.println("Peek: " + i))
        //      .forEachOrdered(System.out::println);
                .forEachOrdered(i -> System.out.println("Ordered: " + i));
        Assert.assertTrue(true);
    }

    /**
     * Assuming this class is accessed by only a single thread at a time, what is the result of calling the question8()
     * method?
     *
     * A. The method looks like it executes a task concurrently, but it actually runs synchronously. In each iteration
     * of the forEach() loop, the process waits for the run() method to complete before moving on. For this reason, the
     * code is actually thread-safe. It executes a total of 499 times, since the second value of range() excludes the 500.
     *
     * Note that if start() had been used instead of run() (or the stream was parallel), then the output would be
     * indeterminate.
     */
    private static int counter;
    @Test
    public void question8()  {
        counter = 0;
        Runnable task = () -> counter++;
        LongStream.range(1, 500)
                .forEach(m -> new Thread(task).run());      // WARN: Calls to 'run()' should probably be 'start()'.
        System.out.println(counter);                        // PRINT: 499
        Assert.assertTrue(true);
    }

    /**
     * What is the result of executing the following code snippet?
     *
     * A. The CopyOnWriteArrayList class is designed to preserve the original list on iteration, so the first loop will
     * be executed exactly three times and, in the process, will increase the size of tigers to six elements.
     *
     * The ConcurrentSkipListSet class allows modifications, and since it enforces uniqueness of its elements, the value
     * 5 is added only once leading to a total of four elements in bears.
     *
     * Finally, despite using the elements of lions to populate the collections, tigers and bears are not backed by the
     * original list, so the size of lions is 3 throughout this program.
     */
    @Test
    public void question10() {
        List<Integer> lions = new ArrayList<>(List.of(1, 2, 3));
        List<Integer> tigers = new CopyOnWriteArrayList<>(lions);
        Set<Integer> bears = new ConcurrentSkipListSet<>();
        bears.addAll(lions);
        for (Integer item : tigers) tigers.add(4);      // x1
        for (Integer item : bears) bears.add(5);        // x2
        System.out.println(lions.size() + " " + tigers.size() + " " + bears.size());    // PRINT: 3 6 4
        Assert.assertTrue(true);
    }

    /**
     * What statements about the following code are true?
     *
     * F. The output cannot be determined ahead of time.
     *
     * There are two important things to notice. First, synchronizing on the first variable doesn't actually impact the
     * results of the code. Second, sorting on a parallel stream does not mean that findAny() will return the first
     * record.
     */
    @Test
    public void question11() {
        Integer i1 = List.of(1, 2, 3, 4, 5).stream()
                .findAny()
                .get();                                         // y1
        synchronized (i1) {
            Integer i2 = List.of(6,7,8,9,10).parallelStream()
                    .sorted()
                    .findAny()
                    .get();                                     // y2
            System.out.println(i1 + " - " + i2);
        }
        Assert.assertTrue(true);
    }

    /**
     * Assuming takeNap() is a method that takes five seconds to execute without throwing an exception, what is the
     * expected result of executing the following code snippet?
     *
     * B. It will pause for 2 seconds and then print DONE!.
     *
     * The awaitTermination() method waits a specified amount of time for all tasks to complete, and the service to
     * finish shutting down. Since each five-second task is still executing, the awaitTermination() method will return
     * with a value of false after two seconds but not throw an exception.
     */
    public void takeNap() {
        try { Thread.sleep(5000);
        } catch (InterruptedException e) { e.printStackTrace(); }
    }
    @Test
    public void question12() throws InterruptedException {
        System.out.println("BEGIN -> " + LocalDateTime.now().getSecond());
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(4);
            service.execute(() -> takeNap());                   // Lambda can be replaced with method reference.
            service.execute(this::takeNap);
            service.execute(this::takeNap);
        } finally {
            if (service != null) service.shutdown();
        }
        service.awaitTermination(2, TimeUnit.SECONDS);
        // NOTE: Here we can use -->> "if(service.isTerminated()){...}" to confirm that all tasks are completed.
        System.out.println("DONE! -> " + LocalDateTime.now().getSecond());
        Assert.assertTrue(true);
    }

    /**
     * What statements about the following code are true?
     */
    @Test
    public void question13() {
        System.out.print(List.of("duck", "flamingo", "pelican")
                .parallelStream().parallel()                        // q1: Allowed.
                .reduce(0,
                //      (c1, c2) -> c1.length() + c2.length(),         q2: Cannot resolve method 'length' in 'Integer'.
                        (c1, c2) -> c1 + c2.length(),
                        Integer::sum));  // (s1,s2) -> s1 + s2));      q3: This code always print 19.
        Assert.assertTrue(true);
    }

    /**
     * What statements about the following code snippet are true?
     *
     * C. If the code does output anything, the order cannot be determined.
     * E. The code compiles but may produce a deadlock at runtime.
     *
     * The key here is that the order in which the resources o1 and o2 are synchronized could result in a deadlock. For
     * example, if the first thread gets a lock on o1 and the second thread gets a lock on o2 before either thread can
     * get their second lock, then the code will hang at runtime.
     *
     * The code cannot produce a livelock, since both threads are waiting. Finally, if a deadlock does occur, an
     * exception will not be thrown, so option G is incorrect.
     */
    @Test
    public void question14() throws ExecutionException, InterruptedException {
        Object o1 = new Object();
        Object o2 = new Object();
        var service = Executors.newFixedThreadPool(2);
        var f1 = service.submit(() -> {
            synchronized (o1) {
                synchronized (o2) { System.out.println("Tortoise"); }
            }
        });
        var f2 = service.submit(() -> {
            synchronized (o2) {
                synchronized (o1) { System.out.println("Hare"); }
            }
        });
        f1.get();
        f2.get();
        Assert.assertTrue(true);
    }

    /**
     * Which statement about the following code snippet is correct?
     */
    @Test
    public void question15() {
        var cats = Stream.of("leopard", "lynx", "ocelot", "puma").parallel();
        var bears = Stream.of("panda", "grizzly", "polar").parallel();
        var data = Stream.of(cats, bears)
                .flatMap(s -> s)
                .collect(Collectors.groupingByConcurrent(s -> !s.startsWith("p")));
        System.out.println(data.get(false).size() + " " + data.get(true).size());       // PRINT: 3 4
        Assert.assertTrue(true);
    }

    /**
     * What is the result of calling the following method?
     *
     * D. The code will not compile.
     *
     * If InterruptedException was declared in the method signature, then the answer would be:
     *      F. It compiles but throws an exception at runtime.
     * Because adding items to the queue may be blocked at runtime. In this case, the queue is passed into the method,
     * so there could be other threads operating on it.
     *
     * Finally, if the operations were not blocked and there were no other operations on the queue, then the output
     * would be "103 20".
     *
     */
    public void addAndPrintItems(BlockingQueue<Integer> queue) throws InterruptedException {
        queue.offer(103);
        queue.offer(20, 1, TimeUnit.SECONDS);
        queue.offer(85, 7, TimeUnit.HOURS);
        System.out.print(queue.poll(200, TimeUnit.NANOSECONDS));    // PRINT: 103
        System.out.print(" " + queue.poll(1, TimeUnit.MINUTES));    // PRINT: 20
    }
    @Test
    public void question17() throws InterruptedException {
        var blockingQueue = new LinkedBlockingQueue<Integer>();
        addAndPrintItems(blockingQueue);
        Assert.assertTrue(true);
    }

    /**
     * What is the result of executing the following application?
     *
     * F. It compiles, but the output cannot be determined ahead of time.
     * H. It compiles but waits forever at runtime.
     *
     * The thread executor is never shutdown; therefore, the code will run, but it will never terminate, making option
     * H also correct.
     */
    @Test
    public void question19() {
        var s = Executors.newScheduledThreadPool(10);
        DoubleStream.of(3.14159, 2.71828)                       // b1
                .forEach(c -> s.submit(                         // b2
                        () -> System.out.println(10 * c)));     // b3: PRINT ==>> 27.1828 -- 31.4159
        s.execute(() -> System.out.println("Printed"));         // b4
        Assert.assertTrue(true);
    }

    /**
     * What is the result of executing the following application?
     */
    static int count = 0;
    @Test
    public void question20() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();
        try {
            var r = new ArrayList<Future<?>>();
            IntStream.iterate(0, (i -> i+1))
                    .limit(5)
            //      .forEach(i -> r.add(service.execute(() -> { count++; }))    n1: Required type: Future<?>, Provided: void.
                    .forEach(i -> r.add(service.submit(() -> { count++; })));
            for(Future<?> result : r) {
                System.out.print(result.get() + " ");                        // n2 -> PRINT: null null null null null
            }
        } finally {
            if(service != null) service.shutdown();
        }
        Assert.assertTrue(true);
    }

    /**
     * Given the following code snippet and blank lines on p1 and p2, which values guarantee 1 is printed at runtime?
     *
     * A. stream() at line p1, findFirst() at line p2.
     * D. parallelStream() in line p1, findFirst() at line p2.
     *
     * NOTE: The findFirst() method guarantees the first element in the stream will be returned, whether it is serial
     * or parallel.
     */
    @Test
    public void question21() {
        var data = List.of(List.of(1,2),
                List.of(3,4),
                List.of(5,6));
        data.parallelStream()                       // p1
        //      .flatMap(s -> s.stream())           Lambda can be replaced with method reference.
                .flatMap(Collection::stream)
                .findFirst()                        // p2
                .ifPresent(System.out::print);
        Assert.assertTrue(true);
    }

    /**
     * Assuming 100 milliseconds is enough time for the tasks submitted to the service executor to complete, what is the
     * result of executing the following method?
     *
     * B. The method consistently prints 100 100.
     *
     * NOTE: The key aspect to notice in the code is that a single-thread executor is used, meaning that no task will be
     * executed concurrently. Therefore, the results are valid and predictable with 100 100 being the output. If a pooled
     * thread executor was used with at least two threads, then the s2++ operations could overwrite each other, making
     * the second value indeterminate at the end of the program.
     *
     */
    private AtomicInteger s1 = new AtomicInteger(0);                // w1
    private int s2 = 0;
    @Test
    public void question22() throws InterruptedException {
        ExecutorService service = null;
        try {
            service = Executors.newSingleThreadExecutor();                  // w2
            for (int i = 0; i < 100; i++)
                service.execute(() -> { s1.getAndIncrement(); s2++; });     // w3
            Thread.sleep(1000);
            System.out.println(s1 + " " + s2);                              // PRINT: 100 100
        } finally {
            if(service != null) service.shutdown();
        }
        Assert.assertTrue(true);
    }

    /**
     * What is the result of executing the following application?
     *
     * F. It compiles but waits forever at runtime.
     */
    public static void await(CyclicBarrier cb) {                    // j1
        try { cb.await(); }
        catch (Exception ignored) {}
    }
    @Test
    public void question23() {
        var cb = new CyclicBarrier(10,
                () -> System.out.println("Stock Room Full!"));      // j2
        IntStream.iterate(1, i -> 1)
        //      .limit(9)                                           This limit causes that the program waits forever.
                .limit(10)
                .parallel()
                .forEach(i -> await(cb));                           // j3
        Assert.assertTrue(true);
    }

    /**
     * Assuming an implementation of the performCount() method is provided prior to runtime, which of the following are
     * possible results of executing the following application?
     *
     * C. It outputs a null value 10 times.
     * D. It outputs Exception! 10 times.
     */
    @Test
    public void question26() throws Exception {
        CountZooAnimals.main();
        Assert.assertTrue(true);
    }
}