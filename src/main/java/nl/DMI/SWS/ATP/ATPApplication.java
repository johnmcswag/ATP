package nl.DMI.SWS.ATP;

import nl.DMI.SWS.ATP.Service.ThreadService;
import nl.DMI.SWS.ATP.Util.ViewLoader;
import nl.DMI.SWS.ATP.Singleton.ResourceManager;
import javafx.application.Application;
import javafx.stage.Stage;
import xyz.froud.jvisa.JVisaResourceManager;

public class ATPApplication extends Application {
    JVisaResourceManager rm;
    ViewLoader vl;
    @Override
    public void start(Stage mainStage) {
        rm = ResourceManager.getResourceManager();
        mainStage.setTitle("Made by Jarno :)");
        vl = new ViewLoader(mainStage);
    }

    @Override
    public void stop() {
        try {
            ViewLoader.unload();
            rm.close();
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