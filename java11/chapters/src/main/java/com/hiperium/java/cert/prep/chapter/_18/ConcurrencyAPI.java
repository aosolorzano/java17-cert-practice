package com.hiperium.java.cert.prep.chapter._18;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

/**
 *
 * EXECUTOR-SERVICE METHODS:
 *
 * The first method "execute()" is inherited from the Executor interface, which the ExecutorService interface extends.
 * The "execute()" method takes a Runnable lambda expression or instance and completes the task asynchronously.
 *
 * public interface Runnable {
 *    void run();
 * }
 *
 * Unlike execute(), "submit()" returns a Future instance that can be used to determine whether the task is complete. It
 * can also be used to return a generic result object after the task has been completed.
 *
 * *****************************************|***************************************************************************|
 * METHOD NAME                              | DESCRIPTION                                                               |
 * *****************************************|***************************************************************************|
 *                                          |                                                                           |
 * void execute(Runnable command)           | Executes a Runnable task at some point in the future.                     |
 * -----------------------------------------|---------------------------------------------------------------------------|
 * Future<?> submit(Runnable task)          | Executes a Runnable task at some point in the future and returns a Future |
 *                                          | representing the task.                                                    |
 * -----------------------------------------|---------------------------------------------------------------------------|
 * <T> Future<T> submit(Callable<T> task)   | Executes a Callable task at some point in the future and returns a Future |
 *                                          | representing the pending results of the task.                             |
 * -----------------------------------------|---------------------------------------------------------------------------|
 * <T> List<Future<T>>                      | Executes the given tasks and waits for all tasks to complete. Returns a   |
 *     invokeAll(Collection<? extends       | List of Future instances, in the same order they were in the original     |
 *     Callable<T>> tasks)                  | collection.                                                               |
 *     throws InterruptedException          |                                                                           |
 * -----------------------------------------|---------------------------------------------------------------------------|
 * <T> T invokeAny(Collection<? extends     | Executes the given tasks and waits for at least one to complete. Returns  |
 *     Callable<T>> tasks)                  | a Future instance for a complete task and cancels any unfinished tasks.   |
 *     throws InterruptedException,         |                                                                           |
 *     ExecutionException                   |                                                                           |
 * *****************************************|***************************************************************************|
 *
 * The "execute()" and "submit()" methods are nearly identical when applied to Runnable expressions. The "submit()"
 * method has the obvious advantage of doing the same thing "execute()" does, but with a return object that can be used
 * to track the result.
 */
public class ConcurrencyAPI {

