package nl.DMI.SWS.ATP;

import nl.DMI.SWS.ATP.Service.ThreadService;
import nl.DMI.SWS.ATP.Singleton.ViewLoader;
import nl.DMI.SWS.ATP.Singleton.ResourceManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class ATPApplication extends Application {
    @Override
    public void start(Stage mainStage) {
        ResourceManager.getResourceManager();
        mainStage.setTitle("Made by Jarno :)");
        ViewLoader.getViewLoader(mainStage);
    }

    @Override
    public void stop() {
        try {
            ViewLoader.getViewLoader().unload();
            ResourceManager.getResourceManager().closeInstruments();
            ThreadService.shutdown();
            super.stop();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}