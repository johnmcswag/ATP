package nl.DMI.SWS.ATP.Observers;

public class GenericObserver implements Observer {
    private UpdateHandler<Object> updateHandler;

    public GenericObserver() {}

    @Override
    public void notify(Object newValue) {
        if(updateHandler == null) return;
        updateHandler.handle(newValue);
    }

    @Override
    public void onUpdate(UpdateHandler<Object> handler) {
        updateHandler = handler;
    }
}