    private static int counter = 0;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        singleThread();
        waitingForResults();
        usingCallable();
        allTasksFinished();
        taskCollections();
        schedulingTask();
    }

    /**
     * Unlike our earlier example, in which we had three extra threads for newly created tasks, this example uses only
     * one, which means that the threads will order their results.
     *
     * The 'shutdown()' method initiates an orderly shutdown in which previously submitted tasks are executed, but no new
     * tasks will be accepted. During this time, calling to 'isShutdown()' will return true, while 'isTerminated()' will
     * return false. If a new task is submitted to the thread executor while it is shutting down, a RejectedExecutionException
     * will be thrown. Once all active tasks have been completed, 'isShutdown()' and 'isTerminated()' will both return true.
     *
     * What if we want to cancel all running and upcoming tasks? The ExecutorService provides a method called 'shutdownNow()',
     * which attempts to stop all running tasks and discards any that have not been started yet. It is possible to create
     * a thread that will never terminate, so any attempt to interrupt it may be ignored. Lastly, 'shutdownNow()' returns
     * a List<Runnable> of tasks that were submitted to the thread executor but that were never started.
     *
     */
    public static void singleThread() {
        Runnable task1 = () -> System.out.println("Printing zoo inventory");
        Runnable task2 = () -> {
          for (int i = 0; i < 3; i++) {
              System.out.println("Printing record: " + i);
          }
        };
        ExecutorService service = null;
        try {
            service = Executors.newSingleThreadExecutor();
            System.out.println("begin");
            service.execute(task1);
            service.execute(task2);
            service.execute(task1);
            System.out.println("end");
        } finally {
            if (Objects.nonNull(service)) {
                service.shutdown();
            }
        }
    }

    /**
     *
     * How do we know when a task submitted to an ExecutorService is complete? As mentioned in the previous section, the
     * 'submit()' method returns a "java.util.concurrent.Future<V>" instance that can be used to determine this result.
     *
     * Future Methods:
     *
     * *****************************|************************************************************************************|
     * Method name                  | Description                                                                        |
     * -----------------------------|------------------------------------------------------------------------------------|
     * boolean isDone()             | Returns true if the task was completed, threw an exception, or was cancelled.      |
     * -----------------------------|------------------------------------------------------------------------------------|
     * boolean isCancelled()        | Returns true if the task was cancelled before it completed normally.               |
     * -----------------------------|------------------------------------------------------------------------------------|
     * boolean cancel(boolean       | Attempts to cancel execution of the task and returns true if it was                |
     *     mayInterruptIfRunning)   | successfully cancelled or false if it could not be cancelled or is complete.       |
     * -----------------------------|------------------------------------------------------------------------------------|
     * V get()                      | Retrieves the result of a task, waiting endlessly if it is not yet available.      |
     * -----------------------------|------------------------------------------------------------------------------------|
     * V get(long timeout,          | Retrieves the result of a task, waiting the specified amount of time. If the       |
     *     TimeUnit unit)           | result is not ready by the time the timeout is reached, a checked TimeoutException |
     *                              | will be thrown.                                                                    |
     * -----------------------------|------------------------------------------------------------------------------------|
     *
     * The following is an updated version of our earlier polling example, which uses a Future instance to wait for the
     * results.
     */
    public static void waitingForResults() throws ExecutionException, InterruptedException {
        ExecutorService service = null;
        try {
            service = Executors.newSingleThreadExecutor();
            // Here we are using the Runnable interface.
            Future<?> result = service.submit(() -> {
                for (int i = 0; i < 500; i++)
                    counter++;
            });
            // Waits at most 10 seconds, throwing a TimeoutException if the task is not done.
            result.get(10, TimeUnit.MILLISECONDS);
            System.out.println("Reached!! ==>> " + counter);
        } catch (TimeoutException e) {
            System.out.println("Not reached at time...");
        } finally {
            if (Objects.nonNull(service))
                service.shutdown();
        }
    }

    /**
     * The "Callable" interface is often preferable over "Runnable", since it allows more details to be retrieved easily
     * from the task after it is completed.
     *
     * We use both interfaces throughout as they are interchangeable in situations where the lambda does not throw an
     * exception and there is no return type.
     *
     * Luckily, the ExecutorService includes an overloaded version of "submit()" method that takes a Callable object and
     * returns a generic Future<T> instance.
     *
     * public interface Callable<V> {
     *      V call() throws Exception;
     * }
     */
    public static void usingCallable() throws ExecutionException, InterruptedException {
        ExecutorService service = null;
        try {
            service = Executors.newSingleThreadExecutor();
            // We are using the "Callable" interface.
            Future<Integer> result = service.submit(() -> {
                for (int i = 0; i < 250; i++)
                    counter++;
                return counter;
            });
            System.out.println("Callable result: " + result.get());
        } finally {
            if (Objects.nonNull(service))
                service.shutdown();
        }
    }

    /**
     * First, we shut down the thread executor using the "shutdown()" method.
     * Second, we use the "awaitTermination()" method available for all thread executors. The method waits the specified
     * time to complete all tasks, returning sooner if all tasks finish or an InterruptedException is detected.
     */
    public static void allTasksFinished() throws InterruptedException {
        ExecutorService service = null;
        try {
            service = Executors.newSingleThreadExecutor();
            Future<String> task1 = service.submit(() -> {
                System.out.println("Printing zoo inventory for task 1");
                return "OK-1";
            });
            Future<String> task2 = service.submit(() -> {
                System.out.println("Printing zoo inventory for task 2");
                return "OK-2";
            });
            Future<String> task3 = service.submit(() -> {
                System.out.println("Printing zoo inventory for task 3");
                return "OK-3";
            });
        } finally {
            if (Objects.nonNull(service))
                service.shutdown();
        }
        if (Objects.nonNull(service)) {
            service.awaitTermination(10, TimeUnit.SECONDS);
            // Check whether all task are finished
            if (service.isTerminated())
                System.out.println("Finished!!!");
            else
                System.out.println("At least one task is still running...");
        }
        // Notice that we can call "isTerminated()" after the "awaitTermination()" method finishes confirming that all
        // tasks are actually finished. If "awaitTermination()" is called before "shutdown()" within the same thread,
        // then that thread will wait the full timeout value sent with "awaitTermination()".
    }

    /**
     * invokeAll(): will wait indefinitely until all tasks are complete.
     * invokeAny(): method will wait indefinitely until at least one task completes.
     *
     * The ExecutorService interface also includes overloaded versions of invokeAll() and invokeAny() that take a
     * timeout value and TimeUnit parameter.
     */
    public static void taskCollections() throws InterruptedException, ExecutionException {
        ExecutorService service = null;
        System.out.println("begin");
        try {
            service = Executors.newSingleThreadExecutor();
            Callable<String> task = () -> "result";
            // The JVM waits on for all tasks to finish before moving on to the next line.
            List<Future<String>> list = service.invokeAll(List.of(task, task, task));
            for (Future<String> future : list)
                System.out.println(future.get());
        } finally {
            if (Objects.nonNull(service))
                service.shutdown();
        }
        System.out.println("end");
    }

    /**
     * ScheduledExecutorService methods:
     *
     * ********************************************|********************************************************************|
     * METHOD NAME                                 | DESCRIPTION                                                        |
     * ********************************************|********************************************************************|
     * schedule(Callable<V> callable, long delay,  | Creates and executes a Callable task after the given delay.        |
     *      TimeUnit unit)                         |                                                                    |
     * --------------------------------------------|--------------------------------------------------------------------|
     * schedule(Runnable command, long delay,      | Creates and executes a Runnable task after the given delay.        |
     *      TimeUnit unit)                         |                                                                    |
     * --------------------------------------------|--------------------------------------------------------------------|
     * scheduleAtFixedRate(Runnable command,       | Creates and executes a Runnable task after the given initial delay,|
     *      long initialDelay, long period,        | creating a new task every period value that passes.                |
     *      TimeUnit unit)                         |                                                                    |
     * --------------------------------------------|--------------------------------------------------------------------|
     * scheduleWithFixedDelay(Runnable command,    | Creates and executes a Runnable task after the given initial delay |
     *      long initialDelay, long delay,         | and subsequently with the given delay between the termination of   |
     *      TimeUnit unit)                         | one execution and the commencement of the next.                    |
     * --------------------------------------------|--------------------------------------------------------------------|
     *
     * The first two schedule() methods take a Callable or Runnable, respectively; perform the task after some delay;
     * and return a ScheduledFuture instance. The ScheduledFuture interface is identical to the Future interface, except
     * that it includes a getDelay() method that returns the remaining delay.
     *
     */
    public static void schedulingTask() {
        ScheduledExecutorService service = null;
        try {
            service = Executors.newSingleThreadScheduledExecutor();
            Runnable task1 = () -> System.out.println("Hello zoo from 1");
            Callable<String> task2 = () -> {
                System.out.println("Hello zoo from task 2");
                return "Monkey";
            };
            ScheduledFuture<?> r1 = service.schedule(task1, 3, TimeUnit.SECONDS);
            ScheduledFuture<String> r2 = service.schedule(task2, 5, TimeUnit.SECONDS);
        } finally {
            if (Objects.nonNull(service))
                service.shutdown();
        }
    }
}
