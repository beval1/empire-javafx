package com.beval.empirejavafx.alerts;

import javafx.scene.control.Alert;

public class BoughtSuccessfullyAlert implements CustomAlert{
    @Override
    public void show() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bought successfully!");
        alert.setHeaderText(null);
        alert.setContentText("Bought successfully!");
        alert.show();
    }
}
