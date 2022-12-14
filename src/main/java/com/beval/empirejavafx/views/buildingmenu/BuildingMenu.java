package com.beval.empirejavafx.views.buildingmenu;

import com.beval.empirejavafx.Main;
import com.beval.empirejavafx.manager.StageManager;
import com.beval.empirejavafx.views.AbstractView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class BuildingMenu implements AbstractView {
    @Override
    public void show() throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("buildingmenu/building-menu.fxml")));
        Parent root = loader.load();
        Scene scene = new Scene(root, 300, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Building Menu");
        stage.setResizable(false);

        BuildingMenuController buildingMenuController = loader.getController();
        buildingMenuController.updateView();

        StageManager.addPopUpWindow(stage);
    }
}
