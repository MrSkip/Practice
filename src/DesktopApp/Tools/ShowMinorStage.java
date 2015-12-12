package DesktopApp.Tools;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// Class can show the minor stage over the main stage
public class ShowMinorStage {

    private Button button = null;
    private Stage stage = null,
            mStage = null;

    /*
    * Constructor get the parameters:
    * button - the button that can call a mStage
    * stage - the main stage
    * mStage - the minor stage (this stage shot over the main stage)
    */

    public ShowMinorStage(Button button, Stage stage, Stage mStage){
        this.button = button;
        this.stage = stage;
        this.mStage = mStage;

        setProperties();
    }

    private void setProperties(){
        mStage.initStyle(StageStyle.UNDECORATED);
        mStage.initOwner(stage);

        /*
        * Create EventFilter
        * If cursor over the `button` than not close mStage
        * else close mStage
        */
        stage.getScene().addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if (!(mouseEvent.getX() >= button.getLayoutX() && mouseEvent.getX() <= button.getLayoutX() + button.getWidth()
                    && mouseEvent.getY() >= button.getLayoutY() && mouseEvent.getY() <= button.getLayoutY() + button.getHeight()))
                closeStudy();
        });

        // Add listener that can be close stage_study if window is change Width
        stage.xProperty().addListener((observable, oldValue, newValue) -> {
            closeStudy();
        });

        // Add listener than can be close stage_study when window change Height
        stage.yProperty().addListener((observable, oldValue, newValue) -> {
            closeStudy();
        });
    }

    // Method close the mStage (If mStage is showing)
    private boolean closeStudy(){
        if (mStage.isShowing())
            mStage.close();
        return mStage.isShowing();
    }

    // Method close mStage (is mStage is show) - and show mStage (if mStage is close)
    public void closeOrShow(){
        if (closeStudy()){
            mStage.setX(stage.getX() + 9);
            mStage.setY(stage.getY() + 30 + button.getHeight());
            mStage.show();
        }
    }
}
