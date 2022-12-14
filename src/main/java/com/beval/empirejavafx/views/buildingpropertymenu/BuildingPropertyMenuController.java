package com.beval.empirejavafx.views.buildingpropertymenu;

import com.beval.empirejavafx.api.ApiClient;
import com.beval.empirejavafx.dto.response.BuildingEntityDTO;
import com.beval.empirejavafx.dto.response.CastleBuildingDTO;
import com.beval.empirejavafx.dto.response.ResponseDTO;
import com.beval.empirejavafx.exception.CustomException;
import com.beval.empirejavafx.manager.BuildingStateManager;
import com.beval.empirejavafx.manager.StageManager;
import com.beval.empirejavafx.utils.GridUtils;
import com.beval.empirejavafx.views.AbstractViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public class BuildingPropertyMenuController implements AbstractViewController {
    @FXML
    private StackPane root;

    @FXML
    private Text resourceRequired;

    @FXML
    private ImageView buildingImage;

    @FXML
    private Text currentBuildingInfo;

    @Override
    public void updateView() throws IOException, InterruptedException {
        CastleBuildingDTO selectedBuildingDTO = BuildingStateManager.getSelectedCastleBuilding();
        ResponseDTO<List<BuildingEntityDTO>> responseDTO =
                ApiClient.getSpecificBuildingType(selectedBuildingDTO.getBuildingEntity().getBuildingType().getId());

        int nextLevel = selectedBuildingDTO.getBuildingEntity().getLevel() + 1;
        BuildingEntityDTO upgradeEntity = responseDTO.getContent().stream()
                .filter(buildingEntityDTO -> buildingEntityDTO.getLevel() == nextLevel).findFirst().orElse(null);

        if (upgradeEntity != null) {
            String productionString = upgradeEntity.getProduction() == 0 ? "" :
                    String.format("Production: %d%n", upgradeEntity.getProduction());
            String buildTime = String.format("Time: %.2f min", (double)upgradeEntity.getBuildingTimeSeconds() / 60);
            resourceRequired.setText(String.format("%sUnlocks at lvl: %d%nWood: %d%nStone: %d%n%s",
                    productionString,
                    upgradeEntity.getUnlocksOnLevel(),
                    upgradeEntity.getWoodRequired(),
                    upgradeEntity.getStoneRequired(),
                    buildTime));
        } else {
            resourceRequired.setText(String.format("%nMax Level Reached!"));
        }

        buildingImage.setImage(new Image(selectedBuildingDTO.getBuildingEntity().getBuildingImage()));
        currentBuildingInfo.setText(String.format("   %s%n   Level: %d",
                selectedBuildingDTO.getBuildingEntity().getBuildingType().getBuildingName(),
                selectedBuildingDTO.getBuildingEntity().getLevel()));
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
            //confirm dialogue
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("");
            alert.setContentText("Are you sure?");
            alert.showAndWait();
            if (alert.getResult() != ButtonType.OK) {
                return;
            }

            CastleBuildingDTO castleBuildingDTO = BuildingStateManager.getSelectedCastleBuilding();
            int buildingId = castleBuildingDTO.getId();
            ResponseDTO<Object> destroyResponse = ApiClient.destroyBuilding(buildingId);
            if (destroyResponse.getStatus() != 200) {
                throw new CustomException(destroyResponse.getMessage());
            } else {
                GridUtils.removeNodeByRowColumnIndex(castleBuildingDTO.getCoordinateY(),
                        castleBuildingDTO.getCoordinateX(),
                        BuildingStateManager.getBuildingsGrid());
                //add new pane on the place of the removed object, to trace clicks
                Pane pane = new Pane();
                GridUtils.addNodeByRowColumnIndex(castleBuildingDTO.getCoordinateY(),
                        castleBuildingDTO.getCoordinateX(), BuildingStateManager.getBuildingsGrid(), pane);
                BuildingStateManager.setSelectedCastleBuilding(null);
                StageManager.removePopUpWindow(StageManager.getPopupWindows().size() - 1);
            }
        }
    }


}
