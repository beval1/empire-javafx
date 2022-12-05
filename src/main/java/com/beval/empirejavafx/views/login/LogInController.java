package com.beval.empirejavafx.views.login;

import com.beval.empirejavafx.api.ApiClient;
import com.beval.empirejavafx.config.AppConstants;
import com.beval.empirejavafx.dto.response.JwtResponseDTO;
import com.beval.empirejavafx.dto.response.ResponseDTO;
import com.beval.empirejavafx.exception.CustomException;
import com.beval.empirejavafx.utils.ExceptionUtils;
import com.beval.empirejavafx.views.AbstractViewController;
import com.beval.empirejavafx.views.game.Game;
import com.beval.empirejavafx.views.game.LoadingScreen;
import com.beval.empirejavafx.views.register.RegisterForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;

public class LogInController implements AbstractViewController {
    @FXML
    private Text errorMessage;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws IOException, InterruptedException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (AppConstants.DEBUG_MODE){
            username = AppConstants.DEBUG_MODE_USERNAME;
            password = AppConstants.DEBUG_MODE_PASSWORD;
        }
        System.out.println(username);
        System.out.println(password);

        ResponseDTO<JwtResponseDTO> responseDTO = null;
        try {
            responseDTO = ApiClient.signIn(username, password);
            System.out.println(responseDTO);
        } catch (Exception e){
            Throwable rootCause = ExceptionUtils.getCause(e);
            if (rootCause instanceof ClosedChannelException) {
                throw new CustomException("Can't connect to server!");
            } else {
                throw e;
            }
        }

        if (responseDTO.getStatus() != 200){
            errorMessage.setText(responseDTO.getMessage());
        } else {
            if (!AppConstants.DEBUG_MODE) {
                LoadingScreen loadingScreen = new LoadingScreen();
                loadingScreen.show();
            } else {
                Game game = new Game();
                game.show();
            }
        }
    }

    @FXML
    private void handleRegisterButtonAction(ActionEvent event) throws IOException {
        RegisterForm registerForm = new RegisterForm();
        registerForm.show();
    }

    @Override
    public void updateView() throws IOException, InterruptedException {
        //operation is not required
    }
}