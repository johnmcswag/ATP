package nl.DMI.SWS.ATP.Models;

import nl.DMI.SWS.ATP.Util.TimeBase;
import nl.DMI.SWS.ATP.Exception.InstrumentException;
import xyz.froud.jvisa.JVisaInstrument;
import xyz.froud.jvisa.JVisaResourceManager;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public abstract class Oscilloscope extends Instrument {

    public Oscilloscope(JVisaResourceManager rm, String visaResourceName) throws InstrumentException {
        super(rm, visaResourceName);
    }

    public Oscilloscope(JVisaInstrument jVisaInstrument) throws InstrumentException {
        super(jVisaInstrument);
    }

    /*TODO:
     * === TIMebase ===
     * - Timebase delay
     * - Timebase Reference
     * === CHANnel ===
     * - Enable/Disable
     * - BW Limit
     * - Coupling
     * - Display aka enabled/disabled
     * - Impedence
     * - Invert
     * - Label
     * - Offset
     * - Probe
     *  - Detect autoprobe
     * - Protection detection
     * - Scale
     * - Units
     * === MARKer ===
     * - MODE???? - LATER
     * - Set source
     * - X1 position
     * - X2 position
     * - Y1 position
     * - Y2 position
     * - Read x Delta
     * - Read Y Delta
     * === TRIGger ===
     * - Sweep
     * - Edge:Slope
     */

    private TimeBase timeBaseRange = new TimeBase(100, TimeUnit.MICROSECONDS);

    protected void setTimeBaseRange(long duration, TimeUnit unit) {
        timeBaseRange.updateTimeBase(duration, unit);
    }

    protected TimeBase getTimeBaseRange() {
        return timeBaseRange;
    }

}
