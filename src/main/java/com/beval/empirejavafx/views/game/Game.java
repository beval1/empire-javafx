package com.beval.empirejavafx.views.game;

import com.beval.empirejavafx.Main;
import com.beval.empirejavafx.manager.BuildingStateManager;
import com.beval.empirejavafx.manager.RenderingManager;
import com.beval.empirejavafx.manager.StageManager;
import com.beval.empirejavafx.views.AbstractView;
import com.beval.empirejavafx.views.RenderingView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.beval.empirejavafx.config.AppConstants.*;

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
    public void render() throws IOException {
        gameController.updateView();
    }

    private Scene createScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("game/game.fxml")));
        Parent root = loader.load();
        this.gameController = loader.getController();
        initializeGrid(gameController.getGrid());
        return new Scene(root, 800, 800);
    }

    public void initializeGrid(GridPane grid) {
        if (!DEBUG_MODE){
            grid.setGridLinesVisible(false);
        }

        int numCols = CASTLE_GRID_COLUMNS;
        int numRows = CASTLE_GRID_ROWS;

        for (int i = 0 ; i < numCols ; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints(CASTLE_GRID_CELL_COLUMN_SIZE);
//            colConstraints.setHgrow(Priority.SOMETIMES);
            grid.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0 ; i < numRows ; i++) {
            RowConstraints rowConstraints = new RowConstraints(CASTLE_GRID_CELL_ROW_SIZE);
//            rowConstraints.setVgrow(Priority.SOMETIMES);
            grid.getRowConstraints().add(rowConstraints);
        }

        //this is required because you can't trace click of something that doesn't exist on the grid;
        for (int i = 0 ; i < numCols ; i++) {
            for (int j = 0; j < numRows; j++) {
                addPane(i, j, grid);
            }
        }

        BuildingStateManager.setBuildingsGrid(grid);
    }

    private void addPane(int colIndex, int rowIndex, GridPane grid) {
        Pane pane = new Pane();
        grid.add(pane, colIndex, rowIndex);
    }

    private Stage createStage(Scene scene){
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Empire");
        stage.setMaximized(true);
        stage.setResizable(false);
        StageManager.setStage(stage);
        return stage;
    }
}
