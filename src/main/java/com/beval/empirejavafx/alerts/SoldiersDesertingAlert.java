package com.beval.empirejavafx.alerts;

import javafx.scene.control.Alert;

public class SoldiersDesertingAlert implements CustomAlert {
    @Override
    public void show() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Soldiers Deserting");
        alert.setHeaderText(null);
        alert.setContentText("You have negative food production, soldiers are deserting!");
        alert.show();
    }
}
