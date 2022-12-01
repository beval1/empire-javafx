package com.beval.empirejavafx.views.buildingpropertymenu;

import com.beval.empirejavafx.Main;
import com.beval.empirejavafx.manager.StageManager;
import com.beval.empirejavafx.views.AbstractView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class BuildingPropertyMenu implements AbstractView {
    @Override
    public void show() throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class
                .getResource("buildingpropertymenu/building-property-menu.fxml")));
        Parent root = loader.load();
        Scene scene = new Scene(root, 360, 175);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Properties Menu");

        BuildingPropertyMenuController buildingPropertyMenuController = loader.getController();
        buildingPropertyMenuController.updateView();

        StageManager.addPopUpWindow(stage);
    }
}
