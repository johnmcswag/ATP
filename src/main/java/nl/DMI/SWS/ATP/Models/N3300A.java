package nl.DMI.SWS.ATP.Models;

import nl.DMI.SWS.ATP.Exception.InstrumentException;
import xyz.froud.jvisa.*;
import java.util.HashMap;

public class N3300A extends Instrument {

    private final int CHANNEL_COUNT;
    public final HashMap<Integer, N3300AModule> LOADS = new HashMap<>();


    public N3300A(String visaResourceName) throws InstrumentException {
        super(visaResourceName);
        CHANNEL_COUNT = Integer.parseInt(query("CHANNEL? MAX"));
        setupLoads();
    }

    private void setupLoads() {
        for (int i = 1; i <= CHANNEL_COUNT; i++) {
            try {
                LOADS.put(i, new N3300AModule(i, this));
            } catch (InstrumentException e) {
                System.out.println("Issue with load: " + i);
                e.printStackTrace();
            }
        }
    }

    public N3300AModule getLoad(int channel) {
        return LOADS.get(channel);
    }

    @Override
    public String toString() {
        return "ELoad{" +
                "CHANNEL_COUNT=" + CHANNEL_COUNT +
                ", LOADS=" + LOADS +
                '}';
    }
}
