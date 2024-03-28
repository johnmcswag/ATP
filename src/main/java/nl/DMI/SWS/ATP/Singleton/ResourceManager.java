package nl.DMI.SWS.ATP.Singleton;

import xyz.froud.jvisa.JVisaException;
import xyz.froud.jvisa.JVisaResourceManager;

public final class ResourceManager {
    private static JVisaResourceManager instance;

    private ResourceManager() {}

    public static synchronized JVisaResourceManager getResourceManager() {
        if(instance == null) {
            try {
                JVisaResourceManager resourceManager = new JVisaResourceManager();
                instance = resourceManager;
            } catch (JVisaException e) {
                System.out.println("Error getting VISA resource manager. Check GPIB cable.");
                System.out.println(e.getMessage());
            }
        }
        return instance;
    }
}
