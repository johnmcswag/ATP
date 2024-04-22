package nl.DMI.SWS.ATP.Service;

import java.util.concurrent.Future;

public abstract class Task {
    protected Future<?> task;

    public abstract void execute();
    public void stop() {
        if(task.isCancelled()) return;
        task.cancel(true);
    };
}
