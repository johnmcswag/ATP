package dmi.sws.dlview.Views;

import javafx.scene.text.Text;

public class HomeView extends View {
    public HomeView() {
        title = "Test 2";

        Text text = new Text("Test!");
        System.out.println("Test!");
        this.getChildren().add(text);

    }
}
