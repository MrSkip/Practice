package DesktopApp.Controlling;

import DesktopApp.Controlling.minorControllers.C_study;
import DesktopApp.Tools.CreateStage;
import DesktopApp.Tools.ShowMinorStage;
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
    private Stage stage = null,
            stage_study = null;
    public Button study;
    private C_study controller_study = null;
    private ShowMinorStage stageStudy = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CreateStage createStage = new CreateStage("", "..\\GUI\\minorScenes\\study.fxml", 215, 304, true, false);
        stage_study = createStage.getStage();
        controller_study = (C_study) createStage.getController();
    }

    // Show the scene
    public void show(Stage stage){
        stage.show();
        ShowMinorStage stageStudy = new ShowMinorStage(study, stage, stage_study);
    }
}
