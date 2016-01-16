package DesktopApp.Controlling;

import DesktopApp.Controlling.HandMadeControllers.TypedWords;
import DesktopApp.Tools.CreateStage;
import DesktopApp.Tools.MyClassWithText;
import DesktopApp.Tools.ReadLog;
import DesktopApp.Tools.ShowMinorStage;
import DesktopApp.Tools.Vocabulary.Manager;
import DesktopApp.Tools.Vocabulary.UserVocabularyManager;
import DesktopApp.Tools.Vocabulary.Words;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.Blend;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class C_main implements Initializable{
    private Vector<String> historyOfSearch;
    public BorderPane borderPanePrimary;
    public ImageView
            history,
            find;
    public TextField textFieldF;
    public Label userChooseDictionary;
    private Stage
            stage_study = null;
    public Button study;
    private static Manager manager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        historyOfSearch = new Vector<>();

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
        stage.setOnCloseRequest(event1 -> ReadLog.writeLog());

        stage.show();

//      Assign button "study" with the show stage "study"
        new ShowMinorStage(
                1, study.getHeight(),
                stage_study, stage).setObject(study);

//        // Assign label "userChooseDictionary" with the show stage "getStageForChooseLanguage()"
        new ShowMinorStage(
                -23, userChooseDictionary.getHeight(),
                getStageForChooseLanguage(), stage).setObject(userChooseDictionary);

        // Assign textField wish stage in class TypedWord
        MyClassWithText word = new MyClassWithText();
        TypedWords.getInstance(0, textFieldF.getHeight() + 2, stage, userChooseDictionary, textFieldF, word);
        // add listener when the word is selected
        word.addPropertyChangeListener(evt -> {
            showPaneWithTranslate(evt.getNewValue().toString().substring(0, evt.getNewValue().toString().indexOf("#")));

            historyOfSearch.removeElement(evt.getNewValue().toString());
            historyOfSearch.add(0, evt.getNewValue().toString());
        });

        MyClassWithText myClassWithText = new MyClassWithText();
        StageHistory.getInstance(-110,history.getFitHeight(), stage,history,historyOfSearch, myClassWithText);
        myClassWithText.addPropertyChangeListener(evt -> {
            if (evt.getNewValue().toString().equals(""))
                return;

            textFieldF.setText(evt.getNewValue().toString().substring(0, evt.getNewValue().toString().indexOf("#")));
            textFieldF.positionCaret(textFieldF.getText().length());

            userChooseDictionary.setText(evt.getNewValue().toString().substring(evt.getNewValue().toString().indexOf("#") + 1));

            showPaneWithTranslate(evt.getNewValue().toString().substring(0, evt.getNewValue().toString().indexOf("#")));

            historyOfSearch.removeElement(evt.getNewValue().toString());
            historyOfSearch.add(0, evt.getNewValue().toString());

            myClassWithText.setText("");
        });

        UserDictionary.createFirstPane(borderPanePrimary);
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
            else if (event.getButton() == MouseButton.PRIMARY &&
                    event.getEventType() == MouseEvent.MOUSE_CLICKED){
                if (!textFieldF.getText().equals("")){
                    String s = textFieldF.getText() + "#" + userChooseDictionary.getText();
                    historyOfSearch.removeElement(s);
                    historyOfSearch.add(0, s);

                    showPaneWithTranslate(textFieldF.getText());
                }
            }
        });
        find.setCursor(Cursor.HAND);

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
        });
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
        Words w = new Words("", "", "", "", "");
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

        int n = 0;
        Vector<String> vector = new Vector<>();
        for (Words words : Manager.getDictionaryWord(userChooseDictionary.getText(), word.toLowerCase())) {
            wordTextField.setText(words.getWord());
            transcriptionTextField.setText(words.getTranscription());
            vector.add(words.getTranslate().trim());

            w.setWord(words.getWord().trim());
            w.setTranscription(words.getTranscription().trim());
            w.setTranslate(w.getTranslate().trim() + " " + words.getTranslate().trim());
            w.setInfo(userChooseDictionary.getText().trim() + ":");
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

        if (! w.getWord().isEmpty() && w.getWord() != null)
            UserDictionary.dunamicAddToDictionary(w);
    }
}

