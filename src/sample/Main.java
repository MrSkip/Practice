package sample;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        CreateWindow createWindow = new CreateWindow("", "sample.fxml", 588, 389, false, false);
        Controller controller = (Controller) createWindow.createModalWindow();
        Stage stage = createWindow.getDialog();
        stage.showAndWait();

        if (controller.BACK) return;

        createWindow = new CreateWindow("", "playingVideo\\playing.fxml", 602, 370, false, false);
        sample.playingVideo.Controller controller1 = (sample.playingVideo.Controller) createWindow.createModalWindow();
        stage = createWindow.getDialog();
        stage.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
