package DesktopApp;

import DesktopApp.Controlling.C_main;
import DesktopApp.GUI.CreateStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        CreateStage createStage = null;
        try {
            createStage = new CreateStage("run", System.getProperty("user.dir") + "\\DesktopApp\\GUI\\main.fxml", 100, 100, false, true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        assert createStage != null;
        C_main c_main = (C_main) createStage.getController();
        c_main.show(createStage.getStage());
    }

    public static void main(String[] arg){
        launch(arg);
    }
}
