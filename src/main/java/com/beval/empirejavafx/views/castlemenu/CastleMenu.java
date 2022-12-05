package com.beval.empirejavafx.views.castlemenu;

import com.beval.empirejavafx.Main;
import com.beval.empirejavafx.manager.StageManager;
import com.beval.empirejavafx.views.AbstractView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CastleMenu implements AbstractView {
    @Override
    public void show() throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                Main.class.getResource("castlemenu/castle-menu.fxml")));
        Parent root = loader.load();
        Scene scene = new Scene(root, 450, 300);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Castle Menu");
        stage.setResizable(false);

        CastleMenuController castleMenuController = loader.getController();
        castleMenuController.updateView();

        StageManager.addPopUpWindow(stage);
    }
}
