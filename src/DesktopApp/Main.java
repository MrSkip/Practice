package DesktopApp;

import DesktopApp.Controlling.C_main;
import DesktopApp.Tools.CreateStage;
import DesktopApp.Tools.ReadLog;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        CreateStage createStage = null;
        try {
            createStage = new CreateStage("Your Dictionary", ReadLog.getDesktopAppFolder() + "GUI\\main.fxml", 730, 450, false, true);
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
