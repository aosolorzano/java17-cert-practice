package schedule.test;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;

public class Schedule {

    public static void main(String[] args) {
        System.out.println("begin");
        ScheduledExecutorService service = null;
        try {
            service = Executors.newSingleThreadScheduledExecutor();
            Runnable task1 = () -> System.out.println("Hello Zoo from 1.");
            Callable<String> task2 = () -> {
                System.out.println("Hello Zoo from task 2.");
                return "Monkey";
            };
            ScheduledFuture<?> r1 = service.schedule(task1, 5, TimeUnit.SECONDS);
            ScheduledFuture<String> r2 = service.schedule(task2, 10, TimeUnit.SECONDS);
        } finally {
            if (Objects.nonNull(service))
                service.shutdown();
        }
        System.out.println("end");
    }
}
