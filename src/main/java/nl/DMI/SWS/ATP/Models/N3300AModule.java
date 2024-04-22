package nl.DMI.SWS.ATP.Models;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.DMI.SWS.ATP.Components.DLSlider;
import nl.DMI.SWS.ATP.Exception.InstrumentException;
import nl.DMI.SWS.ATP.Singleton.TaskManager;
import nl.DMI.SWS.ATP.Singleton.TaskProducer;

import java.util.concurrent.Future;

import static nl.DMI.SWS.ATP.Util.Math.toFixed;

public class N3300AModule implements TaskProducer {
    private static int loadCount = 0;
    private static int currentLoadIndex = 0;

    private final int channel;
    private final N3300A INSTRUMENT;
    private final double MAX_CURRENT;
    private final double MAX_VOLTAGE;
    private final int ROUNDINGDIGITS = 3;
    private int loadIndex;
    private DLSlider slider = null;
    private boolean isEnabled = false;

    private Future<?> task;

    public N3300AModule(int channel, N3300A instrument) throws InstrumentException {
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

    @Override
    public void startTask() {
        task = TaskManager.submitTask(this::scheduledTask);
    }

    @Override
    public void stopTask() {
        if(task.isCancelled()) return;
        TaskManager.closeTask(task);
    }

    @Override
    public void scheduledTask() {
        if(!slider.isEnabled() && !this.isEnabled()) return;
        VBox sliderContainer = slider.getContainer();

        if (slider.isEnabled() != this.isEnabled()) {
            HBox controlWrapper = (HBox) sliderContainer.getChildren().get(3);
            VBox controlContainer = (VBox) controlWrapper.getChildren().get(0);

            Slider sliderControl = (Slider) ((VBox) controlContainer.getChildren().get(0)).getChildren().get(1);
            Button set = (Button) controlContainer.getChildren().get(1);
            Label loadLabel = (Label) sliderContainer.getChildren().get(0);
            boolean state = slider.isEnabled();

            try {
                if(state) {
                    this.enable();
                } else {
                    this.disable();
                }
            } catch (InstrumentException e) {
                System.out.println("Issue toggling " + this);
                System.out.println(e.getMessage());
            }

            Platform.runLater(() -> {
                set.setDisable(!state);
                sliderControl.setDisable(!state);

                if(state) {
                    loadLabel.setText(this.toString());
                } else {
                    loadLabel.setText("DISABLED");
                    sliderControl.setValue(0.0);
                }
            });
            return;
        }

        // Measure voltage
        try {
            double voltage = this.getVoltage();
            Platform.runLater(() -> {
                Label voltageLabel = (Label) sliderContainer.getChildren().get(1);
                voltageLabel.setText(voltage + "");
            });
        } catch (InstrumentException e) {
            System.out.println("Error measuring voltage. " + this);
            System.out.println(e.getMessage());
        }

        // Measure current
        try {
            double current = this.getCurrent();
            Platform.runLater(() -> {

                Label currentLabel = (Label) sliderContainer.getChildren().get(2);
                currentLabel.setText(current + "");
            });
        } catch (InstrumentException e) {
            System.out.println("Error measuring current. " + this);
            System.out.println(e.getMessage());
        }

        // Set current
        try {
            HBox controlWrapper = (HBox) sliderContainer.getChildren().get(3);
            VBox controlContainer = (VBox) controlWrapper.getChildren().get(0);
            Slider sliderControl = (Slider) ((VBox) controlContainer.getChildren().get(0)).getChildren().get(1);

            double sliderControlValue = toFixed(sliderControl.getValue(), 1);
            double sliderValue = slider.getSliderValue();
            double nearestTick = Math.round(sliderControlValue / 0.1) * 0.1;
            if (Math.abs(nearestTick - sliderValue) >= 0.1 - 0.01) {
                slider.setSliderValue(sliderControlValue);
                try {
                    this.setCurrent(sliderControlValue);
                } catch (InstrumentException e) {
                    Platform.runLater(() -> {
                        System.out.println("Error setting current to level: " + sliderControlValue);
                        System.out.println(e.getMessage());
                    });
                }
            }
        } catch (Exception e) {
            System.out.println("Error in setCurrTask");
            e.printStackTrace();
        }
    }
}
