package nl.DMI.SWS.ATP.Views;

import nl.DMI.SWS.ATP.Enum.ViewType;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import nl.DMI.SWS.ATP.Service.ViewLoader;

public class HomeView extends View {

    public HomeView() {
        HBox viewContainer = new HBox(8);
        this.getChildren().add(viewContainer);


        for (ViewType viewType: ViewType.values()) {
            Class<? extends Parent> viewClass = viewType.getViewClass();
            String displayName = viewType.getDisplayName();
            if(displayName == "Home") continue;

            Button button = new Button(displayName);
            button.setOnAction((event) -> {
                try {
                    ViewLoader.setView((View) viewClass.getDeclaredConstructor().newInstance());
                } catch (Exception e) {
                    System.out.println("Error loading view: " + displayName);
                    System.out.println(e.getMessage());
                }
            });

            viewContainer.getChildren().add(button);
        }
    }
}
