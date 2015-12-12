package DesktopApp.Controlling;

import DesktopApp.Controlling.minorControllers.C_study;
import DesktopApp.Tools.CreateStage;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class C_main implements Initializable{
    public BorderPane borderPanePrimary;
    private Stage stage = null,
            stage_study = null;
    public Button study;
    C_study controller_study = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CreateStage createStage = new CreateStage("", "..\\GUI\\minorScenes\\study.fxml", 200, 100, true, false);
        stage_study = createStage.getStage();
//        stage_study.initStyle(StageStyle.UNDECORATED);
        controller_study = (C_study) createStage.getController();

        study.setOnAction(event -> {
            if (!stage_study.isShowing()) {
                stage_study.setX(stage.getX() + 9);
                stage_study.setY(stage.getY() + 30 + study.getHeight());
                stage_study.show();
            }
            else {
                closeStudy();
            }
        });

//        borderPanePrimary.heightProperty().addListener((observable, oldValue, newValue) -> closeStudy());
//        borderPanePrimary.widthProperty().addListener((observable, oldValue, newValue) -> closeStudy());
    }

    public void closeStudy(){
            stage_study.close();
    }

    // Show the scene
    public void show(Stage stage){
//        stage_study.initOwner(stage);
        stage.show();
        this.stage = stage;

        /*
        * Create EventFilter
        * If cursor over the button `study` than not close stage_study
        * else close stage_study
        */
//        stage.getScene().addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
//            if (!(mouseEvent.getX() >= study.getLayoutX() && mouseEvent.getX() <= study.getLayoutX() + study.getWidth()
//                    && mouseEvent.getY() >= study.getLayoutY() && mouseEvent.getY() <= study.getLayoutY() + study.getHeight()))
//                closeStudy();
//        });
//
//        // Add listener that can be close stage_study if window is change Width
//        stage.xProperty().addListener((observable, oldValue, newValue) -> {
//            closeStudy();
//        });
//
//        // Add listener than can be close stage_study when window change Height
//        stage.yProperty().addListener((observable, oldValue, newValue) -> {
//            closeStudy();
//        });
    }
}
