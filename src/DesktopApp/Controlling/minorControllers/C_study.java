package DesktopApp.Controlling.minorControllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;

public class C_study implements Initializable{
    public BorderPane borderPanePrimary;
    public Button bt1, bt2, bt3;
    public Label l1, l2, l3;
    public ImageView listImage1, listImage2, listImage3, listImage4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        URL url = this.getClass().getResource("..\\..\\GUI\\minorScenes\\style.css");
        if (url == null) {
            System.out.println("Resource not found. Aborting.");
            System.exit(-1);
        }

        borderPanePrimary.getStylesheets().clear();
        borderPanePrimary.getStylesheets().add(url.toExternalForm());
        borderPanePrimary.setId("bgStudy");

        bt1.addEventFilter(MouseEvent.ANY, this::setButtonProperties);
        bt2.addEventFilter(MouseEvent.ANY, this::setButtonProperties);
        bt3.addEventFilter(MouseEvent.ANY, this::setButtonProperties);

        bt1.focusedProperty().addListener((observable, oldValue, newValue) -> changeFocused(bt1, newValue));
        bt2.focusedProperty().addListener((observable, oldValue, newValue) -> changeFocused(bt2, newValue));
        bt3.focusedProperty().addListener((observable, oldValue, newValue) -> changeFocused(bt3, newValue));

        l1.addEventFilter(MouseEvent.ANY, this::setLabelPropeties);
        l2.addEventFilter(MouseEvent.ANY, this::setLabelPropeties);
        l3.addEventFilter(MouseEvent.ANY, this::setLabelPropeties);

        setImages();
    }



    private void setButtonProperties(MouseEvent e){
        if (e.getEventType() == MouseEvent.MOUSE_ENTERED && !((Button) e.getSource()).isFocused())
            ((Button) e.getSource()).setStyle("-fx-background-color: #7d7d7d; -fx-text-fill: #ffffff");
        else if (e.getEventType() == MouseEvent.MOUSE_EXITED && !((Button) e.getSource()).isFocused())
            ((Button) e.getSource()).setStyle("-fx-background-color: #909090; -fx-text-fill: #ece8e8");
    }

    private void changeFocused(Button button, boolean newValue){
        if (newValue) button.setStyle("-fx-background-color: #7d7d7d; -fx-text-fill: #ffffff");
        else button.setStyle("-fx-background-color: #909090; -fx-text-fill: #ece8e8");
    }

    private void setLabelPropeties(MouseEvent e){
        if (e.getEventType() == MouseEvent.MOUSE_ENTERED)
            ((Label) e.getSource()).setStyle("-fx-background-color: #7d7d7d; -fx-text-fill: #ffffff");
        else if (e.getEventType() == MouseEvent.MOUSE_EXITED)
            ((Label) e.getSource()).setStyle("-fx-background-color: #909090; -fx-text-fill: #ece8e8");
    }

    private void setImages(){
        Image image = new Image("file:/C:/Users/Mr%20Skip/Downloads/Photos/Goodest.jpg");
        listImage1.setImage(image);
        listImage2.setImage(image);
        listImage3.setImage(image);
        listImage4.setImage(image);
    }
}