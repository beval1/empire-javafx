package com.beval.empirejavafx.views.game;

import com.beval.empirejavafx.Main;
import com.beval.empirejavafx.manager.BuildingStateManager;
import com.beval.empirejavafx.manager.RenderingManager;
import com.beval.empirejavafx.manager.StageManager;
import com.beval.empirejavafx.utils.GridUtils;
import com.beval.empirejavafx.views.AbstractView;
import com.beval.empirejavafx.views.RenderingView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class Game implements AbstractView, RenderingView {
    private GameController gameController;

    @Override
    public void show() throws IOException {
        Scene scene = createScene();
        Stage stage = createStage(scene);
        stage.show();

        RenderingManager.setRenderingView(this);
    }

    @Override
    public void render() throws IOException, InterruptedException {
        gameController.updateView();
    }

    private Scene createScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("game/game.fxml")));
        Parent root = loader.load();
        this.gameController = loader.getController();
        initializeGrid(gameController.getGrid());
        return new Scene(root);
    }

    public void initializeGrid(GridPane grid) {
        GridUtils.initializeGridCastle(grid);
        BuildingStateManager.setBuildingsGrid(grid);
    }

    private Stage createStage(Scene scene){
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Empire");
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.setMaxWidth(1920);
        stage.setMaxHeight(1080);
        StageManager.setStage(stage);
        return stage;
    }
}
