package nl.DMI.SWS.ATP;

import nl.DMI.SWS.ATP.Singleton.TaskManager;
import nl.DMI.SWS.ATP.Singleton.ViewLoader;
import nl.DMI.SWS.ATP.Singleton.ResourceManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class ATPApplication extends Application {
    @Override
    public void start(Stage mainStage) {
        mainStage.setTitle("Made by Jarno :)");
        ResourceManager.getResourceManager().discover();
        ViewLoader.getViewLoader(mainStage);
    }

    @Override
    public void stop() {
        try {
            ResourceManager.getResourceManager().closeInstruments();
            TaskManager.shutdown();
            super.stop();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}