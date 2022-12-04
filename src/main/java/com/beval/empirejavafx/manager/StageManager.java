package com.beval.empirejavafx.manager;

import com.jpro.webapi.WebAPI;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StageManager {
    private StageManager() {}
    private static Stage stage = null;
    private static final List<Stage> popupWindows = new ArrayList<>();

    public static Stage getStage() {
        return stage;
    }

    public static List<Stage> getPopupWindows() {
        return Collections.unmodifiableList(popupWindows);
    }

    public static void addPopUpWindow(Stage stage) {
        StageManager.popupWindows.add(stage);
        if (WebAPI.isBrowser()){
            WebAPI.getWebAPI(getStage()).openStageAsPopup(stage);
        } else {
            stage.show();
        }
    }

    public static void removePopUpWindow(Stage stage) {
        stage.close();
        StageManager.popupWindows.remove(stage);
    }

    public static void removePopUpWindow(int index) {
        Stage stage = StageManager.popupWindows.remove(index);
        stage.close();
        if (WebAPI.isBrowser()){
            WebAPI.getWebAPI(stage).closeInstance();
        }
    }

    public static void setStage(Stage stage) {
        if (StageManager.stage != null){
            StageManager.stage.close();
        }
        StageManager.stage = stage;
    }

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
