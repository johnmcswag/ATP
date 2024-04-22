package nl.DMI.SWS.ATP.DTO;

import nl.DMI.SWS.ATP.Models.Instrument;

public class InstrumentDTO {
    private String IDN;
    private Instrument instrument;

    public InstrumentDTO(String IDN, Instrument instrument) {
        this.IDN = IDN;
        this.instrument = instrument;
    }

    public String getIDN() {
        return IDN;
    }

    public void setIDN(String IDN) {
        this.IDN = IDN;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }
}
