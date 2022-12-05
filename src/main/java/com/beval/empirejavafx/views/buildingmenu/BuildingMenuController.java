package com.beval.empirejavafx.views.buildingmenu;

import com.beval.empirejavafx.api.ApiClient;
import com.beval.empirejavafx.dto.response.BuildingEntityDTO;
import com.beval.empirejavafx.dto.response.ResponseDTO;
import com.beval.empirejavafx.manager.BuildingStateManager;
import com.beval.empirejavafx.manager.StageManager;
import com.beval.empirejavafx.views.AbstractViewController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public class BuildingMenuController implements AbstractViewController {
    @FXML
    private StackPane root;

    @Override
    public void updateView() throws IOException, InterruptedException {
        ResponseDTO<List<BuildingEntityDTO>> responseDTO = ApiClient.getBuildings();
        ListView<HBox> listView = new ListView<>();
        for(BuildingEntityDTO buildingEntityDTO : responseDTO.getContent()){
            if (!buildingEntityDTO.getBuildingType().isBuildable()){
                continue;
            }
            HBox hBox = createBuildingCard(buildingEntityDTO);
            listView.getItems().add(hBox);
            hBox.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    StageManager.removePopUpWindow(StageManager.getPopupWindows().size()-1);
                    BuildingStateManager.setInBuildingMode(true);
                    BuildingStateManager.setBuildingEntity(buildingEntityDTO);
                }
            });
        }
        root.getChildren().add(listView);
    }

    private HBox createBuildingCard(BuildingEntityDTO buildingEntityDTO) {
        HBox hBox = new HBox();
        VBox infoVBox = new VBox();

        Text buildingName = new Text();
        buildingName.setText(buildingEntityDTO.getBuildingType().getBuildingName());
        buildingName.setStyle("-fx-font-size: 24");
        buildingName.setStyle("-fx-font-weight: bold");

        Text buildingTime = new Text();
        buildingTime.setText(String.format("Time: %.2f min", (double)buildingEntityDTO.getBuildingTimeSeconds() / 60));

        String productionString = buildingEntityDTO.getProduction() == 0 ? "" :
                String.format("Production: %d%n", buildingEntityDTO.getProduction());
        Text buildingResources = new Text();
        buildingResources.setText(String.format("%sWood: %d%nStone: %d",
                productionString, buildingEntityDTO.getWoodRequired(), buildingEntityDTO.getStoneRequired()));

        infoVBox.getChildren().add(buildingName);
        infoVBox.getChildren().add(buildingResources);
        infoVBox.getChildren().add(buildingTime);

        ImageView imageView = new ImageView(new Image(buildingEntityDTO.getBuildingImage()));
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);

        hBox.getChildren().add(imageView);
        hBox.getChildren().add(infoVBox);
        hBox.setSpacing(30);
        return hBox;
    }
}
