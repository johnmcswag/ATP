package nl.DMI.SWS.ATP.Views;

import javafx.geometry.Pos;
import nl.DMI.SWS.ATP.Components.DLSlider;
import nl.DMI.SWS.ATP.Enum.ToastType;
import nl.DMI.SWS.ATP.Models.N3300A;
import nl.DMI.SWS.ATP.Models.N3300AModule;
import javafx.scene.layout.HBox;
import nl.DMI.SWS.ATP.Singleton.ResourceManager;
import nl.DMI.SWS.ATP.Util.ToastManager;

import java.util.List;

public class DLCView extends View {
    public DLCView() {
        title = "Dynamic Load Control";
        HBox sliderContainer = new HBox(8);
        this.getChildren().add(sliderContainer);
        sliderContainer.setAlignment(Pos.TOP_CENTER);

        List<String> resourceNames = ResourceManager.getResourceManager().getResourceNames("N3300A");
        if(resourceNames.size() < 2) {
            ToastManager.showToast("Not enough N3300A devices found. Returning to home.", ToastType.ERROR);
            throw new RuntimeException("Not enough N3300A devices found.");
        }

        try {
            N3300A DLLoad1 = new N3300A(resourceNames.get(0));
            for(N3300AModule load: DLLoad1.getLoads()) {
                DLSlider slider = load.getSlider();
                sliderContainer.getChildren().add(slider.getContainer());
            }

            N3300A DLLoad2 = new N3300A(resourceNames.get(1));
            for(N3300AModule load: DLLoad2.getLoads()) {
                DLSlider slider = load.getSlider();
                sliderContainer.getChildren().add(slider.getContainer());
            }

        } catch (Exception e) {
            ToastManager.showToast("Error setting up dynamic loads. Returning to home.", ToastType.ERROR);
            throw new RuntimeException("Error setting up dynamic loads.", e);
        }
    }
}
