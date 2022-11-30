package com.beval.empirejavafx.views.game;

import com.beval.empirejavafx.api.ApiClient;
import com.beval.empirejavafx.config.AppConstants;
import com.beval.empirejavafx.dto.response.ResponseDTO;
import com.beval.empirejavafx.exception.CustomException;
import com.beval.empirejavafx.manager.BuildingStateManager;
import com.beval.empirejavafx.manager.CastleStateManager;
import com.beval.empirejavafx.manager.StageManager;
import com.beval.empirejavafx.manager.UserStateManager;
import com.beval.empirejavafx.views.buildingmenu.BuildingMenu;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;

@Getter
public class GameController {
    @FXML
    private GridPane grid;

    @FXML
    private Text levelText;

    @FXML
    private Text usernameText;

    @FXML
    private Text mightyPointsText;

    @FXML
    private Text rubiesText;

    @FXML
    private Text coinsText;

    @FXML
    private Text wood;

    @FXML
    private Text stone;

    @FXML
    private Text food;

    @FXML
    private Text army;

    @FXML
    private Text citizens;

    @FXML
    private void handleBuildingMenu() throws IOException, InterruptedException {
        BuildingMenu buildingMenu = new BuildingMenu();
        buildingMenu.show();
    }

    @SneakyThrows
    public void updateView() throws IOException {
        if (BuildingStateManager.isInBuildingMode()) {
            buildingMode();
        }

        loadUserInfo();
        loadCastleInfo();
    }

    private void loadCastleInfo() throws IOException, InterruptedException {
        CastleStateManager.loadUserCastle(UserStateManager.getUsername(), getGrid());
        wood.setText(String.valueOf(CastleStateManager.getWood()));
        stone.setText(String.valueOf(CastleStateManager.getStone()));
        food.setText(String.valueOf(CastleStateManager.getFood()));
        army.setText(String.valueOf(CastleStateManager.getArmy()));
        citizens.setText(String.valueOf(CastleStateManager.getCitizens()));
    }

    private void loadUserInfo() throws IOException, InterruptedException {
        UserStateManager.updateUserState();
        levelText.setText(String.valueOf(UserStateManager.getLevel()));
        usernameText.setText(UserStateManager.getUsername());
        mightyPointsText.setText(String.valueOf(UserStateManager.getMightyPoints()));
        rubiesText.setText(String.valueOf(UserStateManager.getRubies()));
        coinsText.setText(String.valueOf(UserStateManager.getCoins()));
    }

    private void buildingMode() {
        if (BuildingStateManager.getCursor() == null) {
            setCursorImage(BuildingStateManager.getBuildingEntity().getBuildingImage());
        }
        grid.setOnMouseClicked(mouseEvent -> {
            Node clickedNode = mouseEvent.getPickResult().getIntersectedNode();
            if (clickedNode != grid) {
                // click on descendant node
                Node parent = clickedNode.getParent();
                while (parent != grid) {
                    clickedNode = parent;
                    parent = clickedNode.getParent();
                }
                Integer colIndex = GridPane.getColumnIndex(clickedNode);
                Integer rowIndex = GridPane.getRowIndex(clickedNode);
                System.out.println("Mouse clicked cell: column - " + colIndex + " And: row - " + rowIndex);
                try {
                    ResponseDTO<Object> responseDTO = ApiClient.createBuilding(rowIndex, colIndex, BuildingStateManager
                            .getBuildingEntity().getBuildingType().getId());
                    System.out.println(responseDTO);
                    //throw server message if building not successful
                    if (responseDTO.getStatus() != 200){
                        throw new CustomException(responseDTO.getMessage());
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    BuildingStateManager.setInBuildingMode(false);
                    BuildingStateManager.setBuildingEntity(null);
                    BuildingStateManager.setCursor(null);
                    StageManager.getStage().getScene().setCursor(null);
                }
            }
        });
    }

    private void setCursorImage(String buildingImage) {
        ImageView imageView = new ImageView(new Image(buildingImage));
        imageView.setFitHeight(AppConstants.CASTLE_BUILDING_IMAGE_HEIGHT);
        imageView.setFitWidth(AppConstants.CASTLE_BUILDING_IMAGE_WIDTH);
        Image cursorImage = imageView.getImage();
        Cursor cursor = new ImageCursor(cursorImage);
        StageManager.getStage().getScene().setCursor(cursor);
        BuildingStateManager.setCursor(cursor);
    }
}