class StageHistory extends ShowMinorStage {
    private static StageHistory instance = null;
    private Stage stage;
    private Vector<String> vector = null;
    private MyClassWithText word;
    private double x;

    public static StageHistory getInstance(double x, double y, Stage mainStage, ImageView imageView, Vector<String> vector,
                                           MyClassWithText word){
        if (instance == null)
            return instance = new StageHistory(x, y, mainStage, imageView, vector, word);
        else return instance;
    }

    private StageHistory(double x, double y, Stage mainStage, ImageView imageView, Vector<String> vector, MyClassWithText word){
        super();
        this.vector = vector;
        this.word = word;
        this.x = x;
        stage = new Stage();
        super.helpConstructor(x, y, stage, mainStage);
        super.setObject(imageView);

        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (MouseButton.PRIMARY == event.getButton()) {
                createStage();
                super.closeOrShow();
            }
        });
    }

    private void createStage(){
        if (vector == null) return;
        int height = 0;
        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: dimgrey;");

        Label label = new Label("History".toUpperCase());
        label.setStyle("-fx-background-color: #7d7d7d; -fx-text-fill: #ffffff; -fx-pref-width: 130");
        label.setAlignment(Pos.CENTER);
        vBox.getChildren().add(label);
        height += 17;

        if (vector.size() == 0){
            BorderPane borderPane = new BorderPane();
            Label label1 = new Label("Here will be \n      show \n your history");
            label1.setAlignment(Pos.CENTER);
            label1.setStyle("-fx-text-fill: #ffffff; -fx-pref-width: 130; -fx-font-weight: bold");
            borderPane.setCenter(label1);
            vBox.getChildren().add(borderPane);
            height += 51;
        } else {
            VBox vBox1 = new VBox();
            vBox1.autosize();
            for (String aVector : vector) {
                Label label1 = new Label(" " + aVector.substring(0, aVector.indexOf("#")));
                label1.setStyle("-fx-background-color: #909090; -fx-text-fill: #ece8e8; -fx-pref-width: 118px;");
                label1.setId(aVector.substring(aVector.indexOf("#")));
                label1.setCursor(Cursor.HAND);

                label1.addEventHandler(MouseEvent.ANY, e -> {
                    if (e.getEventType() == MouseEvent.MOUSE_ENTERED)
                        ((Label) e.getSource()).setStyle("-fx-background-color: #7d7d7d; -fx-text-fill: #ffffff; -fx-pref-width: 118px;");
                    else if (e.getEventType() == MouseEvent.MOUSE_EXITED)
                        ((Label) e.getSource()).setStyle("-fx-background-color: #909090; -fx-text-fill: #ece8e8; -fx-pref-width: 118px;");
                    else if (MouseButton.PRIMARY == e.getButton() && MouseEvent.MOUSE_CLICKED == e.getEventType()) {
                        word.setText(((Label) e.getSource()).getText().trim() + ((Label) e.getSource()).getId());
                        super.closeStudy();
                    }
                });
                vBox1.getChildren().add(label1);
                VBox.setMargin(label1, new Insets(1, 3, 0, 3));
                height += 18;
            }
            ScrollPane scrollPane = new ScrollPane(vBox1);
            scrollPane.setStyle("-fx-background: dimgrey; -fx-border-color: dimgray");
            scrollPane.setMinHeight(19);
            vBox.getChildren().addAll(scrollPane);
        }
        if (vector.size() != 0) {
            String name = System.getProperty("user.dir") +
                    "\\src\\DesktopApp\\Resource\\images\\" + "delete85.png";
            File file = new File(name);
            ImageView imageView = new ImageView();
            imageView.setFitHeight(25);
            imageView.setFitWidth(25);
            imageView.setCursor(Cursor.HAND);
            try {
                imageView.setImage(new Image(file.toURI().toURL().toString()));
                imageView.setEffect(new Lighting());
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
            imageView.addEventFilter(MouseEvent.ANY, e -> {
                if (e.getEventType() == MouseEvent.MOUSE_ENTERED)
                    imageView.setEffect(new Blend());
                else if (e.getEventType() == MouseEvent.MOUSE_EXITED)
                    imageView.setEffect(new Lighting());
                else if (MouseButton.PRIMARY == e.getButton() && MouseEvent.MOUSE_CLICKED == e.getEventType() &&
                        vector.size() != 0) {
                    vector.clear();
                    ((VBox) ((ScrollPane) vBox.getChildren().get(1)).getContent()).getChildren().clear();
                    super.closeStudy();
                }
            });
            BorderPane borderPane = new BorderPane();
            borderPane.autosize();
            borderPane.setRight(imageView);

            vBox.getChildren().add(borderPane);
            height += 25;
        }

        height += 5;
        int width = 0;
        if (vector.size() >= 9) {
            width = 13;
            super.setX(x + -13);
        }

        if (height <= 190)
            stage.setScene(new Scene(vBox, 130 + width, height));
        else
            stage.setScene(new Scene(vBox, 130 + width, 200));
    }
}

