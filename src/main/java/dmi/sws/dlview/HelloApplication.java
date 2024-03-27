package dmi.sws.dlview;

import dmi.sws.dlview.Service.ViewLoader;
import dmi.sws.dlview.Singleton.ExecutorServiceSingleton;
import dmi.sws.dlview.Singleton.ResourceManager;
import javafx.application.Application;
import javafx.stage.Stage;
import xyz.froud.jvisa.JVisaResourceManager;

import java.io.IOException;

public class HelloApplication extends Application {
    JVisaResourceManager rm;
    ViewLoader vl;
    @Override
    public void start(Stage mainStage) throws IOException {
        rm = ResourceManager.getResourceManager();
        mainStage.setTitle("Made by Jarno :)");
        vl =new ViewLoader(mainStage);

    }

    @Override
    public void stop() throws Exception {
        vl.unload();
        rm.close();
        ExecutorServiceSingleton.shutdown();
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}