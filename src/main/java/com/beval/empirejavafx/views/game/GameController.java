package com.beval.empirejavafx.views.game;

import com.beval.empirejavafx.manager.UserStateManager;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import lombok.Getter;

@Getter
public class GameController {
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

    public void updateView(){
        levelText.setText(String.valueOf(UserStateManager.getLevel()));
        usernameText.setText(UserStateManager.getUsername());
        mightyPointsText.setText(String.valueOf(UserStateManager.getMightyPoints()));
        rubiesText.setText(String.valueOf(UserStateManager.getRubies()));
        coinsText.setText(String.valueOf(UserStateManager.getCoins()));
    }
}
