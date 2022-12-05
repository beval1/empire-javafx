package com.beval.empirejavafx.views.messagemenu;

import com.beval.empirejavafx.dto.response.PlayerMessageDTO;
import com.beval.empirejavafx.manager.UserStateManager;
import com.beval.empirejavafx.views.AbstractViewController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.List;

public class MessageMenuController implements AbstractViewController {
//    @FXML
//    private ListView<HBox> listView;

    @FXML
    private StackPane root;

    @Override
    public void updateView() throws IOException, InterruptedException {
        TableView<PlayerMessageDTO> tableView = new TableView<>();
        List<PlayerMessageDTO> playerMessageDTOList = UserStateManager.getMessages();

        TableColumn titleCol = new TableColumn("Title");
        titleCol.setMinWidth(100);
        titleCol.setCellValueFactory(
                new PropertyValueFactory<PlayerMessageDTO, String>("title"));

        TableColumn senderCol = new TableColumn("Sender");
        senderCol.setMinWidth(100);
        senderCol.setCellValueFactory(
                new PropertyValueFactory<PlayerMessageDTO, String>("senderUsername"));

        TableColumn contentCol = new TableColumn("Content");
        contentCol.setMinWidth(200);
        contentCol.setCellValueFactory(
                new PropertyValueFactory<PlayerMessageDTO, String>("content"));

        tableView.setItems(FXCollections.observableList(playerMessageDTOList));
        tableView.getColumns().addAll(titleCol, senderCol, contentCol);

        tableView.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                PlayerMessageDTO playerMessageDTO = tableView.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(playerMessageDTO.getTitle());
                alert.setHeaderText(null);
                alert.setContentText(playerMessageDTO.getContent());
                alert.show();
            }
        });

        root.getChildren().add(tableView);
    }
}
