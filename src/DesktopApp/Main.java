package DesktopApp;

import DesktopApp.Tools.CreateStage;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Mr Skip on 09.12.2015.
 */
public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage stage = new CreateStage("run", "..\\GUI\\main.fxml", 600, 400, false, false).getStage();
        stage.show();
    }
}
