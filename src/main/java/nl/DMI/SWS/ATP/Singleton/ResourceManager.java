package nl.DMI.SWS.ATP.Singleton;

import nl.DMI.SWS.ATP.Models.Instrument;
import xyz.froud.jvisa.JVisaException;
import xyz.froud.jvisa.JVisaResourceManager;

import java.util.ArrayList;
import java.util.List;

public final class ResourceManager {
    private static ResourceManager instance;

    private JVisaResourceManager visaResourceManager;
    private final List<Instrument> instrumentList;

    private ResourceManager() {
        instrumentList = new ArrayList<>();
        try {
            visaResourceManager = new JVisaResourceManager();
        } catch (JVisaException e) {
            System.out.println("Error initializing VISA resource manager. Check GPIB cable.");
            e.printStackTrace();
        }
    }

    public static synchronized ResourceManager getResourceManager() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    public JVisaResourceManager getVisaResourceManager() {
        return visaResourceManager;
    }

    public void addInstrument(Instrument instrument) {
        instrumentList.add(instrument);
    }

    public void initializeInstruments() {
        for (Instrument instrument: instrumentList) {
            try {
                instrument.reset();
            } catch (Exception e) {
                System.out.println("Error resetting instrument: " + instrument);
                System.out.println(e.getMessage());
            }
        }
    }

    public void closeInstruments() {
        for (Instrument instrument: instrumentList) {
            try {
                instrument.close();
            } catch (Exception e) {
                System.out.println("Error closing instrument: " + instrument);
                System.out.println(e.getMessage());
            }
        }
    }
}
