package nl.DMI.SWS.ATP.Singleton;

import nl.DMI.SWS.ATP.Service.Task;

public class SingleRunTask extends Task {

    private Runnable action;

    public SingleRunTask(Runnable action) {
        this.action = action;
    }

    @Override
    public void execute() {
        task = TaskManager.submitTask(action, 0L);
    }
}
