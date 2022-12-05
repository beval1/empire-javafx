package com.beval.empirejavafx.views.map;

import com.beval.empirejavafx.Main;
import com.beval.empirejavafx.manager.CastleStateManager;
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

public class GameMap implements AbstractView, RenderingView {
    private GameMapController gameMapController;

    @Override
    public void show() throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class
                .getResource("map/map.fxml")));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("World Map");
        StageManager.setStage(stage);
        StageManager.show();

        this.gameMapController = loader.getController();
        gameMapController.setQuadrant(CastleStateManager.getCastleQuadrant());
        GridPane mapGrid = gameMapController.getMapGrid();
        GridUtils.initializeGridMap(mapGrid);

        RenderingManager.setRenderingView(this);
    }

    @Override
    public void render() throws IOException, InterruptedException {
        gameMapController.updateView();
    }
}
