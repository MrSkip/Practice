package DesktopApp.Controlling;

import javafx.fxml.Initializable;
import javafx.scene.control.Menu;

import java.net.URL;
import java.util.ResourceBundle;

public class C_main implements Initializable{
    public Menu study;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        study.setOnAction(e -> {
            System.out.println("1");
        });
    }
}
