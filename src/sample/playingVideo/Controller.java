package sample.playingVideo;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public MediaView mediaView;
    public ProgressBar progress;
    public Label next;
    public HBox hBox1;
    public BorderPane borderPane1;

    private MediaPlayer mediaPlayer = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        borderPane1.setStyle("-fx-background-color: black;");
        next.setTextFill(Color.WHITE);
        String mediaPath = "file:/C:/Users/Mr%20Skip/Downloads/1.mp4";
        Media media = new Media(mediaPath);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.autoPlayProperty().setValue(true);
        mediaView.setMediaPlayer(mediaPlayer);

        progress.setProgress(0);
        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            progress.setProgress(1.0 * mediaPlayer.getCurrentTime().toMillis() / mediaPlayer.getTotalDuration().toMillis());

        });

        progress.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            if (mediaPlayer.getTotalDuration().toMillis() != 0)
            progress.setTooltip(new Tooltip((
                    ((int)mediaPlayer.getCurrentTime().toMinutes() + "." +
                            (((int)mediaPlayer.getCurrentTime().toMinutes())>0?((int)mediaPlayer.getCurrentTime().toSeconds()-((int)mediaPlayer.getCurrentTime().toMinutes())*60)
                            :(int)mediaPlayer.getCurrentTime().toSeconds())
                    )
                            + " \\ " +
                    (int)mediaPlayer.getTotalDuration().toMinutes() + "." + (int)mediaPlayer.getTotalDuration().toSeconds()/10)));
            progress.setPrefHeight(12);
            progress.setOpacity(0.8);
        });

        progress.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            progress.setPrefHeight(9);
            progress.setOpacity(0.21);
        });
    }

}
