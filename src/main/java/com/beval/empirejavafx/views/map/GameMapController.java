package com.beval.empirejavafx.views.map;

import com.beval.empirejavafx.manager.CastleStateManager;
import com.beval.empirejavafx.views.AbstractViewController;
import com.beval.empirejavafx.views.game.Game;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Setter
@Getter
public class GameMapController implements AbstractViewController {
    private int quadrant;

    @FXML
    private GridPane mapGrid;

    public GridPane getMapGrid() {
        return mapGrid;
    }

    @Override
    public void updateView() throws IOException, InterruptedException {
        CastleStateManager.loadMapCastles(quadrant, mapGrid);
    }

    @FXML
    private void handleGoToCastle(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
            Game game = new Game();
            game.show();
        }
    }
}
