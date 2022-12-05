package com.beval.empirejavafx.views.castlepreview;

import com.beval.empirejavafx.manager.CastleStateManager;
import com.beval.empirejavafx.views.AbstractViewController;
import com.beval.empirejavafx.views.map.GameMap;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import lombok.Getter;

import java.io.IOException;

@Getter
public class CastlePreviewController implements AbstractViewController {
    @FXML
    private GridPane grid;

    @FXML
    private void handleWorldMap(MouseEvent mouseEvent) throws IOException, InterruptedException {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
            GameMap map = new GameMap();
            map.show();
        }
    }

    @Override
    public void updateView() throws IOException, InterruptedException {
        CastleStateManager.loadEnemyCastle(grid);
    }
}
