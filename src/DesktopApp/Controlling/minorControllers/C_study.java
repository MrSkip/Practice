package DesktopApp.Controlling.minorControllers;

import DesktopApp.Tools.ReadLog;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.awt.*;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.Vector;

public class C_study implements Initializable{
    public BorderPane borderPanePrimary;
    public Button bt1, bt2, bt3;
    public ImageView listImage1, listImage2, listImage3, listImage4;
    public VBox vBoxBt;
    private URL url;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        url = this.getClass().getResource("..\\..\\GUI\\minorScenes\\style.css");
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

//        l1.addEventFilter(MouseEvent.ANY, this::setLabelPropeties);
//        l2.addEventFilter(MouseEvent.ANY, this::setLabelPropeties);
//        l3.addEventFilter(MouseEvent.ANY, this::setLabelPropeties);
        setLabel();
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
//        listImage1.setImage(new Image("file:/C:/Users/Mr%20Skip/Downloads/img/lingvoLeo.jpg"));
//        listImage2.setImage(new Image("file:/C:/Users/Mr%20Skip/Downloads/img/duolingo-header-664x374.jpg"));
//        listImage3.setImage(new Image("file:/C:/Users/Mr%20Skip/Downloads/img/elllo.org.jpg"));
//        listImage4.setImage(new Image("file:/C:/Users/Mr%20Skip/Downloads/img/liuro.com.ua.jpg"));
        int countOfLabel = ReadLog.getStudyRecentLog().size();

    }

    // Create label of last open links
    private void setLabel(){
        if (ReadLog.getStudyRecentLog().isEmpty()){
            Label l1 = new Label();
            l1.setAlignment(Pos.CENTER);
            l1.setText("Will be see the last open user links");
            l1.getStylesheets().add(url.toExternalForm());
            l1.setId("label123");
            vBoxBt.getChildren().add(1, l1);
            l1.addEventFilter(MouseEvent.ANY, this::setLabelPropeties);
        }
        else {
            ReadLog.getStudyRecentLog().forEach(s -> {
                Label lb = new Label(" " + s);

                ContextMenu contextMenu = new ContextMenu();
                contextMenu.setId(s);
                contextMenu.setStyle("-fx-background-color: #7d7d7d; -fx-text-fill: #ffffff; -fx-font-size: 11;");
                contextMenu.getItems().addAll(new MenuItem("Show in browser"), new MenuItem("Show in default browser"));

                contextMenu.setOnAction(event -> {
                    if (event.getTarget().hashCode() == ((ContextMenu) event.getSource()).getItems().get(1).hashCode())
                        openInDefaultBrowser(((ContextMenu) event.getSource()).getId());
                });

                lb.setContextMenu(contextMenu);
                lb.getStylesheets().add(url.toExternalForm());
                lb.setId("label123");

                vBoxBt.getChildren().add(vBoxBt.getChildren().size() - 1, lb);
                VBox.setMargin(lb, new Insets(1,0,0,0));

                lb.addEventFilter(MouseEvent.ANY, this::setLabelPropeties);

                lb.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY)
                        openInDefaultBrowser(((Label) event.getSource()).getText().trim());
                });

                lb.setCursor(Cursor.HAND);
            });
        }
    }

    // Open default windows browser
    private void openInDefaultBrowser(String name){
        try {
            Desktop.getDesktop().browse(new URL(("http://" + name)).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add to the top the last open links
        Vector<String> vector = ReadLog.getStudyRecentLog();

        final boolean[] bl = {false};
        vector.forEach(s -> {
            if (name.equals(s)) bl[0] = true;
        });
        if (bl[0]) vector.remove(name);
        vector.add(0, name);
        if (vector.size() > 3) vector.remove(3);
        ReadLog.setStudyRecentLog(vector);

        while (vBoxBt.getChildren().size() >= 3){
            vBoxBt.getChildren().remove(vBoxBt.getChildren().size() - 2);
        }
        setLabel();
    }
}