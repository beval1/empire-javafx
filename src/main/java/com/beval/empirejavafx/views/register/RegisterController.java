package com.beval.empirejavafx.views.register;

import com.beval.empirejavafx.api.ApiClient;
import com.beval.empirejavafx.dto.response.JwtResponseDTO;
import com.beval.empirejavafx.dto.response.ResponseDTO;
import com.beval.empirejavafx.views.game.LoadingScreen;
import com.beval.empirejavafx.views.login.LoginInForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class RegisterController {
    @FXML
    private Text errorMessage;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;


    @FXML
    private void handleRegisterButtonAction(ActionEvent event) throws IOException, InterruptedException {
        System.out.println(usernameField.getText());
        System.out.println(emailField.getText());
        System.out.println(passwordField.getText());

        ResponseDTO<Object> responseDTO = ApiClient.signUp(usernameField.getText(), emailField.getText(),
                passwordField.getText());
        System.out.println(responseDTO);
        if (responseDTO.getStatus() != 200){
            errorMessage.setText(responseDTO.getMessage());
        } else {
            ResponseDTO<JwtResponseDTO> signInResponse =
                    ApiClient.signIn(usernameField.getText(), passwordField.getText());

            if (signInResponse.getStatus() != 200) {
                LoadingScreen loadingScreen = new LoadingScreen();
                loadingScreen.show();
            }
        }
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws IOException {
        LoginInForm loginInForm = new LoginInForm();
        loginInForm.show();
    }
}