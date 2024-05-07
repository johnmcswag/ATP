package nl.DMI.SWS.ATP.Observers;

public interface Observer {
    void notify(Object newValue);
    void onUpdate(UpdateHandler<Object> handler);
}
