package com.beval.empirejavafx.manager;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class StageManager {
    private StageManager() {}
    private static Stage stage = null;

    public static void setStage(Stage stage) {
        if (StageManager.stage != null){
            StageManager.stage.close();
        }
        StageManager.stage = stage;
    }

//    public static void redirectTo(AbstractForm form) throws IOException {
//        form.show();
//    }

    public static void setTitle(String title) {
        stage.setTitle(title);
    }

    public static void setScene(Scene scene) {
        stage.setScene(scene);
    }

    public static void show() {
        stage.show();
    }
}
