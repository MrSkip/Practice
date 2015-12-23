package DesktopApp.Tools;

import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// Class can show the minor stage over the main stage
public class ShowMinorStage {
    private Stage
            mainStage = null,
            minorStage = null;
    private double x, y;

    /*
    * Constructor get the parameters:
    * x - x coordinate of minorStage
    * y - y coordinate of minorStage
    * mainStage - the main stage
    * minorStage - the minor stage (this stage show over the mainStage)
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

    // Method references button with minorStage
    public void setButton(Button button){
        setCloseOnObject(
                button.localToScreen(Point2D.ZERO).getX(),
                button.localToScreen(Point2D.ZERO).getY(),
                button.getWidth(), button.getHeight());
        button.setOnAction(event -> closeOrShow());
    }

    // Method references label with minorStage
    public void setLabel(Label label){
        setCloseOnObject(
                label.localToScreen(Point2D.ZERO).getX(),
                label.localToScreen(Point2D.ZERO).getY(),
                label.getWidth(), label.getHeight());

        // set listener on MouseClicked
        label.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                closeOrShow();
        });
    }

    private void setCloseOnObject(double layoutX, double layoutY, double width, double height){
        /*
        * Create EventFilter
        * If cursor over the `object` than not close mStage
        * else close minorStage
        */
        mainStage.getScene().addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if (!(mouseEvent.getScreenX() >= layoutX && mouseEvent.getScreenX() <= layoutX + width
                    && mouseEvent.getScreenY() >= layoutY && mouseEvent.getScreenY() <= layoutY + height))
                closeStudy();
        });
    }

    // Method close the minorStage (If minorStage is showing)
    private void closeStudy(){
        if (minorStage.isShowing())
            minorStage.close();
    }

    // Method close mStage (is minorStage is show) - and show minorStage (if mStage is close)
    public void closeOrShow(){
        System.out.println("closeOrShow");
        if (!minorStage.isShowing()){
            minorStage.setX(x);
            minorStage.setY(y);
            minorStage.show();
            System.out.println("-show");
        }
        else {
            System.out.println("-close");
            closeStudy();
        }
    }
}
