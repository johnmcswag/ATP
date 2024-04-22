package nl.DMI.SWS.ATP.Singleton;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskManager {
    static int taskPeriod_ms = 25;
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public static void shutdown() {
        executor.shutdown();
    }

    public static void closeTask(Future<?> task) {
        task.cancel(true);
    }

    public static Future<?> submitTask(Runnable task) {
        return executor.scheduleAtFixedRate(task, 0, TaskManager.taskPeriod_ms, TimeUnit.MILLISECONDS);
    }
}
