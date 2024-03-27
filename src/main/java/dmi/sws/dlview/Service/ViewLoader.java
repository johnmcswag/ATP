package dmi.sws.dlview.Service;

import dmi.sws.dlview.Views.DLView;
import dmi.sws.dlview.Views.View;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ViewLoader {
    private final Stage mainStage;
    private static final StackPane mainContainer = new StackPane();

    public ViewLoader(Stage mainStage) {
        this.mainStage = mainStage;
        Scene mainScene = new Scene(mainContainer, 1280, 720);
        try {
            mainScene.getStylesheets().add("main.css");
        } catch (Exception e) {
            System.out.println(e);
        }
        this.mainStage.setScene(mainScene);
        this.mainStage.show();

        mainContainer.setPadding(new Insets(16));
        mainContainer.setBackground(new Background(new BackgroundFill(Color.DARKGREY, null, null)));

        setView(new DLView());
//        setView(new HomeView());
    }

    private void setNewTitle(View view) {
        mainStage.setTitle(view.getTitle());
    }

    public void setView(View view) {
        mainContainer.getChildren().removeAll();
        mainContainer.getChildren().add(view);
        setNewTitle(view);
    }

    public View getView() {
        return (View) mainContainer.getChildren().get(0);
    }

    public void unload() {
        getView().unload();
    }

}
