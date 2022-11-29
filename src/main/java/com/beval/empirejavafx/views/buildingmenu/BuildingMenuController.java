package com.beval.empirejavafx.views.buildingmenu;

import com.beval.empirejavafx.api.ApiClient;
import com.beval.empirejavafx.dto.response.BuildingEntityDTO;
import com.beval.empirejavafx.dto.response.ResponseDTO;
import com.beval.empirejavafx.manager.BuildingStateManager;
import com.beval.empirejavafx.manager.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public class BuildingMenuController {
    @FXML
    private StackPane root;

    public void updateView() throws IOException, InterruptedException {
        ResponseDTO<List<BuildingEntityDTO>> responseDTO = ApiClient.getBuildings();
        ListView<HBox> listView = new ListView<>();
        //TODO: display building cost and time
        for(BuildingEntityDTO buildingEntityDTO : responseDTO.getContent()){
            if (!buildingEntityDTO.getBuildingType().isBuildable()){
                continue;
            }

            HBox hBox = new HBox();
            ImageView imageView = new ImageView(new Image(buildingEntityDTO.getBuildingImage()));
            imageView.setFitWidth(60);
            imageView.setFitHeight(60);
            Text buildingName = new Text();
            buildingName.setText(buildingEntityDTO.getBuildingType().getBuildingName());
            hBox.getChildren().add(imageView);
            hBox.getChildren().add(buildingName);
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
}
