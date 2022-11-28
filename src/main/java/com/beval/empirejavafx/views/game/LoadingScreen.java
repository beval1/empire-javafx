package com.beval.empirejavafx.views.game;

import com.beval.empirejavafx.Main;
import com.beval.empirejavafx.manager.StageManager;
import com.beval.empirejavafx.views.AbstractView;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadingScreen implements AbstractView {
    @Override
    public void show() throws IOException {
        Media media = new Media(Main.class.getResource("animation/ModernStartscreenAnimation_large.mp4").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView (mediaPlayer);
        Group root = new Group();
        root.getChildren().add(mediaView);


        Stage stage = new Stage();
        stage.setScene(new Scene(root, 1400, 800));
        stage.setTitle("Starting Game...");
        stage.setMaximized(true);

        mediaView.fitWidthProperty().bind(stage.widthProperty());
        mediaView.fitHeightProperty().bind(stage.heightProperty());
        mediaView.setPreserveRatio(false);

        StageManager.setStage(stage);
        StageManager.show();

        System.out.println(mediaPlayer.getTotalDuration());
        mediaPlayer.setOnEndOfMedia(() -> {
            Game game = new Game();
            try {
                game.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        mediaPlayer.play();
    }
}
