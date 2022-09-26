package com.hiperium.java.cert.prep.chapter._18;

import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Choosing an appropriate pool size requires some thought. In general, we want at least a handful more threads than we
 * think we will ever possibly need. On the other hand, we don't want to choose so many threads that our application uses
 * up too many resources or too much CPU processing power. Oftentimes, the number of CPUs available is used to determine
 * the thread pool size using this command:
 *
 *      Runtime.getRuntime().availableProcessors()
 *
 * It is a common practice to allocate threads based on the number of CPUs.
 *
 */
public class ThreadSafe {

    public static void main(String[] args) throws InterruptedException {
        nonThreadSafeOperation();
        atomicOperation();
        usingSynchronizedBlocks();
        synchronizedMethods();
        synchronizedStaticMethods();
        reentrantLock();
        reentrantLockWithTimeUnits();
        duplicateLockRequests();
        withoutCyclicBarrier();
        withCyclicBarrier();
    }

    /**
     * A thread pool is a group of pre‐instantiated reusable threads that are available to perform a set of arbitrary
     * tasks.
     *
     * EXECUTORS FACTORY METHOD:
     *
     * *************************************|***************************************************************************|
     * METHOD                               | DESCRIPTION                                                               |
     * *************************************|***************************************************************************|
     * ExecutorService                      | Creates a single‐threaded executor that uses a single worker thread       |
     * newSingleThreadExecutor()            | operating off an unbounded queue. Results are processed sequentially in   |
     *                                      | the order in which they are submitted.                                    |
     * -------------------------------------|---------------------------------------------------------------------------|
     * ScheduledExecutorService             | Creates a single‐threaded executor that can schedule commands to run after|
     * newSingleThreadScheduledExecutor()   | a given delay or to execute periodically.                                 |
     * -------------------------------------|---------------------------------------------------------------------------|
     * ExecutorService                      | Creates a thread pool that creates new threads as needed but will reuse   |
     * newCachedThreadPool()                | previously constructed threads when they are available.                   |
     * -------------------------------------|---------------------------------------------------------------------------|
     * ExecutorService                      | Creates a thread pool that reuses a fixed number of threads operating off |
     * newFixedThreadPool(int)              | a shared unbounded queue.                                                 |
     * -------------------------------------|---------------------------------------------------------------------------|
     * ScheduledExecutorService             | Creates a thread pool that can schedule commands to run after a given     |
     * newScheduledThreadPool(int)          | delay or to execute periodically.                                         |
     * *************************************|***************************************************************************|
     *
     */
    private int sheepCount = 0;

    private void incrementAndReport() {
        System.out.print((++sheepCount) + " ");
    }

