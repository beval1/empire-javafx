package com.beval.empirejavafx.views.login;

import com.beval.empirejavafx.Main;
import com.beval.empirejavafx.manager.StageManager;
import com.beval.empirejavafx.views.AbstractView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

public class LoginInForm implements AbstractView {
    @Override
    public void show() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("login/login.fxml")));
        Scene scene = new Scene(root, 500, 350);
        StageManager.setScene(scene);
        StageManager.setTitle("Log In");
        StageManager.show();
    }
}