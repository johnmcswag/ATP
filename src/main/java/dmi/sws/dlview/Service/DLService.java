package dmi.sws.dlview.Service;

import dmi.sws.dlview.Components.DLSlider;
import dmi.sws.dlview.DTO.InstrumentInfoDTO;
import dmi.sws.dlview.Exception.InstrumentException;
import dmi.sws.dlview.Models.DynamicLoad;
import dmi.sws.dlview.Models.Load;
import dmi.sws.dlview.Singleton.ExecutorServiceSingleton;
import dmi.sws.dlview.Singleton.ResourceManager;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import xyz.froud.jvisa.JVisaResourceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static dmi.sws.dlview.util.Math.toFixed;

public class DLService {
    private DynamicLoad DLLoad1;
    private DynamicLoad DLLoad2;
    private List<Load> loads = new ArrayList<>();

    private JVisaResourceManager rm;

    public DLService() {

        rm = ResourceManager.getResourceManager();
    }

    public List<DynamicLoad> discoverLoads() {
        List<InstrumentInfoDTO> instruments = InstrumentService.discover();
        List<InstrumentInfoDTO> DLResources = instruments.stream().filter(i -> i.getIDN().contains("N3300A")).collect(Collectors.toList());
        List<DynamicLoad> DLLoads = new ArrayList<>();
        try {
            if(DLResources.size() != 2) throw new InstrumentException("Dynamic load missing. Are both turned on?");
            DLLoad1 = new DynamicLoad(rm, DLResources.get(0).getResourceName());
            DLLoad2 = new DynamicLoad(rm, DLResources.get(1).getResourceName());
            DLLoads.add(DLLoad1);
            DLLoads.add(DLLoad2);

            loads.addAll(DLLoad1.LOADS.values());
            loads.addAll(DLLoad2.LOADS.values());

            ScheduledExecutorService scheduler =  ExecutorServiceSingleton.getInstance();
            scheduler.scheduleAtFixedRate(this::measureTask, 0, 250, TimeUnit.MILLISECONDS);
        } catch (InstrumentException e) {
            System.out.println(e.getMessage());
        }

        return DLLoads;
    }

    public DynamicLoad getDLLoad1() {
        return DLLoad1;
    }

    public DynamicLoad getDLLoad2() {
        return DLLoad2;
    }

    public List<Load> getLoads() {
        return loads;
    }

    private void measureTask() {
        try {
            for (int i = 0; i < loads.size(); i++) {
                Load load = loads.get(i);
                DLSlider slider = load.getSlider();
                if(!slider.isEnabled()) continue;
                measureVoltTask(slider);
                measureCurrTask(slider);
                setCurrTask(slider);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void measureVoltTask(DLSlider slider) {
        VBox sliderContainer = slider.getContainer();
        Load load = slider.getLoad();
        try {
            double voltage = load.getVoltage();
            Platform.runLater(() -> {
                Label voltageLabel = (Label) sliderContainer.getChildren().get(1);
                voltageLabel.setText(voltage + "");
            });
        } catch (InstrumentException e) {
            System.out.println("Error measuring voltage. " + load);
            System.out.println(e.getMessage());
        }
    }

    private void measureCurrTask(DLSlider slider) {
        VBox sliderContainer = slider.getContainer();
        Load load = slider.getLoad();
        try {
            double current = load.getCurrent();
            Platform.runLater(() -> {

                Label currentLabel = (Label) sliderContainer.getChildren().get(2);
                currentLabel.setText(current + "");
            });
        } catch (InstrumentException e) {
            System.out.println("Error measuring current. " + load);
            System.out.println(e.getMessage());
        }

    }

    private void setCurrTask(DLSlider slider) {
        VBox sliderContainer = slider.getContainer();
        Load load = slider.getLoad();
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
                    load.setCurrent(sliderControlValue);
                } catch (InstrumentException e) {
                    Platform.runLater(() -> {
                        System.out.println("Error setting current to level: " + sliderControlValue);
                        System.out.println(e.getMessage());
                    });
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
