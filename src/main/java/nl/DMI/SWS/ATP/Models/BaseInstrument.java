package nl.DMI.SWS.ATP.Models;

import nl.DMI.SWS.ATP.Exception.InstrumentException;
import xyz.froud.jvisa.JVisaInstrument;

public class BaseInstrument extends Instrument {
    public BaseInstrument(String visaResourceName) throws InstrumentException {
        super(visaResourceName);
    }

    public BaseInstrument(JVisaInstrument instrument) {
        super(instrument);
    }
}
