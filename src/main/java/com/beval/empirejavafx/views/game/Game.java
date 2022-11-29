package com.beval.empirejavafx.views.game;

import com.beval.empirejavafx.Main;
import com.beval.empirejavafx.manager.StageManager;
import com.beval.empirejavafx.views.AbstractView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Game implements AbstractView {
    @Override
    public void show() throws IOException {
        Scene scene = createScene();
        Stage stage = createStage(scene);
        stage.show();
    }

    private Scene createScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("game/game.fxml")));
        Parent root = loader.load();
        GameController gameController = loader.getController();
        gameController.updateView();
        return new Scene(root);
    }

    private Stage createStage(Scene scene){
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Empire");
        stage.setMaximized(true);
        StageManager.setStage(stage);
        return stage;
    }
}
