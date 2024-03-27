package dmi.sws.dlview.Views;

import javafx.scene.layout.StackPane;

public abstract class View extends StackPane implements IView {
    public String title = "Test!";

    public String getTitle() {
        return title;
    }

    public void unload() {};
}
