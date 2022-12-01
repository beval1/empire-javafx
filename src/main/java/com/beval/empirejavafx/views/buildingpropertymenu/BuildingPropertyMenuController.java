package com.beval.empirejavafx.views.buildingpropertymenu;

import com.beval.empirejavafx.api.ApiClient;
import com.beval.empirejavafx.dto.response.BuildingEntityDTO;
import com.beval.empirejavafx.dto.response.CastleBuildingDTO;
import com.beval.empirejavafx.dto.response.ResponseDTO;
import com.beval.empirejavafx.exception.CustomException;
import com.beval.empirejavafx.manager.BuildingStateManager;
import com.beval.empirejavafx.manager.StageManager;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public class BuildingPropertyMenuController {
    @FXML
    private StackPane root;

    @FXML
    private Text resourceRequired;

    @FXML
    private ImageView buildingImage;

    public void updateView() throws IOException, InterruptedException {
        CastleBuildingDTO selectedBuildingDTO = BuildingStateManager.getSelectedCastleBuilding();
        ResponseDTO<List<BuildingEntityDTO>> responseDTO =
                ApiClient.getSpecificBuildingType(selectedBuildingDTO.getBuildingEntity().getBuildingType().getId());

        int nextLevel = selectedBuildingDTO.getBuildingEntity().getLevel() + 1;
        BuildingEntityDTO upgradeEntity = responseDTO.getContent().stream()
                .filter(buildingEntityDTO -> buildingEntityDTO.getLevel() == nextLevel).findFirst().orElse(null);

        if (upgradeEntity != null) {
            String buildTime = String.format("Time: %.2f min", (double)upgradeEntity.getBuildingTimeSeconds() / 60);
            resourceRequired.setText(String.format("Unlocks at lvl: %d%nWood: %d%nStone: %d%n%s",
                    upgradeEntity.getUnlocksOnLevel(),
                    upgradeEntity.getWoodRequired(),
                    upgradeEntity.getStoneRequired(),
                    buildTime));
        } else {
            resourceRequired.setText(String.format("%nMax Level Reached!"));
        }

        buildingImage.setImage(new Image(selectedBuildingDTO.getBuildingEntity().getBuildingImage()));
    }

    @FXML
    private void handleUpgrade(MouseEvent mouseEvent) throws IOException, InterruptedException {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
            CastleBuildingDTO castleBuildingDTO = BuildingStateManager.getSelectedCastleBuilding();
            int buildingId = castleBuildingDTO.getId();
            ResponseDTO<Object> upgradeBuildingDTO = ApiClient.upgradeBuilding(buildingId);
            if (upgradeBuildingDTO.getStatus() != 200) {
                throw new CustomException(upgradeBuildingDTO.getMessage());
            } else {
                BuildingStateManager.setSelectedCastleBuilding(null);
                StageManager.removePopUpWindow(StageManager.getPopupWindows().size() - 1);
            }
        }
    }

    @FXML
    private void handleDestroy(MouseEvent mouseEvent) throws IOException, InterruptedException {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
            CastleBuildingDTO castleBuildingDTO = BuildingStateManager.getSelectedCastleBuilding();
            int buildingId = castleBuildingDTO.getId();
            ResponseDTO<Object> destroyResponse = ApiClient.destroyBuilding(buildingId);
            if (destroyResponse.getStatus() != 200) {
                throw new CustomException(destroyResponse.getMessage());
            } else {
                BuildingStateManager.removeNodeByRowColumnIndex(castleBuildingDTO.getCoordinateY(),
                        castleBuildingDTO.getCoordinateX(),
                        BuildingStateManager.getBuildingsGrid());
                BuildingStateManager.setSelectedCastleBuilding(null);
                StageManager.removePopUpWindow(StageManager.getPopupWindows().size() - 1);
            }
        }
    }


}
