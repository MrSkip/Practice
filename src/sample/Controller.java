package sample;

import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Button back, next, see;
    public RadioButton radio1, radio2, radio3;
    public boolean BACK = false, NEXT = false, SEE = false, RADIO1 = false, RADIO2 = false, RADIO3 = false;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setProperties();
    }

    private void setProperties(){
        back.setTooltip( new Tooltip("Закриття програми"));
        see.setTooltip(new Tooltip("Виклик довідки"));
        radio3.setTooltip(new Tooltip("Дозволяє пропустити даний крок\nпри наступному завантаженні програми"));

        back.addEventHandler(Event.ANY, this::setButtonProperties);
        see.addEventHandler(Event.ANY, this::setButtonProperties);
        next.addEventHandler(Event.ANY, this::setButtonProperties);

        radio1.addEventHandler(Event.ANY, this::setRadioButtonProperties);
        radio2.addEventHandler(Event.ANY, this::setRadioButtonProperties);
        radio3.addEventHandler(Event.ANY, this::setRadioButtonProperties);
    }

    private void setButtonProperties(Event event){
        if (event.getEventType() == MouseEvent.MOUSE_ENTERED ||
                (event.getEventType() == KeyEvent.KEY_RELEASED && (((KeyEvent) event).getCode() == KeyCode.TAB))){
            ((Button) event.getSource()).setStyle("-fx-opacity: 1;");
        }
        else if (event.getEventType() == MouseEvent.MOUSE_EXITED ||
                (event.getEventType() == KeyEvent.KEY_PRESSED && (((KeyEvent) event).getCode() == KeyCode.TAB))){
            ((Button) event.getSource()).setStyle("-fx-opacity: 0.66;");
        }
        else if (event.getEventType() == MouseEvent.MOUSE_CLICKED && ((MouseEvent)event).getButton() == MouseButton.PRIMARY ||
                (event.getEventType() == KeyEvent.KEY_PRESSED && (((KeyEvent) event).getCode() == KeyCode.ENTER))){
            if (event.getSource() == next){
                System.out.println("Clicked button NEXT - next step (close this window)");
                NEXT = true;
                ((Stage) next.getScene().getWindow()).close();
            }
            else if (event.getSource() == back){
                BACK = true;
                ((Stage) back.getScene().getWindow()).close();
            }
            else {
                System.out.println("Get information");
                SEE = true;
            }
        }
    }

    private void setRadioButtonProperties(Event event){

        if (event.getEventType() == MouseEvent.MOUSE_ENTERED ||
                (event.getEventType() == KeyEvent.KEY_RELEASED && (((KeyEvent) event).getCode() == KeyCode.TAB))){
            if (event.getSource() != radio3) ((RadioButton) event.getSource()).setStyle("-fx-pref-width: 170; -fx-font-size: 23;");
        }
        else if (event.getEventType() == MouseEvent.MOUSE_EXITED ||
                (event.getEventType() == KeyEvent.KEY_PRESSED && (((KeyEvent) event).getCode() == KeyCode.TAB))){
            if (event.getSource() != radio3) ((RadioButton) event.getSource()).setStyle("-fx-font-size: 19; -fx-pref-width: 142;");
        }
        else if ((event.getEventType() == MouseEvent.MOUSE_CLICKED && ((MouseEvent)event).getButton() == MouseButton.PRIMARY) ||
                (event.getEventType() == KeyEvent.KEY_PRESSED && (((KeyEvent) event).getCode() == KeyCode.ENTER))){
            if (event.getSource() == radio1){
                System.out.println("Clicked button RADIO1");
                RADIO1 = true;
            }
            else if (event.getSource() == radio2){
                System.out.println("Clicked button RADIO2");
                RADIO2 = true;
            }
            else {
                RADIO3 = false;
                System.out.println("Clicked button RADIO3");
            }
        }
    }
}


