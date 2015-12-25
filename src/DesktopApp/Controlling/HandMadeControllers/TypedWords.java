package DesktopApp.Controlling.HandMadeControllers;

import DesktopApp.Tools.MyClassWithText;
import DesktopApp.Tools.ShowMinorStage;
import DesktopApp.Tools.Vocabulary.Manager;
import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Vector;

public class TypedWords extends ShowMinorStage{
    private static TypedWords instance = null;

    private Stage stage;
    private VBox vBox;
    private int upDown;
    private Label label;
    private TextField textField;
    private MyClassWithText word;

    public static TypedWords getInstance(double x, double y, Stage mainStage, Label label, TextField textField, MyClassWithText word){
        if (instance == null)
            return instance = new TypedWords(x, y, mainStage, label, textField, word);
        else return instance;
    }

    private TypedWords(double x, double y, Stage mainStage, Label label, TextField textField, MyClassWithText word){
        super(x, y, new Stage(), mainStage);

        this.word = word;
        this.label = label;
        this.textField = textField;

        stage = new Stage();
        vBox = new VBox();
        upDown = 0;

        stage.setScene(new Scene(vBox, 190, (vBox.getChildren().size() - 1) * 18 + 20));

        super.setMinorStage(stage);
        super.setTextField(textField);
        setTextFieldListener();
    }

    private void setTextFieldListener(){
        textField.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode().getName().equals("Enter") || textField.getText().equals("")) {
                super.closeStudy();
            }
            else if (event.getCode().getName().equals("Up") ||
                    event.getCode().getName().equals("Down")) {
                dS(event.getCode().getName());
                return;
            }
            else if (event.getText().length() != 0 && !stage.isShowing()) {
                super.closeOrShow();
            }

            setSearchWords(label.getText(),textField.getText());
            super.getMainStage().requestFocus();
        });
    }

    public void setSearchWords(String userChooseDictionary, String text){
        Vector<String> vector = Manager.getTypedWord(userChooseDictionary.trim().toLowerCase(), text.toLowerCase());
        Vector<String> goldVector = new Vector<>();

        assert vector != null;
        if (!vector.isEmpty())
            goldVector.add(vector.firstElement());

        for (String aVector : vector) {
            if (!aVector.equals(goldVector.lastElement()))
                goldVector.add(aVector);

            if (goldVector.size() == 10) break;
        }

        if (!vBox.getChildren().isEmpty()){
            int n = vBox.getChildren().size();
            for (int i = 0; i < n; i++) {
                vBox.getChildren().remove(0);
            }
        }

        Label firstLabel = new Label(text);
        firstLabel.setStyle(
                        "    -fx-font-style: normal;" +
                        "    -fx-font-size: 14;" +
                        "    -fx-pref-width: 190;");
        firstLabel.setAlignment(Pos.CENTER);

        vBox.getChildren().add(firstLabel);

        if (vector.isEmpty()) {
            stage.setHeight((vBox.getChildren().size() - 1) * 18 + 20);
            return;
        }
        else if (vBox.getChildren().size() == 1)
            stage.setHeight((vBox.getChildren().size() - 1) * 18 + 20);

        FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
        for (String aGoldVector : goldVector) {

            Label labelCorrect = new Label(text);

            HBox hBox = new HBox();

            Label label = new Label(aGoldVector.substring(labelCorrect.getText().length(), aGoldVector.length()));
            label.setStyle("-fx-background-color: #b3b3b3; -fx-pref-height: 17");
            label.setPrefWidth(190 - fontLoader.computeStringWidth(labelCorrect.getText(), labelCorrect.getFont()) - 5);

            hBox.getChildren().addAll(labelCorrect, label);
            vBox.getChildren().add(hBox);

            VBox.setMargin(hBox, new Insets(1,1,0,2));

            label.addEventFilter(MouseEvent.ANY, event -> setStyle(event, label, labelCorrect));
            labelCorrect.addEventFilter(MouseEvent.ANY, event -> setStyle(event, label, labelCorrect));
        }

        vBox.setStyle("-fx-background-color: #cacaca");
        stage.setHeight((vBox.getChildren().size() - 1) * 18 + 20);
        upDown = 0;
    }

    private void setStyle(MouseEvent event, Label label, Label labelCorrect){
        if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
            label.setStyle("-fx-background-color: #dedede;");
            labelCorrect.setStyle("-fx-background-color: #dedede;");
        }
        else if (event.getEventType() == MouseEvent.MOUSE_EXITED){
            labelCorrect.setStyle("-fx-background-color: #cacaca;");
            label.setStyle("-fx-background-color: #b3b3b3;");
        }
        else if (event.getEventType() == MouseEvent.MOUSE_CLICKED &&
                event.getButton() == MouseButton.PRIMARY)
            selected(labelCorrect.getText() + label.getText());
    }

    private void selected(String word){
        this.word.setText(word);
        super.closeOrShow();
    }

    public void dS(String upDown){
        if (vBox.getChildren().size() == 0 || vBox.getChildren().size() == 1) return;

        int currentStep = this.upDown;

        if (upDown.equals("Up")) {
            this.upDown--;
            textField.positionCaret(textField.getText().length());
        }
        else
            this.upDown++;

        if (this.upDown < 0) this.upDown = vBox.getChildren().size() - 1;
        else if (this.upDown >= vBox.getChildren().size()) this.upDown = 0;

        if (currentStep == 0) {
            vBox.getChildren().get(currentStep).setStyle(
                    "-fx-background-color: #b3b3b3;" +
                            "-fx-font-style: normal;" +
                            "-fx-font-size: 14;" +
                            "-fx-pref-width: 190;");
        }
        else if (currentStep > 0) {
            ((HBox) vBox.getChildren().get(currentStep)).getChildren().get(0).setStyle("-fx-background-color: #cacaca;");
            ((HBox) vBox.getChildren().get(currentStep)).getChildren().get(1).setStyle("-fx-background-color: #b3b3b3;");
        }

        if (this.upDown == 0) {
            vBox.getChildren().get(this.upDown).setStyle(
                    "-fx-background-color: #dedede;" +
                            "-fx-font-style: normal;" +
                            "-fx-font-size: 14;" +
                            "-fx-pref-width: 190;");
        }
        else {
            ((HBox) vBox.getChildren().get(this.upDown)).getChildren().get(0).setStyle("-fx-background-color: #dedede;");
            ((HBox) vBox.getChildren().get(this.upDown)).getChildren().get(1).setStyle("-fx-background-color: #dedede;");
        }
    }
}
