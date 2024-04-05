package nl.DMI.SWS.ATP.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class DeviceService {
    int taskPeriod_ms = 1000;
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    Future<?> task;

    public void close() {
        task.cancel(true);
        executor.shutdown();
    }
    public static ScheduledExecutorService getInstance() {
        return executor;
    }

    protected abstract void updateTask();
    protected void startExecutor() {
        task = getInstance().scheduleAtFixedRate(this::updateTask, 0, this.taskPeriod_ms, TimeUnit.MILLISECONDS);
    }
}
