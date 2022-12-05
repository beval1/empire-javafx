package com.beval.empirejavafx.views.castlepreview;

import com.beval.empirejavafx.Main;
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

public class CastlePreview implements AbstractView, RenderingView {
    private CastlePreviewController castlePreviewController;

    @Override
    public void show() throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class
                .getResource("castlepreview/castle-preview.fxml")));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Castle Preview");
        StageManager.setStage(stage);
        StageManager.show();

        this.castlePreviewController = loader.getController();
        GridPane mapGrid = castlePreviewController.getGrid();
        GridUtils.initializeGridCastle(mapGrid);

        RenderingManager.setRenderingView(this);
    }

    @Override
    public void render() throws IOException, InterruptedException {
        castlePreviewController.updateView();
    }
}
