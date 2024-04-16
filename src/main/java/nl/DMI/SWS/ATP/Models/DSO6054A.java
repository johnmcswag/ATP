package nl.DMI.SWS.ATP.Models;

import nl.DMI.SWS.ATP.Exception.InstrumentException;
import xyz.froud.jvisa.JVisaInstrument;
import xyz.froud.jvisa.JVisaResourceManager;

public class DSO6054A extends Oscilloscope {
    public DSO6054A(JVisaResourceManager rm, String visaResourceName) throws InstrumentException {
        super(rm, visaResourceName);
    }

    public DSO6054A(JVisaInstrument jVisaInstrument) throws InstrumentException {
        super(jVisaInstrument);
    }


}
