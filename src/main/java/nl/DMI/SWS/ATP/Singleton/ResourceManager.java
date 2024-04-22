package nl.DMI.SWS.ATP.Singleton;

import nl.DMI.SWS.ATP.DTO.InstrumentDTO;
import nl.DMI.SWS.ATP.DTO.InstrumentInfoDTO;
import nl.DMI.SWS.ATP.Enum.ToastType;
import nl.DMI.SWS.ATP.Models.BaseInstrument;
import nl.DMI.SWS.ATP.Models.Instrument;
import nl.DMI.SWS.ATP.Util.ToastManager;
import xyz.froud.jvisa.JVisaException;
import xyz.froud.jvisa.JVisaResourceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ResourceManager {
    private static ResourceManager instance;

    private JVisaResourceManager visaResourceManager;
    private final List<InstrumentInfoDTO> instrumentList = new ArrayList<>();
    private final List<Instrument> activeInstruments = new ArrayList<>();

    private ResourceManager() {
        try {
            visaResourceManager = new JVisaResourceManager();
        } catch (JVisaException e) {
            System.out.println("Error initializing VISA resource manager. Check GPIB cable.");
            e.printStackTrace();
        }
    }

    private List<String> getResourceNames() {
        List<String> resources = new ArrayList<>();
        try {
            final String[] resourceNames = visaResourceManager.findResources();
            resources.addAll(Arrays.asList(resourceNames));
        } catch (JVisaException e) {
            ToastManager.showToast("Error finding resources. Check connection.", ToastType.WARNING);
            System.out.println(e.getMessage());
        }
        return resources;
    }


    public void discover() {
        List<String> resourceNames = getResourceNames();

        for (String resourceName : resourceNames) {
            if (!resourceName.contains("INSTR")) continue;
            System.out.println("Found VISA resource: " + resourceName);
            try {
                Instrument instrument = new BaseInstrument(resourceName);
                String IDN = instrument.getIDN();
                instrument.close();
                instrumentList.add(new InstrumentInfoDTO(IDN, resourceName));
            } catch (Exception e) {
                System.out.println("Unable to connect to device: " + resourceName);
                e.printStackTrace();
            }
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

    public List<String> getResourceNames(String deviceType) {
        return instrumentList.stream().filter(d -> d.getIDN().contains(deviceType)).map(InstrumentInfoDTO::getResourceName).collect(Collectors.toList());
    }

    public void addInstrument(Instrument instrument) {
        activeInstruments.add(instrument);
    }

    public void removeInstrument(Instrument instrument) {
        activeInstruments.remove(instrument);
    }

    public void initializeInstruments() {
        for (Instrument instrument: activeInstruments) {
            try {
                instrument.reset();
            } catch (Exception e) {
                System.out.println("Error resetting instrument: " + instrument);
                System.out.println(e.getMessage());
            }
        }
    }

    public void closeInstruments() {
        for (Instrument instrument: activeInstruments) {
            try {
                instrument.close();
            } catch (Exception e) {
                System.out.println("Error closing instrument: " + instrument);
                System.out.println(e.getMessage());
            }
        }
    }
}
