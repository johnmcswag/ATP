package nl.DMI.SWS.ATP.Service;

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

public class ViewLoader {
    private final Stage mainStage;
    private final static StackPane mainContainer = new StackPane();

    public ViewLoader(Stage stage) {
        mainStage = stage;
        Scene mainScene = new Scene(mainContainer, 1280, 720);
        try {
            mainScene.getStylesheets().add("main.css");
        } catch (Exception e) {
            System.out.println("Error loading css file.");
            System.out.println(e.getMessage());
        }
        mainStage.setScene(mainScene);
        mainStage.show();

        mainContainer.setPadding(new Insets(16));
        mainContainer.setBackground(new Background(new BackgroundFill(Color.DARKGREY, null, null)));
//        setHomeView();
        setView(new DLView());
    }

    public static void setView(View view) {
        mainContainer.getChildren().clear();
        mainContainer.getChildren().add(view);
        ((Stage) mainContainer.getScene().getWindow()).setTitle(view.getTitle());
    }

    public View getView() {
        return (View) mainContainer.getChildren().get(0);
    }

    public void setHomeView() {
        Class<? extends Parent> newClass = ViewType.VIEW1.getViewClass();
        try {
            setView((View) newClass.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void unload() {
        getView().unload();
    }

}
