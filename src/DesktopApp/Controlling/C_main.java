package DesktopApp.Controlling;

import DesktopApp.Controlling.HandMadeControllers.TypedWords;
import DesktopApp.Controlling.minorControllers.C_study;
import DesktopApp.Tools.CreateStage;
import DesktopApp.Tools.MyClassWithText;
import DesktopApp.Tools.ReadLog;
import DesktopApp.Tools.ShowMinorStage;
import DesktopApp.Tools.Vocabulary.Manager;
import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

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
    private static Manager manager;
    private static String CURRENT_LANGUAGE;
//    private ShowMinorStage showTypedWord;
//    private TypedWords typedWords = TypedWords.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager = Manager.getInstance();
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

//      Assign button "study" with the show stage "study"
        new ShowMinorStage(
                1, study.getHeight(),
                stage_study, stage).setButton(study);
//
//        // Assign label "userChooseDictionary" with the show stage "getStageForChooseLanguage()"
        new ShowMinorStage(
                -15, userChooseDictionary.getHeight(),
                getStageForChooseLanguage(),thisStage).setLabel(userChooseDictionary);

        // Assign textField "textFieldF" with the show stage "getStageForTypedWords()"
        MyClassWithText word = new MyClassWithText();
        TypedWords.getInstance(0, textFieldF.getHeight() + 2, thisStage, userChooseDictionary, textFieldF, word);
        // add listener when the word is selected
        word.addPropertyChangeListener(evt -> {
            System.out.println(evt.getNewValue());
        });
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

        textFieldF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                textFieldF.setStyle("-fx-background-color: #f3f3f3;");
            else
                textFieldF.setStyle("-fx-background-color: #c8c8c8;");
            textFieldF.requestFocus();
//            textFieldF.selectAll();
        });

//        textFieldF.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
//            if (event.getCode().getName().equals("Enter") || textFieldF.getText().equals("")) {
//                showTypedWord.closeStudy();
//            }
//            else if (event.getCode().getName().equals("Up") ||
//                    event.getCode().getName().equals("Down")) {
//                typedWords.dS(event.getCode().getName());
//                return;
//            }
//            else if (event.getText().length() != 0 && !showTypedWord.getMinorStage().isShowing()) {
//                showTypedWord.closeOrShow();
//            }
//
//            typedWords.setSearchWords(userChooseDictionary.getText(),textFieldF.getText());
//            thisStage.requestFocus();
//        });
    }

    private Stage getStageForChooseLanguage(){
        Stage stage = new Stage();

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #bababa");

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        for (int i = 0; i < manager.getAllVocabularies().size(); i++) {
            Label label = new Label(" " + manager.getAllVocabularies().get(i).getVocabularyName());

            label.getStylesheets().clear();
            label.getStylesheets().add(this.getClass().getResource("..\\GUI\\Style.css").toExternalForm());
            label.setId("labelForVocabulary");

            label.setCursor(Cursor.HAND);
            label.setAlignment(Pos.CENTER);

            vBox.getChildren().add(label);
            VBox.setMargin(label, new Insets(1,3,1,3));

            label.addEventFilter(MouseEvent.ANY, event -> {
                if (event.getEventType() == MouseEvent.MOUSE_ENTERED ||
                        event.getEventType() == MouseEvent.MOUSE_EXITED){

                    if (event.getEventType() == MouseEvent.MOUSE_ENTERED)
                        label.setStyle("-fx-text-fill: #1f467f; -fx-underline: true;");
                    else
                        label.setStyle("-fx-text-fill: black; -fx-underline: false;");
                }
                else if (MouseEvent.MOUSE_CLICKED == event.getEventType() &&
                        MouseButton.PRIMARY == event.getButton()) {
                    CURRENT_LANGUAGE = ((Label) event.getSource()).getText();
                    userChooseDictionary.setText(CURRENT_LANGUAGE);
                    stage.close();
                }
            });

            borderPane.setCenter(vBox);
            stage.setScene(new Scene(new Group(borderPane), 130, manager.getAllVocabularies().size() * 19));
        }

        return stage;
    }
}
