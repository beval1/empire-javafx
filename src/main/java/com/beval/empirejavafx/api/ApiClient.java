package com.beval.empirejavafx.api;

import com.beval.empirejavafx.config.AppConstants;
import com.beval.empirejavafx.dto.payload.SignInDTO;
import com.beval.empirejavafx.dto.payload.SignUpDTO;
import com.beval.empirejavafx.dto.response.ResponseDTO;
import com.beval.empirejavafx.utils.JsonMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {
    private ApiClient() {}
    public static final HttpClient client = HttpClient.newHttpClient();

    public static ResponseDTO signIn(String usernameOrEmail, String password) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(AppConstants.LOGIN_URL))
                .POST(HttpRequest.BodyPublishers.ofString(JsonMapper.getMapper()
                        .writeValueAsString(new SignInDTO(usernameOrEmail, password))))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        return JsonMapper.getMapper().readValue(httpResponse.body(), ResponseDTO.class);
    }

    public static ResponseDTO signUp(String username, String email, String password) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(AppConstants.REGISTER_URL))
                .POST(HttpRequest.BodyPublishers.ofString(JsonMapper.getMapper()
                        .writeValueAsString(new SignUpDTO(username, email, password, "", ""))))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        return JsonMapper.getMapper().readValue(httpResponse.body(), ResponseDTO.class);
    }
}
