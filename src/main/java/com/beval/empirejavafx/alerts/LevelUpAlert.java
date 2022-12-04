package com.beval.empirejavafx.alerts;

import com.beval.empirejavafx.manager.StageManager;
import com.beval.empirejavafx.manager.UserStateManager;
import javafx.geometry.Pos;
import org.controlsfx.control.Notifications;

public class LevelUpAlert implements CustomAlert{
    @Override
    public void show() {
        int userLevel = UserStateManager.getLevel();
        Notifications notificationBuilder = Notifications.create()
                .title("Leveled UP")
                .text(String.format("Congratz! You have reached Level %d", userLevel))
                .owner(StageManager.getStage())
                .position(Pos.CENTER);
        notificationBuilder.showConfirm();
    }
}
