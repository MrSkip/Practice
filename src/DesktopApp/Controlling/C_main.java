package DesktopApp.Controlling;

import DesktopApp.Controlling.HandMadeControllers.TypedWords;
import DesktopApp.Controlling.minorControllers.C_study;
import DesktopApp.Tools.CreateStage;
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
    private static VBox vBoxOfAnswer;
    private Vector<String> vector = null;
    private ShowMinorStage showTypedWord;
    private static int upDown = 0;
    private TypedWords typedWords = TypedWords.getInstanse();

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

        // Assign button "study" with the show stage "study"
        new ShowMinorStage(
                1, study.getHeight(),
                stage_study, stage).setButton(study);

        // Assign label "userChooseDictionary" with the show stage "getStageForChooseLanguage()"
        new ShowMinorStage(
                -15, userChooseDictionary.getHeight(),
                getStageForChooseLanguage(),thisStage).setLabel(userChooseDictionary);

        // Assign textField "textFieldF" with the show stage "getStageForTypedWords()"
        showTypedWord = new ShowMinorStage(
                0, textFieldF.getHeight() + 2,
                typedWords.getStage(), thisStage);
        showTypedWord.setTextField(textFieldF);
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

        textFieldF.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode().getName().equals("Enter") || textFieldF.getText().equals("")) {
                showTypedWord.closeStudy();
                upDown = 0;
            }
            else if (event.getCode().getName().equals("Up") ||
                    event.getCode().getName().equals("Down")) {
                typedWords.dS();
                return;
            }
            else if (event.getText().length() != 0 && !showTypedWord.getMinorStage().isShowing()) {
                showTypedWord.closeOrShow();
                upDown = 0;
            }

            typedWords.setSearchWords(userChooseDictionary.getText(),textFieldF.getText());
            thisStage.requestFocus();
        });
    }

    private Stage getStageForTypedWords(){
        Stage stage = new Stage();

        vBoxOfAnswer = new VBox();

        stage.setScene(new Scene(vBoxOfAnswer,190, 20));
        return stage;
    }

//    private void setSearchWords(){
//        vector = manager.getTypedWord(userChooseDictionary.getText().trim().toLowerCase(), textFieldF.getText());
//        Vector<String> goldVector = new Vector<>();
//
//        if (!vector.isEmpty())
//            goldVector.add(vector.firstElement());
//
//        for (String aVector : vector) {
//            if (!aVector.equals(goldVector.lastElement()))
//                goldVector.add(aVector);
//
//            if (goldVector.size() == 10) break;
//        }
//
//        if (!vBoxOfAnswer.getChildren().isEmpty()){
//            int n = vBoxOfAnswer.getChildren().size();
//            for (int i = 0; i < n; i++) {
//                vBoxOfAnswer.getChildren().remove(0);
//            }
//        }
//
//        Label firstLabel = new Label(textFieldF.getText());
//        firstLabel.setStyle(
//                "-fx-background-color: #ebebeb;" +
//                        "    -fx-font-style: normal;" +
//                        "    -fx-font-size: 14;" +
//                        "    -fx-pref-width: 190;");
//        firstLabel.setAlignment(Pos.CENTER);
//
//        vBoxOfAnswer.getChildren().add(firstLabel);
//
//        if (vector.isEmpty()) return;
//        FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
//        for (String aGoldVector : goldVector) {
//
//            Label labelCorrect = new Label(textFieldF.getText());
//
//            HBox hBox = new HBox();
//
//            Label label = new Label(aGoldVector.substring(labelCorrect.getText().length(), aGoldVector.length()));
//            label.setStyle("-fx-background-color: #b3b3b3; -fx-pref-height: 17");
//            label.setPrefWidth(190 - fontLoader.computeStringWidth(labelCorrect.getText(), labelCorrect.getFont()) - 5);
//
//            hBox.getChildren().addAll(labelCorrect, label);
//            vBoxOfAnswer.getChildren().add(hBox);
//
//            VBox.setMargin(hBox, new Insets(1,1,0,2));
//        }
//        vBoxOfAnswer.setStyle("-fx-background-color: #cacaca");
//        Stage stage = showTypedWord.getMinorStage();
//        stage.setHeight((vBoxOfAnswer.getChildren().size() - 1) * 18 + 20);
//        showTypedWord.setMinorStage(stage);
//    }

    private void setUpDownSelect(String codeName){
        int n = upDown;

        if (codeName.equals("Up")) upDown++;
        else upDown--;

        if (upDown < 1 ) upDown = 1;
        else if (upDown > vBoxOfAnswer.getChildren().size())
            upDown = vBoxOfAnswer.getChildren().size();

//        System.out.println(((HBox) vBoxOfAnswer.getChildren().get(1)).getChildren().get(1));
//        ((Label) ((HBox) vBoxOfAnswer.getChildren().get(1)).getChildren().get(0)).setText("df");
//        ((HBox) vBoxOfAnswer.getChildren().get(1)).getChildren().get(1).setStyle("-fx-background-color: white");
//        System.out.println(n + " - " + upDown + " -size-> " + vBoxOfAnswer.getChildren().size());
//        System.out.println(((HBox) vBoxOfAnswer.getChildren().get(1)).getChildren().get(1));
//        vBoxOfAnswer.getChildren().remove(0);
//        System.out.println(((HBox) vBoxOfAnswer.getChildren().get(1)).getChildren().get(0));
//        vBoxOfAnswer.getChildren().get(1).setStyle("-fx-background-color: black");
//        System.out.println(vBoxOfAnswer.getChildren());
//        System.out.println(vBoxOfAnswer.getChildren().size());
//        Stage stage = showTypedWord.getMinorStage();
//        stage.setHeight((vBoxOfAnswer.getChildren().size() - 1) * 18 + 20);
//        showTypedWord.setMinorStage(stage);

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
