package DesktopApp.Controlling.minorControllers;

import DesktopApp.Tools.ReadLog;
import javafx.event.EventType;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

public class C_study implements Initializable{
    public BorderPane borderPanePrimary;
    public Button bt1, bt2, bt3;
    public Label firstBt;
    public Pane pane;
    public VBox vBoxBt;
    public ImageView add;
    public TextField textField;
    private URL url;
    private ImageView [] images;

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

        firstBt.setText("Teach languages with web".toUpperCase());
        firstBt.setStyle("-fx-text-fill: #ffffff;");
        firstBt.setAlignment(Pos.CENTER);

        bt1.addEventFilter(MouseEvent.ANY, this::setButtonProperties);
        bt2.addEventFilter(MouseEvent.ANY, this::setButtonProperties);
        bt3.addEventFilter(MouseEvent.ANY, this::setButtonProperties);

        add.addEventFilter(MouseEvent.ANY, this::setAdd);

        bt1.focusedProperty().addListener((observable, oldValue, newValue) -> changeFocused(bt1, newValue));
        bt2.focusedProperty().addListener((observable, oldValue, newValue) -> changeFocused(bt2, newValue));
        bt3.focusedProperty().addListener((observable, oldValue, newValue) -> changeFocused(bt3, newValue));

        setLabel();
        referenceImages();
        setImages(1);
        setAddImage();
    }

    private void setButtonProperties(MouseEvent e){
        if (e.getEventType() == MouseEvent.MOUSE_ENTERED && !((Button) e.getSource()).isFocused())
            ((Button) e.getSource()).setStyle("-fx-background-color: #7d7d7d; -fx-text-fill: #ffffff");
        else if (e.getEventType() == MouseEvent.MOUSE_EXITED && !((Button) e.getSource()).isFocused())
            ((Button) e.getSource()).setStyle("-fx-background-color: #909090; -fx-text-fill: #ece8e8");
    }

    private void changeFocused(Button button, boolean newValue){
        if (newValue) {
            button.setStyle("-fx-background-color: #7d7d7d; -fx-text-fill: #ffffff");
            if (!button.getText().trim().equals("3"))
                setImages(Integer.parseInt(button.getText().trim()));
            else
                setUserSites();
        }
        else button.setStyle("-fx-background-color: #909090; -fx-text-fill: #ece8e8");
    }

    private void setLabelProperties(MouseEvent e){
        if (e.getEventType() == MouseEvent.MOUSE_ENTERED)
            ((Label) e.getSource()).setStyle("-fx-background-color: #7d7d7d; -fx-text-fill: #ffffff");
        else if (e.getEventType() == MouseEvent.MOUSE_EXITED)
            ((Label) e.getSource()).setStyle("-fx-background-color: #909090; -fx-text-fill: #ece8e8");
    }

    // Set the image into pane (part - is 1 ot 2) from images[1..4] or images[4..8]
    private void setImages(int part){
        int countOfLabel;
        if (ReadLog.getStudyRecentLog().size() == 0 ||
                ReadLog.getStudyRecentLog().size() == 1)
            countOfLabel = 0;
        else countOfLabel = ReadLog.getStudyRecentLog().size();

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.autosize();
        pane.getChildren().remove(0);
        pane.getChildren().add(vBox);

        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();

        hBox1.autosize();
        hBox2.autosize();

        for (ImageView image : images) {
            image.fitWidthProperty().set(103);
            image.fitHeightProperty().set(110 - countOfLabel * (countOfLabel==2?5:6));
        }

        for (int i = (part==1?0:4); i < (part==1?2:6); i++) {
            hBox1.getChildren().add(images[i]);
            hBox2.getChildren().add(images[i + 2]);
        }

        vBox.getChildren().addAll(hBox1, hBox2);
        for (int i = (part==1?0:4); i < (part==1?2:6); i++) {
            HBox.setMargin(images[i], new Insets(5, 3, 5, 2));
            HBox.setMargin(images[i + 2], new Insets(0, 3, 5, 2));
        }
        VBox.setMargin(pane, new Insets(0,0,1,1));
    }

    // Reference images with array of ImageView "images[]"
    private void referenceImages(){
        String path = System.getProperty("user.dir") +
                "\\src\\DesktopApp\\Resource\\linksImage\\";

        images = new ImageView[8];

        for (int i = 0; i < ReadLog.getStudyLog().size(); i++){
            String s = path + ReadLog.getStudyLog().get(i).substring(
                    ReadLog.getStudyLog().get(i).indexOf("://") + 3,
                    ReadLog.getStudyLog().get(i).lastIndexOf("/")) + ".jpg";

            File f = new File(s);
            try {
                images[i] = new ImageView(new Image(f.toURI().toURL().toString()));
                images[i].setId(ReadLog.getStudyLog().get(i));
                images[i].setCursor(Cursor.HAND);
                images[i].setEffect(new Lighting());

                images[i].addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        String name = ((ImageView)event.getSource()).getId();
                        openInDefaultBrowser(name.substring(name.indexOf("://") + 3, name.lastIndexOf("/")));
                    }
                });

                images[i].addEventFilter(MouseEvent.ANY, this::setImagesProperties);

                Tooltip tooltip = new Tooltip(images[i].getId().substring(
                        images[i].getId().indexOf("://") + 3,
                        images[i].getId().lastIndexOf("/")));
                tooltip.setStyle("-fx-font-size: 13;");
                Tooltip.install(images[i], tooltip);
            } catch (Exception e){
                System.out.println("Error with - " + s + "\n" + e);
            }
        }
    }

    // Change effect for image when cursor is entered ot exited
    private void setImagesProperties(MouseEvent e){
        if (e.getEventType() == MouseEvent.MOUSE_ENTERED)
            ((ImageView) e.getSource()).setEffect(new Blend());
        else if (e.getEventType() == MouseEvent.MOUSE_EXITED)
            ((ImageView) e.getSource()).setEffect(new Lighting());
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
            l1.addEventFilter(MouseEvent.ANY, this::setLabelProperties);
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

                lb.addEventFilter(MouseEvent.ANY, this::setLabelProperties);

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
            String http = "";
            if (!name.startsWith("http")) http = "http://";

            Desktop.getDesktop().browse(new URL((http + name)).toURI());
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

    // Create the page were the user can add they own sites
    private void setUserSites(){
        // If user don`t have sites than show "Hear will be show your sites"
        if (ReadLog.getStudyUserSites().size() == 0){
            Label label = new Label("Hear will be\n     show\n your sites");
            label.setAlignment(Pos.CENTER);
            label.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 15; -fx-font-weight: bold;");
            label.setPrefHeight(170);
            label.setPrefWidth(213);

            pane.getChildren().remove(0);
            pane.getChildren().add(label);
        }
        //If user have sites than show them
        else {
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setPrefWidth(209);
            scrollPane.setPrefHeight(237 - ReadLog.getStudyRecentLog().size() * 17);
            scrollPane.setStyle("-fx-background: #696969");

            VBox vBox = new VBox();
            vBox.autosize();

            Label pref = new Label(" " + "User links" + ":");
            pref.setAlignment(Pos.CENTER);
            pref.setId("newLabel123");
            pref.setStyle("-fx-background-color: #7e7e7e; -fx-text-fill: #ffffff;");

            vBox.getChildren().add(pref);

            // Add label with user`s sites
            ReadLog.getStudyUserSites().forEach(s -> {
                Label label = new Label(" " + s);
                label.setId("newLabel123");
                label.setCursor(Cursor.HAND);

                ContextMenu contextMenu = new ContextMenu();
                contextMenu.setId(s);
                contextMenu.setStyle("-fx-background-color: #7d7d7d; -fx-text-fill: #ffffff; -fx-font-size: 11;");
                contextMenu.getItems().addAll(
                        new MenuItem("Delete link"),
                        new MenuItem("Show in browser"),
                        new MenuItem("Show in default browser"));

                label.setContextMenu(contextMenu);

                // Delete user site and upgrade window
                contextMenu.setOnAction(event -> {
                    if (event.getTarget().hashCode() == ((ContextMenu) event.getSource()).getItems().get(2).hashCode())
                        openInDefaultBrowser(((ContextMenu) event.getSource()).getId());
                    else if (event.getTarget().hashCode() == ((ContextMenu) event.getSource()).getItems().get(0).hashCode()){
                        Vector<String> vector = ReadLog.getStudyUserSites();
                        vector.removeElement(((ContextMenu) event.getSource()).getId());

                        ReadLog.setStudyUserSites(vector);

                        for (int i = 1; i < vBox.getChildren().size(); i++) {
                            if (event.getSource().equals(((Label)vBox.getChildren().get(i)).getContextMenu()))
                                vBox.getChildren().remove(i);
                        }
                    }
                });

                label.addEventFilter(MouseEvent.ANY, this::setLabelProperties);

                label.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY)
                        openInDefaultBrowser(((Label) event.getSource()).getText().trim());
                });

                vBox.getChildren().add(label);
            });
            pane.getChildren().remove(0);
            scrollPane.autosize();
            scrollPane.setContent(vBox);
            pane.getChildren().add(scrollPane);

            vBox.getChildren().forEach(node -> VBox.setMargin(node, new Insets(1,2,1,3)));
            VBox.setMargin(pane, new Insets(7,0,1,1));
        }
    }

    private void setAdd(MouseEvent event){
        if (event.getEventType() == MouseEvent.MOUSE_ENTERED ||
                event.getEventType() == MouseEvent.MOUSE_EXITED){
            String path = System.getProperty("user.dir") +
                    "\\src\\DesktopApp\\Resource\\images\\";
            String imageName;

            if (event.getEventType() == MouseEvent.MOUSE_ENTERED)
                imageName = "left224(2).png";
            else
                imageName = "left224.png";

            File f = new File(path + imageName);
            try {
                add.setImage(new Image(f.toURI().toURL().toString()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        else if (event.getButton() == MouseButton.PRIMARY &&
                event.getEventType() == MouseEvent.MOUSE_CLICKED){
            if (!textField.getText().isEmpty()){
                Vector<String> vector = ReadLog.getStudyUserSites();
                for (String aVector : vector) {
                    if (aVector.equals(textField.getText())) {
                        textField.setText("");
                        return;
                    }
                }
                vector.add(0,textField.getText());
                textField.setText("");
            }
        }
    }

    private void setAddImage(){
        String path = System.getProperty("user.dir") +
                "\\src\\DesktopApp\\Resource\\images\\"
                + "left224.png";

        File f = new File(path);
        try {
            add.setImage(new Image(f.toURI().toURL().toString()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}