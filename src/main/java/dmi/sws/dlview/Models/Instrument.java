package dmi.sws.dlview.Models;

import dmi.sws.dlview.Exception.InstrumentException;
import xyz.froud.jvisa.*;

public class Instrument {
    public final JVisaInstrument JVISA_INSTRUMENT;

    public Instrument(JVisaResourceManager rm, String visaResourceName) throws InstrumentException {
        try {
            JVISA_INSTRUMENT = rm.openInstrument(visaResourceName);
            reset();
        } catch (JVisaException ex) {
            throw new InstrumentException(ex);
        }
    }

    public Instrument(JVisaInstrument jVisaInstrument) throws InstrumentException {
        JVISA_INSTRUMENT = jVisaInstrument;
        try {
            reset();
        } catch (InstrumentException ex) {
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
//            JVISA_INSTRUMENT.queryString("*OPC?");
            return value;
        } catch (JVisaException ex) {
            System.out.println("Error with querying command: " + command);
            throw new InstrumentException(ex);
        }
    }

    protected void write(String command) throws InstrumentException {
        try {
            JVISA_INSTRUMENT.write(command);
            JVISA_INSTRUMENT.queryString("*OPC?");
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
