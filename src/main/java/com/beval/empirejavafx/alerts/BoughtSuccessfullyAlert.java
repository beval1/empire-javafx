package com.beval.empirejavafx.alerts;

import com.beval.empirejavafx.manager.StageManager;
import javafx.geometry.Pos;
import org.controlsfx.control.Notifications;

public class BoughtSuccessfullyAlert implements CustomAlert{
    @Override
    public void show() {
        Notifications notificationBuilder = Notifications.create()
                .title("Bought successfully!")
                .text("Bought successfully!")
                .owner(StageManager.getStage())
                .position(Pos.CENTER);
        notificationBuilder.showConfirm();
    }
}
