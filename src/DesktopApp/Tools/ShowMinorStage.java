package DesktopApp.Tools;

import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// Class can show the minor stage over the main stage
public class ShowMinorStage {
    private Stage mainStage = null,
            minorStage = null;
    private double x, y;

    /*
    * Constructor get the parameters:
    * button - the button that can call a mStage
    * stage - the main stage
    * mStage - the minor stage (this stage show over the main stage)
    */

    public ShowMinorStage(double x, double y, Stage minorStage, Stage mainStage){
        this.x = x;
        this.y = y;
        this.minorStage = minorStage;
        this.mainStage = mainStage;

        setStageProperties();
    }

    private void setStageProperties(){
        minorStage.initStyle(StageStyle.UNDECORATED);
        minorStage.initOwner(mainStage);

        // Add listener that can be close stage_study if window is change Width
        mainStage.xProperty().addListener((observable, oldValue, newValue) -> {
            closeStudy();
        });

        // Add listener than can be close stage_study when window change Height
        mainStage.yProperty().addListener((observable, oldValue, newValue) -> {
            closeStudy();
        });
    }

    public void setButton(Button button){
        setCloseOnObject(button.getLayoutX(), button.getLayoutY(), button.getWidth(), button.getHeight());
        button.setOnAction(event -> closeOrShow());
    }

    public void setLabel(Label label){
        setCloseOnObject(label.getLayoutX(), label.getLayoutY(), label.getWidth(), label.getHeight());

        // set listener on MouseClicked
        label.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.PRIMARY)
                closeOrShow();
        });
    }

    private void setCloseOnObject(double layoutX, double layoutY, double width, double height){
        /*
        * Create EventFilter
        * If cursor over the `button` than not close mStage
        * else close mStage
        */
        mainStage.getScene().addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if (!(mouseEvent.getX() >= layoutX && mouseEvent.getX() <= layoutX + width
                    && mouseEvent.getY() >= layoutY && mouseEvent.getY() <= layoutY + height))
                closeStudy();
        });
    }

    // Method close the mStage (If mStage is showing)
    private void closeStudy(){
        if (minorStage.isShowing())
            minorStage.close();
    }

    // Method close mStage (is mStage is show) - and show mStage (if mStage is close)
    public void closeOrShow(){
        if (!minorStage.isShowing()){
            minorStage.setX(x);
            minorStage.setY(y);
            minorStage.show();
        }
        else closeStudy();
    }
}
