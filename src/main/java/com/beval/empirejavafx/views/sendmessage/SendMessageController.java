package com.beval.empirejavafx.views.sendmessage;

import com.beval.empirejavafx.alerts.SentSuccessfully;
import com.beval.empirejavafx.api.ApiClient;
import com.beval.empirejavafx.dto.response.ResponseDTO;
import com.beval.empirejavafx.exception.CustomException;
import com.beval.empirejavafx.manager.CastleStateManager;
import com.beval.empirejavafx.manager.StageManager;
import com.beval.empirejavafx.views.AbstractViewController;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SendMessageController implements AbstractViewController {
    @FXML
    private TextField title;
    @FXML
    private TextArea content;

    @Override
    public void updateView() throws IOException, InterruptedException {
        //not required
    }

    @FXML
    private void handleSend(MouseEvent mouseEvent) throws IOException, InterruptedException {
        String username = CastleStateManager.getSelectedEnemyCastle().getOwnerUsername();
        ResponseDTO<Object> responseDTO = ApiClient.sendMessage(username, title.getText(), content.getText());
        if (responseDTO.getStatus() != 200){
            throw new CustomException(responseDTO.getMessage());
        }

        StageManager.removePopUpWindow(StageManager.getPopupWindows().size()-1);
        SentSuccessfully sentSuccessfully = new SentSuccessfully();
        sentSuccessfully.show();
    }
}
