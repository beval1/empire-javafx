package com.beval.empirejavafx.views.game;

import com.beval.empirejavafx.manager.StageManager;
import com.beval.empirejavafx.views.AbstractView;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Game implements AbstractView {
    @Override
    public void show() throws IOException {
        Group group = new Group();
        Scene scene = new Scene(group, 1000, 1000);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Empire");
        stage.setMaximized(true);
        StageManager.setStage(stage);
        StageManager.show();
    }
}
