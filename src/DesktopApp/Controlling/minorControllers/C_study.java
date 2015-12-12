package DesktopApp.Controlling.minorControllers;

import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;

public class C_study implements Initializable{
    private boolean mouseIsOver = false;
    public BorderPane borderPanePrimary;

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

        borderPanePrimary.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> mouseIsOver = true);
        borderPanePrimary.addEventFilter(MouseEvent.MOUSE_EXITED, event -> mouseIsOver = false);
    }



    public boolean isMouseIsOver() {
        return mouseIsOver;
    }
}