    public static void nonThreadSafeOperation() {
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(20);
            ThreadSafe manager = new ThreadSafe();
            for(int i = 0; i < 10; i++)
                // service.submit(() -> manager.incrementAndReport());      We can use a lambda expression instead.
                service.submit(manager::incrementAndReport);
        } finally {
            if(Objects.nonNull(service))
                service.shutdown();
        }
    }

    /**
     * Atomic is the property of an operation to be carried out as a single unit of execution without any interference
     * by another thread. Any thread trying to access the sheepCount variable while an atomic operation is in process
     * will have to wait until the atomic operation on the variable is complete.
     *
     * NOTE: Since accessing primitives and references in Java is common in shared environments, the Concurrency API
     * includes numerous useful classes that are conceptually the same as our primitive classes but that support atomic
     * operations. Each class includes numerous methods that are equivalent to many of the primitive built‐in operators
     * that we use on primitives, such as the assignment operator (=) and the increment operators (++).
     *
     * COMMON ATOMIC METHODS:
     *
     * *****************************|*****************************************************************************|
     * METHOD NAME                  | DESCRIPTION                                                                 |
     * *****************************|*****************************************************************************|
     * get()                        | Retrieves the current value.                                                |
     * -----------------------------|-----------------------------------------------------------------------------|
     * set()                        | Sets the given value, equivalent to the assignment = operator.              |
     * -----------------------------|-----------------------------------------------------------------------------|
     * getAndSet()                  | Atomically sets the new value and returns the old value.                    |
     * -----------------------------|-----------------------------------------------------------------------------|
     * incrementAndGet()            | For numeric classes, atomic pre‐increment operation equivalent to ++value   |
     * -----------------------------|-----------------------------------------------------------------------------|
     * getAndIncrement()            | For numeric classes, atomic post‐increment operation equivalent to value++  |
     * -----------------------------|-----------------------------------------------------------------------------|
     * decrementAndGet()            | For numeric classes, atomic pre‐decrement operation equivalent to ‐‐value   |
     * -----------------------------|-----------------------------------------------------------------------------|
     * getAndDecrement()            | For numeric classes, atomic post‐decrement operation equivalent to value‐‐  |
     * -----------------------------|-----------------------------------------------------------------------------|
     *
     */
    private AtomicInteger sheepCountAtomic = new AtomicInteger(0);

    private void incrementAndReportAtomic() {
        System.out.print(sheepCountAtomic.incrementAndGet() + " ");
    }

    public static void atomicOperation() {
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(20);
            ThreadSafe manager = new ThreadSafe();
            for(int i = 0; i < 10; i++)
                service.submit(manager::incrementAndReportAtomic);
        } finally {
            if(Objects.nonNull(service))
                service.shutdown();
        }
    }

    /**
     * A monitor is a structure that supports mutual exclusion, which is the property that at most one thread is executing
     * a particular segment of code at a given time. In Java, any Object can be used as a monitor, along with the synchronized
     * keyword.
     *
     * This example is referred to as a synchronized block. Each thread that arrives will first check if any threads are
     * in the block. In this manner, a thread “acquires the lock” for the monitor. If the lock is available, a single
     * thread will enter the block, acquiring the lock and preventing all other threads from entering. While the first
     * thread is executing the block, all threads that arrive will attempt to acquire the same lock and wait for the
     * first thread to finish. Once a thread finishes executing the block, it will release the lock, allowing one of the
     * waiting threads to proceed.
     *
     */
    private int sheepCountSync = 0;

    private void incrementAndReportSync() {
        synchronized (this) {
            System.out.print((++sheepCountSync) + " ");
        }
    }

    public static void usingSynchronizedBlocks() {
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(20);
            ThreadSafe manager = new ThreadSafe();
            for(int i = 0; i < 10; i++)
                service.submit(manager::incrementAndReportSync);
        } finally {
            if(Objects.nonNull(service))
                service.shutdown();
        }
    }

    /**
     * We can add the synchronized modifier to any instance method to synchronize automatically on the object itself.
     */
    private synchronized void incrementAndReportSyncM() {
        System.out.print((++sheepCountSync) + " ");
    }

    private static void synchronizedMethods() {
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(20);
            ThreadSafe manager = new ThreadSafe();
            for(int i = 0; i < 10; i++)
                service.submit(manager::incrementAndReportSyncM);
        } finally {
            if(Objects.nonNull(service))
                service.shutdown();
        }
    }

    /**
     * We can also apply the synchronized modifier to static methods. What object is used as the monitor when we
     * synchronize on a static method? The class object of course:
     *
     *      synchronized(ThreadSafe.class) {
     *          System.out.print("Finished work");
     *      }
     *
     * NOTE: You can use static synchronization if you need to order thread access across all instances, rather than a
     * single instance.
     */
    private static int sheepCountSyncSM = 0;

    private static synchronized void incrementAndReportSyncSM() {
        System.out.print((++sheepCountSyncSM) + " ");
    }

    private static void synchronizedStaticMethods() {
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(20);
            for(int i = 0; i < 10; i++)
                // service.submit(() -> incrementAndReportSyncSM());        We can use a lambda expression instead.
                service.submit(ThreadSafe::incrementAndReportSyncSM);
        } finally {
            if(Objects.nonNull(service))
                service.shutdown();
        }
    }

    /**
     * Understanding the Lock Framework:
     *
     * The Concurrency API includes the Lock interface that is conceptually similar to using the synchronized keyword,
     * but with a lot more bells and whistles. Instead of synchronizing any Object, though, we can “lock” only an object
     * that implements the Lock interface.
     *
     * When you need to protect a piece of code from multi-threaded processing, create an instance of Lock that all
     * threads have access to. Each thread then calls lock() before it enters the protected code and calls unlock()
     * before it exits the protected code.
     *
     * The Lock solution has a number of features not available to the synchronized block.
     *
     * LOCK METHODS:
     *
     * *******************************|*******************************************************************************|
     * METHOD                         | DESCRIPTION                                                                   |
     * *******************************|*******************************************************************************|
     * void lock()                    | Requests a lock and blocks until lock is acquired.                            |
     * -------------------------------|-------------------------------------------------------------------------------|
     * void unlock()                  | Releases a lock.                                                              |
     * -------------------------------|-------------------------------------------------------------------------------|
     * boolean tryLock()              | Requests a lock and returns immediately. Returns a boolean indicating whether |
     *                                | the lock was successfully acquired.                                           |
     * -------------------------------|-------------------------------------------------------------------------------|
     * boolean tryLock(long,TimeUnit) | Requests a lock and blocks up to the specified time until lock is required.   |
     *                                | Returns a boolean indicating whether the lock was successfully acquired.      |
     * -------------------------------|-------------------------------------------------------------------------------|
     */
    public static void printMessage(Lock lock) {
        try {
            lock.lock();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * The Lock solution has a number of features not available to the synchronized block.
     *
     * ===>>> Implementation #1 with a synchronized block <<<===
     *
     * Object obj = new Object();
     * synchronized(obj) {
     *     [[ *** Protected code *** ]]
     * }
     *
     * ===>>> Implementation #2 with a Lock <<<===
     *
     * Lock lck = new ReentrantLock();
     * try {
     *    lck.lock();
     *    [[ *** Protected code *** ]]
     * } finally {
     *    lck.unlock();
     * }
     *
     * The ReentrantLock class ensures that once a thread has called "lock()" and obtained the lock, all other threads
     * that call "lock()" will wait until the first thread calls "unlock()".
     *
     * NOTE: Besides always making sure to release a lock, we also need to make sure that we only release a lock that
     * we actually have. If we attempt to release a lock that we do not have, we will get an exception at runtime.
     *
     * Lock lck = new ReentrantLock();
     * lck.unlock();                       // IllegalMonitorStateException
     *
     * While the ReentrantLock class allows us to wait for a lock, it so far suffers from the same problem as a
     * synchronized block. A thread could end up waiting forever to obtain a lock. Luckily, the last table includes two
     * additional methods that make the Lock interface a lot safer to use than a synchronized block; "tryLock()" and
     * "tryLock(long,TimeUnit)".
     *
     * The "tryLock()" method will attempt to acquire a lock and immediately return a boolean result indicating whether
     * the lock was obtained. Unlike the "lock()" method, it does not wait if another thread already holds the lock. It
     * returns immediately, regardless of whether a lock is available.
     */
    public static void reentrantLock() {
        Lock lock = new ReentrantLock();
        new Thread(() -> printMessage(lock)).start();
        // NOTE: It is imperative that your program always checks the return value of the "tryLock()" method. It tells
        // your program whether the lock needs to be released later.
        if (lock.tryLock()) {
            try {
                System.out.println("Lock obtained, entering protected mode.");
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("Unable to acquire lock, doing something else...");
        }
    }

    /**
     * The Lock interface includes an overloaded version of "tryLock(long,TimeUnit)". If a lock is available, then it
     * will immediately return with it. If a lock is unavailable, though, it will wait up to the specified time limit
     * for the lock.
     */
    public static void reentrantLockWithTimeUnits() throws InterruptedException {
        Lock lock = new ReentrantLock();
        new Thread(() -> printMessage(lock)).start();
        if (lock.tryLock(3, TimeUnit.SECONDS)) {
            try {
                System.out.println("Lock obtained, entering protected mode.");
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("Unable to acquire lock, doing something else...");
        }
    }

    /**
     * To release the lock for other threads to use, "unlock()" must be called the same number of times the lock was
     * granted. The following code snippet contains an error. Can you spot it? The thread obtains the lock twice but
     * releases it only once. You can verify this by spawning a new thread after this code runs that attempts to obtain
     * a lock.
     */
    public static void duplicateLockRequests() throws InterruptedException {
        Lock lock = new ReentrantLock();
        if (lock.tryLock(3, TimeUnit.SECONDS)) {
            try {
                lock.lock();
                System.out.println("Lock obtained, entering protected code.");
            } finally {
                lock.unlock();
            }
        } else { // NEVER ENTER HERE
            System.out.println("Unable to acquire lock, doing something else...");
        }
        new Thread(() -> System.out.println("Trying to acquire new lock: " + lock.tryLock())).start();
    }

    /**
     * Orchestrating Tasks with a CyclicBarrier:
     *
     * Our zoo workers are back, and this time they are cleaning pens. Imagine that there is a lion pen that needs to be
     * emptied, cleaned, and then filled back up with the lions. To complete the task, we have assigned 4 zoo workers.
     * Obviously, we don't want to start cleaning the cage while a lion is roaming in it. Furthermore, we don't want to
     * let the lions back into the pen while it is still being cleaned.
     *
     * We could have all the work completed by a single worker, but this would be slow and ignore the fact that we have
     * 2 zoo workers standing by to help. A better solution would be to have all 4 zoo employees work concurrently,
     * pausing between the end of one set of tasks and the start of the next.
     *
     */
    private void removeLions() { System.out.println("Removing lions..."); }
    private void cleanPen()    { System.out.println("Cleaning the pen..."); }
    private void addLions()    { System.out.println("Adding lions..."); }

    public void performTask() {
        removeLions();
        cleanPen();
        addLions();
    }

    public static void withoutCyclicBarrier() {
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(4);
            var manager = new ThreadSafe();
            for (int i = 0; i < 4; i++)
                // service.submit(() -> manager.performTask());     Lambda can be replaced with method reference.
                service.submit(manager::performTask);

        } finally {
            if (Objects.nonNull(service))
                service.shutdown();
        }
    }

    /**
     * Although within a single thread the results are ordered, among multiple workers the output is entirely random.
     * We see that some lions are still being removed while the cage is being cleaned, and other lions are added before
     * the cleaning process is finished. In our conceptual example, this would be quite chaotic and would not lead to a
     * very clean cage.
     *
     * We can improve these results by using the CyclicBarrier class. The CyclicBarrier takes in its constructors a
     * limit value, indicating the number of threads to wait for. As each thread finishes, it calls the "await()" method
     * on the cyclic barrier. Once the specified number of threads have each called "await()", the barrier is released,
     * and all threads can continue.
     */
    public void performTaskWithCB(CyclicBarrier c1, CyclicBarrier c2) {
        try {
            removeLions();
            c1.await();
            cleanPen();
            c2.await();
            addLions();
        } catch (BrokenBarrierException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * IMPORTANT: If you are using a thread pool, make sure that you set the number of available threads to be at least
     * as large as your CyclicBarrier limit value. For example, what if we changed the code in the follow method to
     * allocate only 2 threads?:
     *
     *      ExecutorService service = Executors.newFixedThreadPool(2);
     *
     * In this case, the code will hang indefinitely. The barrier would never be reached as the only threads available
     * in the pool are stuck waiting for the barrier to be complete.
     */
    public static void withCyclicBarrier() {
        ExecutorService service = null;
        try {
            service = Executors.newFixedThreadPool(4);
            var manager = new ThreadSafe();
            // In this example, we used two different constructors for our CyclicBarrier objects, the latter of which
            // called a Runnable method upon completion.
            var c1 = new CyclicBarrier(4);
            var c2 = new CyclicBarrier(4, () -> System.out.println("*** Pen Cleaned!"));
            for (int i = 0; i < 4; i++)
                service.submit(() -> manager.performTaskWithCB(c1, c2));
        } finally {
            if (Objects.nonNull(service))
                service.shutdown();
        }
    }
}
