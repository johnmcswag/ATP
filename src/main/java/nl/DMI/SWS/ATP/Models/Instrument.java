package nl.DMI.SWS.ATP.Models;

import nl.DMI.SWS.ATP.Exception.InstrumentException;
import nl.DMI.SWS.ATP.Singleton.ResourceManager;
import xyz.froud.jvisa.*;

public class Instrument {
    private JVisaInstrument JVISA_INSTRUMENT;
    private final String resourceName;
    protected final JVisaResourceManager rm = ResourceManager.getResourceManager().getVisaResourceManager();

    public Instrument(String resourceName) throws InstrumentException {
        this.resourceName = resourceName;
        open();
    }

    public Instrument(JVisaInstrument instrument) {
        this.JVISA_INSTRUMENT = instrument;
        this.resourceName = instrument.RESOURCE_NAME;;
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
        } catch (JVisaException ex) {
            System.out.println("Issue with closing connection");
            throw new InstrumentException(ex);
        }
    }

    public void reset() throws InstrumentException {
        write("*RST;*CLS;*OPC");
    }

    protected String query(String command) throws InstrumentException {
        try {
            String value = JVISA_INSTRUMENT.queryString(command);
            return value;
        } catch (JVisaException ex) {
            System.out.println("Error with querying command: " + command);
            throw new InstrumentException(ex);
        }
    }

    protected void write(String command) throws InstrumentException {
        try {
            JVISA_INSTRUMENT.write(command);
//            JVISA_INSTRUMENT.queryString("*OPC?");
        } catch (JVisaException ex) {
            System.out.println("Error with writing command: " + command);
            throw new InstrumentException(ex);
        }
    }

    public String getIDN() throws InstrumentException {
        return query("*IDN?");
    }

    @Override
    public String toString() {
        try {
            return "Instrument " + getIDN();
        } catch (InstrumentException e) {
            throw new RuntimeException(e);
        }
    }

}
