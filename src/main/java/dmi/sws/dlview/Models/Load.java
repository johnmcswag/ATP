package dmi.sws.dlview.Models;

import dmi.sws.dlview.Components.DLSlider;
import dmi.sws.dlview.Exception.InstrumentException;
import dmi.sws.dlview.Service.DLService;

import static dmi.sws.dlview.util.Math.toFixed;

public class Load {
    private static int loadCount = 0;
    private static int currentLoadIndex = 0;

    private final int channel;
    private final Instrument INSTRUMENT;
    private final double MAX_CURRENT;
    private final double MAX_VOLTAGE;
    private final int ROUNDINGDIGITS = 3;
    private int loadIndex = 0;
    private DLSlider slider = null;

    public Load(int channel, Instrument instrument) throws InstrumentException {
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
//        System.out.println("Changing channel to: " + channel);
        INSTRUMENT.write("INST " + channel);
        currentLoadIndex = channel;
    }

    public void enable() throws InstrumentException {
        changeToChannel();
        INSTRUMENT.write("OUTP ON");
    }

    public void disable() throws InstrumentException {
        changeToChannel();
        INSTRUMENT.write("OUTP OFF");
    }

    public double getCurrent() throws InstrumentException {
        changeToChannel();
        return toFixed(Double.parseDouble(INSTRUMENT.query("MEASURE:CURRENT?")),ROUNDINGDIGITS);
    }

    public void setCurrent(double current) throws InstrumentException {
        if (current > MAX_CURRENT) {
            current = (float) MAX_CURRENT;
            System.out.println("Current is too high.");
//                throw new InstrumentException("Current is too high");
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

    @Override
    public String toString() {
        return "Load-" + loadIndex;
    }

    //    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Load{").append("\n");
//        sb.append("\tchannel=").append(channel).append("\n");
//        sb.append("\tMAX_CURRENT=").append(MAX_CURRENT).append("\n");
//        sb.append("\tMAX_VOLTAGE=").append(MAX_VOLTAGE).append("\n");
//        try {
//            sb.append("\tcurrent=").append(getCurrent()).append("\n");
//            sb.append("\tvoltage=").append(getVoltage()).append("\n");
//        } catch (InstrumentException e) {
//            throw new RuntimeException(e);
//        }
//        sb.append('}');
//        return sb.toString();
//    }
}
