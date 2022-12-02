package com.beval.empirejavafx.views.armymenu;

import com.beval.empirejavafx.api.ApiClient;
import com.beval.empirejavafx.dto.response.ArmyUnitDTO;
import com.beval.empirejavafx.dto.response.ResponseDTO;
import com.beval.empirejavafx.exception.CustomException;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ArmyMenuController {
    @FXML
    private StackPane root;

    public void updateView() throws IOException, InterruptedException {
        ResponseDTO<List<ArmyUnitDTO>> responseDTO = ApiClient.getArmyUnits();
        if (responseDTO.getStatus() != 200) {
            throw new CustomException(responseDTO.getMessage());
        }

        ListView<HBox> listView = new ListView<>();
        for (ArmyUnitDTO armyUnitDTO : responseDTO.getContent()) {
            HBox hBox = createArmyUnitCard(armyUnitDTO);
            listView.getItems().add(hBox);
            hBox.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    System.out.println("unit clicked");
                    buyDialogue(armyUnitDTO);
                }
            });
        }
        root.getChildren().add(listView);
    }

    private void buyDialogue(ArmyUnitDTO armyUnitDTO) {
        Stage stage = new Stage();
        StackPane stackPane = new StackPane();
        HBox coinsTextHbox = new HBox();
        Text coinsText = new Text("Current Price: 0");
        coinsTextHbox.setAlignment(Pos.CENTER);
        coinsTextHbox.getChildren().add(coinsText);

        HBox userInputHbox = new HBox();
        TextField userCountInput = new TextField();
        userCountInput.setMaxSize(40, 25);
        userInputHbox.getChildren().add(userCountInput);
        userInputHbox.setAlignment(Pos.CENTER);
        userCountInput.textProperty().addListener((observableValue, s, newValue) -> {
            int totalCost = 0;
            try {
                totalCost = armyUnitDTO.getCoinPrice() * Integer.parseInt(newValue);
            } catch (NumberFormatException ignored){}
            coinsText.setText("Current Price: " + totalCost);
        });

        HBox buttonHBox = new HBox();
        Button button = new Button();
        button.setText("BUY");
        button.setOnMouseClicked(mouseEvent -> {
            try {
                ResponseDTO<Object> responseDTO = ApiClient.buyArmyUnits(armyUnitDTO.getId());
                if (responseDTO.getStatus() != 200){
                    throw new CustomException(responseDTO.getMessage());
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.getChildren().add(button);

        VBox vBox = new VBox();
        vBox.getChildren().add(coinsTextHbox);
        vBox.getChildren().add(userInputHbox);
        vBox.getChildren().add(buttonHBox);
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);

        stackPane.getChildren().add(vBox);
        Scene scene = new Scene(stackPane, 200, 100);
        stage.setScene(scene);
        stage.setTitle("Buy");
        stage.show();
    }

    private HBox createArmyUnitCard(ArmyUnitDTO armyUnitDTO) {
        HBox hBox = new HBox();
        VBox infoVBox = new VBox();

        Text armyUnitName = new Text();
        armyUnitName.setText(armyUnitDTO.getName());
        armyUnitName.setStyle("-fx-font-size: 24");
        armyUnitName.setStyle("-fx-font-weight: bold");

        Text attributes = new Text();
        String infoString = String.format(
                "Attack melee: %d%n" +
                        "Attack ranged: %d%n" +
                        "Defense melee: %d%n" +
                        "Defense range: %d%n" +
                        "Food consumption: %d%n" +
                        "Coin price: %d",
                armyUnitDTO.getMeleeAttackPower(), armyUnitDTO.getRangedAttackPower(), armyUnitDTO.getMeleeDefensePower(),
                armyUnitDTO.getRangedDefensePower(), armyUnitDTO.getFoodConsumption(), armyUnitDTO.getCoinPrice()
        );
        attributes.setText(infoString);

        infoVBox.getChildren().add(armyUnitName);
        infoVBox.getChildren().add(attributes);

        ImageView imageView = new ImageView(new Image(armyUnitDTO.getUnitImage()));
        imageView.setFitWidth(85);
        imageView.setFitHeight(120);

        hBox.getChildren().add(imageView);
        hBox.getChildren().add(infoVBox);
        hBox.setSpacing(30);
        return hBox;
    }
}
