package nl.DMI.SWS.ATP.Views;

import javafx.scene.layout.StackPane;
import nl.DMI.SWS.ATP.Singleton.ResourceManager;

public abstract class View extends StackPane {
    public String title = "Made by Jarno Luucies :)";

    public String getTitle() {
        return title;
    }

    public void unload() {
        ResourceManager.getResourceManager().closeInstruments();
    };
}
