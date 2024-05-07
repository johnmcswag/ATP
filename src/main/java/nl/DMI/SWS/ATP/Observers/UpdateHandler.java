package nl.DMI.SWS.ATP.Observers;

@FunctionalInterface
public interface UpdateHandler <Object> {
    void handle(Object value);
}
