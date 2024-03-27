package dmi.sws.dlview.Service;

import dmi.sws.dlview.DTO.InstrumentInfoDTO;
import dmi.sws.dlview.Exception.InstrumentException;
import dmi.sws.dlview.Models.Instrument;
import dmi.sws.dlview.Singleton.ResourceManager;
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
            System.out.println(e);
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
                System.out.println(e);
            }
        }
        return instruments;
    }
}