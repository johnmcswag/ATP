package nl.DMI.SWS.ATP.Components;

import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import nl.DMI.SWS.ATP.Service.ToastManager;
import nl.DMI.SWS.ATP.Service.ViewLoader;

public class AppContainer extends VBox {
    private final MenuBar menuBar = new MenuBar();
    private final StackPane contentPane = new StackPane();
    public AppContainer() {
        super();
        applyStyling();
        this.getChildren().addAll(menuBar, contentPane);
        contentPane.getChildren().add(new ToastManager().getContainer());

        Menu menu = new Menu("Menu");
        menuBar.getMenus().add(menu);

        MenuItem item1 = new MenuItem("Home");
        item1.setOnAction((event) -> ViewLoader.setHomeView());

        menu.getItems().addAll(item1);
    }

    public StackPane getContentPane() {
        return contentPane;
    }

    private void applyStyling() {
        contentPane.setPadding(new Insets(16));
        contentPane.setBackground(new Background(new BackgroundFill(Color.web("#747678"), null, null)));
        VBox.setVgrow(contentPane, Priority.ALWAYS);
    }
}
