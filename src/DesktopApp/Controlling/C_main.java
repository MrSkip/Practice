package DesktopApp.Controlling;

import DesktopApp.Controlling.minorControllers.C_study;
import DesktopApp.Tools.CreateStage;
import DesktopApp.Tools.ReadLog;
import DesktopApp.Tools.ShowMinorStage;
import DesktopApp.Tools.Vocabulary.Manager;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class C_main implements Initializable{
    public BorderPane borderPanePrimary;
    public ImageView
            history,
            find;
    public TextField textFieldF;
    public Label userChooseDictionary;
    private Stage
            stage_study = null,
            thisStage = null;
    public Button study;
    private Manager manager;
    private static String CURRENT_LANGUAGE;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager = new Manager();
        stage_study = new CreateStage("", "..\\GUI\\minorScenes\\study.fxml", 215, 304, true, false).getStage();

        borderPanePrimary.getStylesheets().clear();
        borderPanePrimary.getStylesheets().add(getClass().getResource("..\\GUI\\Style.css").toExternalForm());
        borderPanePrimary.setId("scene");

        setImages();
        setListeners();
    }

    // Show the scene
    public void show(Stage stage){
        thisStage = stage;
        stage.show();

        // Assign button "study" with the show stage "study"
        new ShowMinorStage(
                study.localToScreen(Point2D.ZERO).getX(),
                study.localToScreen(Point2D.ZERO).getY() + study.getHeight(),
                stage_study, stage).setButton(study);

        // Assign label "userChooseDictionary" with the show stage "getStageForChooseLanguage()"
        new ShowMinorStage(
                userChooseDictionary.localToScreen(Point2D.ZERO).getX() - 10,
                userChooseDictionary.localToScreen(Point2D.ZERO).getY() + userChooseDictionary.getHeight(),
                getStageForChooseLanguage(),thisStage).setLabel(userChooseDictionary);
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
        history.setCursor(Cursor.HAND);

        textFieldF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                textFieldF.setStyle("-fx-background-color: #f3f3f3;");
            else
                textFieldF.setStyle("-fx-background-color: #c8c8c8;");
        });

        userChooseDictionary.addEventFilter(MouseEvent.ANY, event -> {
            if (event.getEventType() == MouseEvent.MOUSE_ENTERED ||
                    event.getEventType() == MouseEvent.MOUSE_EXITED){

                if (event.getEventType() == MouseEvent.MOUSE_ENTERED)
                    userChooseDictionary.setStyle("-fx-text-fill: #1f467f; -fx-underline: true");
                else
                    userChooseDictionary.setStyle("-fx-text-fill: #3e3e3e; -fx-underline: false");
            }
        });
        userChooseDictionary.setCursor(Cursor.HAND);
    }

//    private Stage getStageForTypedWords(){
//
//    }
    private Stage getStageForChooseLanguage(){
        Stage stage = new Stage();
//        stage.setHeight(/*manager.getAllVocabularies().size() * 19 + 6*/70);
//        stage.setWidth(130);

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #bababa");

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < manager.getAllVocabularies().size(); i++) {
            Label label = new Label(" " + manager.getAllVocabularies().get(i).getVocabularyName());
            label.setStyle(
                    "-fx-background-color: #bababa; " +
                    "-fx-text-fill: #3e3e3e; " +
                    "-fx-font-style:italic; " +
                    "-fx-font-size: 12;" +
                    "-fx-pref-width: 213;");
            label.setCursor(Cursor.HAND);

            vBox.getChildren().add(label);
            VBox.setMargin(label, new Insets(1,3,1,3));

            label.addEventFilter(MouseEvent.ANY, event -> {
                if (event.getEventType() == MouseEvent.MOUSE_ENTERED ||
                        event.getEventType() == MouseEvent.MOUSE_EXITED){

                    if (event.getEventType() == MouseEvent.MOUSE_ENTERED)
                        label.setStyle("-fx-text-fill: #1f467f; -fx-underline: true");
                    else
                        label.setStyle("-fx-text-fill: #3e3e3e; -fx-underline: false");
                }
                else if (MouseEvent.MOUSE_CLICKED == event.getEventType() &&
                        MouseButton.PRIMARY == event.getButton()) {
                    CURRENT_LANGUAGE = ((Label) event.getSource()).getText();
                    stage.close();
                }
            });

            borderPane.setCenter(vBox);
            stage.setScene(new Scene(new Group(borderPane), 40, 40));
        }

        return stage;
    }
}
