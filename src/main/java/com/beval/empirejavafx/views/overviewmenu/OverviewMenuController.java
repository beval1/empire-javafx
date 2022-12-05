package com.beval.empirejavafx.views.overviewmenu;

import com.beval.empirejavafx.api.ApiClient;
import com.beval.empirejavafx.dto.response.CastleArmyDTO;
import com.beval.empirejavafx.dto.response.ResponseDTO;
import com.beval.empirejavafx.exception.CustomException;
import com.beval.empirejavafx.manager.CastleStateManager;
import com.beval.empirejavafx.views.AbstractViewController;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public class OverviewMenuController implements AbstractViewController {
    @FXML
    private ListView<StackPane> listView;
    @FXML
    private Text noSoldiers;
    @FXML
    private Text production;

    @Override
    public void updateView() throws IOException, InterruptedException {
        ResponseDTO<List<CastleArmyDTO>> responseDTO = ApiClient.getCastleArmyUnits();
        if (responseDTO.getStatus() != 200) {
            throw new CustomException(responseDTO.getMessage());
        }

        List<CastleArmyDTO> castleArmyDTOS = responseDTO.getContent();
        listView.setOrientation(Orientation.HORIZONTAL);
        for (CastleArmyDTO castleArmyDTO : castleArmyDTOS) {
            StackPane stackPane = new StackPane();
            ImageView soldierImage = new ImageView(new Image(castleArmyDTO.getArmyUnit().getUnitImage()));
            Text countText = new Text();
            countText.setText(String.valueOf(castleArmyDTO.getArmyUnitCount()));
            countText.setStyle("-fx-font-size: 60px; -fx-fill: red");
            stackPane.getChildren().add(soldierImage);
            stackPane.getChildren().add(countText);
            listView.getItems().add(stackPane);
        }

        String productionString = String.format(
                "Wood production: %.2f\tStone production: %.2f\tFood production %.2f%n",
                CastleStateManager.getWoodProduction(),
                CastleStateManager.getStoneProduction(),
                CastleStateManager.getFoodProduction()
        );
        production.setText(productionString);
        production.setStyle("-fx-font-size: 16px");

        if (CastleStateManager.getArmy() == 0) {
            noSoldiers.setText("No Soldiers in the Castle!");
            noSoldiers.setStyle("-fx-font-size: 40px; -fx-fill: red");
        }
    }

}
