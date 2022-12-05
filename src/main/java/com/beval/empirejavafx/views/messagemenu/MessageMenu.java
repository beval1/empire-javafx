package com.beval.empirejavafx.views.messagemenu;

import com.beval.empirejavafx.Main;
import com.beval.empirejavafx.manager.StageManager;
import com.beval.empirejavafx.views.AbstractView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MessageMenu implements AbstractView {
    @Override
    public void show() throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class
                .getResource("messagemenu/message-menu.fxml")));
        Parent root = loader.load();
        Scene scene = new Scene(root, 400, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Messages Menu");
        stage.setResizable(false);

        MessageMenuController messageMenuController = loader.getController();
        messageMenuController.updateView();

        StageManager.addPopUpWindow(stage);
    }
}
