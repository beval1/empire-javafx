package com.beval.empirejavafx.views.game;

import com.beval.empirejavafx.Main;
import com.beval.empirejavafx.manager.StageManager;
import com.beval.empirejavafx.views.AbstractView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Game implements AbstractView {
    @Override
    public void show() throws IOException {
        Scene scene = createScene();
        Stage stage = createStage(scene);
        stage.show();
    }

//    private HBox createHorizontalBottomBar(){
//        HBox hBox = new HBox();
//
//        return hBox;
//    }
//
//    private VBox createVerticalBar() {
//        VBox vbox = new VBox();
//
//        return vbox;
//    }
//
//    private GridPane createGrid(){
//        GridPane gridPane = new GridPane();
//        gridPane.setHgap(10);
//        gridPane.setHgap(20);
//        gridPane.setGridLinesVisible(true);
//        Text text = new Text("wassup");
//        gridPane.add(text, 0, 1);
//        return gridPane;
//    }
//
//    private BorderPane createBorderPane() {
//        BorderPane borderPane = new BorderPane();
////        borderPane.setCenter(createGrid());
//        borderPane.setBottom(createHorizontalBottomBar());
//        borderPane.setLeft(createVerticalBar());
//        return borderPane;
//    }

    private Scene createScene() throws IOException {
//        StackPane stackPane = new StackPane();
//        stackPane.getChildren().add(createGrid());
//        stackPane.getChildren().add(createBorderPane());
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("game/game.fxml")));
        Parent root = loader.load();
        GameController gameController = loader.getController();
        gameController.updateView();
        return new Scene(root);
    }

    private Stage createStage(Scene scene){
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Empire");
        stage.setMaximized(true);
        StageManager.setStage(stage);
        return stage;
    }
}
