package nl.DMI.SWS.ATP.Singleton;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ExecutorServiceSingleton {

    private static final ScheduledExecutorService instance = Executors.newScheduledThreadPool(1);

    private ExecutorServiceSingleton() {
        // Private constructor to prevent instantiation
    }

    public static ScheduledExecutorService getInstance() {
        return instance;
    }

    public static void shutdown() {
        instance.shutdown();
    }
}
