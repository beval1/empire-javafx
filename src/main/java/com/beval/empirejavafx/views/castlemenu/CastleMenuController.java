package com.beval.empirejavafx.views.castlemenu;

import com.beval.empirejavafx.dto.response.MapCastleDTO;
import com.beval.empirejavafx.manager.CastleStateManager;
import com.beval.empirejavafx.manager.StageManager;
import com.beval.empirejavafx.views.AbstractViewController;
import com.beval.empirejavafx.views.castlepreview.CastlePreview;
import com.beval.empirejavafx.views.sendmessage.SendMessage;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;

public class CastleMenuController implements AbstractViewController {
    @FXML
    private ImageView castleImage;

    @FXML
    private Text castleInfo;

    @Override
    public void updateView() throws IOException, InterruptedException {
        MapCastleDTO mapCastleDTO = CastleStateManager.getSelectedEnemyCastle();
        castleImage.setImage(new Image(mapCastleDTO.getCastleImage()));
        castleInfo.setText(String.format(
                "Player: %s%n" + "Level: %d%n" + "Mighty Points: %d%n" + "    Castle Name: %s",
                mapCastleDTO.getOwnerUsername(),
                mapCastleDTO.getOwnerLevel(),
                mapCastleDTO.getOwnerMightyPoints(),
                mapCastleDTO.getCastleName()
        ));
    }

    @FXML
    private void handleAttack(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
            System.out.println("Attack");
        }
    }

    @FXML
    private void handleMessage(MouseEvent mouseEvent) throws IOException, InterruptedException {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.show();
        }
    }

    @FXML
    private void handlePreview(MouseEvent mouseEvent) throws IOException, InterruptedException {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
            StageManager.removePopUpWindow(StageManager.getPopupWindows().size() - 1);
            CastlePreview castlePreview = new CastlePreview();
            castlePreview.show();
        }
    }
}
