package nl.DMI.SWS.ATP.Enum;

import nl.DMI.SWS.ATP.Views.DLView;
import nl.DMI.SWS.ATP.Views.HomeView;
import nl.DMI.SWS.ATP.Views.View;
import javafx.scene.Parent;

public enum ViewType {
    MAINVIEW(HomeView.class, "Home"),
    VIEW1(DLView.class, "Dynamic Load Control");

    private final Class<? extends View> viewClass;
    private final String displayName;

    ViewType(Class<? extends View> viewClass, String displayName) {
        this.viewClass = viewClass;
        this.displayName = displayName;
    }

    public Class<? extends Parent> getViewClass() {
        return viewClass;
    }

    public String getDisplayName() {
        return displayName;
    }
}
