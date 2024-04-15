package nl.DMI.SWS.ATP.Views;

import javafx.geometry.Pos;
import nl.DMI.SWS.ATP.Components.DLSlider;
import nl.DMI.SWS.ATP.Models.N3300AModule;
import nl.DMI.SWS.ATP.Service.N3300AService;
import javafx.scene.layout.HBox;

import java.util.List;

public class DLCView extends View {
    N3300AService service;
    private List<N3300AModule> loads;
    public DLCView() {
        title = "Dynamic Load Control";
        HBox sliderContainer = new HBox(8);
        this.getChildren().add(sliderContainer);
        sliderContainer.setAlignment(Pos.TOP_CENTER);
        service = new N3300AService();
        service.discoverLoads();
        loads = service.getLoads();
        for(N3300AModule load: service.getLoads()) {
            DLSlider slider = new DLSlider(load);
            sliderContainer.getChildren().add(slider.getContainer());
        }
    }

    @Override
    public void unload() {
        service.close();
    }
}
