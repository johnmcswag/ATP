package nl.DMI.SWS.ATP.Service;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import nl.DMI.SWS.ATP.Components.Toast;
import nl.DMI.SWS.ATP.Enum.ToastType;

public class ToastManager {
    private static final VBox container = new VBox(16);

    public ToastManager() {
        container.setAlignment(Pos.BOTTOM_CENTER);
    }

    public VBox getContainer() {
        return container;
    }

    public static void showToast(String message) {
        showToast(message, ToastType.INFO);
    }

    public static void showToast(String message, ToastType type) {
        Platform.runLater(() -> {
            Toast toast = new Toast(message, type);
            container.getChildren().add(toast);

            new Thread(() -> {
                try {
                    Thread.sleep(3000); // Laat de toast voor 3 seconden zien
                    Platform.runLater(() -> {
                        container.getChildren().remove(toast);
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }
}
