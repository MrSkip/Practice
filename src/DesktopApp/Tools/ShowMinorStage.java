package DesktopApp.Tools;

import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// Class can show the minor stage over the main stage
public class ShowMinorStage {
    private Stage
            mainStage = null,
            minorStage = null;
    private double x, y, x2, y2;
    private Object object = null;

    /*
    * Constructor get the parameters:
    * x - x coordinate of minorStage
    * y - y coordinate of minorStage
    * mainStage - the main stage
    * minorStage - the minor stage (this stage show over the mainStage)
    */

    public ShowMinorStage(double x, double y, Stage minorStage, Stage mainStage){
        this.x2 = x;
        this.y2 = y;
        this.minorStage = minorStage;
        this.mainStage = mainStage;

        setStageProperties();
    }

    private void setStageProperties(){
        minorStage.initStyle(StageStyle.UNDECORATED);
        minorStage.initOwner(mainStage);

        // Add listener that can be close stage_study if window is change Width AND set new new positions at minorStage
        mainStage.xProperty().addListener((observable, oldValue, newValue) -> {
            setPosition();
            closeStudy();
        });

        // Add listener than can be close stage_study when window change Height AND set new new positions at minorStage
        mainStage.yProperty().addListener((observable, oldValue, newValue) -> {
            setPosition();
            closeStudy();
        });
    }

    // When the windows resize or first show than take position at object
    private void setPosition(){
        if (object instanceof Button){
            this.x = ((Button)object).localToScreen(Point2D.ZERO).getX();
            this.y = ((Button)object).localToScreen(Point2D.ZERO).getY();
        }
        else if (object instanceof Label){
            this.x = ((Label)object).localToScreen(Point2D.ZERO).getX();
            this.y = ((Label)object).localToScreen(Point2D.ZERO).getY();
        }
        else if (object instanceof TextField){
            this.x = ((TextField)object).localToScreen(Point2D.ZERO).getX();
            this.y = ((TextField)object).localToScreen(Point2D.ZERO).getY();
        }
    }

    // Method references button with minorStage
    public void setButton(Button button){
        object = button;
        setCloseOnObject(button.getWidth(), button.getHeight());
        button.setOnAction(event -> closeOrShow());
        setPosition();
    }

    // Method references label with minorStage
    public void setLabel(Label label){
        object = label;
        setCloseOnObject(label.getWidth(), label.getHeight());
        label.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> closeOrShow());
        setPosition();
    }

    public void setTextField(TextField textField){
        object = textField;
        setCloseOnObject(textField.getWidth(), textField.getHeight());
        setPosition();
    }

    private void setCloseOnObject(double width, double height){
        /*
        * Create EventFilter
        * If cursor over the `object` than not close mStage
        * else close minorStage
        */
        mainStage.getScene().addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if (!(mouseEvent.getScreenX() >= x && mouseEvent.getScreenX() <= x + width
                    && mouseEvent.getScreenY() >= y && mouseEvent.getScreenY() <= y + height))
                closeStudy();
        });
    }

    // Method close the minorStage (If minorStage is showing)
    public void closeStudy(){
        if (minorStage.isShowing())
            minorStage.close();
    }

    // Method close mStage (is minorStage is show) - and show minorStage (if minorStage is close)
    public void closeOrShow(){
        if (!minorStage.isShowing()){
            minorStage.setX(x + x2);
            minorStage.setY(y + y2);
            minorStage.show();
        }
        else {
            closeStudy();
        }
    }

    public Stage getMinorStage(){
        return minorStage;
    }

    public void setMinorStage(Stage stage){
        minorStage = stage;
    }
}
