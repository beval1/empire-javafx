package com.beval.empirejavafx.views.overviewmenu;

import com.beval.empirejavafx.Main;
import com.beval.empirejavafx.manager.StageManager;
import com.beval.empirejavafx.views.AbstractView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class OverviewMenu implements AbstractView {
    private OverviewMenuController overviewMenuController;

    @Override
    public void show() throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class
                .getResource("overviewmenu/overview-menu.fxml")));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 250);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Overview Menu");
        stage.setResizable(false);

        this.overviewMenuController = loader.getController();
        overviewMenuController.updateView();

        StageManager.addPopUpWindow(stage);
    }
}
