package com.beval.empirejavafx;

import com.beval.empirejavafx.exception.CustomException;
import com.beval.empirejavafx.manager.StageManager;
import com.beval.empirejavafx.views.login.LoginInForm;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
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
        Thread.setDefaultUncaughtExceptionHandler(Main::showError);
    }

    private static void showError(Thread t, Throwable e) {
        if (Platform.isFxApplicationThread()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            if (!(e instanceof CustomException)){
                e.printStackTrace();
            }
        } else {
            System.err.println("An unexpected error occurred in "+ t);
        }
    }

}
