package DesktopApp.Controlling.HandMadeControllers;

import DesktopApp.Tools.Vocabulary.Manager;
import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Vector;

public class TypedWords {
    private static TypedWords instance = null;

    private Stage stage;
    private VBox vBox;
    private Vector<String> vector;

    public static TypedWords getInstanse(){
        if (instance == null)
            return instance = new TypedWords();
        else return instance;
    }

    private TypedWords(){
        stage = new Stage();
        vBox = new VBox();

        stage.setScene(new Scene(vBox, 190, (vBox.getChildren().size() - 1) * 18 + 20));
    }

    public void setSearchWords(String userChooseDictionary, String text){
        vector = Manager.getTypedWord(userChooseDictionary.trim().toLowerCase(), text);
        Vector<String> goldVector = new Vector<>();

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
                "-fx-background-color: #ebebeb;" +
                        "    -fx-font-style: normal;" +
                        "    -fx-font-size: 14;" +
                        "    -fx-pref-width: 190;");
        firstLabel.setAlignment(Pos.CENTER);

        vBox.getChildren().add(firstLabel);

        if (vector.isEmpty()) return;
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
        }

        vBox.setStyle("-fx-background-color: #cacaca");
        stage.setHeight((vBox.getChildren().size() - 1) * 18 + 20);
    }

    public void dS(){
        ((VBox) stage.getScene().getRoot()).getChildren().remove(0);
        vBox.getChildren().remove(0);
    }

    public Stage getStage(){
        return stage;
    }
}
