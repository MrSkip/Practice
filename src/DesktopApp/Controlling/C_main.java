package DesktopApp.Controlling;

import DesktopApp.Controlling.minorControllers.C_study;
import DesktopApp.Tools.CreateStage;
import DesktopApp.Tools.ShowMinorStage;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class C_main implements Initializable{
    public BorderPane borderPanePrimary;
    public ImageView history;
    public ImageView find;
    public TextField textFieldF;
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

        borderPanePrimary.getStylesheets().clear();
        borderPanePrimary.getStylesheets().add(getClass().getResource("..\\GUI\\Style.css").toExternalForm());
        borderPanePrimary.setId("scene");

        setImages();
        setListeners();
    }

    // Show the scene
    public void show(Stage stage){
        stage.show();
        new ShowMinorStage(stage.getX() + 9,stage.getY() + 30 + study.getHeight(), stage_study, stage).setButton(study);
    }

    private void setImages() {

        try {
            find.setImage(new Image(new File("D:\\magnifying-glass23.png").toURI().toURL().toString()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            history.setImage(new Image(new File("D:\\notebook91.png").toURI().toURL().toString()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void setListeners(){
        find.addEventFilter(MouseEvent.ANY, event -> {
            if (event.getEventType() == MouseEvent.MOUSE_ENTERED ||
                    event.getEventType() == MouseEvent.MOUSE_EXITED){
                String path;

                if (event.getEventType() == MouseEvent.MOUSE_EXITED)
                    path = "D:\\magnifying-glass23.png";
                else
                    path = "D:\\magnifying-glass23(blue).png";

                try {
                    find.setImage(new Image(new File(path).toURI().toURL().toString()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        history.addEventFilter(MouseEvent.ANY, event -> {
            if (event.getEventType() == MouseEvent.MOUSE_ENTERED ||
                    event.getEventType() == MouseEvent.MOUSE_EXITED){
                String path;

                if (event.getEventType() == MouseEvent.MOUSE_EXITED)
                    path = "D:\\notebook91.png";
                else
                    path = "D:\\notebook91(blue).png";

                try {
                    history.setImage(new Image(new File(path).toURI().toURL().toString()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        textFieldF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                textFieldF.setStyle("-fx-background-color: #f3f3f3;");
            else
                textFieldF.setStyle("-fx-background-color: #c8c8c8;");
        });
    }
}
