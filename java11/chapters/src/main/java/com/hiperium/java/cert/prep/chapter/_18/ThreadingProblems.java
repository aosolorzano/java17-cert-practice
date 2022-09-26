package com.hiperium.java.cert.prep.chapter._18;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * UNDERSTANDING LIVENESS:
 *
 * Many thread operations can be performed independently, but some require coordination, or wait for the barrier limit
 * to be reaching before continuing, but what happens to the application while all of these threads are waiting? In many
 * cases, the waiting is ephemeral, and the user has very little idea that any delay has occurred. In other cases, though,
 * the waiting may be extremely long, perhaps infinite.
 *
 * Liveness is the ability of an application to be able to execute in a timely manner. Liveness problems, are those in
 * which the application becomes unresponsive or in some kind of “stuck” state. For the exam, there are three types of
 * liveness issues with which we should be familiar: DEADLOCK, STARVATION, and LIVELOCK.
 *
 * DEADLOCK occurs when two or more threads are blocked forever, each waiting on the other. How do you fix a deadlock
 * once it has occurred? The answer is that you can't in most situations. On the other hand, there are numerous strategies
 * to help prevent deadlocks from ever happening in the first place. One common strategy to avoid deadlocks is for all
 * threads to order their resource requests.
 *
 * STARVATION occurs when a single thread is perpetually denied access to a shared resource or lock. The thread is still
 * active, but it is unable to complete its work as a result of other threads constantly taking the resource that they
 * are trying to access.
 *
 * LIVELOCK occurs when two or more threads are conceptually blocked forever, although they are each still active and
 * trying to complete their task. Livelock is a special case of resource starvation in which two or more threads actively
 * try to acquire a set of locks, are unable to do so, and restart part of the process.
 *
 * Livelock is often a result of two threads trying to resolve a deadlock indefinitely with a cyclical process of lock
 * and unlock.
 *
 *
 * MANAGING RACE CONDITIONS:
 *
 * A race condition is an undesirable result that occurs when two tasks, which should be completed sequentially, are
 * completed at the same time. For the exam, you should understand that race conditions lead to invalid data if they are
 * not properly handled.
 *
 * Race conditions tend to appear in highly concurrent applications. As a software system grows and more users are added,
 * they tend to appear more frequently. One solution is to use a monitor to synchronize on the relevant overlapping task.
 *
 */
public class ThreadingProblems {

    private static class Food {}
    private static class Water {}

    public void move() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // Handle exception
        }
    }

    public void eatAndDrink(Food food, Water water) {
        synchronized(food) {
            System.out.println("Got Food!");
            move();
            synchronized(water) {
                System.out.println("Got Water!");
            }
        }
    }
    public void drinkAndEat(Food food, Water water) {
        synchronized(water) {
            System.out.println("Got Water!");
            move();
            synchronized(food) {
                System.out.println("Got Food!");
            }
        }
    }

    /**
     * DEADLOCK EXAMPLE: Imagine that our zoo has two foxes: Foxy and Tails. Foxy likes to eat first and then drink H2O,
     * while Tails likes to drink water first and then eat. Furthermore, neither animal likes to share, and they will
     * finish their meal only if they have exclusive access to both food and water.
     *
     * The zookeeper places the food on one side of the environment and the water on the other side. Although our foxes
     * are fast, it still takes them 100 milliseconds to run from one side of the environment to the other. What happens
     * if Foxy gets the food first and Tails gets the water first?
     *
     * In this example, Foxy obtains the food and then moves to the other side of the environment to obtain the water.
     * Unfortunately, Tails already drank the water and is waiting for the food to become available. The result is that
     * our program outputs the following, and it hangs indefinitely:
     *
     *      Got Food!
     *      Got Water!
     *
     * This example is considered a deadlock because both participants are permanently blocked, waiting on resources
     * that will never become available.
     */
    public static void main(String[] args) {
        // Create participants and resources
        ThreadingProblems foxy = new ThreadingProblems();
        ThreadingProblems tails = new ThreadingProblems();
        Food food = new Food();
        Water water = new Water();
        // Process data
        ExecutorService service = null;
        try {
            service = Executors.newScheduledThreadPool(10);
            service.submit(() -> foxy.eatAndDrink(food,water));
            service.submit(() -> tails.drinkAndEat(food,water));
        } finally {
            if(Objects.nonNull(service))
                service.shutdown();
        }
    }

}
