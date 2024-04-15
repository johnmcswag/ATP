package nl.DMI.SWS.ATP.Models;

import nl.DMI.SWS.ATP.Components.DLSlider;
import nl.DMI.SWS.ATP.Exception.InstrumentException;

import static nl.DMI.SWS.ATP.Util.Math.toFixed;

public class N3300AModule {
    private static int loadCount = 0;
    private static int currentLoadIndex = 0;

    private final int channel;
    private final Instrument INSTRUMENT;
    private final double MAX_CURRENT;
    private final double MAX_VOLTAGE;
    private final int ROUNDINGDIGITS = 3;
    private int loadIndex = 0;
    private DLSlider slider = null;
    private boolean isEnabled = false;

    public N3300AModule(int channel, Instrument instrument) throws InstrumentException {
        this.loadIndex = ++loadCount;
        this.channel = channel;
        INSTRUMENT = instrument;
        changeToChannel();
        String MAX_CURRENT = INSTRUMENT.query("CURRENT? MAX");
        String MAX_VOLTAGE = INSTRUMENT.query("VOLTAGE? MAX");
        // Convert to int from scientific value
        this.MAX_CURRENT = Double.parseDouble(MAX_CURRENT);
        this.MAX_VOLTAGE = Double.parseDouble(MAX_VOLTAGE);
    }

    private void changeToChannel() throws InstrumentException {
        if(currentLoadIndex == channel) return;
        INSTRUMENT.write("INST " + channel);
        currentLoadIndex = channel;
    }

    public void enable() throws InstrumentException {
        changeToChannel();
        INSTRUMENT.write("OUTP ON");
        isEnabled = true;
    }

    public void disable() throws InstrumentException {
        changeToChannel();
        INSTRUMENT.write("OUTP OFF");
        isEnabled = false;
    }

    public double getCurrent() throws InstrumentException {
        changeToChannel();
        return toFixed(Double.parseDouble(INSTRUMENT.query("MEASURE:CURRENT?")),ROUNDINGDIGITS);
    }

    public void setCurrent(double current) throws InstrumentException {
        if (current > MAX_CURRENT) {
            current = (float) MAX_CURRENT;
            System.out.println("Current is too high.");
        }
        changeToChannel();
        INSTRUMENT.write("CURRENT " + current);
    }

    public double getVoltage() throws InstrumentException {
        changeToChannel();
        return toFixed(Double.parseDouble(INSTRUMENT.query("MEASURE:VOLTAGE?")),ROUNDINGDIGITS);
    }

    public DLSlider getSlider() {
        return slider;
    }

    public void setSlider(DLSlider slider) {
        this.slider = slider;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public String toString() {
        return "Load-" + loadIndex;
    }
}
