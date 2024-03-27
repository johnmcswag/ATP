package dmi.sws.dlview.Views;

import dmi.sws.dlview.Components.DLSlider;
import dmi.sws.dlview.Models.Load;
import dmi.sws.dlview.Service.DLService;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class DLView extends View {
    DLService service;
    public DLView() {
        title = "Dynamic Load Control";
        HBox sliderContainer = new HBox(8);
        this.getChildren().add(sliderContainer);
        service = new DLService();
        service.discoverLoads();
        for(Load load: service.getLoads()) {
            DLSlider slider = new DLSlider(load);
            sliderContainer.getChildren().add(slider.getContainer());
        }
    }

    @Override
    public void unload() {
        for(Load load: service.getLoads()) {
            load.getSlider().unload();
        }
    }
}
