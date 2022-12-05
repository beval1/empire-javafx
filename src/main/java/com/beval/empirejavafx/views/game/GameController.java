package com.beval.empirejavafx.views.game;

import com.beval.empirejavafx.alerts.LevelUpAlert;
import com.beval.empirejavafx.api.ApiClient;
import com.beval.empirejavafx.dto.response.ResponseDTO;
import com.beval.empirejavafx.exception.CustomException;
import com.beval.empirejavafx.manager.BuildingStateManager;
import com.beval.empirejavafx.manager.CastleStateManager;
import com.beval.empirejavafx.manager.StageManager;
import com.beval.empirejavafx.manager.UserStateManager;
import com.beval.empirejavafx.views.AbstractViewController;
import com.beval.empirejavafx.views.armymenu.ArmyMenu;
import com.beval.empirejavafx.views.buildingmenu.BuildingMenu;
import com.beval.empirejavafx.views.map.GameMap;
import com.beval.empirejavafx.views.messagemenu.MessageMenu;
import com.beval.empirejavafx.views.overviewmenu.OverviewMenu;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import lombok.Getter;

import java.io.IOException;

import static com.beval.empirejavafx.config.AppConstants.CASTLE_BUILDING_IMAGE_HEIGHT;
import static com.beval.empirejavafx.config.AppConstants.CASTLE_BUILDING_IMAGE_WIDTH;

@Getter
public class GameController implements AbstractViewController {
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
    private void handleBuildingMenu(MouseEvent mouseEvent) throws IOException, InterruptedException {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
            BuildingMenu buildingMenu = new BuildingMenu();
            buildingMenu.show();
        }
    }

    @FXML
    private void handleArmyMenu(MouseEvent mouseEvent) throws IOException, InterruptedException {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
            ArmyMenu armyMenu = new ArmyMenu();
            armyMenu.show();
        }
    }

    @FXML
    private void handleWorldMap(MouseEvent mouseEvent) throws IOException, InterruptedException {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
            GameMap map = new GameMap();
            map.show();
        }
    }

    @FXML
    private void handleOverviewMenu(MouseEvent mouseEvent) throws IOException, InterruptedException {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
            OverviewMenu overviewMenu = new OverviewMenu();
            overviewMenu.show();
        }
    }

    @FXML
    private void handleMessageMenu(MouseEvent mouseEvent) throws IOException, InterruptedException {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
            MessageMenu messageMenu = new MessageMenu();
            messageMenu.show();
        }
    }

    @Override
    public void updateView() throws IOException, InterruptedException {
        if (BuildingStateManager.isInBuildingMode()) {
            buildingMode();
        }

        loadUserInfo();
        loadCastleInfo();
        UserStateManager.loadUserMessages();
    }

    private void loadCastleInfo() throws IOException, InterruptedException {
        CastleStateManager.loadUserCastle(UserStateManager.getUsername(), getGrid());
        wood.setText(String.format("%.2f", CastleStateManager.getWood()));
        stone.setText(String.format("%.2f", CastleStateManager.getStone()));
        food.setText(String.format("%.2f", CastleStateManager.getFood()));
        army.setText(String.valueOf(CastleStateManager.getArmy()));
        citizens.setText(String.valueOf(CastleStateManager.getCitizens()));
    }

    private void loadUserInfo() throws IOException, InterruptedException {
        UserStateManager.updateUserState();

        //User Level Up Alert
        if (!levelText.getText().isBlank() && Integer.parseInt(levelText.getText()) != UserStateManager.getLevel()){
            LevelUpAlert levelUpAlert = new LevelUpAlert();
            levelUpAlert.show();
        }

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
                    grid.setOnMouseClicked(null);
                }
            }
        });
    }

    private void setCursorImage(String buildingImage) {
        Image cursorImage = new Image(buildingImage, CASTLE_BUILDING_IMAGE_WIDTH,
                CASTLE_BUILDING_IMAGE_HEIGHT, false, false);
        Cursor cursor = new ImageCursor(cursorImage);
        StageManager.getStage().getScene().setCursor(cursor);
        BuildingStateManager.setCursor(cursor);
    }
}
