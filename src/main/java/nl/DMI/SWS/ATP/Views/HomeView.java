package nl.DMI.SWS.ATP.Views;

import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import nl.DMI.SWS.ATP.Enum.ViewType;
import javafx.scene.control.Button;
import nl.DMI.SWS.ATP.Util.ViewLoader;

import java.util.Arrays;

public class HomeView extends View {
    private VBox optionContainer = new VBox(8);

    public HomeView() {
        setBaseStyle();
        loadViewList();
    }

    private void loadViewListTest() {
        int[] array = new int[25];
        Arrays.fill(array, 0);

        for (int i = 0; i < array.length; i++) {

            Button button = new Button("Test-" + i);
            button.getStyleClass().add("home-button");
            button.setMaxWidth(Double.MAX_VALUE);

            optionContainer.getChildren().add(button);
        }

    }

    private void loadViewList() {
        for (ViewType viewType: ViewType.values()) {
            String displayName = viewType.getDisplayName();
            if(viewType.equals(ViewType.MAINVIEW)) continue;

            Button button = new Button(displayName);
            button.getStyleClass().add("home-button");
            button.setMaxWidth(Double.MAX_VALUE);
            button.setOnAction((event) -> {
                try {
                    ViewLoader.setView(viewType);
                } catch (Exception e) {
                    System.out.println("Error loading view: " + displayName);
                    System.out.println(e.getMessage());
                }
            });

            optionContainer.getChildren().add(button);
        }

    }


    private void setBaseStyle() {
        ScrollPane scrollWrapper = new ScrollPane();
        scrollWrapper.setContent(optionContainer);
        scrollWrapper.setFitToWidth(true);

        optionContainer.setFillWidth(true);
        GridPane grid = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        col1.setHgrow(Priority.ALWAYS);
        col1.setFillWidth(true);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        col2.setHgrow(Priority.ALWAYS);
        col2.setFillWidth(true);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(25);
        col3.setHgrow(Priority.ALWAYS);
        col3.setFillWidth(true);
        grid.getColumnConstraints().addAll(col1,col2,col3);
        RowConstraints row1 = new RowConstraints();
        row1.setVgrow(Priority.ALWAYS);
        row1.setFillHeight(true);
        grid.getRowConstraints().add(row1);

        grid.add(scrollWrapper, 1, 0, 1, 1);
        grid.setAlignment(Pos.TOP_CENTER);

        this.getChildren().add(grid);
    }

    @Override
    public void unload() {
        super.unload();
    }
}
