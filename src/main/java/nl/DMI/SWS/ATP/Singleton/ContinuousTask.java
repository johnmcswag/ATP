package nl.DMI.SWS.ATP.Singleton;

import nl.DMI.SWS.ATP.Service.Task;

public class ContinuousTask extends Task {

    private Runnable action;
    private Long period;

    public ContinuousTask(Runnable action, Long period) {
        this.action = action;
        this.period = period;
    }

    @Override
    public void execute() {
        task = TaskManager.submitTask(action, period);
    }
}
