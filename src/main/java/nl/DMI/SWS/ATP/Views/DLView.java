package nl.DMI.SWS.ATP.Views;

import javafx.geometry.Pos;
import nl.DMI.SWS.ATP.Components.DLSlider;
import nl.DMI.SWS.ATP.Models.Load;
import nl.DMI.SWS.ATP.Service.DLService;
import javafx.scene.layout.HBox;

public class DLView extends View {
    DLService service;
    public DLView() {
        title = "Dynamic Load Control";
        HBox sliderContainer = new HBox(8);
        this.getChildren().add(sliderContainer);
        sliderContainer.setAlignment(Pos.TOP_CENTER);
        service = new DLService();
        service.discoverLoads();
        for(Load load: service.getLoads()) {
            DLSlider slider = new DLSlider(load);
            sliderContainer.getChildren().add(slider.getContainer());
        }
    }

    @Override
    public void unload() {
        service.close();
    }
}
