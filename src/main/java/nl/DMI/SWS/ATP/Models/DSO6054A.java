package nl.DMI.SWS.ATP.Models;

import nl.DMI.SWS.ATP.Exception.InstrumentException;

public class DSO6054A extends Oscilloscope {
    public DSO6054A(String visaResourceName) throws InstrumentException {
        super(visaResourceName);
    }
}
