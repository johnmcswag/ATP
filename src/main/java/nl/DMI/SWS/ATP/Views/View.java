package nl.DMI.SWS.ATP.Views;

import javafx.scene.layout.StackPane;

public abstract class View extends StackPane {
    public String title = "Made by Jarno Luucies :)";

    public String getTitle() {
        return title;
    }

    public void unload() {};
}
