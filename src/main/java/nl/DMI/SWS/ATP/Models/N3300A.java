package nl.DMI.SWS.ATP.Models;

import nl.DMI.SWS.ATP.Components.DLSlider;
import nl.DMI.SWS.ATP.Exception.InstrumentException;
import xyz.froud.jvisa.*;

import java.util.ArrayList;
import java.util.List;

public class N3300A extends Instrument {

    private final int CHANNEL_COUNT;
    private final List<N3300AModule> LOADS = new ArrayList<>();


    public N3300A(String visaResourceName) throws InstrumentException {
        super(visaResourceName);
        CHANNEL_COUNT = Integer.parseInt(query("CHANNEL? MAX"));
        setupLoads();
    }

    public N3300A(JVisaInstrument instrument) throws InstrumentException {
        super(instrument);
        CHANNEL_COUNT = Integer.parseInt(query("CHANNEL? MAX"));
        setupLoads();
    }

    private void setupLoads() {
        for (int i = 1; i <= CHANNEL_COUNT; i++) {
            try {
                N3300AModule module = new N3300AModule(i, this);
                LOADS.add(module);
                new DLSlider(module);
//                TaskManager.getTaskManager().addTask(module::scheduledTask);
                module.startTask();
            } catch (InstrumentException e) {
                System.out.println("Issue with load: " + i);
                e.printStackTrace();
            }
        }
    }

    public List<N3300AModule> getLoads() {
        return LOADS;
    }

    @Override
    public void close() throws InstrumentException {
        for (N3300AModule load : LOADS) {
            load.stopTask();
        }

        super.close();
    }

    @Override
    public String toString() {
        return "ELoad{" +
                "CHANNEL_COUNT=" + CHANNEL_COUNT +
                ", LOADS=" + LOADS +
                '}';
    }
}
