package com.beval.empirejavafx.views.sendmessage;

import com.beval.empirejavafx.Main;
import com.beval.empirejavafx.manager.StageManager;
import com.beval.empirejavafx.views.AbstractView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SendMessage implements AbstractView {
    @Override
    public void show() throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class
                .getResource("sendmessage/send-message.fxml")));
        Parent root = loader.load();
        Scene scene = new Scene(root, 350, 250);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Send Message");
        stage.setResizable(false);

        SendMessageController sendMessageController = loader.getController();
        sendMessageController.updateView();

        StageManager.addPopUpWindow(stage);
    }
}
