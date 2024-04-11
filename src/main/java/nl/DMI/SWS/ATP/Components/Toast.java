package nl.DMI.SWS.ATP.Components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import nl.DMI.SWS.ATP.Enum.ToastType;

public class Toast extends Label {

    public Toast(String text, ToastType type) {
        super(text);

        this.setAlignment(Pos.CENTER);
        this.getStyleClass().add("toast");

        if(type == ToastType.INFO) {
            this.getStyleClass().add("info");
        } else if (type == ToastType.SUCCESS) {
            this.getStyleClass().add("success");
        } else if (type == ToastType.WARNING) {
            this.getStyleClass().add("warning");
        } else if (type == ToastType.ERROR) {
            this.getStyleClass().add("error");
        } else {
            this.getStyleClass().add("info");
        }
    }
}
