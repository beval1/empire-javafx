package com.beval.empirejavafx.alerts;

import com.beval.empirejavafx.manager.UserStateManager;
import javafx.scene.control.Alert;

public class LevelUpAlert implements CustomAlert{
    @Override
    public void show() {
        int userLevel = UserStateManager.getLevel();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Leveled UP");
        alert.setHeaderText(null);
        alert.setContentText(String.format("Congratz! You have reached Level %d", userLevel));
        alert.show();
    }
}
