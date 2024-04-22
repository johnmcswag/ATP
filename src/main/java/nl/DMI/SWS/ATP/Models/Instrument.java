package nl.DMI.SWS.ATP.Models;

import nl.DMI.SWS.ATP.Exception.InstrumentException;
import nl.DMI.SWS.ATP.Singleton.ResourceManager;
import nl.DMI.SWS.ATP.Singleton.TaskManager;
import xyz.froud.jvisa.*;

public abstract class Instrument extends TaskManager {
    private JVisaInstrument JVISA_INSTRUMENT;
    private final String resourceName;
    protected final JVisaResourceManager rm = ResourceManager.getResourceManager().getVisaResourceManager();

    public Instrument(String resourceName) throws InstrumentException {
        this.resourceName = resourceName;
        open();
        ResourceManager.getResourceManager().addInstrument(this);
    }

    public Instrument(JVisaInstrument instrument) {
        this.JVISA_INSTRUMENT = instrument;
        this.resourceName = instrument.RESOURCE_NAME;
        ResourceManager.getResourceManager().addInstrument(this);
    }

    public void open() throws InstrumentException {
        try {
            JVISA_INSTRUMENT = rm.openInstrument(resourceName);
            reset();
        } catch (JVisaException ex) {
            System.out.println("Issue with opening connection");
            throw new InstrumentException(ex);
        }
    }

    public void close() throws InstrumentException {
        try {
            JVISA_INSTRUMENT.close();
            ResourceManager.getResourceManager().removeInstrument(this);
        } catch (JVisaException ex) {
            System.out.println("Issue with closing connection");
            throw new InstrumentException(ex);
        }
    }

    public void reset() throws InstrumentException {
        write("*RST;*CLS;*OPC");
    }

    public String getIDN() throws InstrumentException {
        return query("*IDN?");
    }

    protected String query(String command) throws InstrumentException {
        try {
            return JVISA_INSTRUMENT.queryString(command);
        } catch (JVisaException ex) {
            System.out.println("Error with querying command: " + command + " to " + (Instrument) this);
            throw new InstrumentException(ex);
        }
    }

    protected void write(String command) throws InstrumentException {
        try {
            JVISA_INSTRUMENT.write(command);
//            JVISA_INSTRUMENT.queryString("*OPC?");
        } catch (JVisaException ex) {
            System.out.println("Error with writing command: " + command + " to " + (Instrument) this);
            throw new InstrumentException(ex);
        }
    }

    @Override
    public String toString() {
        return "Instrument " + resourceName;
    }

}
