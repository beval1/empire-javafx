package com.beval.empirejavafx;

import com.beval.empirejavafx.views.login.LoginInForm;
import com.beval.empirejavafx.manager.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        StageManager.setStage(stage);
        LoginInForm loginInForm = new LoginInForm();
        loginInForm.show();
    }

}
