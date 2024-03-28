package nl.DMI.SWS.ATP.Models;

import nl.DMI.SWS.ATP.Exception.InstrumentException;
import xyz.froud.jvisa.*;
import java.util.HashMap;

public class DynamicLoad extends Instrument {

    private final int CHANNEL_COUNT;
    public final HashMap<Integer, Load> LOADS = new HashMap<>();


    public DynamicLoad(JVisaResourceManager resourceManager, String visaResourceName) throws InstrumentException {
        super(resourceManager, visaResourceName);
        CHANNEL_COUNT = Integer.parseInt(query("CHANNEL? MAX"));
        setupLoads();
    }

    public DynamicLoad(JVisaInstrument jVisaInstrument) throws InstrumentException {
        super(jVisaInstrument);
        CHANNEL_COUNT = Integer.parseInt(query("CHANNEL? MAX"));
        setupLoads();
    }

    private void setupLoads() {
        for (int i = 1; i <= CHANNEL_COUNT; i++) {
            try {
                LOADS.put(i, new Load(i, this));
            } catch (InstrumentException e) {
                System.out.println("Issue with load: " + i);
                e.printStackTrace();
            }
        }
    }

    public Load getLoad(int channel) {
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
