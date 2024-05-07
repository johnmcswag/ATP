package nl.DMI.SWS.ATP.Observers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChannelObservable {
    private static ChannelObservable instance;
    private final Map<String, List<Observer>> observers = new HashMap<>();

    private ChannelObservable() {}

    public static synchronized ChannelObservable getInstance() {
        if (instance == null) {
            instance = new ChannelObservable();
        }
        return instance;
    }

    public synchronized void register(String channel, Observer observer) {
        this.observers.putIfAbsent(channel, new ArrayList<>());
        this.observers.get(channel).add(observer);
    }

    public synchronized void unregister(String channel, Observer observer) {
        if (observers.containsKey(channel)) {
            observers.get(channel).remove(observer);
        }
    }

    public void notifyObservers(String channel, Object message) {
        List<Observer> observersSnapshot;
        synchronized (this) {
            if (!observers.containsKey(channel)) return;
            observersSnapshot = new ArrayList<>(observers.get(channel));
        }
        for (Observer observer : observersSnapshot) {
            observer.notify(message);
        }
    }
}
