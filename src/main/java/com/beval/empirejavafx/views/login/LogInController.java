package com.beval.empirejavafx.views.login;

import com.beval.empirejavafx.api.ApiClient;
import com.beval.empirejavafx.config.AppConstants;
import com.beval.empirejavafx.dto.response.JwtResponseDTO;
import com.beval.empirejavafx.dto.response.ResponseDTO;
import com.beval.empirejavafx.manager.UserStateManager;
import com.beval.empirejavafx.views.game.Game;
import com.beval.empirejavafx.views.game.LoadingScreen;
import com.beval.empirejavafx.views.register.RegisterForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class LogInController {
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

        ResponseDTO<JwtResponseDTO> responseDTO = ApiClient.signIn(username, password);
        System.out.println(responseDTO);
        if (responseDTO.getStatus() != 200){
            errorMessage.setText(responseDTO.getMessage());
        } else {
            UserStateManager.updateUserState();
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
}