class UserDictionary {
    public static boolean isShowing = false;
    private static BorderPane borderPane, mPane;
    private static Button
            buttonViewAllOrThis = new Button("View first Dictionary"),
            buttonDelete = new Button("Delete selected"),
            buttonCreate = new Button("Create");

    public static void createFirstPane(BorderPane pane) {
        isShowing = true;
        borderPane = pane;
        createFirstPane();

        buttonDelete.setCursor(Cursor.HAND);
        buttonDelete.setTooltip(new Tooltip("Delete the first vocabulary in list"));

        buttonDelete.addEventHandler(MouseEvent.ANY, event2 -> {
            if (MouseButton.PRIMARY == event2.getButton() && MouseEvent.MOUSE_CLICKED == event2.getEventType()) {
                if (mPane.getLeft() instanceof ScrollPane) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Dialog");
                    alert.setHeaderText("Delete the dictionary - " +
                            ((Label) ((HBox) ((VBox) ((ScrollPane) mPane.getLeft()).getContent()).getChildren().get(0))
                                    .getChildren().get(0)).getText().trim().toUpperCase());
                    alert.setContentText("Are you sure?");
                    alert.initStyle(StageStyle.UTILITY);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        UserVocabularyManager.deleteVocabulary(((Label) ((HBox) ((VBox) ((ScrollPane) mPane.getLeft()).getContent()).getChildren().get(0))
                                .getChildren().get(0)).getText().trim());
                        ((VBox) ((ScrollPane) mPane.getLeft()).getContent()).getChildren().remove(0);
                        if (!((VBox) ((ScrollPane) mPane.getLeft()).getContent()).getChildren().isEmpty()) {
                            (((HBox) ((VBox) ((ScrollPane) mPane.getLeft()).getContent()).getChildren().get(0)).getChildren().get(0))
                                    .setStyle("-fx-background-color: #4175a4; -fx-text-fill: #eeeeee; -fx-font-family: cursive; -fx-pref-width: 130");
                        } else {
                            createBorderOfDictionariesNames();
                        }
                    }
                }
                else if (mPane.getLeft() instanceof TableView){
                    if (! ((TableView) mPane.getLeft()).getItems().isEmpty()
                            && ! ((TableView) mPane.getLeft()).getSelectionModel().isEmpty()){
                        UserVocabularyManager.removeWordFromVocabulary(((Label) ((HBox) ((VBox) mPane.getTop()).getChildren().get(1))
                                .getChildren().get(1)).getText().trim(), (Words)((TableView) mPane.getLeft()).getSelectionModel().getSelectedItem());
                        ((TableView) mPane.getLeft()).getItems().remove(((TableView) mPane.getLeft()).getSelectionModel().getSelectedItem());
                    }
                }
            }
        });

        buttonCreate.setCursor(Cursor.HAND);

        buttonCreate.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (MouseButton.PRIMARY == event.getButton()){
                if (buttonCreate.getText().equals("Create")) {
                    ((HBox) mPane.getBottom()).getChildren().remove(1);
                    TextField textField = new TextField();
                    textField.setPromptText("Create new vocabulary");
                    textField.setPrefWidth(200);
                    ((HBox) mPane.getBottom()).getChildren().add(0, textField);
                    ((Button) ((HBox) mPane.getBottom()).getChildren().get(1)).setText("OK");

                    textField.addEventHandler(javafx.scene.input.KeyEvent.KEY_TYPED, event1 -> {
                        if (!event1.getCharacter().equals(" "))
                        if (!Character.isLetterOrDigit(event1.getCharacter().toCharArray()[0]) || textField.getText().trim().length() > 250) {
                            event1.consume();
                        }
                    });
                }
                else {
                    String s = ((TextField) ((HBox) mPane.getBottom()).getChildren().get(0)).getText().trim() + "";
                    if (!s.isEmpty()) {
                        UserVocabularyManager.createVocabulary(s);
                        createBorderOfDictionariesNames();
                    }
                    ((HBox) mPane.getBottom()).getChildren().remove(0);
                    ((HBox) mPane.getBottom()).getChildren().add(1, buttonDelete);
                    ((Button) ((HBox) mPane.getBottom()).getChildren().get(0)).setText("Create");
                }
                HBox.setMargin(((HBox) mPane.getBottom()).getChildren().get(1), new Insets(1, 12, 1, 3));
                borderPane.setLeft(mPane);
            }
        });
    }

    public static void createFirstPane(){
        if (mPane == null) {
            mPane = new BorderPane();
            mPane.setStyle("-fx-background-color: #747474; -fx-pref-width: 250");
        }
        buttonViewAllOrThis.setText("");
        VBox vBox = new VBox();
        vBox.autosize();
        HBox
                hBoxFirst = new HBox(),
                hBoxSecond = new HBox();
        hBoxFirst.setAlignment(Pos.CENTER_RIGHT);
        hBoxFirst.autosize();
        hBoxSecond.autosize();

        Label labelOfPaneName = new Label("Your Vocabulary" + "  ");
        labelOfPaneName.setStyle("-fx-text-fill: #f0f0f0; -fx-font-weight: bold");
        hBoxFirst.getChildren().add(labelOfPaneName);

        buttonViewAllOrThis.setStyle("-fx-background-color: #9c9c9c; -fx-background-radius: 5px; -fx-text-fill: #eeeeee");
        buttonViewAllOrThis.setMinHeight(10);
        buttonViewAllOrThis.setPrefHeight(24);

        buttonViewAllOrThis.addEventHandler(MouseEvent.ANY, e -> {
            if (e.getEventType() == MouseEvent.MOUSE_ENTERED)
                buttonViewAllOrThis.setStyle("-fx-background-color: #838383; -fx-background-radius: 5px; -fx-text-fill: #eeeeee");
            else if (e.getEventType() == MouseEvent.MOUSE_EXITED)
                buttonViewAllOrThis.setStyle("-fx-background-color: #9c9c9c; -fx-background-radius: 5px; -fx-text-fill: #eeeeee");
            else if (MouseButton.PRIMARY == e.getButton() && MouseEvent.MOUSE_CLICKED == e.getEventType()) {
                if (buttonViewAllOrThis.getText().equals("View first Dictionary")) {
                    createSecondPane(((Label) ((HBox) ((VBox) ((ScrollPane) mPane.getLeft()).getContent()).getChildren().get(0))
                            .getChildren().get(0)).getText().trim());
                }
                else {
                    createBorderOfDictionariesNames();
                    borderPane.setLeft(mPane);
                }
            }
        });
        hBoxSecond.getChildren().add(buttonViewAllOrThis);
        HBox.setMargin(buttonViewAllOrThis, new Insets(0, 0, 5, 2));
        vBox.getChildren().addAll(hBoxFirst, hBoxSecond);
        mPane.setTop(vBox);

        createBorderOfDictionariesNames();

        HBox hBoxLast = new HBox();
        hBoxLast.setAlignment(Pos.CENTER_RIGHT);
        hBoxLast.autosize();

        buttonDelete.setStyle("-fx-background-color: #565656; -fx-background-radius: 5px; -fx-text-fill: #eeeeee");
        buttonCreate.setStyle("-fx-background-color: #565656; -fx-background-radius: 5px; -fx-text-fill: #eeeeee");
        hBoxLast.getChildren().addAll(buttonCreate, buttonDelete);
        HBox.setMargin(buttonDelete, new Insets(1, 11, 1, 2));
        mPane.setBottom(hBoxLast);

        borderPane.setLeft(mPane);
    }

    private static void setCurrentVocabulary(String text, int x){
        Label label = new Label();
        label.setPrefHeight(25);

        if (x == 0) {
            label.setText("  " + text + "  ");
            label.setTooltip(new Tooltip("Current dictionary"));
            label.setStyle("-fx-background-color: #068088; -fx-background-radius: 5px; -fx-text-fill: #eeeeee");
        }
        else {
            label.setText(" " + text + " ");
            label.setTooltip(new Tooltip("Open dictionary"));
            label.setStyle("-fx-background-color: #c5c247; -fx-background-radius: 5px; -fx-text-fill: #ffffff");
        }

        ((HBox) ((VBox) mPane.getTop()).getChildren().get(1)).getChildren().add(1, label);
        HBox.setMargin(label, new Insets(0, 0, 1, 5));
    }

    private static void createBorderOfDictionariesNames(){
        buttonViewAllOrThis.setText("View first Dictionary");
        if (((HBox) ((VBox) mPane.getTop()).getChildren().get(1)).getChildren().size() > 1)
        for (int i = 1; i <= ((HBox) ((VBox) mPane.getTop()).getChildren().get(1)).getChildren().size(); i++) {
            ((HBox) ((VBox) mPane.getTop()).getChildren().get(1)).getChildren().remove(1);
        }

        Vector<String> namesOfUserDictionaries = UserVocabularyManager.getAllNamesOfUserDictionaries();
        VBox vBoxContentOfUserDictionary = new VBox();
        borderPane.getChildren().remove(borderPane.getLeft());
        mPane.getChildren().removeAll(mPane.getLeft(), mPane.getCenter());

        if (namesOfUserDictionaries.isEmpty())
        {
            Label label = new Label("            Please \n" +
                                    "create your dictionary");
            label.setStyle("-fx-text-fill: #dbdbdb; -fx-font-weight: bold");
            buttonViewAllOrThis.setDisable(true);
            buttonDelete.setDisable(true);
            mPane.setCenter(label);
            borderPane.setLeft(mPane);
            return;
        }
        else {
            buttonViewAllOrThis.setDisable(false);
            buttonDelete.setDisable(false);
        }
        for (int i = namesOfUserDictionaries.size() - 1; i >= 0; i--) {
            HBox hBox = new HBox();
            hBox.setId(i + "");
            hBox.autosize();
            hBox.setAlignment(Pos.CENTER);
            Label label = new Label("  " + namesOfUserDictionaries.get(i));
            label.setPrefHeight(23);
            label.setCursor(Cursor.HAND);
            if (i == 0)
                label.setStyle("-fx-background-color: #4175a4; -fx-text-fill: #f1f1f1; -fx-font-family: cursive; -fx-pref-width: 130");
            else
                label.setStyle("-fx-background-color: #9c9c9c; -fx-text-fill: #f1f1f1; -fx-font-family: cursive; -fx-pref-width: 130");

            label.addEventHandler(MouseEvent.ANY, e -> {
                if (((VBox) ((Label) e.getSource()).getParent().getParent()).getChildren().indexOf(((Label) e.getSource()).getParent()) != 0) {
                    if (e.getEventType() == MouseEvent.MOUSE_ENTERED) {
                            label.setStyle("-fx-background-color: #7f7f7f; -fx-text-fill: #eeeeee; -fx-font-family: cursive; -fx-pref-width: 130");
                    }
                    else if (e.getEventType() == MouseEvent.MOUSE_EXITED)
                        label.setStyle("-fx-background-color: #9c9c9c; -fx-text-fill: #eeeeee; -fx-font-family: cursive; -fx-pref-width: 130");
                    else if (MouseButton.PRIMARY == e.getButton() && MouseEvent.MOUSE_CLICKED == e.getEventType()) {
                        if (e.getClickCount() == 2)
                            createSecondPane(((Label) ((HBox) ((VBox) ((ScrollPane) mPane.getLeft()).getContent()).getChildren().get(0))
                                    .getChildren().get(0)).getText().trim());
                        else {
                            UserVocabularyManager.move(((Label) e.getSource()).getText().trim());
                            VBox vBox = ((VBox) ((Label) e.getSource()).getParent().getParent());
                            HBox hBox1 = new HBox();

                            hBox1.getChildren().addAll(((HBox) ((Label) e.getSource()).getParent()).getChildren());
                            hBox1.autosize();
                            hBox1.getChildren().get(0)
                                    .setStyle("-fx-background-color: #4175a4; -fx-text-fill: #eeeeee; -fx-font-family: cursive; -fx-pref-width: 130");
                            vBox.getChildren().add(0, hBox1);

                            if (vBox.getChildren().size() > 1)
                                ((HBox) vBox.getChildren().get(1)).getChildren().get(0)
                                        .setStyle("-fx-background-color: #7f7f7f; -fx-text-fill: #eeeeee; -fx-font-family: cursive; -fx-pref-width: 130");
                        }
                    }
                }
            });

            ContextMenu contextMenu = new ContextMenu();
            contextMenu.setId(namesOfUserDictionaries.get(i));
            contextMenu.setStyle("-fx-background-color: #7d7d7d; -fx-text-fill: #ffffff; -fx-font-size: 11;");
            contextMenu.getItems().addAll(new MenuItem("Open vocabulary"), new MenuItem("Delete vocabulary"));
            label.setContextMenu(contextMenu);
            contextMenu.setOnAction(event -> {
                if (event.getTarget().hashCode() == ((ContextMenu) event.getSource()).getItems().get(0).hashCode()) {
                    if (! ((ContextMenu) event.getSource()).getId().equals(
                            ((Label) ((HBox) vBoxContentOfUserDictionary.getChildren().get(0)).getChildren().get(0)).getText().trim()))
                        setCurrentVocabulary(((ContextMenu) event.getSource()).getId(), 1);
                    createSecondPane(((ContextMenu) event.getSource()).getId());
                }
                else
                {
                    ObservableList<Node> nodes = ((VBox) ((ScrollPane) ((BorderPane) borderPane.getLeft()).getLeft()).getContent()).getChildren();
                    int positionDelete = -1;
                    for (Node node : nodes) {
                        positionDelete++;
                        if (((Label) ((HBox) node).getChildren().get(0)).getText().trim().equals(((ContextMenu) event.getSource()).getId())) {
                            ((VBox) node.getParent()).getChildren().remove(node);
                            UserVocabularyManager.deleteVocabulary(((ContextMenu) event.getSource()).getId());
                            break;
                        }
                    }
                    if (nodes.isEmpty()) {
                        createBorderOfDictionariesNames();
                    }
                    else if (positionDelete == 0) {
                        (((HBox) ((VBox) ((ScrollPane) mPane.getLeft()).getContent()).getChildren().get(0)).getChildren().get(0))
                                .setStyle("-fx-background-color: #4175a4; -fx-text-fill: #eeeeee; -fx-font-family: cursive; -fx-pref-width: 130");
                    }
                }
            });

            TextField textField = new TextField();
            textField.setEditable(true);
            textField.setId("po" + namesOfUserDictionaries.get(i));
            textField.setPromptText("Add a note to dictionary");
            textField.setStyle("-fx-background-color: #696969; -fx-text-fill: #eeeeee; -fx-pref-width: 100");

            textField.setText(UserVocabularyManager.getNote(namesOfUserDictionaries.get(i)));

            textField.addEventHandler(MouseEvent.ANY, e -> {
                if (e.getEventType() == MouseEvent.MOUSE_ENTERED)
                    textField.setStyle("-fx-background-color: #979797; -fx-text-fill: #353535; -fx-pref-width: 100");
                else if (e.getEventType() == MouseEvent.MOUSE_EXITED)
                    textField.setStyle("-fx-background-color: #696969; -fx-text-fill: #eeeeee; -fx-pref-width: 100");
            });

            textField.addEventHandler(javafx.scene.input.KeyEvent.KEY_RELEASED, event ->
                    UserVocabularyManager.changeNoteForDictionary(
                            ((TextField) event.getSource()).getId().substring(2),
                            ((TextField) event.getSource()).getText())
            );
            hBox.getChildren().addAll(label, textField);
            HBox.setMargin(label, new Insets(0, 3, 0, 0));
            HBox.setMargin(textField, new Insets(1, 2, 1, 1));
            vBoxContentOfUserDictionary.getChildren().add(0, hBox);
        }
        ScrollPane scrollPane = new ScrollPane(vBoxContentOfUserDictionary);
        scrollPane.autosize();
        scrollPane.setStyle("-fx-background: #747474");
        mPane.setLeft(scrollPane);
    }

    public static void createSecondPane(String vocabularyName){
        buttonViewAllOrThis.setText("View all vocabularies");
        setCurrentVocabulary(((Label) ((HBox) ((VBox) ((ScrollPane) mPane.getLeft()).getContent()).getChildren().get(0))
                .getChildren().get(0)).getText().trim(), 0);

        ObservableList<Words> list = FXCollections.observableArrayList(UserVocabularyManager.getVocabulary(vocabularyName));
        TableView<Words> tableView = new TableView<>(list);
        tableView.setTableMenuButtonVisible(true);
        tableView.setEditable(true);
        mPane.getChildren().remove(mPane.getLeft());

        TableColumn<Words, String> word = new TableColumn<>("Word");
        word.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getWord()));
        setCellFactory(word);

        TableColumn<Words, String> transcription = new TableColumn<>("Transcription");
        transcription.setCellValueFactory(param -> new ReadOnlyStringWrapper("[" + param.getValue().getTranscription() + "]"));
        setCellFactory(transcription);

        TableColumn<Words, String> translate = new TableColumn<>("Translate");
        translate.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getTranslate()));
        setCellFactory(translate);

        TableColumn<Words, String> userTranslate = new TableColumn<>("User Translate");
        userTranslate.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getInfo().substring(param.getValue().getInfo().indexOf(':') + 1)));
        setCellFactory(userTranslate);

        TableColumn<Words, String> note = new TableColumn<>("Note");
        note.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getNote()));
        setCellFactory(note);

        tableView.getColumns().addAll(word, transcription, translate, userTranslate, note);
        tableView.setEditable(true);

        tableView.getItems().addListener((ListChangeListener<? super Words>) c -> {
            for (Words words : c.getList()) {
                UserVocabularyManager.addWordToVocabulary(((Label) ((HBox) ((VBox) mPane.getTop()).getChildren().get(1)).getChildren()
                        .get(1)).getText().trim(), words);
            }
        });
        mPane.setLeft(tableView);
    }

    private static void setCellFactory(TableColumn column){
        column.setCellFactory(param -> new TextFieldTableCell<Words, String>(){
            @Override
            public void updateItem(String item, boolean empty) {
                if(item != null)
                    setText(item);
                else
                    setText(null);
            }
            @Override
            public void startEdit() {
                super.startEdit();

                TextField textField = new TextField(getText());
                textField.setMinWidth(1);
                textField.setPrefWidth(getPrefWidth());

                setGraphic(textField);
                textField.requestFocus();
                setText("");

                textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                    if (! newValue)
                        commitEdit(textField.getText());
                });
            }

            @Override
            public void commitEdit(String newValue) {
                super.commitEdit(newValue);
                setText(newValue);
                setGraphic(null);
                if (column.getText().equals("Word"))
                    getTableView().getItems().get(getTableRow().getIndex()).setWord(newValue);
                else if (column.getText().equals("Transcription")) {
                    String s = newValue;

                    if (s != null && ! s.isEmpty()) {
                        if (s.toCharArray()[0] == '[')
                            s = s.substring(1);
                        if (s.toCharArray()[s.length() - 1] == ']')
                            s = s.substring(0, s.lastIndexOf(']'));
                    }

                    getTableView().getItems().get(getTableRow().getIndex()).setTranscription(s);
                }
                else if (column.getText().equals("Translate"))
                    getTableView().getItems().get(getTableRow().getIndex()).setTranslate(newValue);
                else if (column.getText().equals("User Translate")){
                    String s = getTableView().getItems().get(getTableRow().getIndex()).getInfo();
                    getTableView().getItems().get(getTableRow().getIndex()).setInfo(s.substring(0, s.indexOf(':') + 1) + newValue);
                }
                else if (column.getText().equals("Note"))
                    getTableView().getItems().get(getTableRow().getIndex()).setNote(newValue);
            }
        });
    }

    public static boolean getIsShowing(){
        return isShowing;
    }

    public static void dunamicAddToDictionary(Words words){
        if (mPane != null && mPane.getLeft() instanceof TableView)
            ((TableView) mPane.getLeft()).getItems().add(0, words);
        else if (mPane != null && mPane.getLeft() instanceof ScrollPane)
            UserVocabularyManager.addWordToVocabulary(((Label) ((HBox) ((VBox) ((ScrollPane) mPane.getLeft()).getContent()).getChildren().get(0))
                    .getChildren().get(0)).getText().trim(), words);
    }
}
