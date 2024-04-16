package nl.DMI.SWS.ATP.Singleton;

import javafx.scene.image.Image;
import nl.DMI.SWS.ATP.ATPApplication;
import nl.DMI.SWS.ATP.Components.AppContainer;
import nl.DMI.SWS.ATP.Enum.ViewType;
import nl.DMI.SWS.ATP.Views.View;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;

public class ViewLoader {

    private static ViewLoader instance;

    private Stage mainStage;
    private final static AppContainer mainContainer = new AppContainer();

    private ViewLoader(Stage stage) {
        mainStage = stage;

        Scene mainScene = new Scene(mainContainer, 1280, 720);
        try {
            mainScene.getStylesheets().add("main.css");
            mainStage.getIcons().add(new Image(Objects.requireNonNull(ATPApplication.class.getResourceAsStream("/logo.png"))));
        } catch (Exception e) {
            System.out.println("Error loading files.");
            System.out.println(e.getMessage());
        }
        mainStage.setScene(mainScene);
        mainStage.show();

        setHomeView();
    }

    public static synchronized ViewLoader getViewLoader() {
        return instance;
    }

    public static synchronized ViewLoader getViewLoader(Stage stage) {
        if (instance == null) {
            instance = new ViewLoader(stage);
        }
        return instance;
    }

    public void setView(ViewType viewClass) {
        unload();

        try {
            View view = (View) viewClass.getViewClass().getDeclaredConstructor().newInstance();

            StackPane contentPane = mainContainer.getContentPane();
            if(contentPane.getChildren().size() > 1) {
                contentPane.getChildren().remove(1);
            }
            contentPane.getChildren().add(view);
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setTitle(view.getTitle());
        } catch (Exception e) {
            System.out.println("Error setting view: " + viewClass.getDisplayName());
            e.printStackTrace();
        }
    }

    public View getView() {
        if(mainContainer.getContentPane().getChildren().size() < 2) return null;
        return (View) mainContainer.getContentPane().getChildren().get(1);
    }

    public void setHomeView() {
        setView(ViewType.MAINVIEW);
    }

    public void unload() {
        View view = getView();
        if(view != null) view.unload();
    }

}
