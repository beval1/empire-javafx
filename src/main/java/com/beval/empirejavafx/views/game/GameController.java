package com.beval.empirejavafx.views.game;

import com.beval.empirejavafx.manager.CastleStateManager;
import com.beval.empirejavafx.manager.UserStateManager;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import lombok.Getter;

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

    public void updateView() throws IOException {
        levelText.setText(String.valueOf(UserStateManager.getLevel()));
        usernameText.setText(UserStateManager.getUsername());
        mightyPointsText.setText(String.valueOf(UserStateManager.getMightyPoints()));
        rubiesText.setText(String.valueOf(UserStateManager.getRubies()));
        coinsText.setText(String.valueOf(UserStateManager.getCoins()));

        try {
            CastleStateManager.loadUserCastle(UserStateManager.getUsername(), getGrid());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        wood.setText(String.valueOf(CastleStateManager.getWood()));
        stone.setText(String.valueOf(CastleStateManager.getStone()));
        food.setText(String.valueOf(CastleStateManager.getFood()));
        army.setText(String.valueOf(CastleStateManager.getArmy()));
        citizens.setText(String.valueOf(CastleStateManager.getCitizens()));
    }
}
