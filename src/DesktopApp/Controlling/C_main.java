package DesktopApp.Controlling;

import DesktopApp.Controlling.HandMadeControllers.TypedWords;
import DesktopApp.Tools.CreateStage;
import DesktopApp.Tools.MyClassWithText;
import DesktopApp.Tools.ShowMinorStage;
import DesktopApp.Tools.Vocabulary.Manager;
import DesktopApp.Tools.Vocabulary.Words;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;

public class C_main implements Initializable{
    public BorderPane borderPanePrimary;
    public ImageView
            history,
            find;
    public TextField textFieldF;
    public Label userChooseDictionary;
    public TextArea textArea;
    private Stage
            stage_study = null,
            thisStage = null;
    public Button study;
    private static Manager manager;

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

//        // Assign label "userChooseDictionary" with the show stage "getStageForChooseLanguage()"
        new ShowMinorStage(
                -23, userChooseDictionary.getHeight(),
                getStageForChooseLanguage(),thisStage).setLabel(userChooseDictionary);

        // Assign textField wish stage in class TypedWord
        MyClassWithText word = new MyClassWithText();
        TypedWords.getInstance(0, textFieldF.getHeight() + 2, thisStage, userChooseDictionary, textFieldF, word);
        // add listener when the word is selected
        word.addPropertyChangeListener(evt -> {
//            textArea.setText(manager.getWord(userChooseDictionary.getText(), evt.getNewValue().toString()).toString());
            showPaneWithTranslate(evt.getNewValue().toString());
        });

    }

    private void setImages() {
        try {
            find.setImage(new Image(new File("D:\\GUI\\find1.png").toURI().toURL().toString()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            history.setImage(new Image(new File("D:\\GUI\\history1.png").toURI().toURL().toString()));
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
                    path = "D:\\GUI\\find1.png";
                else
                    path = "D:\\GUI\\find2.png";

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
                    path = "D:\\GUI\\history1.png";
                else
                    path = "D:\\GUI\\history2.png";

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
                    userChooseDictionary.setText(((Label) event.getSource()).getText());
                    stage.close();
                }
            });

            borderPane.setCenter(vBox);
            stage.setScene(new Scene(new Group(borderPane), 130, manager.getAllVocabularies().size() * 19));
        }

        return stage;
    }

    private void showPaneWithTranslate(String word){
        borderPanePrimary.getChildren().remove(borderPanePrimary.getCenter());

        Label
                wordLabel = new Label("Word"),
                transcriptionLabel = new Label("Transcription"),
                translateLabel = new Label("Translate");
        TextField
                wordTextField = new TextField(),
                transcriptionTextField = new TextField("");
        TextArea translateTextArea = new TextArea();

        VBox vBox = new VBox();
        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();

        hBox1.setAlignment(Pos.CENTER);
        hBox2.setAlignment(Pos.TOP_CENTER);

        wordLabel.setAlignment(Pos.CENTER);
        transcriptionLabel.setAlignment(Pos.CENTER);
        translateLabel.setAlignment(Pos.CENTER);

        wordTextField.setAlignment(Pos.CENTER);
        transcriptionTextField.setAlignment(Pos.CENTER);

        wordLabel.setId("outputDate");
        transcriptionLabel.setId("outputDate");
        translateLabel.setId("outputDate");
        translateLabel.setStyle("-fx-pref-width: 290px");

        wordTextField.setStyle("-fx-pref-width: 150; -fx-background-color: #d8d8d8; -fx-text-fill: #616161");
        transcriptionTextField.setStyle("-fx-pref-width: 150; -fx-background-color: #d8d8d8; -fx-text-fill: #616161");
        translateTextArea.setStyle("-fx-pref-width: 300;");


        Iterator<Words> iterator = Manager.getDictionaryWord(userChooseDictionary.getText(), word.toLowerCase()).iterator();
        int n = 0;
        Vector<String> vector = new Vector<>();
        while (iterator.hasNext()){
            Words dictionaryWord = iterator.next();

            wordTextField.setText(dictionaryWord.getWord());
            transcriptionTextField.setText(dictionaryWord.getTranscription());

            vector.add(dictionaryWord.getTranslate());
        }
        String str =  "";
        for (String s : vector) {
            if (!s.contains(" 1. ") && !s.contains(" 1) ")) {
                if (str.length() == 0) str += s;
                else str += "\n" + s;
                n++;
            } else {
                int n1 = 1;
                while (true) {
                    String s1;
                    if (s.contains(n1 + ". ")) {
                        n1++;
                        if (s.contains(n1 + ". ")) {
                            s1 = s.substring(s.indexOf((n1 - 1) + ". ") + 3, s.indexOf(n1 + ". "));
                            s = s.substring(s1.length());
                        } else {
                            s1 = s.substring(s.indexOf((n1 - 1) + ". ") + 3);
                            s = "";
                        }
                    } else {
                        s1 = s;
                        s = "";
                    }
                    int n2 = 1;
                    String type = "";
                    while (true) {
                        String s2;
                        if (s1.contains(" " + n2 + ") ")) {

                            if (type.equals("")) {
                                type = s1.substring(0, s1.indexOf(n2 + ") ")).trim();
                                if (str.length() == 0)
                                    str += type;
                                else
                                    str += "\n" + type;
                                n++;
                            }

                            n2++;
                            String enter = "";
                            if (s1.contains(" " + n2 + ") ")) {
                                int x = n2 >= 10 ? 2 : 1;
                                if (str.length() != 0) enter = "\n";
                                str += enter + " " + s1.substring(s1.indexOf(" " + (n2 - 1) + ") ") + 3 + x, s1.indexOf(" " + n2 + ") "));
                                s1 = s1.substring(s1.indexOf(" " + (n2 - 1) + ") ") + 2 + x);
                                n++;
                            } else {
                                int x = (n2 - 1) >= 10 ? 2 : 1;
                                if (str.length() != 0) enter = "\n";
                                str += enter + " " + s1.substring(s1.indexOf(" " + (n2 - 1) + ") ") + 3 + x);
                                s1 = "";
                                n++;
                            }
                        } else {
                            s2 = s1.substring(s1.indexOf(" " + (n2 - 1) + ") ") + 2);
                            s1 = "";
                            str += s2;
                            n++;
                        }

                        if (s1.equals(""))
                            break;
                    }

                    if (s.equals(""))
                        break;
                }
            }
        }

        translateTextArea.setText(str);
        translateTextArea.setPrefHeight(19 * n + 10);

        if (str.length() >= 45)
            translateTextArea.setMinHeight(40);
        else
            translateTextArea.setMinHeight(25);

        HBox
                hBoxT1 = new HBox(translateLabel),
                hBoxT2 = new HBox(translateTextArea);

        hBoxT1.setAlignment(Pos.CENTER);
        hBoxT2.setAlignment(Pos.CENTER);

        VBox vBox1 = new VBox(hBoxT1, hBoxT2);
        vBox1.setAlignment(Pos.CENTER);

        if (transcriptionTextField.getText() != null && !transcriptionTextField.getText().equals("")) {
            translateLabel.setStyle("-fx-pref-width: 302px");
            hBox1.getChildren().addAll(wordLabel, transcriptionLabel);
            hBox2.getChildren().addAll(wordTextField, transcriptionTextField);
        } else {
            translateLabel.setStyle("-fx-pref-width: 302px");
            wordLabel.setStyle("-fx-pref-width: 302px");
            wordTextField.setStyle("-fx-pref-width: 302; -fx-background-color: #d8d8d8; -fx-text-fill: #616161");

            hBox1.getChildren().addAll(wordLabel);
            hBox2.getChildren().addAll(wordTextField);
        }

        HBox.setMargin(wordLabel, new Insets(0, 1, 0, 2));
        HBox.setMargin(translateLabel, new Insets(0, 1, 0, 1));
        HBox.setMargin(wordTextField, new Insets(0, 1, 0, 2));
        HBox.setMargin(translateTextArea, new Insets(0, 1, 0, 1));

        vBox.getChildren().addAll(hBox1, hBox2);
        vBox.getChildren().add(vBox1);

        borderPanePrimary.setCenter(vBox);
    }
}
