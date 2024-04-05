package nl.DMI.SWS.ATP.Service;

import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import nl.DMI.SWS.ATP.ATPApplication;
import nl.DMI.SWS.ATP.Components.AppContainer;
import nl.DMI.SWS.ATP.Enum.ViewType;
import nl.DMI.SWS.ATP.Views.DLView;
import nl.DMI.SWS.ATP.Views.View;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;

public class ViewLoader {
    private final Stage mainStage;
    private final static AppContainer mainContainer = new AppContainer();

    public ViewLoader(Stage stage) {
        mainStage = stage;
        Scene mainScene = new Scene(mainContainer, 1280, 720);
        try {
            mainScene.getStylesheets().add("main.css");
            mainStage.getIcons().add(new Image(Objects.requireNonNull(ATPApplication.class.getResourceAsStream("/logo.jpg"))));
        } catch (Exception e) {
            System.out.println("Error loading files.");
            System.out.println(e.getMessage());
        }
        mainStage.setScene(mainScene);
        mainStage.show();

        setHomeView();
    }

    public static void setView(ViewType viewClass) {
        try {
            System.out.println(viewClass);
            View view = (View) viewClass.getViewClass().getDeclaredConstructor().newInstance();

            StackPane contentPane = mainContainer.getContentPane();
            contentPane.getChildren().clear();
            contentPane.getChildren().add(view);
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setTitle(view.getTitle());
        } catch (Exception e) {
            System.out.println("Error setting view: " + viewClass.getDisplayName());
            System.out.println(e.getMessage());
        }
    }

    public static View getView() {
        return (View) mainContainer.getContentPane().getChildren().get(0);
    }

    public static void setHomeView() {
        setView(ViewType.MAINVIEW);
    }

    public static void unload() {
        View view = getView();
        if(view != null) view.unload();
    }

}
