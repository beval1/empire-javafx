package com.beval.empirejavafx.alerts;

import javafx.scene.control.Alert;

public class SentSuccessfully implements CustomAlert{
    @Override
    public void show() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sent successfully!");
        alert.setHeaderText(null);
        alert.setContentText("Message sent successfully!");
        alert.show();
    }
}
