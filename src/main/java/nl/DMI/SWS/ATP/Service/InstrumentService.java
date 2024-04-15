package nl.DMI.SWS.ATP.Service;

import nl.DMI.SWS.ATP.DTO.InstrumentInfoDTO;
import nl.DMI.SWS.ATP.Enum.ToastType;
import nl.DMI.SWS.ATP.Models.Instrument;
import nl.DMI.SWS.ATP.Singleton.ResourceManager;
import nl.DMI.SWS.ATP.Util.ToastManager;
import xyz.froud.jvisa.JVisaException;
import xyz.froud.jvisa.JVisaResourceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InstrumentService {
    private static List<String> getResourceNames() {
        List<String> resources = new ArrayList<>();
        JVisaResourceManager resourceManager = ResourceManager.getResourceManager();
        try {
            final String[] resourceNames = resourceManager.findResources();
            if (resourceNames.length == 0) return resources;
            resources = Arrays.asList(resourceNames);
        } catch (JVisaException e) {
            System.out.println("Error finding resources. Check GPIB cable.");
            System.out.println(e.getMessage());
        }
        return resources;
    }

    public static List<InstrumentInfoDTO> discover() {
        List<InstrumentInfoDTO> instruments = new ArrayList<>();
        List<String> resourceNames = getResourceNames();
        JVisaResourceManager resourceManager = ResourceManager.getResourceManager();

        for (String resourceName : resourceNames) {
            if (!resourceName.contains("INSTR")) continue;
            System.out.println("Found VISA resource: " + resourceName);
            try {
                Instrument instrument = new Instrument(resourceManager, resourceName);
                String IDN = instrument.getIDN();
                instrument.close();
                instruments.add(new InstrumentInfoDTO(IDN, resourceName));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                instruments.add(new InstrumentInfoDTO("Not Found", resourceName));
            }
        }
        return instruments;
    }
}