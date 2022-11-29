package com.beval.empirejavafx.views.game;

import com.beval.empirejavafx.config.AppConstants;
import com.beval.empirejavafx.manager.BuildingStateManager;
import com.beval.empirejavafx.manager.CastleStateManager;
import com.beval.empirejavafx.manager.StageManager;
import com.beval.empirejavafx.manager.UserStateManager;
import com.beval.empirejavafx.views.buildingmenu.BuildingMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.SneakyThrows;
import org.w3c.dom.events.MouseEvent;

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
        setCursorImage(BuildingStateManager.getBuildingEntity().getBuildingImage());
//        grid.setOnMouseClicked();
    }


    private void setCursorImage(String buildingImage) {
        ImageView imageView = new ImageView(new Image(buildingImage));
        imageView.setFitHeight(AppConstants.CASTLE_BUILDING_IMAGE_HEIGHT);
        imageView.setFitWidth(AppConstants.CASTLE_BUILDING_IMAGE_WIDTH);
        Image cursorImage = imageView.getImage();
        Cursor cursor = new ImageCursor(cursorImage);
        StageManager.getStage().getScene().setCursor(cursor);
    }
}
