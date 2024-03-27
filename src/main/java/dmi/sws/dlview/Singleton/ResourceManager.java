package dmi.sws.dlview.Singleton;

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
                System.out.println(e.getStackTrace());
            }
        }
        return instance;
    }
}
