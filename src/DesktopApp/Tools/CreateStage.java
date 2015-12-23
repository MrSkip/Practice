package DesktopApp.Tools;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.management.Notification;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

/*
* Class create a window from .fxml files
*/
public class CreateStage {
    private Stage stage = null;
    private String title, path;
    private int x, y;
    private boolean modal = false, styling = false;
    private FXMLLoader fxmlLoader = null;

    /*
    * Constructor get the parameters of:
    *   title - name of window
    *   path - path to .fxml file
    *   x - width of window
    *   y - height of window
    *   modal - modal or not modal window (if modal the user can`t do something with parent window)
    *   styling - add or not add default windows border
    */

    public CreateStage(String title, String path, int x, int y, boolean modal, boolean styling){
        this.title = title;
        this.path = path;
        this.x = x;
        this.y = y;
        this.modal = modal;
        this.styling = styling;
        createScene();
    }

    /*
    * Method return the stage of created windows form
    */

    public Stage getStage() {
        return stage;
    }

    /*
    * Method return the current Controller for stage
    */

    public Object getController(){
        return fxmlLoader.getController();
    }

    /*
    * Method that can create a stage
    */

    private void createScene(){

        stage = new Stage(  );
        stage.setTitle( title );

        if (!modal) stage.initModality( Modality.WINDOW_MODAL );
        else stage.initModality( Modality.NONE );

        if (!styling) {
            stage.initStyle(StageStyle.UTILITY);
        }
        Parent root = null;
        try {
            URL location = getClass().getResource(path);
            fxmlLoader = createFxmlLoader( location );
            root = fxmlLoader.load(location.openStream());
        } catch (Exception e) {
            System.out.println("Error:");
            e.printStackTrace( );
        }

        assert root != null;
        stage.setScene( new Scene( root, x, y ) );
    }

    private FXMLLoader createFxmlLoader(URL location){
        return new FXMLLoader( location, null, new JavaFXBuilderFactory(  ), null, Charset.forName( FXMLLoader.DEFAULT_CHARSET_NAME ) );
    }
}
