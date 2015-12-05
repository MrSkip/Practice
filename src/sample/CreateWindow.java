package sample;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Mr Skip on 05.12.2015.
 */
public class CreateWindow {
    private Stage dialog = null;
    private String title, path;
    private int x, y;
    private boolean modal = false, styling = false;

    public CreateWindow(String title, String path, int x, int y, boolean modal, boolean styling){
        this.title = title;
        this.path = path;
        this.x = x;
        this.y = y;
        this.modal = modal;
        this.styling = styling;
    }

    public Stage getDialog() {
        return dialog;
    }

    public Object createModalWindow(){
        dialog = new Stage(  );
        dialog.setTitle( title );

        if (modal) dialog.initModality( Modality.APPLICATION_MODAL );
        else dialog.initModality( Modality.NONE );

        if (!styling) dialog.initStyle(StageStyle.UNDECORATED);

        Parent root = null;
        FXMLLoader fxmlLoader = null;
        try {
            URL location = getClass().getResource(path);
            fxmlLoader = createFxmlLoader( location );
            root = fxmlLoader.load(location.openStream());
        } catch (IOException e) {
            e.printStackTrace( );
        }

        assert root != null;
        dialog.setScene( new Scene( root, x, y ) );

        return fxmlLoader.getController();
    }

    public FXMLLoader createFxmlLoader(URL location){
        return new FXMLLoader( location, null, new JavaFXBuilderFactory(  ), null, Charset.forName( FXMLLoader.DEFAULT_CHARSET_NAME ) );
    }
}
