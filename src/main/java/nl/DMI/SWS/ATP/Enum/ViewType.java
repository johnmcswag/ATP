package nl.DMI.SWS.ATP.Enum;

import nl.DMI.SWS.ATP.Views.DLCView;
import nl.DMI.SWS.ATP.Views.HomeView;
import nl.DMI.SWS.ATP.Views.TestView;
import nl.DMI.SWS.ATP.Views.View;
import javafx.scene.Parent;

public enum ViewType {
    VIEW1(DLCView.class, "Dynamic Load Control"),
    View2(TestView.class, "Test"),
    MAINVIEW(HomeView.class, "Home");

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
